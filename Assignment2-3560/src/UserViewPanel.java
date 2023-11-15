import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserViewPanel implements Observer{
    private final String[] positiveWords=new String[] {"Good","Great","Excellent","Fantastic","Amazing","Love"};
    private User user;
    private static int totalMessages=0;
    private static int totalPositiveWords=0;

    private JList<String> list;
    private JFrame frame;
    private JTextField newFollowID;
    private JButton followBtn;
    private JTextField newTweetMsg;
    private JButton tweetMsgBtn;
    private JLabel followingLabel;
    private JLabel newsFeedLabel;
    private JScrollPane currentFollowing;
    private JScrollPane tweetsNewsFeed;
    private JPanel rootPanel;
    private JLabel errorLabel;


    public UserViewPanel(User u){
        user=u;
        initialize();
        frame.pack();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("@"+user.toString());
        frame.setContentPane(rootPanel);
        frame.setPreferredSize(new Dimension(400,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600,400);

        ImageIcon icon = new ImageIcon("src/twitterLogo.png");
        frame.setIconImage(icon.getImage());

        //button listeners
        followBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                followUser();
            }
        });
        tweetMsgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tweetPost();
            }
        });

        update(user);
        updateFollowingList();
    }
    private void followUser(){
        User goingToFollow=null;
        String userId=newFollowID.getText();
        errorLabel.setText("");
        if(userId.equals(user.toString()) || userId.equals("")){
            errorLabel.setText("Error: Invalid UserID");
            return;
        }
        else{
            List<User> users = AdminControlPanel.getInstance().getUsers();
            List<User> following=user.getFollowing();
            for(int i=0;i<following.size();i++){
                if(following.get(i).toString().equals(userId)){
                    errorLabel.setText("Error: You are already following that user");
                    return;
                }
            }
            for(int i=0;i<users.size();i++){
                if(users.get(i).toString().equals(userId)){
                    goingToFollow=users.get(i);
                    break;
                }
            }
        }
        if(goingToFollow!=null){
            user.follow(goingToFollow);
            goingToFollow.addFollower(user);

            updateFollowingList();
        }
    }

    private void tweetPost(){
        String tweet=newTweetMsg.getText();
        errorLabel.setText("");
        if(tweet.equals(""))
            errorLabel.setText("Error: Empty tweet");
        else{
            user.addPost(user.toString()+": "+tweet);
            update(user);
            updateAllFollowers(tweet);
        }
        for (int i=0;i<positiveWords.length;i++){
            int intIndex = tweet.indexOf(positiveWords[i]);
            if(intIndex != - 1) {
                totalPositiveWords++;
            }
        }
        totalMessages++;
    }
    public int getTotalMessages(){return totalMessages;}
    public String getPositivePercentage(){
        if(totalMessages==0){
            return "0%";
        }
        return Double.toString(100*(double)totalPositiveWords/(double)totalMessages);
    }

    public static UserViewPanel getInstance(User user){
        user.setUserPanel(new UserViewPanel(user));
        return user.getUserPanel();
    }

    //All Observer Methods
    @Override
    public void update(User u) {
        List<String> feed=user.getFeed();
        list = new JList<String>(feed.toArray(new String[feed.size()]));
        tweetsNewsFeed.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
    }

    @Override
    public void updateAllFollowers(String tweet) {
        for (int i = 0; i < user.getFollowers().size(); ++i) {
            user.getFollowers().get(i).addPost(user.toString()+": "+tweet);
            user.getFollowers().get(i).getUserPanel().update(user);
        }
    }

    @Override
    public void updateFollowingList() {
        List<String> following=new ArrayList<String>();
        for(int i=0;i<user.getFollowing().size();i++)
        {
            following.add(user.getFollowing().get(i).toString());
        }
        list = new JList<String>(following.toArray(new String[following.size()]));
        currentFollowing.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
    }
}

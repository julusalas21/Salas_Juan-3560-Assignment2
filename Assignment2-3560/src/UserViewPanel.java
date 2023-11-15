import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserViewPanel implements Observer{
    private final String[] positiveWords=new String[] {"Good","Great","Excellent","Fantastic","Amazing","Love"};
    private User user;

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
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("@"+user.toString());
        frame.setContentPane(rootPanel);
        frame.setPreferredSize(new Dimension(600,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,500);

        ImageIcon icon = new ImageIcon("src/twitterLogo.png");
        frame.setIconImage(icon.getImage());
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

    @Override
    public void update(User u) {

    }

    @Override
    public void updateAllFollowers(String tweet) {

    }

    @Override
    public void updateFollowingList() {

    }
}

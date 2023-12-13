import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class IsolatedUser implements User {
    private UserViewPanel userPanel;
    private String UID=null;
    private List<User> FollowingList;
    private List<User> FollowersList;
    private List<String> posts;
    private long creationTime;
    private long lastUpdate;
    public IsolatedUser(String ID){
        UID=ID;
        FollowingList=new ArrayList<User>();
        FollowersList=new ArrayList<User>();
        posts=new ArrayList<String>();
        creationTime=System.currentTimeMillis();
        lastUpdate=creationTime;
    }
    public void timeStamp(){
        System.out.println("User created at: "+creationTime);
    }
    public long getLastUpdate(){
        return lastUpdate;
    }
    public void setLastUpdate(long time){
        lastUpdate=time;
    }

    @Override
    public String toString() {
        return UID;
    }

    @Override
    public String getUID() {
        return UID;
    }

    @Override
    public List<User> getFollowing() {
        return FollowingList;
    }

    @Override
    public List<User> getFollowers() {
        return FollowersList;
    }

    @Override
    public List<String> getFeed() {
        return posts;
    }

    @Override
    public void addFollower(User user) {
        FollowersList.add(user);
    }

    @Override
    public void follow(User user) {
        FollowingList.add(user);
    }

    @Override
    public void addPost(String tweet) {
        posts.add(tweet);
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    @Override
    public void setUserPanel(UserViewPanel userViewPanel) {
        userPanel=userViewPanel;
    }

    @Override
    public UserViewPanel getUserPanel() {
        return userPanel;
    }
}

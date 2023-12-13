import java.util.List;

//interface for the user and user group classes
//utilizes the composition design pattern
public interface User {
    public String toString();
    public String getUID();
    public List<User> getFollowing();
    public List<User> getFollowers();
    public List<String> getFeed();
    public void addFollower(User user);
    public void follow(User user);
    public void addPost(String tweet);
    public boolean isGroup();
    public void setUserPanel(UserViewPanel userViewPanel);
    public UserViewPanel getUserPanel();
    public void timeStamp();
    public long getLastUpdate();
    public void setLastUpdate(long time);
}

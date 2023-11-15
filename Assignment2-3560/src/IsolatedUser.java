import java.util.ArrayList;
import java.util.List;

public class IsolatedUser implements User {
    private String UID=null;
    private List<User> FollowingList;
    private List<User> FollowersList;
    private List<String> posts;
    public IsolatedUser(String ID){
        UID=ID;
        FollowingList=new ArrayList<User>();
        FollowersList=new ArrayList<User>();
        posts=new ArrayList<String>();
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
}

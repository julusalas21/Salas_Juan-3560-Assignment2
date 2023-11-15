import java.util.ArrayList;
import java.util.List;

public class CompositeUserGroup implements User{
    private String GID=null;
    private List<User> users;
    private List<User> groups;

    public CompositeUserGroup(String gid){
        GID=gid;
        users=new ArrayList<User>();
        groups=new ArrayList<User>();
    }
    public void addUser(User user){
        users.add(user);
    }
    public void addGroup(User group){
        groups.add(group);
    }
    public String toString(){
        return "Group: "+GID;
    }
    @Override
    public String getUID() {
        return GID;
    }

    @Override
    public List<User> getFollowing() {
        return null;
    }

    @Override
    public List<User> getFollowers() {
        return null;
    }

    @Override
    public List<String> getFeed() {
        return null;
    }

    @Override
    public void addFollower(User user) {

    }

    @Override
    public void follow(User user) {

    }

    @Override
    public void addPost(String tweet) {

    }

    @Override
    public boolean isGroup() {
        return true;
    }
}

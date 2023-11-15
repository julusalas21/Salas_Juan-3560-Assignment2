//observer pattern will be able to update any instance of userViewPanel
public interface Observer {
    public void update(User u);
    public void updateAllFollowers(String tweet);
    public void updateFollowingList();
}

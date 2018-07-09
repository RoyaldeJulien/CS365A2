import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User extends Group {
    private Set<User> followers;
    private Set<User> following;
    private List<String> messages;
    private UserGUI window;

    public User(String userName) {
        super(userName);
        followers = new HashSet<>();
        following = new HashSet<>();
        messages = new ArrayList<>();
        window = null;
    }

    public void setWindow(UserGUI W) {
        window = W;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void nowFollows(User otherUser) {
        this.following.add(otherUser);
        otherUser.followers.add(this);

    }

    public List<String> getMessages() {
        return messages;
    }

    public void sendMessage(String message) {
        String formattedMessage = getID() + ": " + message;
        messages.add(0, formattedMessage);
        for(User follower : followers) {
            follower.recieveMessage(formattedMessage);
        }
    }

    public void recieveMessage(String message) {
        messages.add(0, message);
        if (window != null) {
            window.update();
        }
    }

    public void accept(Visitor V) {
        V.atUser(this);
    }

    @Override
    public void addChild(Group G) {
        return;
    }

    @Override
    public void removeChild(Group G) {
        return;
    }

    @Override
    public boolean hasChild(Group G) {
        return false;
    }
}

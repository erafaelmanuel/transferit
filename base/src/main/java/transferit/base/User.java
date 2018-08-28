package transferit.base;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Signature signature;

    private boolean isOnline;

    private List<User> friends = new ArrayList<>();

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}

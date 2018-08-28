package transferit.api;

import transferit.base.User;

public interface Link {

    void open(User user);

    void close();
}

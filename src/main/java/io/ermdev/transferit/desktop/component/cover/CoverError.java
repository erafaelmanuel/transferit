package io.ermdev.transferit.desktop.component.cover;

import io.ermdev.transferit.desktop.component.Cover;

public class CoverError extends Cover {

    public CoverError() {
        setBackgroundColor("#ff7979");
        setImage("/image/cover/robot-error.png");
        setLabelText("You are disconnected");
        setLabelColor("#b71540");
    }
}

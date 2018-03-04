package io.ermdev.transferit.desktop.cover;

import io.ermdev.transferit.desktop.component.Cover;

public class CoverError extends Cover {

    public CoverError() {
        setBackgroundColor("#fab1a0");
        setImage("/image/cover/robot-error.png");
        setLabelText("You are disconnected");
        setLabelColor("#fff");
    }
}

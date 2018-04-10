package io.ermdev.transferit.desktop.cover;

import io.ermdev.transferit.desktop.component.Cover;

final public class CoverError extends Cover {

    public CoverError() {
        setBackgroundColor("#a4b0be");
        setImage("/image/cover/bear-sad.png");
        setLabelText("You are disconnected");
        setLabelColor("#2f3542");
    }
}

package io.ermdev.transferit.desktop.cover;

import io.ermdev.transferit.desktop.component.Cover;

@Deprecated
final public class Cover1 extends Cover {

    public Cover1() {
        setBackgroundColor("#e58e26");
        setImage("/image/cover/kangaroo.png");
        setLabelColor("#543e11");
        imageView.setFitHeight(95);
        imageView.setFitWidth(95);
    }
}

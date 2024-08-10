package cmpt213.asn4.memorygame.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageAssignment is the class to assign image for the image view in both cases:
 * card flipped and card not flipped
 *
 * @Author Irene Luu
 * @Version 01
 */
public class ImageAssignment {
    private final List<Image> images;

    public ImageAssignment() {
        images = new ArrayList<>();
        loadImages();
    }

    private void loadImages() {
        images.add(new Image("file:img/beige.jpg"));
        images.add(new Image("file:img/bear_brown.png"));
        images.add(new Image("file:img/bear_grey.png"));
        images.add(new Image("file:img/dog.png"));
        images.add(new Image("file:img/elephant.png"));
        images.add(new Image("file:img/lion.png"));
        images.add(new Image("file:img/rabbit.png"));
        images.add(new Image("file:img/tiger.png"));
        images.add(new Image("file:img/zebra.png"));
    }

    public void assignRevealed(ImageView imageView, int val) {
        imageView.setImage(images.get(val));
    }

    public void assignNotRevealed(ImageView imageView) {
        imageView.setImage(images.get(0));
    }
}

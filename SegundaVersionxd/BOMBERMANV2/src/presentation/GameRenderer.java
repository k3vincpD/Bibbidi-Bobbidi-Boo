package presentation;
import Logic.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GameRenderer {

    public void drawImage(Graphics g, GameObject gameObject) {
        BufferedImage sprite = gameObject.sprite;
        Point2D.Float position = gameObject.position;
        float rotation = gameObject.rotation;

        AffineTransform transform = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        transform.rotate(Math.toRadians(rotation), sprite.getWidth() / 2.0, sprite.getHeight() / 2.0);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite, transform, null);
    }

    public void drawCollider(Graphics g, GameObject gameObject) {
        Rectangle2D.Float collider = gameObject.collider;

        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(collider);
    }


    public void drawImageWithRotation(Graphics g, GameObject gameObject) {
        BufferedImage sprite = gameObject.sprite;
        Point2D.Float position = gameObject.position;
        float rotation = gameObject.rotation;

        AffineTransform transform = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        transform.rotate(Math.toRadians(rotation), sprite.getWidth() / 2.0, sprite.getHeight() / 2.0);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite, transform, null);
    }
}

package Logic;

import java.awt.geom.Point2D;

/**
 * Provides the speed for bomb moving from kick. Speed should be 6 to ensure the kicking logic is as smooth
 * as possible. Changing the value is dangerous and can introduce bugs to the kicking logic.
 */
public enum KickDirection {

    FromTop(new Point2D.Float(0, 6)),
    FromBottom(new Point2D.Float(0, -6)),
    FromLeft(new Point2D.Float(6, 0)),
    FromRight(new Point2D.Float(-6, 0)),
    Nothing(new Point2D.Float(0, 0));

    private Point2D.Float velocity;

    KickDirection(Point2D.Float velocity) {
        this.velocity = velocity;
    }

    public Point2D.Float getVelocity() {
        return this.velocity;
    }

}
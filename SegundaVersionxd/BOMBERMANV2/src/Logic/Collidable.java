package Logic;

interface Collidable {
    void onCollisionEnter(GameObject var1);

    default void handleCollision(Bomber collidingObj) {
    }

    default void handleCollision(Wall collidingObj) {
    }

    default void handleCollision(Explosion collidingObj) {
    }

    default void handleCollision(Enemy collidingObj) {
    }

    default void handleCollision(Bomb collidingObj) {
    }

    default void handleCollision(PowerUp collidingObj) {
    }
}


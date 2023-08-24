package Logic;

public interface Observable {

    default void update() {
    }

    default void onDestroy() {
    }
}

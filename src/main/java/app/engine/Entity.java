package app.engine;

public abstract class Entity {
    // Give coords for our entity
    public abstract int X();
    public abstract int Y();
    public abstract int Z();

    // Handle keyboard input events
    public void onKeyPress(int key) {

    }
}

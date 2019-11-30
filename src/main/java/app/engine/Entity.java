package app.engine;

public abstract class Entity {
    // Give coords for our entity
    public abstract int X();
    public abstract int Y();

    public void Tick() {

    }

    public void Render() {

    }

    // Handle keyboard input events
    public void onKeyDown(int key) {

    }

    public void onKeyUp(int key) {

    }
}

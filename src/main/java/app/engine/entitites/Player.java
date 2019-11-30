package app.engine.entitites;

import app.engine.Entity;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity {
    final int leftKey = 263;
    final int rightKey = 262;
    final int step = 10;
    final int size = 25;

    int x = 0;
    int y = 0;

    int minLeft = 0;
    int maxRight = 0;

    boolean leftDown = false;
    boolean rightDown = false;

    public Player(int width, int height) {
        this.x = width / 2; // Start in the center of the screen
        this.y = height - 10; // Ensure we are 10px of the top of the screen

        this.maxRight = width - size;
        this.minLeft = size;
    }

    @Override
    public int X() {
        return x;
    }

    @Override
    public int Y() {
        return y;
    }

    @Override
    public void onKeyDown(int key) {
        if (key == leftKey) {
            // Hold left
            this.leftDown = true;
        }

        if (key == rightKey) {
            // Hold right
            this.rightDown = true;
        }
    }

    @Override
    public void onKeyUp(int key) {
        if (key == leftKey) {
            this.leftDown = false;
        }

        if (key == rightKey) {
            this.rightDown = false;
        }
    }

    @Override
    public void Tick() {
        if (this.leftDown) {
            if (this.x-step > minLeft) {
                this.x = this.x-step;
            }
        }

        if (this.rightDown) {
            if (this.x+step < maxRight) {
                this.x = this.x+step;
            }
        }
    }

    @Override
    public void Render() {
        glPushMatrix();
        {
            glColor3f(0, 0 ,0);
            glTranslatef(this.X(), this.Y(), 0);

            glBegin(GL_TRIANGLES);
            {
                glVertex2d(size, 0);
                glVertex2d(0, -size);
                glVertex2d(-size, 0);
            }
            glEnd();
        }
        glPopMatrix();
    }
}

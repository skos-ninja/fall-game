package app.engine.entitites;

import static org.lwjgl.opengl.GL11.*;

public class Block extends Entity {
    private int size;

    private int x;
    private int y;

    public Block(int size, int x) {
        this.size = size;
        this.y = -size;
        this.x = x;
    }

    @Override
    public int X() {
        return this.x;
    }

    @Override
    public int Y() {
        return this.y;
    }

    public int Size() {
        return size;
    }

    @Override
    public void Tick() {
        this.y = this.y + 10;
    }

    @Override
    public void Render() {
        glPushMatrix();
        {
            glColor3f(0, 0 , 0);
            glTranslatef(this.X(), this.Y(), 0); // Shifts the position

            glBegin(GL_QUADS);
            {
                glVertex2f(0, 0);
                glVertex2f(0, this.size);
                glVertex2f(this.size, this.size);
                glVertex2f(this.size, 0);
            }
            glEnd();
        }
        glPopMatrix();
    }
}

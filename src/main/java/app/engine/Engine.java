package app.engine;

import app.engine.entitites.Block;
import app.engine.entitites.Player;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
    private int width;
    private int height;

    private Random random = new Random();
    private int ticksSinceLastSpawn = 0;

    private Player player;
    private List<Block> blocks;

    public Engine() {
        this.blocks = new ArrayList<>();
    }

    private void tick() {
        // Run our entity ticker here
        this.getEntities().forEach(Entity::Tick);

        // Detect old blocks
        List<Block> blocksForDelete = new ArrayList<>();
        this.blocks.forEach(block -> {
            int y = block.Y();
            int size = block.Size();

            if (y+size > this.height) {
                blocksForDelete.add(block);
            }
        });

        // Remove old blocks
        this.blocks.removeAll(blocksForDelete);

        // Wait 10 ticks before spawning the next set of blocks
        if (ticksSinceLastSpawn >= 20) {
            // Sometimes we should spawn multiple!
            int shouldSpawnMultiple = random.nextInt(100);
            int toSpawn = 1;
            if ((shouldSpawnMultiple/2)*2 == shouldSpawnMultiple) {
                toSpawn = 2;
            }

            for (int i = 1; i<=toSpawn; i++) {
                int x = random.nextInt(this.width - 75) + 50;
                this.blocks.add(new Block(25, x));
            }

            ticksSinceLastSpawn = 0;
        }

        ticksSinceLastSpawn++;
    }

    public boolean detectCollision() {
        int x = this.player.X();
        int y = this.player.Y();
        int size = this.player.Size();

        int playerLeft = x - size;
        int playerRight = x + size;

        boolean hasHit = false;
        for (Block block : this.blocks) {
            // Ignore the block as it hasn't reach the required height
            if (block.Y() <= y - size) {
                continue;
            }

            int blockX = block.X();
            int blockSize = block.Size();

            int blockLeft = blockX - blockSize;
            int blockRight = blockX + blockSize;

            if (blockLeft >= playerLeft && blockLeft <= playerRight) {
                hasHit = true;
                break;
            }

            if (blockRight <= playerRight && blockRight >= playerLeft) {
                hasHit = true;
                break;
            }
        }

        return hasHit;
    }

    private List<Entity> getEntities() {
        List<Entity> array = new ArrayList<>();
        array.add(this.player);
        array.addAll(this.blocks);

        return array;
    }

    public void init(int width, int height) {
        this.width = width;
        this.height = height;

        // We pass in the width and height to ensure we start
        // in the right position for the player
        this.player = new Player(width, height);
    }

    public void render(long window) {
        // Run a tick loop now
        // TODO: cap this ticker to now allow duplicate frames
        this.tick();


        // Set a standard drawing colour here
        GL11.glColor3f(0, 0, 0);

        // Call a render on each entity
        this.getEntities().forEach(Entity::Render);
    }

    public void inputHandler(int key, int action) {
        this.getEntities().forEach(entity -> {
            // Key down or hold
            if (action == 1 || action == 2) {
                entity.onKeyDown(key);
            } else if (action == 0) {
                entity.onKeyUp(key);
            }
        });
    }
}

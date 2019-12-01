package app.render;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {
    private int width;
    private int height;
    private String title;
    private boolean shouldRender = true;

    private long window;

    private DrawCallback renderer;
    private KeyboardCallback inputHandler;

    public Display(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init() {
        // Create our glfw error callback
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to start GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // Deny resizing the window. Simply makes rendering easier

        // Handle window creation
        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            // Get the window size
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the res of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Force center the window on the display
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);

        // Create our keyboard input handler
        glfwSetKeyCallback(window, (window, key, scancode, action, mode) -> {
            if (this.inputHandler != null) {
                this.inputHandler.invoke(key, scancode, action, mode);
            }
        });

        // Enable v-sync
        // This might cause issues later
        // We shall see
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        GL.createCapabilities();

        String glVersion = glGetString(GL_VERSION);
        System.out.println("GL Version: " + glVersion);
    }

    public void destroy() {
        glfwSetWindowShouldClose(window, true);
        glfwDestroyWindow(this.window);
    }

    public void setKeyboardHandler(KeyboardCallback cb) {
        this.inputHandler = cb;
    }

    public void setFrameRenderer(DrawCallback cb) {
        this.renderer = cb;
    }

    public void setShouldRender(boolean should) {
        this.shouldRender = should;
    }

    public boolean isRendering() {
        return (!glfwWindowShouldClose(window));
    }

    public void render() {
        // Set default white background
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.width, 0, this.height, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        // TODO: set a frame render limit
        while (this.isRendering() && this.shouldRender) {
            if (this.renderer != null) {
                this.renderer.invoke(window);
            }

            glfwSwapBuffers(window);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwPollEvents();
        }
    }


}
package io.github.lukasdoesdev.sandboxEngine.engine.io;

import io.github.lukasdoesdev.sandboxEngine.engine.logging.ConsoleOutput;
import io.github.lukasdoesdev.sandboxEngine.engine.maths.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;



public class Window
{
    private int width, height;           // Current width and height

    private int savedWidth, savedHeight; // Width and height get saved here when going fullscreen.

    private int savedPosX, savedPosY;    // Positions get saved here when going fullscreen.

    private GLFWVidMode videoMode;       // GLFW Video Mode

    private String title;                // Window title.

    private long window;                 // The window.

    private int frames;                  // FPS counter?

    private static long time;            // For the FPS counter?

    private Vector3f background = new
            Vector3f(0, 0, 0); // Background Color.

    private GLFWFramebufferSizeCallback
            sizeCallback;               // Size callback.

    private boolean isResized;          // When resizing, true otherwise, false.

    private boolean isFullscreen;       // If fullscreen, true otherwise, false.

    private int[]
            windowPosX = new int[1],    // Window X Position.
            windowPosY = new int[1];    // Window Y Position.


    /**
     * What does the name say?
     * @param width Window width
     * @param height Window height
     * @param title Window title
     */
    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }


    /**
     * Initialize GLFW, window, etc.
     */
    public void create()
    {

        if (!GLFW.glfwInit())
        {
            ConsoleOutput.printError("Failed initializing GLFW");
            return;
        } else {
            ConsoleOutput.printMessage("Initialized GLFW");
        }


        window = GLFW.glfwCreateWindow(width, height, title, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);



        if (window == 0)
        {
            ConsoleOutput.printError("Failed creating window");
            return;
        }



        videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = ((videoMode.width() - width) / 2);
        windowPosY[0] = ((videoMode.height() - height) / 2);
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallbacks();

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }

    private void createCallbacks()
    {
        sizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        GLFW.glfwSetFramebufferSizeCallback(window, sizeCallback);
    }

    public void update()
    {
        if (isResized)
        {
            isResized = false;
        }
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();

        frames++;
        if (System.currentTimeMillis() > time + 1000)
        {
            GLFW.glfwSetWindowTitle(window, (title + " | FPS: " + frames));
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() { GLFW.glfwSwapBuffers(window); }

    public boolean shouldClose() { return GLFW.glfwWindowShouldClose(window); }

    public void destroy()
    {
        Input.destroy();
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    /**
     * Set the background color of the window.
     * @param r Red
     * @param g Green
     * @param b Blue
     */
    public void setBackgroundColor(float r, float g, float b) { background.set(r, g, b); }

    public boolean isResized() { return isResized; }
    public void setResized(boolean resized) { isResized = resized; }

    public boolean isFullscreen() { return isFullscreen; }
    public void setFullscreen(boolean isFullscreen)
    {
        this.isFullscreen = isFullscreen;
        ConsoleOutput.printMessage("setFullscreen() got ran! (bool isFullscreen: " + this.isFullscreen + ")");
        if (isFullscreen)
        {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            savedPosX = windowPosX[0];
            savedPosY = windowPosY[0];
            savedWidth = width;
            savedHeight = height;
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, videoMode.width(), videoMode.height(), 0);
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, savedPosX, savedPosY, savedWidth, savedHeight, 0);
        }
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public String getTitle() { return title; }

    public long getWindow() { return window; }
}

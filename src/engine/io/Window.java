package engine.io;

import engine.logging.ConsoleOutput;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;



public class Window
{
    private int width, height;

    private int savedWidth, savedHeight; // so the user gets the same size as before fullscreen, same with position

    private int savedPosX, savedPosY;    //

    private GLFWVidMode videoMode;

    private String title;

    private long window;

    private int frames;

    private static long time;

    private Input input;

    private Vector3f background = new Vector3f(0, 0, 0);

    private GLFWFramebufferSizeCallback sizeCallback;

    private boolean isResized;

    private boolean isFullscreen;

    private int[] windowPosX = new int[1], windowPosY = new int[1];


    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }


    public void create()
    {

        if (!GLFW.glfwInit())
        {
            ConsoleOutput.printError("Failed initializing GLFW");
            return;
        } else {
            ConsoleOutput.printMessage("Initialized GLFW");
        }



        input = new Input();
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
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
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
        input.destory();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b) { background.set(r, g, b); }

    public boolean isResized() { return isResized; }
    public void setResized(boolean resized) { isResized = resized; }

    public boolean isFullscreen() { return isFullscreen; }

    public void setFullscreen(boolean isFullscreen)
    {
        this.isFullscreen = isFullscreen;
        GL11.glViewport(0, 0, width, height);
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

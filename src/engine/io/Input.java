package engine.io;

import org.lwjgl.glfw.*;

public class Input {
    private static final int NO_STATE = -1;

    private static boolean[] activeKeys = new boolean[GLFW.GLFW_KEY_LAST];
    private static int[] keyStates = new int[GLFW.GLFW_KEY_LAST];
    private static boolean[] activeButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static int[] buttonStates = new int[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static int lastButtonClicked = NO_STATE;
    private static long lastMouseNS = 0;
    private static long mouseDoubleClickPeriodNS = 1000_000_000 / 5; // 5th of a second, in nanoseconds
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    private static GLFWKeyCallback keyboard;
    private static GLFWCursorPosCallback mouseMove;
    private static GLFWMouseButtonCallback mouseButtons;
    private static GLFWScrollCallback mouseScroll;

    public static void init() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                activeKeys[key] = (action != GLFW.GLFW_RELEASE);
                keyStates[key] = action;
            }
        };
        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                activeButtons[button] = (action != GLFW.GLFW_RELEASE);
                buttonStates[button] = action;
            }
        };

        mouseScroll = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };

        update();
    }

    /**
     * Wipes the current list of key and mouse states.
     * <p>
     * Should be called right after querying key inputs for a frame.
     */
    public static void update()
    {
        resetKeyboard();
        resetMouse();
    }

    private static void resetKeyboard()
    {
        for(int i = 0; i < keyStates.length; i++)
        {
            keyStates[i] = NO_STATE;
        }
    }

    private static void resetMouse()
    {
        for(int i = 0; i < buttonStates.length; i++)
        {
            buttonStates[i] = NO_STATE;
        }

        long now = System.nanoTime();

        if(now - lastMouseNS > mouseDoubleClickPeriodNS)
        {
            lastMouseNS = 0;
        }
    }


    public static boolean isKeyDown(int key) { return activeKeys[key]; }

    public static boolean isKeyPressed(int key) { return keyStates[key] == GLFW.GLFW_PRESS; }

    public static boolean isKeyReleased(int key) { return keyStates[key] == GLFW.GLFW_RELEASE; }

    public static boolean isButtonDown(int button) { return activeButtons[button]; }


    public static boolean isButtonPressed(int button) { return buttonStates[button] == GLFW.GLFW_PRESS; }

    public static boolean isButtonReleased(int button)
    {
        boolean flag = buttonStates[button] == GLFW.GLFW_RELEASE;

        if(flag)
        {
            lastMouseNS = System.nanoTime();
            lastButtonClicked = button;
        }

        return flag;
    }

    public static boolean isButtonDoubleClicked(int button)
    {
        long last = lastMouseNS;
        int lastButton = lastButtonClicked;

        boolean flag = isButtonReleased(button);
        if(lastButton == button)
        {
            long now = System.nanoTime();
            if(flag && now - last < mouseDoubleClickPeriodNS)
            {
                lastMouseNS = 0;
                return true;
            }
        }
        return false;
    }


    public void destroy()
    {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }

    public static double getMouseX()
    {
        return mouseX;
    }

    public static double getMouseY()
    {
        return mouseY;
    }

    public static double getScrollX()
    {
        return scrollX;
    }

    public static double getScrollY()
    {
        return scrollY;
    }

    public GLFWKeyCallback getKeyboardCallback()
    {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback()
    {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback()
    {
        return mouseButtons;
    }

    public GLFWScrollCallback getMouseScrollCallback() { return mouseScroll; }



}

package engine.io;

import engine.logging.ConsoleOutput;
import org.lwjgl.glfw.*;

import java.io.Console;

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
    private static long window;

    public static void init(long window) {
        Input.window = window;
        GLFW.glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
            activeKeys[key] = action != GLFW.GLFW_RELEASE;
            keyStates[key] = action;
        });
        GLFW.glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        });
        GLFW.glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            activeButtons[button] = (action != GLFW.GLFW_RELEASE);
            buttonStates[button] = action;
        });

        GLFW.glfwSetScrollCallback(window, (windowHandle, xoffset, yoffset) -> {
            scrollX += xoffset;
            scrollY += yoffset;
        });

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


    public static void destroy()
    {
        ConsoleOutput.printMessage("input.destroy()");
        /*if (keyboard == null)
        {
            ConsoleOutput.printMessage("keyboard is null");
        }
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();*/
    }

    public static double getMouseX() { return mouseX; }

    public static double getMouseY() { return mouseY; }

    public static double getScrollX() { return scrollX; }

    public static double getScrollY() { return scrollY; }

}

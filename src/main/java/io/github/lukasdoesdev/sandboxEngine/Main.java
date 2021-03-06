package io.github.lukasdoesdev.sandboxEngine;

import io.github.lukasdoesdev.sandboxEngine.engine.graphics.*;
import io.github.lukasdoesdev.sandboxEngine.engine.io.Input;
import io.github.lukasdoesdev.sandboxEngine.engine.io.Window;
import io.github.lukasdoesdev.sandboxEngine.engine.logging.ConsoleOutput;
import io.github.lukasdoesdev.sandboxEngine.engine.maths.Vector2f;
import io.github.lukasdoesdev.sandboxEngine.engine.maths.Vector3f;
import io.github.lukasdoesdev.sandboxEngine.engine.utils.Color;
import org.fusesource.jansi.AnsiConsole;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class Main implements Runnable
{

    private Thread game;
    private static Window window;
    private Renderer renderer;
    private Shader shader;
    private Mesh mesh;
    private static final int WIDTH = 1280, HEIGHT = 720;

    private void start()
    {
        game = new Thread(this,"game");
        game.start();
    }


    /**
     * Initialize sandboxer.
     */
    public void init()
    {
        ConsoleOutput.printMessage("Initializing sandboxer!");
        window = new Window(WIDTH, HEIGHT, "sandboxer");
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(shader);

        int[] color = Color.hex2rgb("414141");

        window.setBackgroundColor((color[0] / 255.0f), (color[1] / 255.0f), (color[2] / 255.0f));
        window.create();




        initResources();


        mesh.create();
        shader.create();
        Input.init(window.getWindow());

        // TODO: Set up menus, etc.





        ConsoleOutput.printMessage("Finished initializing!");
    }


    /**
     * Run the game.
     * Also has the gameloop.
     */
    public void run()
    {
        AnsiConsole.systemInstall();
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
        {
            update();
            render();
            queryKeyInputs();
        }
        close();
        ConsoleOutput.printMessage("Exited Game.");
        AnsiConsole.systemUninstall();
    }



    private void update()
    {
        window.update();
        // if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
        // Update
    }


    private void render()
    {
        renderer.renderMesh(mesh);
        window.swapBuffers();
        // Render
    }

    /**
     * Initialized resources like Meshes etc.
     */
    public void initResources()
    {
        mesh = new Mesh(new Vertex[] {
                new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f))
        }, new int[] {
                0, 1, 3,
                3, 1, 2
        }, new Material("textures/blocks/beautiful.png"));
    }

    /**
     * Queries key inputs.
     */
    public void queryKeyInputs()
    {
        if (Input.isKeyPressed(GLFW.GLFW_KEY_F11)) { window.setFullscreen(!window.isFullscreen()); }

        Input.update();
    }

    private void close()
    {
        window.destroy(); // Destroys Input callbacks and closes window
        mesh.destroy();
        shader.destroy();

    }




    public static void main(String[] args) { new Main().start(); }

}

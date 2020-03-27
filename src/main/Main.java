package main;

import engine.graphics.*;
import engine.io.Input;
import engine.io.Window;
import engine.logging.ConsoleOutput;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.utils.Color;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class Main implements Runnable
{

    public Thread game;
    public static Window window;
    public Renderer renderer;
    public Shader shader;
    public static  final int WIDTH = 1280, HEIGHT = 720;

    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f))
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    }, new Material("/textures/blocks/beautiful.png"));



    public void start()
    {
        game = new Thread(this,"game");
        game.start();
    }



    public void init()
    {
        ConsoleOutput.printMessage("Initializing sandboxer!");
        window = new Window(WIDTH, HEIGHT, "sandboxer");
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(shader);

        int[] color = Color.hex2rgb("414141");
        String colorString = Arrays.toString(color);
        ConsoleOutput.printMessage(colorString);

        ConsoleOutput.printMessage(String.valueOf(color[0] / 255.0f) + ", " + String.valueOf(color[1] / 255.0f) + ", " + String.valueOf(color[2] / 255.0f));


        window.setBackgroundColor((color[0] / 255.0f), (color[1] / 255.0f), (color[2] / 255.0f));
        window.create();
        mesh.create();
        shader.create();
        Input.init(window.getWindow());

        // TODO: Set up menus, etc.



        ConsoleOutput.printMessage("Finished initializing!");
    }



    public void run()
    {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
        {
            update();
            render();
            queryKeyInputs();
        }
        close();
        ConsoleOutput.printMessage("Exited Game.");
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

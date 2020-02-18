package main;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable
{

    public Thread game;
    public static Window window;
    public Renderer renderer;
    public Shader shader;
    public static  final int WIDTH = 1280, HEIGHT = 720;

    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f))
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    });



    public void start()
    {
        game = new Thread(this,"game");
        game.start();
    }



    public void init()
    {
        System.out.println("Initializing sandboxer!");
        window = new Window(WIDTH, HEIGHT, "sandboxer");
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(shader);
        window.setBackgroundColor(0.7450980392156863f, 0.5568627450980392f, 0.0823529411764706f);
        window.create();
        mesh.create();
        shader.create();

        // TODO: Set up menus, etc.


        System.out.println("Finished initializing!");
    }



    public void run()
    {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
        {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) { window.setFullscreen(!window.isFullscreen()); }
        }
        System.out.println("Exited Game.");
        window.destroy(); // Destroys Input callbacks and closes window
    }



    private void update()
    {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
        // Update
    }


    private void render()
    {
        renderer.renderMesh(mesh);
        window.swapBuffers();
        // Render
    }


    public static void main(String[] args)
    {
        new Main().start();
    }

}

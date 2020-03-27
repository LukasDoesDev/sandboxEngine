package engine.graphics;

import engine.logging.ConsoleOutput;
import engine.graphics.Texture;
import static org.lwjgl.opengl.GL46.*;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Material
{
    private Texture texture;

    public Material(String path)
    {

        texture = new Texture(path);

    }
    public Texture getTexture() { return texture; }


    private Material mat = new Material("a");

    public void destroy()
    {
        texture.cleanUp();
    }



}

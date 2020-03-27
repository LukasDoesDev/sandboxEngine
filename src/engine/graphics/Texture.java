package engine.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int width, height;
    private int id;


    public Texture(String path)
    {
        System.out.println("helo");
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanUp() {
        unbind();
        glDeleteTextures(id);
    }

    public int getTextureID() { return id; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}

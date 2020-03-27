package engine.graphics;

import engine.logging.ConsoleOutput;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.system.MemoryStack;

public class Texture {

    private int width, height;
    private IntBuffer channel;
    private int id;


    public Texture(String fileName)
    {
        ByteBuffer buf;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(fileName, w, h, channels, 4);
            if(buf == null) {
                ConsoleOutput.printException(new Exception("Image file [" + fileName + "] not loaded: " + stbi_failure_reason()));
            }

            width = w.get();
            height = h.get();
        }

        this.id = createTexture(buf);

        stbi_image_free(buf);
    }

    private int createTexture(ByteBuffer buf) {
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);

        return textureID;
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

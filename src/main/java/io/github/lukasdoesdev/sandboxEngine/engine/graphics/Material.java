package io.github.lukasdoesdev.sandboxEngine.engine.graphics;

public class Material
{
    private Texture texture;

    public Material(String path)
    {

        texture = new Texture(path);

    }
    public Texture getTexture() { return texture; }


    public void destroy()
    {
        texture.cleanUp();
    }



}

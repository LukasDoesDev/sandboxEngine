package engine.maths;

public class Vector3f
{
    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX() { this.x = x; }
    public float getX() { return x; }

    public void setY() { this.y = y; }
    public float getY() { return y; }

    public void setZ() { this.z = z; }
    public float getZ() { return z; }
}

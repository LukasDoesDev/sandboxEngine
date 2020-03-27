package engine.utils;

public class Color {

    public static int[] hex2rgb(String hex)
    {
        int i = Integer.decode(hex);
        int[] rgb = new int[]{(i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF};
        return rgb;
    }
}

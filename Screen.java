import java.util.Random;

public class Screen {

    private int width, height;
    private Random random = new Random();

    public static int tileSize = 4;
    public static int mapSize  = 64;

    public int[] pixels;
    public int[] tiles = new int[mapSize * mapSize];

    public Screen(int width, int height) {
        this.width  = width;
        this.height = height;
        this.pixels = new int[width * height];

        for (int i = 0; i < mapSize * mapSize; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render() {
        int ms = (int)(Math.round(Math.log(mapSize) / Math.log(2)));
        for (int y = 0; y < height; y++) {
            if (y < 0 || y >= height) continue;
            for (int x = 0; x < width; x++) {
                if (x < 0 || x >= width) continue;
                int tileIndex = (x >> tileSize) + ((y >> tileSize) << ms);
                pixels[x + y * width] = tiles[tileIndex];
            }
        }
    }

}
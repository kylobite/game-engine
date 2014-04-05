import java.util.Random;

public class Screen {

    private int width, height;
    private Random random = new Random();

    public static int TILE_SIZE     = 16;
    public static int MAP_SIZE      = 8;
    public static int MAP_SIZE_MASK = MAP_SIZE - 1;

    public int[] pixels;
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

    public Screen(int width, int height) {
        this.width  = width;
        this.height = height;
        pixels      = new int[width * height];

        for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render(int xOffset, int yOffset) {
        int ts = (int)(Math.round(Math.log(TILE_SIZE) / Math.log(2)));
        int ms = (int)(Math.round(Math.log(MAP_SIZE) / Math.log(2)));
        int mm = MAP_SIZE_MASK;

        for (int y = 0; y < height; y++) {
            int yy = y + yOffset;
            // if (yy < 0 || yy >= height) continue;
            for (int x = 0; x < width; x++) {
                int xx = x + xOffset;
                // if (xx < 0 || xx >= width) continue;
                int tileIndex = ((xx >> ts) & mm) + (((yy >> ts) & mm) << ms);

                pixels[x + y * width] = tiles[tileIndex];
            }
        }
    }

}
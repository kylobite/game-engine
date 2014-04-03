import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    
    public static int width  = 300;
    public static int height = width / 16 * 9;
    public static int scale  = 3;

    private Thread thread;
    private JFrame frame;
    private Screen screen;
    private Keyboard key;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private boolean running = false;

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        screen = new Screen(width, height);
        frame  = new JFrame();
        key    = new Keyboard();

        addKeyListener(key);
    }

    private synchronized void start() {
        if (!running) running = true;
        thread = new Thread(this, "Display");
        thread.start();
        System.out.println("Starting...");
    }

    public synchronized void stop() {
        if (running) running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime   = System.nanoTime();
        long timer      = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta    = 0;
        int frames      = 0;
        int updates     = 0;

        while (running) {
            long now = System.nanoTime();
            delta   += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer  += 1000;
                frame.setTitle("KGEv0.1 :: " + updates + " ups, " + frames + " fps");
                frames  = 0;
                updates = 0;
            }
        }
        stop();
    }

    int xTemp = 0, yTemp = 0;

    public void update() {
        key.update();
        
        xTemp++;
        yTemp++;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        screen.render(xTemp, yTemp);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

}
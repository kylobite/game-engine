import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    
    public static int width  = 300;
    public static int height = width / 16 * 9;
    public static int scale  = 3;

    private Thread thread;
    private JFrame frame;

    private boolean running = false;

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        frame = new JFrame();
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
        while (running) {
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle("KGEv0.1 :: Kylobite's Game Engine Version 0.1");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

}
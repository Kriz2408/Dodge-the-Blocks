GamePanel - Calisang



import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private final List<GameObject> objects = new ArrayList<>();
    private final Random random = new Random();
    private final Timer timer;

    private Player player;
    private int score;
    private int spawnCounter;
    private boolean gameOver;
    private boolean started;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(18, 25, 42));
        setFocusable(true);

        setupKeyBindings();
        resetGame();

        timer = new Timer(16, e -> {
            if (started && !gameOver) {
                updateGame();
            }
            repaint();
        });

        timer.start();
    }

    private void setupKeyBindings() {
        bindKey("LEFT", "leftPressed",
            () -> player.setMovingLeft(true));

        bindKey("released LEFT", "leftReleased",
            () -> player.setMovingLeft(false));

        bindKey("RIGHT", "rightPressed",
            () -> player.setMovingRight(true));

        bindKey("released RIGHT", "rightReleased",
            () -> player.setMovingRight(false));

        bindKey("A", "aPressed",
            () -> player.setMovingLeft(true));

        bindKey("released A", "aReleased",
            () -> player.setMovingLeft(false));

        bindKey("D", "dPressed",
            () -> player.setMovingRight(true));

        bindKey("released D", "dReleased",
            () -> player.setMovingRight(false));

        bindKey("SPACE", "startOrRestart", () -> {
            if (!started || gameOver) {
                resetGame();
                started = true;
            }
        });
    }

    private void bindKey(String key, String name, Runnable action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(key), name);

        getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    action.run();
                } catch (RuntimeException ex) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

    private void resetGame() {
        objects.clear();

        player = new Player(WIDTH / 2 - 21, HEIGHT - 75);
        objects.add(player);

        score = 0;
        spawnCounter = 0;
        gameOver = false;
        started = false;
    }

    private void updateGame() {
        for (GameObject object : objects) {
            object.update();
        }

        spawnCounter++;

        int spawnRate = Math.max(18, 48 - score / 4);

        if (spawnCounter >= spawnRate) {
            spawnBlock();
            spawnCounter = 0;
        }

        Iterator<GameObject> iterator = objects.iterator();

        while (iterator.hasNext()) {
            GameObject object = iterator.next();

            if (object instanceof FallingBlock) {
                if (object.getBounds().intersects(player.getBounds())) {
                    gameOver = true;
                    player.setMovingLeft(false);
                    player.setMovingRight(false);
                    return;
                }

                if (object.getY() > HEIGHT) {
                    iterator.remove();
                    score++;
                }
            }
        }
    }

    private void spawnBlock() {
        int size = 24 + random.nextInt(24);
        int x = random.nextInt(WIDTH - size);
        int speed = 3 + Math.min(6, score / 8) + random.nextInt(2);

        Color[] colors = {
            new Color(255, 82, 82),
            new Color(255, 166, 77),
            new Color(255, 213, 79)
        };

        objects.add(new FallingBlock(
            x, -size, size, speed,
            colors[random.nextInt(colors.length)]
        ));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setColor(new Color(55, 68, 92));

        for (int i = 0; i < 35; i++) {
            int x = (i * 97 + 23) % WIDTH;
            int y = (i * 61 + 17) % HEIGHT;
            g.fillOval(x, y, 2, 2);
        }

        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.setColor(Color.WHITE);
        g.drawString("DODGE THE BLOCKS", 20, 32);

        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.setColor(new Color(120, 220, 255));
        g.drawString("Score: " + score, WIDTH - 110, 32);

        for (GameObject object : objects) {
            object.draw(g);
        }

        if (!started && !gameOver) {
            drawOverlay(
                g,
                "DODGE THE BLOCKS",
                "Press SPACE to start",
                "Move with LEFT / RIGHT arrows or A / D"
            );
        } else if (gameOver) {
            drawOverlay(
                g,
                "GAME OVER",
                "Final score: " + score,
                "Press SPACE to play again"
            );
        }

        g.dispose();
    }

    private void drawOverlay(
        Graphics2D g, String title, String line1, String line2
    ) {
        g.setColor(new Color(5, 10, 22, 205));
        g.fillRoundRect(85, 145, WIDTH - 170, 190, 22, 22);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));

        FontMetrics titleMetrics = g.getFontMetrics();

        g.drawString(
            title,
            (WIDTH - titleMetrics.stringWidth(title)) / 2,
            198
        );

        g.setFont(new Font("SansSerif", Font.PLAIN, 18));

        FontMetrics metrics = g.getFontMetrics();

        g.setColor(new Color(160, 225, 255));

        g.drawString(
            line1,
            (WIDTH - metrics.stringWidth(line1)) / 2,
            242
        );

        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        metrics = g.getFontMetrics();

        g.setColor(new Color(220, 225, 235));

        g.drawString(
            line2,
            (WIDTH - metrics.stringWidth(line2)) / 2,
            278
        );
    }
}

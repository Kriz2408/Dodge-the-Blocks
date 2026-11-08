import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends GameObject {
    private static final int SPEED = 7;
    private boolean movingLeft;
    private boolean movingRight;

    public Player(int x, int y) {
        super(x, y, 42, 42);
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    @Override
    public void update() {
        if (movingLeft) {
            setX(Math.max(0, getX() - SPEED));
        }

        if (movingRight) {
            setX(Math.min(
                GamePanel.WIDTH - getWidth(),
                getX() + SPEED
            ));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(40, 145, 255));
        g.fillRoundRect(
            (int) getX(), (int) getY(),
            getWidth(), getHeight(), 12, 12
        );

        g.setColor(Color.WHITE);
        g.fillOval((int) getX() + 9, (int) getY() + 10, 7, 7);
        g.fillOval((int) getX() + 26, (int) getY() + 10, 7, 7);

        g.setColor(new Color(10, 55, 115));
        g.drawArc((int) getX() + 11, (int) getY() + 19, 20, 12, 200, 140);
    }
}

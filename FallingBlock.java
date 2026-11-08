import java.awt.Color;
import java.awt.Graphics2D;

public class FallingBlock extends GameObject {
    private final int speed;
    private final Color color;

    public FallingBlock(int x, int y, int size, int speed, Color color) {
        super(x, y, size, size);
        this.speed = speed;
        this.color = color;
    }

    @Override
    public void update() {
        setY(getY() + speed);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRoundRect(
            (int) getX(), (int) getY(),
            getWidth(), getHeight(), 8, 8
        );

        g.setColor(color.brighter());
        g.drawRoundRect(
            (int) getX() + 4, (int) getY() + 4,
            getWidth() - 8, getHeight() - 8, 5, 5
        );
    }
}

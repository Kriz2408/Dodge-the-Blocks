/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.kriz.oop.dodgegame;

/**
 *
 * @author krizcalisang
 */


abstract class GameObject {
    private double x;
    private double y;
    private int width;
    private int height;

    public GameObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    protected void setX(double x) { this.x = x; }
    protected void setY(double y) { this.y = y; }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);
}

class Player extends GameObject {
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

class FallingBlock extends GameObject {
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

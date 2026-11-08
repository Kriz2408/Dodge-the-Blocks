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


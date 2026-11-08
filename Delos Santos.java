 polymorphism delos santos
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

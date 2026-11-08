 private void setupKeyBindings() {
        bindKey("LEFT", "leftPressed",
            () -> player.setMovingLeft(true));
   
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

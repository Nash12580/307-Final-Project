package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/** @author Grant Robinson **/

class UniversePanel extends JPanel {
    private List<Star> stars;
    private static final int NUM_STARS = 10000;
    private static final int SUN_SIZE = 100;
    private static final double RAY_LENGTH = 1.4;
    private static final int RAY_COUNT = 30;
    private double angle;
    private int ray_x1;
    private int ray_y1;
    private int ray_x2;
    private int ray_y2;

    public UniversePanel () {
        setPreferredSize(new Dimension(1400, 1000));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        if(stars == null){
            stars = Star.createStar(NUM_STARS, getWidth(), getHeight());
        }
        for (Star star : stars) {
            star.draw(g2d);
        }
        drawSun(g);
        for (Planet planet : Officer.getPlanetStack()) {
            planet.updatePosition(getWidth() / 2, getHeight() / 2, planet.getSpeed());
            planet.draw(g2d);
        }
    }

    private void drawSun(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(getWidth() / 2 - SUN_SIZE / 2, getHeight() / 2 - SUN_SIZE / 2, SUN_SIZE, SUN_SIZE);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < RAY_COUNT; i++) {
            angle = 2 * Math.PI / RAY_COUNT * i;
            ray_x1 = (int) (getWidth() / 2 + SUN_SIZE / 2 * Math.cos(angle));
            ray_y1 = (int) (getHeight() / 2 + SUN_SIZE / 2 * Math.sin(angle));
            ray_x2 = (int) (getWidth() / 2 + SUN_SIZE / 2 * RAY_LENGTH * Math.cos(angle));
            ray_y2 = (int) (getHeight() / 2 + SUN_SIZE / 2 * RAY_LENGTH * Math.sin(angle));
            g.drawLine(ray_x1, ray_y1, ray_x2, ray_y2);
        }
    }
}

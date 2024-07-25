package org.example;

import java.awt.*;

public class Meteorite {
    private int x;
    private int y;
    private int size;
    private Color color;
    private double angle;
    private double distanceFromPlanet;

    public Meteorite(int centerX, int centerY, int size, double distanceFromPlanet, double angle) {
        this.size = size;
        this.distanceFromPlanet = distanceFromPlanet;
        this.angle = angle;
        this.color = Color.GRAY;
        this.x = (int) (centerX + distanceFromPlanet * Math.cos(Math.toRadians(angle)));
        this.y = (int) (centerY + distanceFromPlanet * Math.sin(Math.toRadians(angle)));
    }
    public void updatePosition(int centerX, int centerY, double speed) {
        angle += speed;
        if (angle >= 360) {
            angle -= 360;
        }
        x = (int) (centerX + distanceFromPlanet * Math.cos(Math.toRadians(angle)));
        y = (int) (centerY + distanceFromPlanet * Math.sin(Math.toRadians(angle)));
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(x, y, size, size);
    }
}

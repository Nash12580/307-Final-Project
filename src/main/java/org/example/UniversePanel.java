package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class UniversePanel extends JPanel {
    private List<Planet> planets;
    private List<Star> stars = new ArrayList<>();
    private static final int NUM_STARS = 300;

    public UniversePanel (List<Planet> planetsIn) {
        planets = planetsIn;
        createStars();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;
        for (Planet planet : planets) {
            planet.draw(g2d);
        }
        for (Star star : stars) {
            star.draw(g2d);
        }
    }

    private void createStars() {
        for (int i = 0; i < NUM_STARS; i++) {
            stars.add(new Star());
        }
    }
}
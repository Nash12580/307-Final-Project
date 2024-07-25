package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**@author Nashali Vicente Lopez**/
public class ZoomPanel extends JPanel {
    private Planet planet;
    private static final int ZOOM_EFFECT = 5;
    private static final int NUM_STARS = 10000;
    private List<Star> stars = new ArrayList<>();



    public ZoomPanel(Planet planet){
        this.planet = planet;
        createStars();
        JButton backButton =  new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ZoomPanel.this);
                frame.getContentPane().removeAll();
                UniversePanel universePanel = new UniversePanel();
                frame.add(universePanel);
                universePanel.addMouseListener(new MouseNanny());
                universePanel.addMouseMotionListener(new MouseNanny());
                Officer.setUniversePanel(universePanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        setLayout(new BorderLayout());
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        for (Star star : stars) {
            star.draw(g2d);
        }
        drawZoomedPlanet(g);

    }

    private void createStars() {
        for (int i = 0; i < NUM_STARS; i++) {
            stars.add(new Star());
        }
    }
    private void drawZoomedPlanet(Graphics g){
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = planet.getRadius() * ZOOM_EFFECT;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawPlanetBase(g2d, centerX, centerY, radius);
        drawPlanetHighlights(g2d, centerX, centerY, radius);
        drawPlanetName(g2d, centerX, centerY, radius);


    }

    private void drawPlanetBase(Graphics2D g2d, int centerX, int centerY, int radius){
        Point2D center = new Point2D.Float(centerX, centerY);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {planet.getMainColor(), planet.getSecondaryColor()};
        RadialGradientPaint radialGradient = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(radialGradient);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    private void drawPlanetHighlights(Graphics2D g2d, int centerX, int centerY, int radius){
        Point2D center = new Point2D.Float(centerX, centerY);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {planet.getMainColor(), planet.getSecondaryColor()};
        RadialGradientPaint radialGradient = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(radialGradient);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    private void drawPlanetName(Graphics2D g2d, int centerX, int centerY, int radius){
        g2d.setColor(Color.WHITE);
        g2d.drawString(planet.getName(), centerX - radius/2, centerY - radius/2);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(800, 600);
    }
}


package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**@author Nashali Vicente Lopez**/
public class ZoomPanel extends JPanel {
    private final Planet planet;
    public static final int ZOOM_EFFECT = 5;
    private static final int NUM_STARS = 10000;
    private List<Star> stars;
    private List<Meteorite> meteorites = new ArrayList<>();
    private static final int METEORITE_SIZE = 10;
    private static final double METEORITE_DISTANCE = 300;
    private static final double METEORITE_SPEED = 0.5;


    public ZoomPanel(Planet planet){
        this.planet = planet;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        int numMethods = planet.getMethods().size();
        for (int i = 0; i < numMethods; i++) {
            double angle = (360.0 / numMethods) * i;
            meteorites.add(new Meteorite(getWidth() / 2, getHeight() / 2, METEORITE_SIZE, METEORITE_DISTANCE, angle));
        }

        MetricsPanel metricsPanel = new MetricsPanel();
        add(metricsPanel, BorderLayout.WEST);
        JButton backButton =  new JButton("Back");
        backButton.addActionListener(e-> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ZoomPanel.this);
            frame.getContentPane().removeAll();
            UniversePanel universePanel = new UniversePanel();
            frame.add(universePanel);
            universePanel.addMouseListener(new MouseNanny());
            universePanel.addMouseMotionListener(new MouseNanny());
            Officer.setUniversePanel(universePanel);
            frame.revalidate();
            frame.repaint();
        });
        add(backButton, BorderLayout.SOUTH);
        metricsPanel.updateMetrics(planet);
        addMouseMotionListener(new MouseNanny());
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(stars == null){
            stars = Star.createStar(NUM_STARS, getWidth(), getHeight());
        }
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        for (Star star : stars) {
            star.draw(g2d);
        }
        drawZoomedPlanet(g);
        for (Meteorite meteorite : meteorites) {
            meteorite.updatePosition(getWidth() / 2, getHeight() / 2, METEORITE_SPEED);
            meteorite.draw(g2d);
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
        drawPlanetName(g2d, centerX, centerY);
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
    private void drawPlanetName(Graphics2D g2d, int centerX, int centerY){
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(planet.getName());
        int textHeight = fm.getHeight();
        int textX = centerX - textWidth/2;
        int textY = centerY + textHeight/4 ;
        g2d.drawString(planet.getName(), textX, textY);
    }
}
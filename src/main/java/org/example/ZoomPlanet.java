package org.example;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
/** @author Nashali Vicente Lopez**/
public class ZoomPlanet {
    private static final int ZOOM_EFFECT = 5;
    private final Planet planet;
    private final List<Star> stars;
    private final List<Meteorite> meteorites;
    public ZoomPlanet(Planet planet, List<Star> stars, List<Meteorite> meteorites) {
        this.planet = planet;
        this.stars = stars;
        this.meteorites = meteorites;
    }
    public void draw(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        drawStars(g2d);
        drawZoomedPlanet(g, width, height);
        drawMeteorites(g2d, width, height);
    }
    private void drawStars(Graphics2D g2d) {
        for (Star star : stars) {
            star.draw(g2d);
        }
    }
    private void drawZoomedPlanet(Graphics g, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = planet.getRadius() * ZOOM_EFFECT;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawPlanetBase(g2d, centerX, centerY, radius);
        drawPlanetHighlights(g2d, centerX, centerY, radius);
        drawPlanetName(g2d, centerX, centerY);
    }
    private void drawPlanetBase(Graphics2D g2d, int centerX, int centerY, int radius) {
        Point2D center = new Point2D.Float(centerX, centerY);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {planet.getMainColor(), planet.getSecondaryColor()};
        RadialGradientPaint radialGradient = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(radialGradient);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }
    private void drawPlanetHighlights(Graphics2D g2d, int centerX, int centerY, int radius) {
        Point2D center = new Point2D.Float(centerX, centerY);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {planet.getMainColor(), planet.getSecondaryColor()};
        RadialGradientPaint radialGradient = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(radialGradient);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }
    private void drawPlanetName(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(planet.getName());
        int textHeight = fm.getHeight();
        int textX = centerX - textWidth / 2;
        int textY = centerY + textHeight / 4;
        g2d.drawString(planet.getName(), textX, textY);
    }
    private void drawMeteorites(Graphics2D g2d, int width, int height) {
        for (Meteorite meteorite : meteorites) {
            meteorite.updatePosition(width / 2, height / 2, ZoomPanel.METEORITE_SPEED);
            meteorite.draw(g2d);
        }
    }
}
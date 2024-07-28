package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**@author Nashali Vicente Lopez**/
public class ZoomPanel extends JPanel {
    private final Planet planet;
    public static final int NUM_STARS = 10000, METEORITE_SIZE = 10;
    private List<Star> stars;
    private final List<Meteorite> meteorites = new ArrayList<>();
    public static final double METEORITE_DISTANCE = 300, METEORITE_SPEED = 0.5;
    private ZoomPlanet zoomPlanet;
    public ZoomPanel(Planet planet){
        this.planet = planet;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout());
        int numMethods = planet.getMethods().size();
        int i = 0;

        for (Map.Entry<String, String> method : planet.getMethods().entrySet()) {
            double angle = (360.0 / numMethods) * i++;
            meteorites.add(new Meteorite(getWidth() / 2, getHeight() / 2, METEORITE_SIZE, METEORITE_DISTANCE, angle, method.getKey(), method.getValue()));
        }
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
        addMouseMotionListener(new MouseNanny());
        addMouseMotionListener(new MouseNanny(meteorites));
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(stars == null){
            stars = Star.createStar(NUM_STARS, getWidth(), getHeight());
        }
        if(zoomPlanet == null){
            zoomPlanet = new ZoomPlanet(planet, stars, meteorites);
        }
        zoomPlanet.draw(g, getWidth(), getHeight());
    }
}
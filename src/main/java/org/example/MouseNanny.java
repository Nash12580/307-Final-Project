package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

/** @author Nashali Vicente Lopez **/
/** @author Grant Robinson **/

public class MouseNanny implements MouseListener, MouseMotionListener {
    private Planet selectedPlanet = null;

    private Planet getPlanetAt(int x, int y){
        Stack<Planet> planets = Officer.getPlanetStack();
        for(int i = planets.size() - 1; i >= 0; i--){
            Planet planet = planets.get(i);
            if (planetContains(planet, x, y)){
                return planet;
            }
        }
        return null;
    }

    private boolean planetContains(Planet planet, int x, int y){
        return x >= planet.getX() && x <= planet.getX() + planet.getRadius() * 2 &&
                y >= planet.getY() && y <= planet.getY() + planet.getRadius() * 2;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            selectedPlanet = getPlanetAt(e.getX(), e.getY());
            if (selectedPlanet != null) {
                selectedPlanet.setSelected(!selectedPlanet.isSelected());
                for (Planet planet : Officer.getPlanetStack()) {
                    if (planet.isSelected() && planet != selectedPlanet) {
                        planet.setSelected(false);
                    }
                }
                selectedPlanet.setSelected(!selectedPlanet.isSelected());
                showClassMetrics(selectedPlanet);
                switchToZoomPanel(selectedPlanet, e);
                Officer.tellYourBoss();
            }
        }
        if (selectedPlanet == null) { System.out.println("selected: null"); } else {
            System.out.println("selected: " + selectedPlanet.getName()); }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}

    private void showClassMetrics(Planet planet){
        try{
            String metrics = Officer.getMetrics(planet.getFilepath());
            StringBuilder sb = new StringBuilder(metrics);

            sb.append("\nFields:\n");
            List<String> fields = planet.getFields();
            if(fields != null){
                for(String field: fields){
                    sb.append(field).append("\n");
                }
            }else{
                sb.append("No fields available\n");
            }

            sb.append("\nMethods:\n");
            List<String> methods = planet.getMethods();
            if(methods != null){
                for(String method: methods){
                    sb.append(method).append("\n");
                }
            }else{
                sb.append("No methods available.\n");
            }


            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JFrame metricsFrame = new JFrame("Class Metrics");
            metricsFrame.setSize(400, 300);
            metricsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            metricsFrame.add(scrollPane);
            metricsFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void switchToZoomPanel(Planet planet, MouseEvent e){
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.getContentPane().removeAll();
            frame.add(new ZoomPanel(planet));
            frame.revalidate();
            frame.repaint();
    }
}

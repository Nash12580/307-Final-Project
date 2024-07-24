package org.example;

import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame {

    public static void main(String[] args) throws IOException {
        String testFilepath = "C:\\Users\\Nasha\\JavaProjects\\CSC 307\\PaintApp";
        int panelWidth = 1400;
        int panelHeight = 1000;

        CodeParser.initialize(panelWidth, panelHeight);
        CodeParser.parseJavaDirectory(testFilepath);

        Main universe = new Main();
        universe.setSize(panelWidth, panelHeight);
        universe.setTitle("Universe");
        universe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        universe.setVisible(true);
    }

    public Main() {
        // Setup the menu bar using FileMenu class
        setJMenuBar(new FileMenu());

        UniversePanel universePanel = new UniversePanel();
        MouseNanny mouseNanny = new MouseNanny();
        universePanel.addMouseListener(mouseNanny);
        universePanel.addMouseMotionListener(mouseNanny);
        add(universePanel);
        Officer.setUniversePanel(universePanel);
    }
}

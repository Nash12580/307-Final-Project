package org.example;

import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame {

    public static void main(String[] args) throws IOException {
        String testFilepath = "C:\\Users\\Nasha\\JavaProjects\\CSC 307\\PaintApp";
        CodeParser.parseJavaDirectory(testFilepath);
        Main universe = new Main();
        universe.setSize(1400, 1000);
        universe.setTitle("Universe");
        universe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        universe.setVisible(true);
    }

    public Main() {
        UniversePanel universePanel = new UniversePanel();
        MouseNanny mouseNanny = new MouseNanny();
        universePanel.addMouseListener(mouseNanny);
        universePanel.addMouseMotionListener(mouseNanny);
        add(universePanel);
        Officer.setUniversePanel(universePanel);
    }
}
package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class FileMenu extends JMenuBar {

    public FileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = fileChooser.getSelectedFile().getPath();
                    try {
                        CodeParser.parseJavaDirectory(selectedPath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        fileMenu.add(loadMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = fileChooser.getSelectedFile().getPath();
                    System.out.println("Save to: " + selectedPath);
                }
            }
        });
        fileMenu.add(saveMenuItem);

        JMenuItem githubMenuItem = new JMenuItem("GitHub Visualization");
        githubMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String owner = JOptionPane.showInputDialog("Enter GitHub repo owner:");
                    String repo = JOptionPane.showInputDialog("Enter GitHub repo name:");
                    GitHubVisualizer.displayCommitHistory(owner, repo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        fileMenu.add(githubMenuItem);
    }
}

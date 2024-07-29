package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/** @author Veer Bhagia **/

public class FileMenu extends JMenuBar {

    public FileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        // Removed Load Menu Item

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current frame (Main class) to capture the content
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(FileMenu.this);
                if (mainFrame == null) {
                    return; // Exit if no frame found
                }

                // Create a BufferedImage of the same size as the frame
                Dimension size = mainFrame.getSize();
                BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

                // Render the frame's content to the BufferedImage
                Graphics2D graphics = image.createGraphics();
                mainFrame.paint(graphics);
                graphics.dispose();

                // Save the BufferedImage to a file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write(image, "png", fileToSave);
                        JOptionPane.showMessageDialog(null, "Screenshot saved to: " + fileToSave.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error saving screenshot: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
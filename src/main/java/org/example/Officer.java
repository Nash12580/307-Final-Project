package org.example;

import java.awt.*;

public class Officer {

    public static Color[] generateColors(String fileName) {
        int hash = fileName.hashCode();
        System.out.println("hash1: " + hash);
        Color color1 = new Color((hash & 0xFF0000) >> 16, (hash & 0x00FF00) >> 8, hash & 0x0000FF);
        System.out.println("color1: " + color1);
        int hash2 = Integer.rotateLeft(hash, 16);
        System.out.println("hash2: " + hash2);
        Color color2 = new Color((hash2 & 0xFF0000) >> 16, (hash2 & 0x00FF00) >> 8, hash2 & 0x0000FF);
        System.out.println("color2: " + color2);
        return new Color[]{color1, color2};
    }
}

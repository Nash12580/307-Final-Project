package org.example;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileInputStream;
import java.util.Map;

/**@author Nashali Vicente Lopez**/
public class CodeParser{
    private static int panelWidth;
    private static int panelHeight;

    public static void initialize(int width, int height) {
        panelWidth = width;
        panelHeight = height;
    }

    public static void parseJavaFile(String filePath) throws IOException {
        try(FileInputStream in = new FileInputStream(filePath)) {
            JavaParser parser = new JavaParser();
            CompilationUnit cu = parser.parse(in).getResult().orElse(null);

            if (cu != null) {
                for (ClassOrInterfaceDeclaration cls : cu.findAll(ClassOrInterfaceDeclaration.class)) {
                    String className = cls.getNameAsString();
                    List<String> fields = cls.getFields().stream().map(f -> f.getVariables().toString()).toList();
                    Map<String, String> methods = new HashMap<>();
                    for (MethodDeclaration method: cls.getMethods()){
                        String methodName = method.getNameAsString();
                        String params = method.getParameters().toString();
                        methods.put(methodName, params);
                    }

                    int radius = 20 + methods.size() + fields.size();
                    int x = (int) (radius + (Math.random()*(panelWidth - 15 * radius)));
                    int y = (int) (radius + (Math.random()*(panelHeight - 15 * radius)));
                    Color[] colors = Officer.generateColors(className);
                    double maxRadius = Math.min(panelWidth / 2, panelHeight / 2) - 10;
                    double distanceToSun = 100 + Math.random() * (maxRadius - 100);
                    Planet planet = new Planet(className, radius, distanceToSun, x, y, colors[0], colors[1], filePath, fields, methods);
                    Officer.getPlanetStack().push(planet);
                }
            } else {
                System.out.println("No CompilationUnit found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseJavaDirectory(String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(file -> file.isFile() && file.getName().endsWith(".java"));
            if (files != null) {
                for (File file: files) {
                    parseJavaFile(file.getPath());
                }
            }
        } else {
            System.out.println(dirPath + " is not a directory.");
        }
    }
}



package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import java.io.FileInputStream;

public class CodeParser{
    private static int panelWidth;
    private static int panelHeight;

    public static void initialize(int width, int height) {
        panelWidth = width;
        panelHeight = height;
    }
    public static void parseJavaFile(String filePath) throws IOException{
        try(FileInputStream in = new FileInputStream(filePath)){
            JavaParser parser = new JavaParser();
            CompilationUnit cu = parser.parse(in).getResult().orElse(null);

            if (cu != null){
                for (ClassOrInterfaceDeclaration cls : cu.findAll(ClassOrInterfaceDeclaration.class)) {
                    String className = cls.getNameAsString();
                    List<String> methods = cls.getMethods().stream().map(m -> m.getNameAsString()).toList();
                    List<String> attributes = cls.getFields().stream().map(f -> f.getVariables().toString()).toList();

                    int radius = 20 + methods.size() + attributes.size();
                    int x = (int) (radius + (Math.random()*(panelWidth - 15 * radius)));
                    int y = (int) (radius + (Math.random()*(panelHeight - 15 * radius)));
                    Color[] colors = Officer.generateColors(filePath);

                    Planet planet = new Planet(className, radius, x, y, colors[0], colors[1]);
                    Officer.getPlanetStack().push(planet);
                }
            }else{
                System.out.println("No CompilationUnit found.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void parseJavaDirectory(String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (dir.isDirectory()){
            File[] files = dir.listFiles(file -> file.isFile() && file.getName().endsWith(".java"));
            if(files != null){
                for (File file: files){
                    parseJavaFile(file.getPath());
                }
            }
        }else{
            System.out.println(dirPath + " is not a directory.");
        }
    }
}


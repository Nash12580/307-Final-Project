package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.io.FileInputStream;

public class CodeParser{
    public static void parseJavaFile(String filePath){
        try(FileInputStream in = new FileInputStream(filePath)){
            JavaParser parser = new JavaParser();
            CompilationUnit cu = parser.parse(in).getResult().orElse(null);

            if (cu != null){
                cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls ->{
                    System.out.println("Class: " + cls.getNameAsString());

                    List<MethodDeclaration> methods = cls.getMethods();
                    methods.forEach(method -> System.out.println(" Method: " + method.getNameAsString()));

                    List<FieldDeclaration> attributes = cls.getFields();
                    attributes.forEach(attribute -> System.out.println(" Attributes: " + attribute.getVariables()));

                });
            }else{
                System.out.println("No CompilationUnit found.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void parseJavaDirectory(String dirPath){
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


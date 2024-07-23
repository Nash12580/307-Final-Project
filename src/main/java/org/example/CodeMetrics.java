package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CodeMetrics {
    public static int countTotalLines(String filePath) throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            int totalLines = 0;
            while(reader.readLine() != null){
                totalLines++;
            }
            return totalLines;
        }
    }
    public static int countLOC(String filePath) throws IOException{
        try(BufferedReader reader = new BufferedReader((new FileReader(filePath)))){
            int loc = 0;
            String line;
            boolean insideClass = false;
            while((line = reader.readLine()) != null){
                line = line.trim();
                if(line.startsWith("class ") || line.startsWith("public class ") || line.startsWith("private class ")){
                    insideClass = true;
                }
                if(insideClass){
                    loc++;
                }
            }
            return loc;
        }
    }

    public static int calculateELOC(String filePath) throws IOException{

    }

    public static int calculateLLOC(String filePath) throws IOException{

    }
}

package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class CodeMetrics {
    public static int calculateLOC(String filePath) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int loc = 0;
        while(reader.readLine() != null){
            loc++;
        }
        reader.close();
        return loc;
    }

    public static int calculateELOC(String filePath) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int eloc = 0;
        String line;
        while((line = reader.readLine()) != null){
            line.trim();
            if(!line.isEmpty() && !line.startsWith("//") && !line.startsWith("/*") && !line.startsWith("*") && !line.startsWith("*/")){
                eloc++;
            }
        }
        reader.close();
        return eloc;
    }

    public static int calculateLLOC(String filePath) throws IOException{
        FileInputStream in = new FileInputStream(filePath);
        CompilationUnit cu = JavaParser.parse(in);
    }
}

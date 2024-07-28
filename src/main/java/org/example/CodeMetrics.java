package org.example;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.*;
import java.util.*;
/**@author Nashali Vicente Lopez**/
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
                if(line.startsWith("class ") || line.startsWith("public class ") || line.startsWith("private class ") || line.startsWith("public interface ")){
                    insideClass = true;
                }
                if(insideClass){
                    loc++;
                }
            }
            return loc;
        }
    }
    public static int counteLOC(String filePath) throws IOException{
        CompilationUnit cu = parseFile(filePath);
        if(cu == null) return 0;
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        return visitor.geteLOC();
    }
    public static int countlLOC(String filePath) throws IOException{
        CompilationUnit cu = parseFile(filePath);
        if(cu == null) return 0;
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        return visitor.getLLOC();
    }
    private static  CompilationUnit parseFile(String filePath) throws IOException{
        try(FileInputStream in = new FileInputStream(filePath)){
            JavaParser parser = new JavaParser();
            return parser.parse(in).getResult().orElse(null);
        }
    }
    public static double calculateAbstractness(String filePath) throws IOException{
        CompilationUnit cu = parseFile(filePath);
        if(cu == null) return 0.0;
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        int totalCandI = visitor.getTotalCI();
        int abstractCandI = visitor.getAbstractCI();
        if(totalCandI == 0) return 0.0;
        return (double) abstractCandI/totalCandI;
    }
    public static Map<String, Boolean> analyseMethods(String filepath) throws IOException{
        CompilationUnit cu = parseFile(filepath);
        if(cu == null) return Collections.emptyMap();
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        Map<String, Boolean> methodUsage = new HashMap<>();
        for (String method : Visitor.getDeclaredMethods()) {
            methodUsage.put(method, Visitor.getUsedMethods().contains(method));
        }
        return methodUsage;
    }
}


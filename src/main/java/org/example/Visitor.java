package org.example;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.HashSet;
import java.util.Set;
/**@author Nashali Vicente Lopez**/
public class Visitor extends VoidVisitorAdapter<Void> {
    private int eLOC = 0, lLOC = 0, totalCI = 0, abstractCI = 0;
    private final Set<String> processedLines = new HashSet<>();
    private final Set<String> processedLogicalStm = new HashSet<>();
    private static final Set<String> declaredMethods = new HashSet<>();
    private static final Set<String> usedMethods = new HashSet<>();
    @Override
    public void visit(MethodDeclaration md, Void arg){
        super.visit(md, arg);
        declaredMethods.add(md.getNameAsString());
        if(md.getBody().isEmpty() && processedLogicalStm.add(md.toString().trim())){
            lLOC++;
        }else if(md.getBody().isPresent()){
            BlockStmt body = md.getBody().get();
            processLines(body.toString());
            body.getStatements().forEach(stm -> {
                String stmStr = stm.toString().trim();
                if(isLogicalStatement(stmStr) && processedLogicalStm.add(stmStr)){
                    lLOC++;
                }
                stm.accept(this, null);
            });
        }
    }
    @Override
    public void visit(ClassOrInterfaceDeclaration ci, Void arg){
        super.visit(ci, arg);
        processLines(ci.toString());
        ci.getMembers().forEach(member -> member.accept(this, arg));

        totalCI++;
        if(ci.isAbstract() || ci.isInterface()){
            abstractCI++;
        }
    }
    @Override
    public void visit(ExpressionStmt stm, Void arg) {
        super.visit(stm, arg);
        String stmStr = stm.toString().trim();
        if (isLogicalStatement(stmStr) && processedLogicalStm.add(stmStr)) {
            lLOC++;
        }
        stm.findAll(com.github.javaparser.ast.expr.MethodCallExpr.class).forEach(expr -> {
            usedMethods.add(expr.getNameAsString());
        });
    }
    private void processLines(String code){
        String[] lines = code.split("\n");
        for(String line: lines){
            line = line.trim();
            if(iseffectiveLine(line) && processedLines.add(line)){
                eLOC++;
            }
        }
    }
    private boolean iseffectiveLine(String line) {
        line = line.trim();
        return !line.isEmpty() && !line.equals("{") && !line.equals("}") &&
                !line.startsWith("/*") && !line.startsWith("*") && !line.startsWith("//");
    }
    private boolean isLogicalStatement(String stmtStr) {
        stmtStr = stmtStr.trim();
        return stmtStr.endsWith(";") || stmtStr.startsWith("if") || stmtStr.startsWith("for") || stmtStr.startsWith("while") || stmtStr.startsWith("switch");
    }
    public int geteLOC(){
        return eLOC;
    }
    public int getLLOC(){
        return lLOC;
    }
    public int getTotalCI() {
        return totalCI;
    }
    public int getAbstractCI() {
        return abstractCI;
    }
    public static Set<String> getDeclaredMethods() {
        return declaredMethods;
    }
    public static Set<String> getUsedMethods() {
        return usedMethods;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equationsolver;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Octalus
 */
public class EquationSolver {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        System.out.println(solve(line, Integer.parseInt(input.nextLine())));
        input.close();
    }
    
    public static double solve(String equation, int x) {
        System.out.println("Equation:" + equation);
        
        ArrayList<String> addition = new ArrayList<String>();
        String buffer = "";
        int i = 0;
        int p = 0;
        while(i < equation.length()) {
            char c = equation.charAt(i);
            if(c == '+' && p == 0) {
                addition.add(buffer);
                buffer = "";
            }else if(c == '-' && p ==0) {
                addition.add(buffer);
                buffer = "-";
            }else{
                buffer += c;
                if(c == '(') {
                    p++;
                }else if(c == ')') {
                    p--;
                }
            }
            i++;
        }
        addition.add(buffer);
        
        double solution = 0;
        for(String a: addition) {
            solution += solveChunck(a, x);
        }
        
        return solution;
    }
    
    public static double solveChunck(String chunk, int x) {
        System.out.println("Chunk:" + chunk);
        if(chunk.contains("^") && !(chunk.startsWith("(") && chunk.endsWith(")"))) {
            if(chunk.startsWith("x")) {
                return Math.pow(x, solveChunck(chunk.split("\\^")[1], x));
            }else if(chunk.startsWith("-x")) {
                return Math.pow(x, solveChunck(chunk.split("\\^")[1], x)) * -1;
            }else{
                return Math.pow(x, solveChunck(chunk.split("\\^")[1], x)) * solveChunck(chunk.split("x")[0], x);
            }
        }else if(chunk.contains("*") || chunk.contains("/")) {
            String front = "";
            String back = "";
            boolean mult = false;
            int i = 0;
            while(true) {
                char c = chunk.charAt(i);
                if(c == '*') {
                    mult = true;
                    i++;
                    break;
                }else if(c == '/') {
                    i++;
                    break;
                }else{
                    front += c;
                }
                i++;
            }
            while(i < chunk.length()) {
                back += chunk.charAt(i);
                i++;
            }
            if(mult) {
                return solveChunck(front, x) * solveChunck(back, x);
            }else{
                return solveChunck(front, x) / solveChunck(back, x);
            }
        }if(chunk.startsWith("(") && chunk.endsWith(")")) {
            String inside = "";
            int i = 1;
            while(i < chunk.length()-1) {
                inside += chunk.charAt(i);
                i++;
            }
            return solve(inside, x);
        }else if(chunk.contains("x")) {
            if(chunk.equals("x")) {
                return x;
            }else{
                return x * Double.parseDouble(chunk.split("x")[0]);
            }
        }else{
            try {
                return Double.parseDouble(chunk);
            }catch (NumberFormatException e) {
                
            }
        }
        return 0;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author SKings
 * @version 3/4/2015 - 13/11/1393
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static ArrayList<Matrice> matrix;
    private static int n;
    private static int[][] m, s;
    private static boolean problem;

    public static void main(String[] args) {
        // TODO code application logic here
        matrix = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the path of input(txt) : ");
        ArrayList<String> infos = readFile(sc.nextLine());
        if (!problem) {
            int repeat = solve(buildDimentions(infos));
            printChain(0, n - 1);
            System.out.println("");
            System.out.println("Number of multipliers : " + repeat);
        }
    }

    public static ArrayList<String> readFile(String input) {
        BufferedReader br = null;
        ArrayList<String> infos = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(input + ".txt"));
            try {
                String line = br.readLine();
                while (line != null) {
                    infos.add(line);
                    line = br.readLine();
                }
            } catch (IOException ex) {
                System.out.println("I/O Exception occured");
                problem = true;
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    problem = true;
                    System.out.println("I/O Exception occured");
                }
            }

        } catch (FileNotFoundException ex) {
            problem = true;
            System.out.println("File Not Found");
        }
        return infos;
    }

    public static int[] buildDimentions(ArrayList<String> infos) {

        n = new Integer(infos.get(0));
        infos.remove(0);
        int[] d = new int[n + 1];
        String[] split;
        for (int i = 0; i < n; i++) {
            split = infos.get(i).split(" ");
            Matrice mm = new Matrice(split[0], split[1], split[2]);
            matrix.add(mm);
            d[i] = mm.x;
            d[i + 1] = mm.y;
        }
        return d;
    }

    public static int solve(int[] p) {
        m = new int[n][n];
        s = new int[n][n];
        for (int i = 0; i < n; i++) {
            m[i] = new int[n];
            m[i][i] = 0;
            s[i] = new int[n];
        }
        for (int ii = 1; ii < n; ii++) {
            for (int i = 0; i < n - ii; i++) {
                int j = i + ii;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        return m[0][n - 1];
    }

    public static void printChain(int i, int j) {

        if (i == j) {
            System.out.print(matrix.get(i).name);
        } else {
            int k = s[i][j];

            System.out.print("(");

            //left
            printChain(i, k);

            //right
            printChain(k + 1, j);

            System.out.print(")");
        }
    }

    static class Matrice {

        public String name;
        public int x, y;

        public Matrice(String name, String xx, String yy) {
            this.name = name;
            this.x = new Integer(xx);
            this.y = new Integer(yy);
        }
    }

}

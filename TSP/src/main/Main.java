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
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SKings
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    // Usage example
    public static void main(String[] args) {
        BufferedReader br = null;

        System.out.print("Please insert file path : ");

        Scanner sc = new Scanner(System.in);

        ArrayList<String> lines = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(sc.nextLine()));
            try {
                String line = br.readLine();

                while (line != null) {
                    lines.add(line);
                    line = br.readLine();
                }
            } catch (IOException ex) {
                System.out.println("IO exception");
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    System.out.println("IO exception");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        int[][] w = new int[lines.size() - 1][lines.size() - 1];
        String[] names = new String[lines.size() - 1];

        try {
            for (int i = 0; i < lines.size(); i++) {
                //System.out.println(lines.get(i));
                String[] split = lines.get(i).split(" ");

                if (i != 0) {
                    names[i - 1] = split[0];
                    for (int j = 1; j < split.length; j++) {
                        if (split[j].equals("*")) {
                            w[i - 1][j - 1] = Integer.MAX_VALUE/ 100;
                        } else {
                            w[i - 1][j - 1] = new Integer(split[j]);
                        }
                    }
                }
            }
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < w.length; j++) {
                    System.out.print("" + w[i][j] + " ");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            System.out.println("format is wrong ");
            e.printStackTrace();
        }
//
//        int[][] dist = {
//            {0, 5, 9, 13, 17},
//            {1, 0, 10, 14, 18},
//            {2, 6, 0, 15, 19},
//            {3, 7, 11, 0, 20},
//            {4, 8, 12, 16, 0}};
//        for (int i = 0; i < 5; i++) {
//            for (int j = i; j < 5; j++) {
//                int z = dist[i][j];
//                dist[i][j] = dist[j][i];
//                dist[j][i] = z;
//            }
//        }
        System.out.println(new TravelingSalesMan().getShortestHamiltonianCycle(w , names));
    }

}

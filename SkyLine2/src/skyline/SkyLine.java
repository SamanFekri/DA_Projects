/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author SKings
 * @version 3/4/2015 - 13/11/1393
 */
public class SkyLine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the input file(txt) : ");
        String in = sc.nextLine();
        System.out.print("Enter the output file(txt) : ");
        String out = sc.nextLine();
        solveSkyLine(in, out);
    }

    public static void solveSkyLine(String input, String output) {

        Boolean problem = false;
        BufferedReader br = null;
        ArrayList<String> infos = new ArrayList<>();
        int numOfHouses = 0;

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
                    System.out.println("I/O Exception occured");
                    problem = true;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            problem = true;
        }
        if (!problem) {
            Building[] buildings_1 = null;

            try {
                numOfHouses = new Integer(infos.get(0));
                buildings_1 = new Building[numOfHouses];
                for (int i = 1; i <= numOfHouses; i++) {
                    String[] split = infos.get(i).split(" ");
                    float hx = new Float(split[0]);
                    float hy = new Float(split[1]);
                    float hh = new Float(split[2]);
                    buildings_1[i - 1] = new Building(hx, hy, hh);
                }
            } catch (Exception e) {
                System.out.println("Format of entry is wrong");
            }
            ArrayList<Point> points = findSkyline(buildings_1);
            float cur_h = 0;
            float cur_x = -1;
            for (int i = 0; i < points.size(); i++) {
                if (cur_x == points.get(i).x) {
                    points.remove(i - 1);
                    i--;
                } else {
                    cur_x = points.get(i).x;
                    if (points.get(i).y == cur_h) {
                        points.remove(i);
                        i--;
                    } else {
                        cur_h = points.get(i).y;
                    }
                }
            }

            try {

                File file = new File(output + ".txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("" + numOfHouses);
                bw.newLine();
                bw.flush();
                System.out.println("" + numOfHouses);
                for (Point point : points) {
                    if (point.y % 1 == 0) {
                        int mm = (int) point.y;

                        if (point.x % 1 == 0) {
                            bw.write("" + (int) point.x + " " + mm);
                            System.out.println("" + (int) point.x + " " + mm);
                        } else {
                            bw.write("" + point.x + " " + mm);
                            System.out.println("" + point.x + " " + mm);
                        }
                        bw.newLine();
                        bw.flush();
                    } else {

                        if (point.x % 1 == 0) {
                            bw.write("" + (int) point.x + " " + point.y);
                            System.out.println("" + (int) point.x + " " + point.y);
                        } else {
                            bw.write("" + point.x + " " + point.y);
                            System.out.println("" + point.x + " " + point.y);
                        }
                        bw.newLine();
                        bw.flush();
                    }
                }
                bw.newLine();
                bw.flush();

                bw.write(" // First number x");
                bw.newLine();
                bw.flush();

                bw.write(" // Second number height");
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                System.out.println("I/O error occured");
            }
        }
    }

    public static ArrayList<Point> findSkyline(Building[] buildings) {
        int n = buildings.length;

        System.out.println("" + n);
        for (Building building : buildings) {
            System.out.println("" + building + " ");
        }

        if (n == 1) {
            ArrayList<Point> sl = new ArrayList<Point>();
            sl.add(new Point(buildings[0].left, buildings[0].height));
            sl.add(new Point(buildings[0].right, 0));
            return sl;
        }

        ArrayList<Point> sl1 = findSkyline(Arrays.copyOfRange(buildings, 0, n / 2));
        ArrayList<Point> sl2 = findSkyline(Arrays.copyOfRange(buildings, n / 2, n));
        return merge(sl1, sl2);
    }

    public static ArrayList<Point> merge(ArrayList<Point> sl1, ArrayList<Point> sl2) {
        ArrayList<Point> skyline = new ArrayList<Point>();
        float curH1 = 0, curH2 = 0;
        int curX = 0;

        while (!sl1.isEmpty() && !sl2.isEmpty()) {
            if (sl1.get(0).x < sl2.get(0).x) {

                System.out.println("" + sl1.get(0).x + " < " + sl2.get(0).x);

                curX = (int) sl1.get(0).x;
                curH1 = sl1.get(0).y;
                sl1.remove(0);
                skyline.add(new Point(curX, Math.max(curH1, curH2)));
            } else {
                System.out.println("" + sl1.get(0).x + " > " + sl2.get(0).x);

                curX = (int) sl2.get(0).x;
                curH2 = sl2.get(0).y;
                sl2.remove(0);

                skyline.add(new Point(curX, Math.max(curH1, curH2)));
            }
        }
        if (sl1.isEmpty()) {
            skyline.addAll(sl2);
        } else if (sl2.isEmpty()) {
            skyline.addAll(sl1);
        }
        return skyline;
    }

    public static class Building {

        float left, right, height;

        public Building(float left, float right, float height) {
            this.left = left;
            this.right = right;
            this.height = height;
        }

        @Override
        public String toString() {
            return ("left = " + left + " right = " + right + " height = " + height);
        }

    }

    public static class Point {

        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}

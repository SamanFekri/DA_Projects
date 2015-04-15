/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Arrays;

/**
 *
 * @author SKings
 */
public class TravelingSalesMan {
    
    static int[][] p;

    public static int getShortestHamiltonianCycle(int[][] dist , String[] names) {
        int n = dist.length;
        int[][] dp = new int[n][1 << n];
        p = new int[n][1 << n];
        for (int[] d : dp) {
            Arrays.fill(d, Integer.MAX_VALUE / 2);
        }
        dp[0][1] = 0;
        for (int mask = 1; mask < 1 << n; mask++) {//+= 2) 
            for (int i = 1; i < n; i++) {
                if ((mask & 1 << i) != 0) {
                    //System.out.println("" + mask+"  " + (1 << i) + "   "+(mask & 1 << i));
                    for (int j = 0; j < n; j++) {
                        if ((mask & 1 << j) != 0) {
                            dp[i][mask] = Math.min(dp[i][mask], dp[j][mask ^ (1 << i)] + dist[j][i]);
                            if (dp[i][mask] > dp[j][mask ^ (1 << i)] + dist[j][i]) {
                                p[i][mask] = j;
                                //System.out.println("<--"+j);
                            } else {
                                p[i][mask] = i;
                                //System.out.println("-->"+i);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1 << n; j++) {
                System.out.print("" + p[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("---------------------------------------------------------------");

        int res = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            res = Math.min(res, dp[i][(1 << n) - 1] + dist[i][0]);
        }

        // reconstruct path
        int cur = (1 << n) - 1;
        int[] order = new int[n + 1];
        int last = 0;
        for (int i = n - 1; i >= 1; i--) {
            int bj = -1;
            for (int j = 1; j < n; j++) {
                if ((cur & 1 << j) != 0 && (bj == -1 || dp[bj][cur] + dist[bj][last] > dp[j][cur] + dist[j][last])) {
                    bj = j;
                }
            }
            order[i] = bj;
            cur ^= 1 << bj;
            last = bj;
            order[n] = order[0];
        }
        String path = "";
        for(int i = 0; i < order.length; i++){
            path += names[order[i]] + " --> ";
        }
        path = path.substring(0,path.length() - 5);
        System.out.println(""+path);
        System.out.println("---------------------------------------------------------------");
        return res;
    }
}

package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static int INF=999999999;

    public static void swap(int[] data, int j, int k) {
        int jVal = data[j];
        int kVal = data[k];
        data[j] = kVal;
        data[k] = jVal;
    }

    //taken from here: https://stackoverflow.com/a/8735148/9279181
    public static boolean permuteLexically(int[] data) {
        int k = data.length - 2;
        while (data[k] >= data[k + 1]) {
            k--;
            if (k < 0) {
                return false;
            }
        }
        int l = data.length - 1;
        while (data[k] >= data[l]) {
            l--;
        }
        swap(data, k, l);
        int length = data.length - (k + 1);
        for (int i = 0; i < length / 2; i++) {
            swap(data, k + 1 + i, data.length - i - 1);
        }
        return true;
    }


    //can use a map instead
    public static int getCost(int log) {
        if(log == 1) return -1;
        else if(log == 2) return 3;
        else if(log == 3) return 1;
        else return 0;
    }

    public static int getProfit(int trunks[]) {
        int profit = 0;
        int buffer = 3;
        int thisRound = 0;
        int log = 0;
        for(int i = 0; i < trunks.length; i++) {
            log = trunks[i];
            while(log > 0) {
                if(buffer >= log){
                    thisRound = log;
                } else {
                    thisRound = buffer;
                }
                profit += getCost(thisRound);
                log -= thisRound;
                buffer -= thisRound;
                if(buffer == 0) buffer = 3;
            }
        }
        return profit;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File text = new File("src/test.txt");
        Scanner scanner = new Scanner(text);
        int sawMills;
        int caseNumber = 1;
        ArrayList<List<List>> order;
        do {
            sawMills = scanner.nextInt();
            if(sawMills != 0)
                System.out.println("Case "+caseNumber);
            else break;
            int finalMaxProfit = 0;
            int profit;
            order = new ArrayList<>();
            for(int i = 0; i < sawMills; i++) {
                int maxProfit = -INF;
                int noOfTrunks = scanner.nextInt();
                int trunks[] = new int[noOfTrunks];
                ArrayList<List> maxArrays = new ArrayList<>();
                for(int j = 0; j < noOfTrunks; j++) {
                    trunks[j] = scanner.nextInt();
                }
                Arrays.sort(trunks);
                 do {
                    profit = getProfit(trunks);
                    if(profit > maxProfit) {
                        maxArrays = new ArrayList<>();
                        maxArrays.add(Arrays.stream(trunks).boxed().collect(Collectors.toList()));
                        maxProfit = profit;
                    } else if (profit == maxProfit) {
                        maxArrays.add(Arrays.stream(trunks).boxed().collect(Collectors.toList()));
                    }
                } while(permuteLexically(trunks));
                finalMaxProfit+=maxProfit;
                order.add(maxArrays);
            }
            System.out.println("Max Profit: "+finalMaxProfit);
            System.out.print("Order: ");
            int ansLength = 0;
            for(List maxArrays: order) {
                ansLength++;
                for (Object l: maxArrays) {
                    System.out.print(l.toString());
                }
                if(ansLength<order.size()) {
                    System.out.print(",");
                }
            }
            System.out.println();
            caseNumber++;
        } while(sawMills!= 0);
    }
}
package ru.ray.jolly;

import java.util.ArrayList;

public class Frequency {
    private static double singleTime = 1E-9;
    private static double frequency;

    public static void showAVGFrequency(int[] mas, int mode){
        switch (mode){
            case 1: showNRZ(mas); break;
            case 2: showRZ(mas); break;
            case 3: showAMI(mas); break;
            case 4: showNRZI(mas); break;
            case 5: showMLT3(mas); break;
            case 6: showM2(mas); break;
            case 7: showDifM2(mas); break;
            case 8: showPAM5(mas); break;
            default:
                System.out.println("Error");
        }
    }

    private static void showNRZ(int[] mas2) {
        ArrayList<Float> counterList = new ArrayList<>();
        int k = mas2[0], counter = 0;
        int n = 1;
        for (int j: mas2){
            if (k == j)
                counter++;
            else {
                n++;
                while (counterList.size() < counter)
                    counterList.add(0f);
                counterList.set(counter-1, counterList.get(counter-1)+0.5f);
                counter=1;
                k = j;
            }
        }
        while (counterList.size() < counter)
            counterList.add(0f);
        counterList.set(counter-1, counterList.get(counter-1)+0.5f);
        frequency = 1/singleTime/2;
        float answer = 0;
        System.out.print("(");
        for (int i = 0; i < counterList.size(); i++) {
            System.out.print(counterList.get(i) + "*f_верх/" + (i+1) + "+");
            answer += counterList.get(i)*frequency/(i+1);
        }
        System.out.print(")");
        System.out.println("/" + n + " = " + 2*answer/n);
    }

    private static void showRZ(int[] mas2){
        double[] counters =  new double[2];
        frequency = 1/singleTime;
        int i = 0;
        while (i < mas2.length){
            if (i < mas2.length-2 && ((mas2[i] == 1 && mas2[i+1] == 0 && mas2[i+2] == 1) ||
                    (mas2[i] == 0 && mas2[i+1] == 1 && mas2[i+2] == 0))) {
                counters[0]++;
                counters[1]+=0.5;
                i= i+3;
            }else{
                counters[1]++;
                i++;
            }
        }
        System.out.println("("+counters[1]+"f_верх+"+counters[0]+"f_ниж)/" + (counters[0]+counters[1]) +
                " = " + ((counters[1]*frequency+counters[0]*frequency/2.5)/(counters[0]+counters[1])));
    }

    private static void showAMI(int[] mas2){
        int i = 0, k = mas2[0], counter = 0;
        ArrayList<Integer> counterList = new ArrayList<>();
        counterList.add(0);
        while (i < mas2.length){
            if (i < mas2.length-4 && mas2[i] == 0 && mas2[i+1] == 1 && mas2[i+2] == 0 && mas2[i+3] == 1 && mas2[i+4] == 0){
                i+=5;
                while (counterList.size() < 5)
                    counterList.add(0);
                counterList.set(4, counterList.get(4)+1);
                if (counter > 0) {
                    while (counterList.size() < counter)
                        counterList.add(0);
                    counterList.set(counter-1, counterList.get(counter-1)+1);
                    counter = 0;
                }
            } else {
                if (mas2[i] == 1) {
                    while (counterList.size() < counter)
                        counterList.add(0);
                    counterList.set(0, counterList.get(0)+1);
                    if (counter > 0) {
                        counterList.set(counter-1, counterList.get(counter-1)+1);
                        counter = 0;
                    }
                } else
                    counter++;
                i++;
            }
        }
        if (counter > 0){
            while (counterList.size() < counter)
                counterList.add(0);
            counterList.set(counter-1, counterList.get(counter-1)+1);
        }
        int n = 0;
        double sum = 0;
        frequency = 1/(2*singleTime);
        System.out.print("(");
        for (int j = 0;  j < counterList.size(); j++){
            n += counterList.get(j);
            sum += counterList.get(j) *frequency/(j+1);
            System.out.print(counterList.get(j)+"f_верх/" + (j+1) + "+");
        }
        System.out.println(")/" + n +"=" + sum/n);
    }

    private static void showNRZI(int[] finish2){
        int counter = 0;
        ArrayList<Integer> counterList = new ArrayList<>();
        for (int num: finish2){
            if (num == 1 && counter>0) {
                while (counterList.size() < counter)
                    counterList.add(0);
                counterList.set(counter-1, counterList.get(counter-1)+1);
                counter = 1;
            }
                else counter++;
        }
        while (counterList.size() < counter)
            counterList.add(0);
        counterList.set(counter-1, counterList.get(counter-1)+1);
        counter = 0;
        frequency = 1/(2*singleTime);
        double sum = 0;

        System.out.print("(");
        for (int i = 0; i < counterList.size(); i++){
            if (counterList.get(i)>0){
                sum+=counterList.get(i)*frequency/(i+1);
                counter += counterList.get(i);
                System.out.print(counterList.get(i) + " f_верх /" + (i+1) + "+");
            }
        }
        System.out.print(")/" + counter +  " = " + sum/counter);
    }

    private static void showMLT3(int[] mas2){
        frequency = 1/(2*singleTime);
        ArrayList<Integer> counterList = new ArrayList<>();
        int counter = 0;
        for (int value : mas2) {
            if (value == 0)
                counter++;
            else if (counter > 0) {
                while (counter > counterList.size())
                    counterList.add(0);
                counterList.set(counter - 1, counterList.get(counter - 1) + 1);
                counter = 1;
            }
        }
        while (counter > counterList.size())
            counterList.add(0);
        counterList.set(counter-1, counterList.get(counter-1)+1);

        double sumCounter = 0, sum = 0;
        System.out.print("(");
        for (int i = 0; i < counterList.size(); i++){
            if (counterList.get(i) > 0){
                sum += counterList.get(i) * frequency / (i + 1);
                sumCounter += counterList.get(i);
                if (i+1 != counterList.size())
                    System.out.print(counterList.get(i) + " f_верх /" + (i + 1) + "+");
                else
                    System.out.print(counterList.get(i) + " f_верх /" + (i + 1));
            }
        }
        System.out.println(")/" + sumCounter + " = " + (sum/sumCounter));
    }

    private static void showM2(int[] mas2){
        frequency = 1/singleTime;
        int index = 0;
        int[] counters = new int[2];
        while (index < mas2.length){
            if (index < mas2.length-1 && mas2[index] != mas2[index+1]){
                counters[1]+=1;
                index+=2;
            } else {
                counters[0]+=1;
                index+=1;
            }
        }
        System.out.println("(" + counters[0] + "f_верх + " + counters[1] + "f_ниж)/" + (counters[0]+counters[1]) +
                "=" + ((counters[0]*frequency + counters[1]*frequency/2)/(counters[0]+counters[1])));
    }

    private static void showDifM2(int[] mas2){
        frequency = 1/singleTime;
        int index = 0;
        int[] counters = new int[2];
        while (index < mas2.length){
            if (index < mas2.length-1 && mas2[index+1] == 1){
                counters[1]+=1;
                index+=2;
            } else {
                counters[0]+=1;
                index+=1;
            }
        }
        System.out.println("(" + counters[0] + "f_верх + " + counters[1] + "f_ниж)/" + (counters[0]+counters[1]) +
                "=" + ((counters[0]*frequency + counters[1]*frequency/2)/(counters[0]+counters[1])));
    }

    private static void showPAM5(int[] mas2){
        frequency = 1/singleTime/4;
        ArrayList<Integer> counterList = new ArrayList<>();
        int counter = 0, last = mas2[0]*10+mas2[1];
        for (int i = 0; i < mas2.length; i+=2){
            if (mas2[i]*10+mas2[i+1] == last)
                counter++;
            else {
                while (counterList.size() < counter)
                    counterList.add(0);
                counterList.set(counter-1, counterList.get(counter-1)+1);
                last = mas2[i]*10+mas2[i+1];
                counter = 1;
            }
        }

        while (counterList.size() < counter)
            counterList.add(0);
        counterList.set(counter-1, counterList.get(counter-1)+1);

        double sumCounter = 0, sum = 0;
        System.out.print("(");
        for (int i = 0; i < counterList.size(); i++){
            if (counterList.get(i) > 0){
                sum += counterList.get(i) * frequency / (i + 1);
                sumCounter += counterList.get(i);
                if (i+1 != counterList.size())
                    System.out.print(counterList.get(i) + " f_верх /" + (i + 1) + "+");
                else
                    System.out.print(counterList.get(i) + " f_верх /" + (i + 1));
            }
        }
        System.out.println(")/" + sumCounter + " = " + (sum/sumCounter));
    }
}

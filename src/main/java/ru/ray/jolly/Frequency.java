package ru.ray.jolly;

import java.util.ArrayList;

public class Frequency {
    private static double singleTime = 1E-9;
    private static double frequency;

    public static String showAVGFrequency(int[] mas, int mode){
        switch (mode){
            case 1: return showNRZ(mas);
            case 2: return showRZ(mas);
            case 3: return showAMI(mas);
            case 4: return showNRZI(mas);
            case 5: return showMLT3(mas);
            case 6: return showM2(mas);
            case 7: return showDifM2(mas);
            case 8: return showPAM5(mas);
            default:
                return "Error";
        }
    }

    private static String showNRZ(int[] mas2) {
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < counterList.size(); i++) {
            stringBuilder.append(counterList.get(i)).append("*f_верх/").append(i + 1);
            if (i+1 < counterList.size())
                stringBuilder.append("+");
            answer += counterList.get(i)*frequency/(i+1);
        }
        stringBuilder.append(")");
        stringBuilder.append("/").append(n).append(" = ").append(2 * answer / n);
        return stringBuilder.toString();
    }

    private static String showRZ(int[] mas2){
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

        return ("("+counters[1]+"f_верх+"+counters[0]+"f_ниж)/" + (counters[0]+counters[1]) +
                " = " + ((counters[1]*frequency+counters[0]*frequency/2.5)/(counters[0]+counters[1])));
    }

    private static String showAMI(int[] mas2){
        int i = 0, counter = 0;
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
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append("(");
        for (int j = 0;  j < counterList.size(); j++){
            n += counterList.get(j);
            sum += counterList.get(j) *frequency/(j+1);
            stringBuilder.append(counterList.get(j)).append("f_верх/").append(j + 1).append("+");
        }
        stringBuilder.append(")/").append(n).append("=").append(sum / n);
        return stringBuilder.toString();
    }

    private static String showNRZI(int[] finish2){
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

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < counterList.size(); i++){
            if (counterList.get(i)>0){
                sum+=counterList.get(i)*frequency/(i+1);
                counter += counterList.get(i);
                stringBuilder.append(counterList.get(i)).append(" f_верх /").append(i + 1).append("+");
            }
        }
        stringBuilder.append(")/").append(counter).append(" = ").append(sum / counter);
        return stringBuilder.toString();
    }

    private static String showMLT3(int[] mas2){
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < counterList.size(); i++){
            if (counterList.get(i) > 0){
                sum += counterList.get(i) * frequency / (i + 1);
                sumCounter += counterList.get(i);
                if (i+1 != counterList.size())
                    stringBuilder.append(counterList.get(i)).append(" f_верх /").append(i + 1).append("+");
                else
                    stringBuilder.append(counterList.get(i)).append(" f_верх /").append(i + 1);
            }
        }
        stringBuilder.append(")/").append(sumCounter).append(" = ").append(sum / sumCounter);
        return stringBuilder.toString();
    }

    private static String showM2(int[] mas2){
        frequency = 1/singleTime;
        int[] counters = new int[2];
        counters[0] = 2;
        for (int i = 0; i < mas2.length-1; i++){
            if (mas2[i] != mas2[i+1])
                counters[1]++;
            else
                counters[0] = counters[0] + 2;
        }
        return ("(" + counters[0] + "f_верх + " + counters[1] + "f_ниж)/" + (counters[0]+counters[1]) +
                "=" + ((counters[0]*frequency + counters[1]*frequency/2)/(counters[0]+counters[1])));
    }

    private static String showDifM2(int[] mas2){
        frequency = 1/singleTime;
        int[] counters = new int[2];
        for (int i = 0; i<mas2.length-1; i++){
            if (mas2[i+1] == 1)
                counters[1]++;
            else
                counters[0] = counters[0] + 2;
        }
        return ("(" + counters[0] + "f_верх + " + counters[1] + "f_ниж)/" + (counters[0]+counters[1]) +
                "=" + ((counters[0]*frequency + counters[1]*frequency/2)/(counters[0]+counters[1])));
    }

    private static String showPAM5(int[] mas2){
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < counterList.size(); i++){
            if (counterList.get(i) > 0){
                sum += counterList.get(i) * frequency / (i + 1);
                sumCounter += counterList.get(i);
                if (i+1 != counterList.size())
                    stringBuilder.append(counterList.get(i)).append(" f_верх /").append(i + 1).append("+");
                else
                    stringBuilder.append(counterList.get(i)).append(" f_верх /").append(i + 1);
            }
        }
        stringBuilder.append(")/").append(sumCounter).append(" = ").append(sum / sumCounter);
        return stringBuilder.toString();
    }
}

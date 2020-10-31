package ru.ray.jolly;

import static ru.ray.jolly.Coding.get16Message;
import static ru.ray.jolly.Coding.get2;

public class Main {
    public static void main(String[] args) {
        //mode = 1 - NRZ, 2 - RZ, 3 - AMI, 4 - NRZI, 5 - MLT-3, 6 - M2, 7 - Диф. M2, 8 - PAM-5
        int mode = 7,
                //log = 1 - цифровое кодирование, 2 - избыточное еодирование, 3 - скремблирование
                log = 1;
        //start = <Искодное сообщение>
        String start = "Камышников В.А.";
        StringBuilder finish16 = new StringBuilder();
        int[] finish2;
        for (char let: start.toCharArray()) {
            finish16.append(get16Message(let)).append(' ');
        }
        finish2 = get2(finish16.toString());
        if (log == 2){
            finish2 = LogCoding.excessCoding2(finish2);
            finish16 = Coding.get16Coding(finish2);
        } else if (log == 3){
            finish2 = LogCoding.scramblingCoding2(finish2);
            finish16 = Coding.get16Coding(finish2);
        }
        System.out.println(finish16.toString());
        int counter = 0;
        for (int j: finish2) {
            System.out.print(j);
            if (counter++ % 8 == 7)
                System.out.print(' ');
        }
        showFromMode(finish2, mode);
    }

    public static void showFromMode(int[] mas, int mode){
        System.out.println();
        Frequency.showAVGFrequency(mas, mode);

        Drawer.getFrame();
        Drawer.setUI(mas, mode);
    }
}

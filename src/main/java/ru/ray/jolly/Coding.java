package ru.ray.jolly;

public class Coding {

    static final int deltaOfLittleLetter = (int)'а' - 224;
    static final int deltaOfBigLetter = (int)'А' - 192;

    public static String get16Message(char letter){
        StringBuilder s = new StringBuilder();
        int numLet = letter;
        if (letter >= 'А' && letter <= 'Я'){
            numLet -= deltaOfBigLetter;
            s.append(get16Let(numLet / 16)).append(get16Let(numLet % 16));
            return s.toString();
        }
        if (letter >= 'а' && letter <= 'я'){
            numLet -= deltaOfLittleLetter;
            s.append(get16Let(numLet / 16)).append(get16Let(numLet % 16));
            return s.toString();
        }
        switch (letter){
            case ' ': return "20";
            case ',': return "2C";
            case '.': return "2E";
            case '0': return "30";
            case '1': return "31";
            case '2': return "32";
            case '3': return "33";
            case '4': return "34";
            case '5': return "35";
            case '6': return "36";
            case '7': return "37";
            case '8': return "38";
            case '9': return "39";
        }
        return "";
    }

    public static int[] get2(String start){
        int[] mas = new int[4*(2*(start.length()+1)/3)];
        int i = 0;
        for (char c : start.toCharArray()) {
            if (c != ' ') {
                mas[i] = get16Num(c) / 8;
                mas[i+1] = get16Num(c) / 4 % 2;
                mas[i+2] = get16Num(c) / 2 % 2;
                mas[i+ 3] = get16Num(c) % 2;
                i += 4;
            }
        }
        return mas;
    }


    public static StringBuilder get16Coding(int[] mas) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mas.length/4; i ++){
            stringBuilder.append((char) Coding.get16Let(mas[4*i]*8+mas[4*i+1]*4+mas[4*i+2]*2+mas[4*i+3]));
            if (i%2==1)
                stringBuilder.append(' ');
        }
        if (mas.length % 4 > 0 ) {
            if (mas.length/4 % 2 == 0)
                stringBuilder.append(' ');
            int len = mas.length/4*4;
            stringBuilder.append(Coding.get16Let(
                    mas[len]*8+
                            ((len+1 < mas.length) ? mas[len+1]*4 : 0)+
                            ((len+2 < mas.length) ? mas[len+2]*2 : 0)+
                            ((len+3 < mas.length) ? mas[len+3] : 0)
            ));
        }
        return stringBuilder;
    }

    public static char get16Let(int num){
        switch (num) {
            case 0: return '0';
            case 1: return '1';
            case 2: return '2';
            case 3: return '3';
            case 4: return '4';
            case 5: return '5';
            case 6: return '6';
            case 7: return '7';
            case 8: return '8';
            case 9: return '9';
            case 10: return 'A';
            case 11: return 'B';
            case 12: return 'C';
            case 13: return 'D';
            case 14: return 'E';
            case 15: return 'F';
            default: return ' ';
        }
    }

    public static int get16Num(char l){
        switch (l){
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'A': return 10;
            case 'B': return 11;
            case 'C': return 12;
            case 'D': return 13;
            case 'E': return 14;
            case 'F': return 15;
        }
        return 0;
    }

}

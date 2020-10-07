package ru.ray.jolly;

public class LogCoding {
    public static int[] excessCoding2(int[] mas) {
        int[] codingMas = new int[mas.length/4*5];
        for (int i = 0; i < mas.length/4; i++){
            int now = mas[4*i]*8+mas[4*i+1]*4+mas[4*i+2]*2+mas[4*i+3];
            switch (now){
                case 0:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 0;
                    break;
                case 1:
                    codingMas[5*i] = 0;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 0;
                    codingMas[5*i+4]= 1;
                    break;
                case 2:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 0;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 0;
                    codingMas[5*i+4]= 0;
                    break;
                case 3:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 0;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 0;
                    codingMas[5*i+4]= 1;
                    break;
                case 4:
                    codingMas[5*i] = 0;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 0;
                    break;
                case 5:
                    codingMas[5*i] = 0;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 1;
                    break;
                case 6:
                    codingMas[5*i] = 0;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 0;
                    break;
                case 7:
                    codingMas[5*i] = 0;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 1;
                    break;
                case 8:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 0;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 0;
                    break;
                case 9:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 0;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 1;
                    break;
                case 10:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 0;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 0;
                    break;
                case 11:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 0;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 1;
                    break;
                case 12:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 0;
                    break;
                case 13:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 0;
                    codingMas[5*i+3]= 1;
                    codingMas[5*i+4]= 1;
                    break;
                case 14:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 0;
                    codingMas[5*i+4]= 0;
                    break;
                case 15:
                    codingMas[5*i] = 1;
                    codingMas[5*i+1]= 1;
                    codingMas[5*i+2]= 1;
                    codingMas[5*i+3]= 0;
                    codingMas[5*i+4]= 1;
                    break;
            }
        }
        return codingMas;
    }

    public static int[] scramblingCoding2(int[] mas){
        int[] codingMas = new int[mas.length];
        for (int i = 0; i < mas.length; i++){
            if (i>2){
                if (i>4){
                    codingMas[i] = (mas[i] + codingMas[i-3] + codingMas[i-5])%2;
                }else{
                    codingMas[i] = (mas[i] + codingMas[i-3])%2;
                }
            } else {
                codingMas[i] = mas[i];
            }
        }
        return codingMas;
    }
}

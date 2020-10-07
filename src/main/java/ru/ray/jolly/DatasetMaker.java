package ru.ray.jolly;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DatasetMaker {
    public static XYDataset getDataset(int[] signul, int start, int mode){
        XYSeries series = new XYSeries("");
        switch (mode) {
            case 1:
                series = NRZ(start, signul); break;
            case 2:
                 series = RZ(start, signul); break;
            case 3:
                series = AMI(start, signul); break;
            case 4:
                series = NRZI(start, signul); break;
            case 5:
                series = MLT3(start, signul); break;
            case 6:
                series = M2(start, signul); break;
            case 7:
                 series = DifM2(start, signul); break;
            case 8:
                series = PAM5(start, signul); break;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private static XYSeries NRZ(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                series.add(i, signul[i]);
                series.add(i + 1, signul[i]);
            }
        }
        return series;
    }

    private static XYSeries RZ(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                series.add(i, signul[i]);
                series.add(i + 0.5, signul[i]);
                series.add(i + 0.5, 0.5);
                series.add(i + 1, 0.5);
            }
        }
        return series;
    }

    private static XYSeries AMI(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        boolean flag = false;
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                if (signul[i] == 0){
                    series.add(i, 0.5);
                    series.add(i + 1, 0.5);
                } else {
                    if (flag){
                        flag =  false;
                        series.add(i, 0);
                        series.add(i + 1, 0);
                    } else {
                        flag =  true;
                        series.add(i, 1);
                        series.add(i + 1, 1);
                    }
                }
            }
        }
        return series;
    }

    private static XYSeries NRZI(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        boolean flag = false;
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                if (signul[i] == 0){
                    if (flag){
                        series.add(i, 1);
                        series.add(i + 1, 1);
                    } else {
                        series.add(i, 0);
                        series.add(i + 1, 0);
                    }
                } else {
                    if (flag){
                        series.add(i, 0);
                        series.add(i + 1, 0);
                        flag = false;
                    } else {
                        series.add(i, 1);
                        series.add(i + 1, 1);
                        flag = true;
                    }
                }
            }
        }
        return series;
    }

    private static XYSeries MLT3(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        int pos = 1;
        boolean flag = false;
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                if (signul[i] == 0) {
                    if (pos == 0) {
                        series.add(i, 0);
                        series.add(i + 1, 0);
                    } else if (pos == 1) {
                        series.add(i, 0.5);
                        series.add(i + 1, 0.5);
                    } else {
                        series.add(i, 1);
                        series.add(i + 1, 1);
                    }
                } else {
                    if (pos == 0) {
                        series.add(i, 0.5);
                        series.add(i + 1, 0.5);
                        pos = 1;
                    } else if (pos == 1) {
                        if (flag) {
                            series.add(i, 0);
                            series.add(i + 1, 0);
                            pos = 0;
                            flag = false;
                        }
                        else {
                            series.add(i, 1);
                            series.add(i + 1, 1);
                            pos = 2;
                            flag = true;
                        }
                    } else {
                        series.add(i, 0.5);
                        series.add(i + 1, 0.5);
                        pos = 1;
                    }
                }

            }
        }
        return series;
    }

    private static XYSeries M2(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                if (signul[i] == 0) {
                    series.add(i, 0);
                    series.add(i + 0.5, 0);
                    series.add(i + 0.5, 1);
                    series.add(i + 1, 1);
                }else {
                    series.add(i, 1);
                    series.add(i + 0.5, 1);
                    series.add(i + 0.5, 0);
                    series.add(i + 1, 0);
                }
            }
        }
        return series;
    }

    private static XYSeries DifM2(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        boolean flag = false;
        for (int i = start; i < start + 32; i++) {
            if (signul.length > i) {
                if (signul[i] == 0) {
                    if (flag) {
                        series.add(i, 0);
                        series.add(i + 0.5, 0);
                        series.add(i + 0.5, 1);
                        series.add(i + 1, 1);
                    } else {
                        series.add(i, 1);
                        series.add(i + 0.5, 1);
                        series.add(i + 0.5, 0);
                        series.add(i + 1, 0);
                    }
                }else {
                    if (flag) {
                        series.add(i, 1);
                        series.add(i + 0.5, 1);
                        series.add(i + 0.5, 0);
                        series.add(i + 1, 0);
                        flag = false;
                    } else {
                        series.add(i, 0);
                        series.add(i + 0.5, 0);
                        series.add(i + 0.5, 1);
                        series.add(i + 1, 1);
                        flag = true;
                    }
                }
            }
        }
        return series;
    }

    private static XYSeries PAM5(int start, int[] signul){
        XYSeries series =  new XYSeries("");
        for (int i = 0; i < 16; i++) {
            if (signul.length > 2*i + start) {
                int num = 10*signul[2*i + start] + signul[2*i + start +1];
                switch (num){
                    case 0:
                        series.add(2*i + start, 0);
                        series.add(2*i + start + 2, 0);
                        break;
                    case 1:
                        series.add(2*i + start, 0.25);
                        series.add(2*i + start + 2, 0.25);
                        break;
                    case 10:
                        series.add(2*i + start, 0.75);
                        series.add(2*i + start + 2, 0.75);
                        break;
                    case 11:
                        series.add(2*i + start, 1);
                        series.add(2*i + start + 2, 1);
                        break;
                }
            }
        }
        return series;
    }

}

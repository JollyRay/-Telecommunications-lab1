package ru.ray.jolly;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import javax.swing.*;
import java.awt.*;

public class Drawer {
    private static JFrame jFrame;
    public static void getFrame() {
        jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Lab3 Kamyshnikov Vlad");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(0, 0, dimension.width, dimension.height);
        Container container = jFrame.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout (new GridLayout(1, 1, 0,0));
    }

    public static void setUI(int[] start, int mode){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 0, 0));
        panel.setMaximumSize(new Dimension( 900, 1000));
        jFrame.add(panel);
        for(int i = 0; i < start.length/32 + (start.length % 32 > 0 ? 1 : 0); i++){
            JFreeChart chart = Drawer.getChart(DatasetMaker.getDataset(start, 32*i, mode),i*32, (i+1)*32);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setMaximumSize(new Dimension(900, 1000));
            panel.add(chartPanel);
        }
        panel.revalidate();
    }

    public static JFreeChart getChart(XYDataset dataset, int left, int right){
        JFreeChart chart = ChartFactory.createXYLineChart(
                "", "", "", dataset,
                PlotOrientation.VERTICAL, false, false, false);

        XYPlot plot = chart.getXYPlot();
        NumberAxis axisR = (NumberAxis)plot.getRangeAxis();
        plot.getDomainAxis().setLowerBound(left);
        plot.getDomainAxis().setUpperBound(right);
        axisR.setUpperBound(1.5);
        axisR.setLowerBound(-0.5);

        plot.setBackgroundPaint(new Color(190, 190, 190));
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);

        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible (false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint  (0, Color.red);
        renderer.setSeriesStroke (0, new BasicStroke(2f));
        renderer.setSeriesShapesVisible(0, false);

        plot.setRenderer(renderer);

        return chart;
    }



//    public static XYDataset getDataset(int[] signul, int start, int mode){
//        XYSeries series = new XYSeries("");
//        boolean flag = false;
//        switch (mode) {
//            case 1:
//            for (int i = start; i < start + 32; i++) {
//                if (signul.length > i) {
//                    series.add(i, signul[i]);
//                    series.add(i + 1, signul[i]);
//                }
//            } break;
//            case 2:
//                for (int i = start; i < start + 32; i++) {
//                    if (signul.length > i) {
//                        series.add(i, signul[i]);
//                        series.add(i + 0.5, signul[i]);
//                        series.add(i + 0.5, 0.5);
//                        series.add(i + 1, 0.5);
//                    }
//                } break;
//            case 3:
//                for (int i = start; i < start + 32; i++) {
//                    if (signul.length > i) {
//                        if (signul[i] == 0){
//                            series.add(i, 0.5);
//                            series.add(i + 1, 0.5);
//                        } else {
//                            if (flag){
//                                flag =  false;
//                                series.add(i, 0);
//                                series.add(i + 1, 0);
//                            } else {
//                                flag =  true;
//                                series.add(i, 1);
//                                series.add(i + 1, 1);
//                            }
//                        }
//                    }
//                } break;
//            case 4:
//                for (int i = start; i < start + 32; i++) {
//                    if (signul.length > i) {
//                        if (signul[i] == 0){
//                            if (flag){
//                                series.add(i, 0);
//                                series.add(i + 1, 0);
//                            } else {
//                                series.add(i, 1);
//                                series.add(i + 1, 1);
//                            }
//                        } else {
//                            if (flag){
//                                series.add(i, 1);
//                                series.add(i + 1, 1);
//                                flag = false;
//                            } else {
//                                series.add(i, 0);
//                                series.add(i + 1, 0);
//                                flag = true;
//                            }
//                        }
//                    }
//                } break;
//            case 5:
//                int pos = 1;
//                for (int i = start; i < start + 32; i++) {
//                    if (signul.length > i) {
//                        if (signul[i] == 0) {
//                            if (pos == 0) {
//                                series.add(i, 0);
//                                series.add(i + 1, 0);
//                            } else if (pos == 1) {
//                                series.add(i, 0.5);
//                                series.add(i + 1, 0.5);
//                            } else {
//                                series.add(i, 1);
//                                series.add(i + 1, 1);
//                            }
//                        } else {
//                            if (pos == 0) {
//                                series.add(i, 0.5);
//                                series.add(i + 1, 0.5);
//                                pos = 1;
//                            } else if (pos == 1) {
//                                if (flag) {
//                                    series.add(i, 0);
//                                    series.add(i + 1, 0);
//                                    pos = 0;
//                                    flag = false;
//                                }
//                                else {
//                                    series.add(i, 1);
//                                    series.add(i + 1, 1);
//                                    pos = 2;
//                                    flag = true;
//                                }
//                            } else {
//                                series.add(i, 0.5);
//                                series.add(i + 1, 0.5);
//                                pos = 1;
//                            }
//                        }
//
//                    }
//                } break;
//            case 6:
//                for (int i = start; i < start + 32; i++) {
//                    if (signul.length > i) {
//                        if (signul[i] == 0) {
//                            series.add(i, 0);
//                            series.add(i + 0.5, 0);
//                            series.add(i + 0.5, 1);
//                            series.add(i + 1, 1);
//                        }else {
//                            series.add(i, 1);
//                            series.add(i + 0.5, 1);
//                            series.add(i + 0.5, 0);
//                            series.add(i + 1, 0);
//                        }
//                    }
//                } break;
//            case 7:
//                for (int i = start; i < start + 32; i++) {
//                    if (signul.length > i) {
//                        if (signul[i] == 0) {
//                            if (flag) {
//                                series.add(i, 0);
//                                series.add(i + 0.5, 0);
//                                series.add(i + 0.5, 1);
//                                series.add(i + 1, 1);
//                            } else {
//                                series.add(i, 1);
//                                series.add(i + 0.5, 1);
//                                series.add(i + 0.5, 0);
//                                series.add(i + 1, 0);
//                            }
//                        }else {
//                            if (flag) {
//                                series.add(i, 1);
//                                series.add(i + 0.5, 1);
//                                series.add(i + 0.5, 0);
//                                series.add(i + 1, 0);
//                                flag = false;
//                            } else {
//                                series.add(i, 0);
//                                series.add(i + 0.5, 0);
//                                series.add(i + 0.5, 1);
//                                series.add(i + 1, 1);
//                                flag = true;
//                            }
//                        }
//                    }
//                } break;
//            case 8:
//                for (int i = 0; i < 16; i++) {
//                    if (signul.length > 2*i + start) {
//                        int num = 10*signul[2*i + start] + signul[2*i + start +1];
//                        switch (num){
//                            case 0:
//                                series.add(2*i + start, 0);
//                                series.add(2*i + start + 2, 0);
//                                break;
//                            case 1:
//                                series.add(2*i + start, 0.25);
//                                series.add(2*i + start + 2, 0.25);
//                                break;
//                            case 10:
//                                series.add(2*i + start, 0.75);
//                                series.add(2*i + start + 2, 0.75);
//                                break;
//                            case 11:
//                                series.add(2*i + start, 1);
//                                series.add(2*i + start + 2, 1);
//                                break;
//                        }
//                    }
//                } break;
//        }
//
//
//
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(series);
//        return dataset;
//    }



}

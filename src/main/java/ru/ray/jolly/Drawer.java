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
import java.awt.event.ActionEvent;

import static ru.ray.jolly.Coding.get16Message;
import static ru.ray.jolly.Coding.get2;

public class Drawer {
    private static JFrame jFrame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JTextField text = new JTextField(12);
    private static JComboBox<String> comboBoxMode = new JComboBox<>(new String[]{"NRZ", "RZ", "AMI", "NRZI", "MLT-3", "M2", "Dif M2", "PAM-5"});
    private static JComboBox<String> comboBoxType = new JComboBox<>(new String[]{"Simple", "Logical", "Scrambling"});
    private static JLabel info2 = new JLabel();
    private static JLabel info16 = new JLabel();
    private static JLabel infoFun = new JLabel();

    public static void getFrame() {
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Lab1 Kamyshnikov Vlad");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(0, 0, dimension.width, dimension.height);
        Container container = jFrame.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout (new GridLayout(1, 1, 0,0));
        JPanel jPanelTop = new JPanel();
        jPanelTop.setMaximumSize(new Dimension(100, 10));
        jPanelTop.add(text);
        jPanelTop.add(comboBoxMode);
        jPanelTop.add(comboBoxType);
        jPanelTop.add(new JButton(new CountTel()));
        JPanel jPanelBottom = new JPanel();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1, 0,0));
        jPanel.add(jPanelTop);
        jPanelBottom.setLayout(new GridLayout(3, 1, 0,0));
        jPanelBottom.add(info2);
        jPanelBottom.add(info16);
        jPanelBottom.add(infoFun);
        jPanel.add(jPanelBottom);
        panel.add(jPanel);
        panel.setLayout(new GridLayout(7, 1, 0, 0));
        panel.setMaximumSize(new Dimension( 900, 1000));
        jFrame.add(panel);
        panel.revalidate();
    }

    public static void setUI(int[] start, int mode){
        for(int i = 0; i < start.length/32 + (start.length % 32 > 0 ? 1 : 0); i++){
            JFreeChart chart = Drawer.getChart(DatasetMaker.getDataset(start, 32*i, mode),i*32, (i+1)*32);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setMaximumSize(new Dimension(900, 1000));
            panel.add(chartPanel);
        }
        panel.revalidate();
    }

    private static JFreeChart getChart(XYDataset dataset, int left, int right){
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

    private  static class CountTel extends AbstractAction{
        public CountTel() {
            putValue(AbstractAction.NAME, "Построить");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String start = text.getText();
            deleteChar();
            if (start.length()>0) {
                String finish16 = "";
                int[] finish2;
                finish16 = get16Message(start);
                finish2 = get2(finish16);

                if (comboBoxType.getSelectedIndex() == 1) {
                    finish2 = LogCoding.excessCoding2(finish2);
                    finish16 = Coding.get16Coding(finish2);
                } else if (comboBoxType.getSelectedIndex() == 2) {
                    finish2 = LogCoding.scramblingCoding2(finish2);
                    finish16 = Coding.get16Coding(finish2);
                }

                showResult(finish16, finish2);
                String fun = Frequency.showAVGFrequency(finish2, comboBoxMode.getSelectedIndex() + 1);
                infoFun.setText(fun);
                System.out.println(fun);
                System.out.println();
                Drawer.setUI(finish2, comboBoxMode.getSelectedIndex()+1);
            }

        }
    }

    private static void showResult(String finish16, int[] finish2){
        StringBuilder stringBuilder = new StringBuilder();
        boolean space = false;
        for (char letter: finish16.toCharArray()){
            stringBuilder.append(letter);
            if (space)
                stringBuilder.append(' ');
            space=!space;
        }
        info2.setText(stringBuilder.toString());
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        int counter = 0;
        for (int j: finish2) {
            stringBuilder.append(j);
            if (counter++ % 8 == 7)
                stringBuilder.append(' ');
        }
        info16.setText(stringBuilder.toString());
        System.out.println(stringBuilder.toString());
        panel.revalidate();
        panel.repaint();
    }

    private static void deleteChar(){
        Component[] mas = panel.getComponents();
        for (Component component: mas){
            if (component instanceof ChartPanel)
                panel.remove(component);
        }
    }

}

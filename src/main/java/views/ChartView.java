package views;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import util.Method;
import util.Pair;
import util.Triple;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ChartView {
    private JFrame window;
    private JPanel jPanel;
    private JButton button;
    private JTextField coefficientField;
    private JLabel coefficientFunction1Label;
    private JLabel coefficientFunction2Label;


    public ChartView(Double[][] data, String function1, String function2, int dropId, Method method) {
        createChart(data, function1, function2, dropId, method);
        window = new JFrame();
        setSetting();
        window.add(jPanel);
    }

    private void setSetting() {
        window.setVisible(true);
        window.setBounds(300, 300, 1100, 700);
    }

    private void createChart(Double[][] data, String function1, String function2, int dropId, Method method) {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();
        JPanel panel = createPanel(function1, function2, data, dropId, method);
        jPanel.add(panel);


        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(0, 1));
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel descriptionPointDropLabel = new JLabel("Координаты исключенной точки: (" + data[dropId][0] + "; " + data[dropId][1] + ")");

        coefficientFunction1Label = new JLabel("");
        coefficientFunction2Label = new JLabel("");

        JLabel descriptionCoefficientLabel = new JLabel("Введите x, чтобы узнать значение y: ");
        coefficientField = new JTextField(15);
        button = new JButton("Посмотреть");


        descriptionPanel.add(descriptionPointDropLabel);
        descriptionPanel.add(coefficientFunction1Label);
        descriptionPanel.add(coefficientFunction2Label);
        descriptionPanel.add(descriptionCoefficientLabel);
        descriptionPanel.add(coefficientField);
        descriptionPanel.add(button);

        baseConstraints.gridx = 1;
        jPanel.add(descriptionPanel, baseConstraints);
    }

    public JTextField getCoefficientField() {
        return coefficientField;
    }

    public JLabel getCoefficientFunction1Label() {
        return coefficientFunction1Label;
    }

    public JLabel getCoefficientFunction2Label() {
        return coefficientFunction2Label;
    }

    public JButton getButton() {
        return button;
    }

    public JPanel createPanel(String function1, String function2, Double [][] data, int dropId, Method method) {
        JFreeChart chart = createChart(createDataset(function1, function2, data, dropId, method));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }
        });
        chartPanel.setVerticalAxisTrace(true);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setMouseZoomable(true);

        return chartPanel;
    }

    public Triple<XYDataset, XYDataset, DefaultXYDataset> createDataset(String function1, String function2, Double [][] data, int dropId, Method method) {

        Expression formula1 = new ExpressionBuilder(function1).variable("x").build();
        Expression formula2 = new ExpressionBuilder(function2).variable("x").build();
        Pair<Double, Double> borders = getLeftAndRightBorders(data);
        Double leftBorder, rightBorder;
        if (method == Method.LINEAR || method == Method.SQUARE || method == Method.LOG) {
            leftBorder = borders.snd - 150;
            rightBorder = borders.fst + 150;
        } else {
            leftBorder = borders.snd - 15;
            rightBorder = borders.fst + 15;
        }
        XYDataset first = DatasetUtilities.sampleFunction2D(
                new Function(formula1),
                leftBorder,
                rightBorder,
                300,
                function1
        );
        XYDataset second = DatasetUtilities.sampleFunction2D(
                new Function(formula2),
                leftBorder,
                rightBorder,
                300,
                function2
        );

        DefaultXYDataset points = new DefaultXYDataset();
        double y_in[] = new double[data.length-1];
        double x_in[] = new double[data.length-1];
        double y_ex[] = new double[1];
        double x_ex[] = new double[1];
        int inc = 0;
        for (int i = 0; i<data.length; i++) {
            if (i!=dropId) {
                x_in[inc] = data[i][0];
                y_in[inc] = data[i][1];
                inc++;
            } else {
                x_ex[0] = data[i][0];
                y_ex[0] = data[i][1];
            }
        }

        double included[][] = { x_in , y_in };
        double excluded[][] = { x_ex , y_ex };

        points.addSeries("Включенные", included);
        points.addSeries("Исключенная", excluded);
        return Triple.of(first, second, points);
    }


    private JFreeChart createChart(Triple<XYDataset, XYDataset, DefaultXYDataset> dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики",
                "X",
                "Y",
                 dataset.thrd,
                 PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setRenderer(0, new XYLineAndShapeRenderer());
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer(0);
        r.setSeriesLinesVisible(0, false);

        plot.setDataset(1, dataset.fst);
        plot.setRenderer(1, new StandardXYItemRenderer());
        plot.setDataset(2, dataset.snd);
        plot.setRenderer(2, new StandardXYItemRenderer());



        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);
        return chart;
    }

    private Pair<Double,Double> getLeftAndRightBorders(Double data[] []) {
        Double max = -Integer.MAX_VALUE + 0.0;
        Double min = Integer.MAX_VALUE + 0.0;
        for (Double[] i : data) {
                if (i[0]>max) max = i[0];
                if (i[0]<min) min = i[0];
        }
        return Pair.of(max,min);
    }


    static class Function implements Function2D {
        Expression ex;

        public Function(Expression ex) {
            this.ex = ex;
        }

        public double getValue(double x) {
            return ex.setVariable("x", x).evaluate();
        }
    }



}

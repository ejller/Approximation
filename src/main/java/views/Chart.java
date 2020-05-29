package views;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import model.Method;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Chart {
    private JPanel jPanel;
    private JButton button;
    private JTextField coefficientField;
    private JLabel coefficientFunction1Label;
    private JLabel coefficientFunction2Label;
    private double[][] data;
    private String function1;
    private String function2;
    private int dropId;
    private Method method;

    private XYDataset firstFunction;
    private XYDataset secondFunction;
    private DefaultXYDataset points;


    public Chart(double[][] data, String function1, String function2, int dropId, Method method) {
        this.data=data;
        this.function1=function1;
        this.function2=function2;
        this.dropId=dropId;
        this.method=method;
        setDatasets();
        initUi();

    }

    private void initUi() {

        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();
        JPanel panel = createChartPanel();
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

        baseConstraints.gridy = 1;
        jPanel.add(descriptionPanel, baseConstraints);
    }

    private JPanel createChartPanel() {
        JFreeChart chart = createChart();
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

    private void setDatasets() {

        Expression formula1 = new ExpressionBuilder(function1).variable("x").build();
        Expression formula2 = new ExpressionBuilder(function2).variable("x").build();
        double[][] borders = getLeftAndRightBorders();
        double leftBorder = borders[0][1];
        double rightBorder = borders [0][0];
        firstFunction = DatasetUtilities.sampleFunction2D(
                new Function(formula1, method),
                leftBorder - method.getBias(),
                rightBorder + method.getBias(),
                300,
                function1
        );
        secondFunction = DatasetUtilities.sampleFunction2D(
                new Function(formula2, method),
                leftBorder - method.getBias(),
                rightBorder + method.getBias(),
                300,
                function2
        );

        points = new DefaultXYDataset();
        double[] yIn = new double[data.length - 1];
        double[] xIn = new double[data.length - 1];
        double[] yEx = new double[1];
        double[] xEx = new double[1];
        int inc = 0;
        for (int i = 0; i<data.length; i++) {
            if (i!=dropId) {
                xIn[inc] = data[i][0];
                yIn[inc] = data[i][1];
                inc++;
            } else {
                xEx[0] = data[i][0];
                yEx[0] = data[i][1];
            }
        }

        points.addSeries("Включенные", new double[][]{xIn, yIn});
        points.addSeries("Исключенная", new double[][]{xEx, yEx});
    }


    private JFreeChart createChart() {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Графики",
                "X",
                "Y",
                 points,
                 PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(0, new XYLineAndShapeRenderer());
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer(0);
        r.setSeriesLinesVisible(0, false);

        plot.setDataset(1, firstFunction);
        plot.setRenderer(1, new StandardXYItemRenderer());
        plot.setDataset(2, secondFunction);
        plot.setRenderer(2, new StandardXYItemRenderer());

        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);
        return chart;
    }

    private double[][] getLeftAndRightBorders() {
        double max = (double)-Integer.MAX_VALUE;
        double min = (double)Integer.MAX_VALUE;
        for (double[] i : data) {
                if (i[0]>max) max = i[0];
                if (i[0]<min) min = i[0];
        }
        return new double[][]{{max, min}};
    }


    static class Function implements Function2D {
        Expression ex;
        Method method;

        Function(Expression ex, Method method) {
            this.ex = ex;
            this.method = method;
        }

        public double getValue(double x) {
            if (method == Method.HYPERBOLA && x==0) x+=0.1E-1;
            return ex.setVariable("x", x).evaluate();
        }
    }

    public JPanel getJPanel() {
        return jPanel;
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



}

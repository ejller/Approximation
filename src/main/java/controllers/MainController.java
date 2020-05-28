package controllers;

import model.ApproximationMethod;
import model.Method;
import views.Chart;
import views.MainView;

import javax.swing.*;
import java.awt.*;

public class MainController {
    private MainView view;
    private Method method;
    private int dropId;
    private int sizeTable;
    private Chart chart;
    private ApproximationMethod approximationMethod;

    public MainController() {
        view = new MainView(1300, 800);
        addActionListener();
    }

    private void addActionListener() {
        view.getButtonSize().addActionListener(li -> {
            sizeTable = Integer.parseInt(view.getCountField().getText());

            view.getPanelTableAndInput().remove(view.getPanelWrapperTable());
            JScrollPane wrapperTable = view.initTable(sizeTable);
            GridBagConstraints baseConstraints = new GridBagConstraints();
            view.setPanelWrapperTable(wrapperTable);

            baseConstraints.gridy = 2;
            baseConstraints.anchor = GridBagConstraints.LAST_LINE_START;
            view.getPanelTableAndInput().add(wrapperTable, baseConstraints);

            view.getPanelTableAndInput().revalidate();
            view.getPanelTableAndInput().repaint();
        });

        view.getButtonChart().addActionListener(li -> {
            double[][] data = dataProcessing();
            String function1 = getFunction(data);
            String function2 = getFunction(dropPoint(data, function1));
            if (chart != null) {
                view.getPanelChart().remove(chart.getJPanel());
            }

            chart = new Chart(data, function1, function2, dropId, method);
            new ChartController(chart, function1, function2);
            view.getPanelChart().add(chart.getJPanel());

            view.getPanelChart().revalidate();
            view.getPanelChart().repaint();

        });
    }

    private double[][] dataProcessing() {
        double[][] data = new double[sizeTable][sizeTable];
        for (int i = 0; i < sizeTable; i++) {
            data[i][0] = Double.valueOf(view.getValueTable().getModel().getValueAt(i, 0) + "");
            data[i][1] = Double.valueOf(view.getValueTable().getModel().getValueAt(i, 1) + "");
        }
        return data;
    }

    private String getFunction(double[][] data) {
        approximationMethod = new ApproximationMethod();
        String function = "";
        if (view.getLinearApproximation().isSelected()) {
            function = approximationMethod.linear(data);
            method = Method.LINEAR;
        } else if (view.getHyperbolaApproximation().isSelected()) {
            function = approximationMethod.hyperbola(data);
            method = Method.HYPERBOLA;
        } else if (view.getPowerApproximation().isSelected()) {
            function = approximationMethod.power(data);
            method = Method.POWER;
        } else if (view.getSquareApproximation().isSelected()) {
            function = approximationMethod.square(data);
            method = Method.SQUARE;
        } else if (view.getExpApproximation().isSelected()) {
            function = approximationMethod.exp(data);
            method = Method.EXP;
        } else if (view.getLogApproximation().isSelected()) {
            function = approximationMethod.log(data);
            method = Method.LOG;
        }
        return function;
    }

    private double[][] dropPoint(double[][] data, String function) {
        dropId = approximationMethod.findDropPointId(data, function);
        double[][] copyData = new double[data.length - 1][2];
        int offset = 0;
        for (int i = 0; i < data.length; i++) {
            if (i == dropId) {
                offset = 1;
            } else {
                copyData[i - offset][0] = data[i][0];
                copyData[i - offset][1] = data[i][1];
            }
        }
        return copyData;
    }
}

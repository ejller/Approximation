package controllers;

import model.ApproximationMethod;
import model.Type;
import views.Chart;
import views.MainView;

import javax.swing.*;
import java.awt.*;

public class MainController {
    private MainView view;
    private int sizeTable;
    private Type type;
    private int dropId;
    private Chart chart;

    public MainController() {
        view = new MainView(1300, 800);
        addActionListener();
    }

    private void addActionListener() {
        view.getButtonSize().addActionListener(li -> {
            sizeTable = Integer.parseInt(view.getCountField().getText());
            view.getPanelTableAndInput().remove(view.getPanelWrapperTable());
            GridBagConstraints baseConstraints = new GridBagConstraints();
            JScrollPane wrapperTable = view.initTable(sizeTable);
            view.setPanelWrapperTable(wrapperTable);
            baseConstraints.gridy = 2;
            baseConstraints.anchor = GridBagConstraints.LAST_LINE_START;
            view.getPanelTableAndInput().add(wrapperTable, baseConstraints);
            view.getPanelTableAndInput().revalidate();
            view.getPanelTableAndInput().repaint();
        });

        view.getButtonChart().addActionListener(li -> {
            double[][] data = dataProcessing();
            String function1, function2;
            try {
                function1 = getFunction(data);
                function2 = getFunction(dropPoint(data, function1));
            } catch (IllegalArgumentException e) {
                view.getErrorLabel().setText(e.getMessage());
                return;
            } catch (ArithmeticException e) {
                view.getErrorLabel().setText("Введите корректные значения в таблицу");
                return;
            }
            if (chart != null) {
                view.getPanelChart().remove(chart.getJPanel());
            }
            view.getErrorLabel().setText("");
            chart = new Chart(data, function1, function2, dropId, type);
            new ChartController(chart, function1, function2);
            view.getPanelChart().add(chart.getJPanel());
            view.getPanelChart().revalidate();
            view.getPanelChart().repaint();

        });
    }

    private double[][] dataProcessing() {
        double[][] data = new double[sizeTable][sizeTable];
        for (int i = 0; i < sizeTable; i++) {
            data[i][0] = Double.parseDouble(view.getValueTable().getModel().getValueAt(i, 0) + "");
            data[i][1] = Double.parseDouble(view.getValueTable().getModel().getValueAt(i, 1) + "");
        }
        return data;
    }

    private String getFunction(double[][] data) {

        ApproximationMethod approximationMethod = new ApproximationMethod();
        String function;

        switch (view.getButtonGroup().getSelection().getActionCommand()) {
            case "LINEAR": {
                function = approximationMethod.linear(data);
                type = Type.LINEAR;
                break;
            }
            case "HYPERBOLA": {
                if (isPointsContainsIllegalValue(data, true, 0))
                    throw new IllegalArgumentException("x Не должен быть равен 0");
                function = approximationMethod.hyperbola(data);
                type = Type.HYPERBOLA;
                break;
            }
            case "POWER": {
                if (isPointsContainsIllegalValue(data, false, 0))
                    throw new IllegalArgumentException("x Должнен быть больше 0");
                if (isPointsContainsIllegalValue(data, false, 1))
                    throw new IllegalArgumentException("y Должнен быть больше 0");
                function = approximationMethod.power(data);
                type = Type.POWER;
                break;
            }
            case "SQUARE": {
                function = approximationMethod.square(data);
                type = Type.SQUARE;
                break;
            }
            case "EXP": {
                if (isPointsContainsIllegalValue(data, false, 1))
                    throw new IllegalArgumentException("y Должен быть больше 0");
                function = approximationMethod.exp(data);
                type = Type.EXP;
                break;
            }
            case "LOG": {
                if (isPointsContainsIllegalValue(data, false, 0))
                    throw new IllegalArgumentException("x Должен быть больше 0");
                function = approximationMethod.log(data);
                type = Type.LOG;
                break;
            }
            default:
                throw new IllegalArgumentException("Введите корректные значения в таблицу");
        }

        return function;
    }

    private double[][] dropPoint(double[][] data, String function) {
        ApproximationMethod approximationMethod = new ApproximationMethod();
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


    private boolean isPointsContainsIllegalValue(double[][] data, boolean zeroCheck, int value) {
        for (int row = 0; row < data.length; row++) {
             if (zeroCheck) {
                if (data[row][value] == 0) return true;
            } else {
                if (data[row][value] <= 0) return true;
            }
        }
        return false;
    }
}


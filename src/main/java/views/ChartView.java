package views;

import components.Chart;

import javax.swing.*;
import java.awt.*;

public class ChartView {
    private JFrame window;
    private JPanel jPanel;
    private JButton button;
    private JTextField coefficientField;
    private JLabel coefficientFunction1Label;
    private JLabel coefficientFunction2Label;

    public ChartView(Double[][] data, String function1, String function2, int dropId){
        setElement(data, function1, function2, dropId);

        window = new JFrame();
        setSetting();
        window.add(jPanel);
    }

    private void setSetting(){
        window.setVisible(true);
        window.setBounds(300,300,1100,700);
    }

    private void setElement(Double[][] data, String function1, String function2, int dropId){
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();
        jPanel.add(new Chart(500,500, function1, function2, dropId, data), baseConstraints);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(0,1));
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel descriptionFunction1Label = new JLabel("Зеленый график (до исключения точки): "+function1);
        descriptionFunction1Label.setFont(new Font(descriptionFunction1Label.getFont().getName(), Font.BOLD, 13));
        JLabel descriptionFunction2Label = new JLabel("Синий график (после исключения точки): "+function2);
        descriptionFunction2Label.setFont(new Font(descriptionFunction2Label.getFont().getName(), Font.BOLD, 13));
        JLabel descriptionPointDropLabel = new JLabel("Координаты исключенной точки: ("+data[dropId][0]+"; "+data[dropId][1]+")");

        coefficientFunction1Label = new JLabel("");
        coefficientFunction2Label = new JLabel("");

        JLabel descriptionCoefficientLabel = new JLabel("Введите номер точки, чтобы посмотреть ее значение в каждой функции: ");
        coefficientField = new JTextField(15);
        button = new JButton("Посмотреть");

        descriptionPanel.add(descriptionFunction1Label);
        descriptionPanel.add(descriptionFunction2Label);
        descriptionPanel.add(descriptionPointDropLabel);
        descriptionPanel.add(coefficientFunction1Label);
        descriptionPanel.add(coefficientFunction2Label);
        descriptionPanel.add(descriptionCoefficientLabel);
        descriptionPanel.add(coefficientField);
        descriptionPanel.add(button);

        baseConstraints.gridx=1;
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
}

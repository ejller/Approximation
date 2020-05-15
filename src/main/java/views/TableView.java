package views;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TableView {
    private JFrame tableWindow;
    private JPanel jPanel;
    private int width;
    private int height;
    private JTable valueTable;
    private JButton button;
    private JRadioButton linearApproximation;
    private JRadioButton squareApproximation;
    private JRadioButton powerApproximation;
    private JRadioButton hyperbolaApproximation;
    private JRadioButton expApproximation;
    private JRadioButton logApproximation;
    private JRadioButton indicativeApproximation;

    public TableView(int width, int height, int sizeTable){
        this.width=width;
        this.height=height;
        setElement(sizeTable);

        tableWindow = new JFrame("Educational work №3");
        tableWindow.getContentPane().add(jPanel);
        setSetting();

    }

    private void setSetting(){
        tableWindow.setVisible(true);
        tableWindow.setSize(width, height);
    }

    private void setElement(int sizeTable) {
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();

        valueTable = initTable(sizeTable);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(valueTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(valueTable, BorderLayout.CENTER);

        JLabel descriptionLabel = new JLabel("Введите значения: ");
        descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), Font.BOLD, 13));

        ButtonGroup buttonGroup = new ButtonGroup();
        linearApproximation = new JRadioButton("Линейная аппроксимация");
        squareApproximation = new JRadioButton("Квадратичная аппроксимация");
        powerApproximation = new JRadioButton("Степенная аппроксимация");
        hyperbolaApproximation = new JRadioButton("Гиперболическая аппроксимация");
        //indicativeApproximation = new JRadioButton("Показательная аппроксимация");
        logApproximation = new JRadioButton("Логарифмическая аппроксимация");
        expApproximation = new JRadioButton("Экспонециальная аппроксимация");

        JPanel functionTypePanel = new JPanel();
        functionTypePanel.setLayout(new GridLayout(0,1));
        functionTypePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        buttonGroup.add(linearApproximation);
        buttonGroup.add(squareApproximation);
        buttonGroup.add(powerApproximation);
        buttonGroup.add(hyperbolaApproximation);
        //buttonGroup.add(indicativeApproximation);
        buttonGroup.add(expApproximation);
        buttonGroup.add(logApproximation);
        functionTypePanel.add(linearApproximation);
        functionTypePanel.add(squareApproximation);
        functionTypePanel.add(powerApproximation);
        functionTypePanel.add(hyperbolaApproximation);
        //functionTypePanel.add(indicativeApproximation);
        functionTypePanel.add(expApproximation);
        functionTypePanel.add(logApproximation);

        linearApproximation.setSelected(true);

        button = new JButton("Ок");

        jPanel.add(descriptionLabel, baseConstraints);
        baseConstraints.gridy = 1;
        jPanel.add(panel, baseConstraints);

        baseConstraints.gridx = 1;
        jPanel.add(functionTypePanel, baseConstraints);


        baseConstraints.gridy = 2;
        jPanel.add(button, baseConstraints);

    }

    private JTable initTable(int size){
        Vector columnNames =  new Vector<String>(2);
        columnNames.addElement("X");
        columnNames.addElement("Y");

        Vector<Vector<String>> dataTable = new Vector<>(size);
        for (int i = 0; i< size; i++){
            Vector preData = new Vector<String>(2);
            preData.addElement("");
            preData.addElement("");
            dataTable.addElement(preData);
        }

        JTable table = new JTable(dataTable, columnNames);
        table.setCellSelectionEnabled(true);
        return table;
    }

    public JTable getValueTable() {
        return valueTable;
    }

    public JButton getButton() {
        return button;
    }

    public JRadioButton getLinearApproximation() {
        return linearApproximation;
    }

    public JRadioButton getPowerApproximation() {
        return powerApproximation;
    }

    public JRadioButton getSquareApproximation() {
        return squareApproximation;
    }

    public JRadioButton getHyperbolaApproximation() {
        return hyperbolaApproximation;
    }

    public JRadioButton getExpApproximation() {
        return expApproximation;
    }

    public JRadioButton getIndicativeApproximation() {
        return indicativeApproximation;
    }

    public JRadioButton getLogApproximation() {
        return logApproximation;
    }
}

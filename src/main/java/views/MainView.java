package views;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainView {
    private int width;
    private int height;
    private JFrame window;
    private JPanel view;
    private JPanel panelChart;
    private JPanel panelTableAndInput;
    private JScrollPane panelWrapperTable;
    private JTable valueTable;
    private JTextField countField;
    private JButton buttonSize;
    private JButton buttonChart;

    private JRadioButton linearApproximation;
    private JRadioButton squareApproximation;
    private JRadioButton powerApproximation;
    private JRadioButton hyperbolaApproximation;
    private JRadioButton expApproximation;
    private JRadioButton logApproximation;


    public MainView(int width, int height) {
        this.width = width;
        this.height = height;
        setElement();

        window = new JFrame("Educational work №3");
        window.getContentPane().add(view);
        setSetting();
    }

    private void setSetting() {
        window.setVisible(true);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        window.setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2, width, height);
    }

    private void setElement() {

        panelTableAndInput = new JPanel();
        panelTableAndInput.setLayout(new GridBagLayout());
        GridBagConstraints basePanelTableAndInputConstraints = new GridBagConstraints();
        basePanelTableAndInputConstraints.anchor = GridBagConstraints.LAST_LINE_START;

        JLabel descriptionLabel = new JLabel("Введите количество пар (минимально 4): ");
        descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), Font.BOLD, 14));
        countField = new JTextField(15);
        buttonSize = new JButton("Ок");
        JPanel inputPanel = new JPanel();
        inputPanel.add(countField);
        inputPanel.add(buttonSize);

        panelTableAndInput.add(descriptionLabel);
        basePanelTableAndInputConstraints.gridy = 1;
        panelTableAndInput.add(inputPanel, basePanelTableAndInputConstraints);

        panelWrapperTable = initTable(0);
        basePanelTableAndInputConstraints.gridy = 2;
        panelTableAndInput.add(panelWrapperTable, basePanelTableAndInputConstraints);

        JPanel functionTypePanel = new JPanel();
        functionTypePanel.setLayout(new GridLayout(0, 1));
        functionTypePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 20));
        ButtonGroup buttonGroup = new ButtonGroup();
        linearApproximation = new JRadioButton("Линейная аппроксимация");
        squareApproximation = new JRadioButton("Квадратичная аппроксимация");
        powerApproximation = new JRadioButton("Степенная аппроксимация");
        hyperbolaApproximation = new JRadioButton("Гиперболическая аппроксимация");
        logApproximation = new JRadioButton("Логарифмическая аппроксимация");
        expApproximation = new JRadioButton("Экспонециальная аппроксимация");
        buttonGroup.add(linearApproximation);
        buttonGroup.add(squareApproximation);
        buttonGroup.add(powerApproximation);
        buttonGroup.add(hyperbolaApproximation);
        buttonGroup.add(expApproximation);
        buttonGroup.add(logApproximation);
        functionTypePanel.add(linearApproximation);
        functionTypePanel.add(squareApproximation);
        functionTypePanel.add(powerApproximation);
        functionTypePanel.add(hyperbolaApproximation);
        functionTypePanel.add(expApproximation);
        functionTypePanel.add(logApproximation);
        linearApproximation.setSelected(true);
        basePanelTableAndInputConstraints.gridy = 3;
        panelTableAndInput.add(functionTypePanel, basePanelTableAndInputConstraints);

        buttonChart = new JButton("Построить график");
        basePanelTableAndInputConstraints.gridy = 4;
        panelTableAndInput.add(buttonChart, basePanelTableAndInputConstraints);

        panelChart = new JPanel();
        panelChart.setLayout(new GridBagLayout());
        panelChart.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 60));

        view = new JPanel();
        view.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();

        view.add(panelTableAndInput);
        baseConstraints.gridx = 1;
        view.add(panelChart, baseConstraints);

    }

    public JScrollPane initTable(int size) {
        //Vector of swing documentation
        Vector columnNames = new Vector<String>(2);
        columnNames.addElement("X");
        columnNames.addElement("Y");

        Vector<Vector<String>> dataTable = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            Vector preData = new Vector<String>(2);
            preData.addElement("");
            preData.addElement("");
            dataTable.addElement(preData);
        }

        valueTable = new JTable(dataTable, columnNames);
        valueTable.setCellSelectionEnabled(true);

        JPanel panelWrapperTable = new JPanel();
        panelWrapperTable.setLayout(new BorderLayout());
        panelWrapperTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelWrapperTable.add(valueTable.getTableHeader(), BorderLayout.NORTH);
        panelWrapperTable.add(valueTable, BorderLayout.CENTER);

        JScrollPane scroller = new JScrollPane(panelWrapperTable);
        int height = 45;
        if (size < 15) {
            height += size * 15;
        } else {
            height += 15 * 15;
        }
        scroller.setPreferredSize(new Dimension(300, height));
        scroller.setBorder(null);
        return scroller;
    }


    public JPanel getPanelChart() {
        return panelChart;
    }

    public JTable getValueTable() {
        return valueTable;
    }

    public void setPanelWrapperTable(JScrollPane panelWrapperTable) {
        this.panelWrapperTable = panelWrapperTable;
    }

    public JPanel getPanelTableAndInput() {
        return panelTableAndInput;
    }

    public JScrollPane getPanelWrapperTable() {
        return panelWrapperTable;
    }

    public JButton getButtonSize() {
        return buttonSize;
    }

    public JButton getButtonChart() {
        return buttonChart;
    }

    public JTextField getCountField() {
        return countField;
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


    public JRadioButton getLogApproximation() {
        return logApproximation;
    }
}
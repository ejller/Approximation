package views;
import javax.swing.*;
import java.awt.*;

public class MainView {
    private JFrame window;
    private JPanel jPanel;
    private int width;
    private int height;
    private JTextField countField;
    private JButton button;


    public MainView(int width, int height){

        this.width=width;
        this.height=height;
        setElement();

        window = new JFrame("Educational work №3");
        window.getContentPane().add(jPanel);
        setSetting();

    }
    private void setSetting(){
        window.setVisible(true);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        window.setBounds(dimension.width/2-width/2,dimension.height/2-height/2,width,height);
    }

    private void setElement() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints baseConstraints = new GridBagConstraints();

        JLabel descriptionLabel = new JLabel("Введите количество пар значений (минимально 4): ");
        descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), Font.BOLD, 14));
        countField = new JTextField(15);

        button = new JButton("Ок");

        jPanel.add(descriptionLabel);

        baseConstraints.gridy = 1;
        jPanel.add(countField, baseConstraints);

        baseConstraints.gridy = 2;
        jPanel.add(button, baseConstraints);
    }

    public JButton getButton() {
        return button;
    }

    public JTextField getCountField() {
        return countField;
    }
}

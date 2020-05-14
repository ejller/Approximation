package controllers;

import views.MainView;

public class MainController {
    private MainView view;
    public MainController(){
        view = new MainView(400,300);
        addActionListener();
    }
    private void addActionListener() {
        view.getButton().addActionListener(li -> {
            int size = Integer.parseInt(view.getCountField().getText());
            new TableController(size);
        });
    }
}

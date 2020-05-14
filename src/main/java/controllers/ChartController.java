package controllers;

import views.ChartView;

public class ChartController {

    private ChartView view;

    ChartController(Double[][] data, String function1, String function2, int dropId){
        view = new ChartView(data,function1, function2, dropId);
    }
}

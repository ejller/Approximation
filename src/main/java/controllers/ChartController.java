package controllers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import views.ChartView;

class ChartController {

    private ChartView view;
    private String function1;
    private String function2;
    private Double[][] data;

    ChartController(Double[][] data, String function1, String function2, int dropId){
        this.function1=function1;
        this.function2=function2;
        this.data = data;
        view = new ChartView(data,function1, function2, dropId);
        addActionListener();
    }

    private void addActionListener() {
        view.getButton().addActionListener(li -> {
            double x = data[Integer.parseInt(view.getCoefficientField().getText())-1][0];
            view.getCoefficientFunction1Label().setText("Для зеленого графика: "+getCoefficient(x, function1));
            view.getCoefficientFunction2Label().setText("Для синего графика: "+getCoefficient(x, function2));
        });
    }

    private Double getCoefficient(double x, String function){
        try {
            Expression e = new ExpressionBuilder(function)
                    .variables("x")
                    .build()
                    .setVariable("x", x);
            return (e.evaluate());
        } catch (java.lang.ArithmeticException e){
            return 0.0;
        }
    }
}

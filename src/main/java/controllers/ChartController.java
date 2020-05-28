package controllers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import util.Method;
import views.Chart;

class ChartController {

    private Chart view;
    private String function1;
    private String function2;

    ChartController(Chart view, String function1, String function2){
        this.view = view;
        this.function1 = function1;
        this.function2 = function2;
        addActionListener();
    }

    private void addActionListener() {
        view.getButton().addActionListener(li -> {
            Double x;
            try {
                x = Double.parseDouble(view.getCoefficientField().getText());
            } catch (NumberFormatException e) {
                view.getCoefficientFunction1Label().setText("Недопустимое значение x");
                return;
            }
            try {
                Double value = getCoefficient(x, function1);
                view.getCoefficientFunction1Label().setText("Значение до апроксимации: "+value);
            } catch (ArithmeticException e) {
                view.getCoefficientFunction1Label().setText("Недопустимое значение x");
            }

            try {
                Double value = getCoefficient(x, function2);
                view.getCoefficientFunction2Label().setText("Значение после апроксимации: "+value);
            } catch (ArithmeticException e) {
                view.getCoefficientFunction2Label().setText("Недопустимое значение x");
            }

        });
    }

    private Double getCoefficient(double x, String function) throws ArithmeticException{
            Expression e = new ExpressionBuilder(function)
                    .variables("x")
                    .build()
                    .setVariable("x", x);
            return (e.evaluate());
    }
}

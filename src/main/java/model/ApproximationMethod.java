package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.util.Precision;

import static java.lang.Math.abs;

public class ApproximationMethod {

    public int findDropPointId(Double[][] points, String formula) {
        Expression expression = new ExpressionBuilder(formula).variable("x").build();
        Double maxDelta = 0.0;
        int idx = 1;
        for (int i = 0; i<points.length; i++) {
            Double x = points[i][0];
            Double y = points[i][1];
            if (abs(expression.setVariable("x", x).evaluate() - y) > maxDelta) {
                maxDelta = abs(expression.setVariable("x", x).evaluate() - y);
                idx = i;
            }
        }
        return idx;
    }

    public String linear(Double[][] data){
        Double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            sum[0] += data[i][0]; //x
            sum[1] += data[i][1]; //y
            sum[2] += sum[0] * sum[1]; //xy
            sum[3] += Math.pow(sum[0], 2.0); //x2
        }
        double del = Math.pow(sum[0], 2.0) - data.length * sum[3];
        double a =  Precision.round((sum[0] * sum[1] - data.length * sum[2]) / del, 5);
        double b =  Precision.round((sum[0] * sum[2] - sum[3] * sum[1]) / del, 5);
        if (b >= 0) {
            return a+"*x+"+b;
        } else {
            return a+"*x"+b;
        }
    }

    public String square(Double[][] data) {
        Double[] sum = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sum[0] += x; //x
            sum[1] += y; //y
            sum[2] += Math.pow(x, 2.0); //x2
            sum[3] += Math.pow(x, 3.0); //x3
            sum[4] += Math.pow(x, 4.0);//x4
            sum[5] += x * y; //xy
            sum[6] += Math.pow(x, 2.0) * y; //x2y
        }
        Double[][] matrix = new Double[3][3];
        matrix[0][0] = sum[2];
        matrix[0][1] = sum[0];
        matrix[0][2] = data.length * 1.0;
        matrix[1][0] = sum[3];
        matrix[1][1] = sum[2];
        matrix[1][2] = sum[0];
        matrix[2][0] = sum[4];
        matrix[2][1] = sum[3];
        matrix[2][2] = sum[2];
        Double d = determinant(matrix);

        matrix[0][0] = sum[1];
        matrix[1][0] = sum[5];
        matrix[2][0] = sum[6];
        Double da = determinant(matrix);

        matrix[0][0] = sum[2];
        matrix[1][0] = sum[3];
        matrix[2][0] = sum[4];
        matrix[0][1] = sum[1];
        matrix[1][1] = sum[5];
        matrix[2][1] = sum[6];
        Double db = determinant(matrix);

        matrix[0][1] = sum[0];
        matrix[1][1] = sum[2];
        matrix[2][1] = sum[3];
        matrix[0][2] = sum[1];
        matrix[1][2] = sum[5];
        matrix[2][2] = sum[6];
        Double dc = determinant(matrix);

        double a =  Precision.round(da / d,5);
        double b =  Precision.round(db / d,5);
        double c =  Precision.round(dc / d,5);
        String result = a+"*x^2";
        if (b >= 0) {
            result += "+"+b+"*x";
        } else {
            result += b+"*x";
        }
        if (c >= 0) {
            result += "+"+c;
        } else {
            result += c;
        }
        return result;
    }

    public String power(Double[][] data) {
        Double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sum[0] += Math.log(x); //x
            sum[1] += Math.log(y); //y
            sum[2] += Math.pow(Math.log(x), 2); //x2
            sum[3] += Math.log(x) * Math.log(y); //xy
        }

        double b =  Precision.round((data.length * sum[3] - sum[0] * sum[1]) / (data.length * sum[2] - Math.pow(sum[0],2)), 5);
        double a =  Precision.round(Math.exp(1.0 / data.length * sum[1] - b / data.length * sum[0]), 5);
        return a+ "* x ^ "+b;
    }


    public String hyperbola(Double[][] data) {
        Double[] sum = {0.0, 0.0, 0.0, 0.0};
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sum[0] += 1 / x; //x
            sum[1] += 1 / Math.pow(x, 2.0); //x2
            sum[2] += y / x; //yx
            sum[3] += y; //y
        }

        double b =  Precision.round((data.length * sum[2] - sum[0] * sum[3]) / (data.length * sum[1] - Math.pow(sum[0], 2)), 5);
        double a =  Precision.round(1.0 / data.length * sum[3] - b / data.length * sum[0], 5);
        if (b > 0) {
            return a + "+" + b + "/x";
        } else {
            return a + "" + b + "/x";
        }
    }


    private double determinant(Double[][] a) {
        double sum = a[0][0]*(a[1][1]*a[2][2]-a[1][2]*a[2][1]);
        sum-= a[1][0]*(a[0][1]*a[2][2]-a[0][2]*a[2][1]);
        sum+= a[2][0]*(a[0][1]*a[1][2]-a[0][2]*a[1][1]);
        return sum;
    }

}

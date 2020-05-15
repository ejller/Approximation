package controllers;

import model.ApproximationMethod;
import views.TableView;

class TableController {
    private TableView view;
    private int size;
    private int dropId;

    TableController(int size){
        this.size = size;
        view = new TableView(600,300, size);
        addActionListener();
    }

    private void addActionListener() {
        view.getButton().addActionListener(li -> {
            Double[][] data = dataProcessing();
            String function1 = getFunction(data);
            String function2 = getFunction(dropPoint(data,function1));
            new ChartController(data, function1, function2, dropId);


        });
    }

    private Double[][] dataProcessing(){
        Double[][] data = new Double[size][size];
        for (int i=0; i<size; i++){
            data[i][0] = Double.valueOf(view.getValueTable().getModel().getValueAt(i,0)+"");
            data[i][1] = Double.valueOf(view.getValueTable().getModel().getValueAt(i,1)+"");
        }
        return data;
    }

    private String getFunction(Double[][] data){
        ApproximationMethod approximationMethod = new ApproximationMethod();
        String function ="";
        if (view.getLinearApproximation().isSelected()) {
            function = approximationMethod.linear(data);
        } else if (view.getHyperbolaApproximation().isSelected()){
            function = approximationMethod.hyperbola(data);
        } else if (view.getPowerApproximation().isSelected()){
            function = approximationMethod.power(data);
        } else if (view.getSquareApproximation().isSelected()){
            function = approximationMethod.square(data);
        }
        return function;
    }

    private Double[][] dropPoint(Double[][] data, String function){
        ApproximationMethod approximationMethod = new ApproximationMethod();
        dropId = approximationMethod.findDropPointId(data, function);
        Double [][] copyData = new Double[data.length-1][2];
        int offset = 0;
        for (int i=0; i<data.length; i++) {
            if (i == dropId) {
                offset = 1;
            } else {
                copyData[i - offset][0] = data[i][0];
                copyData[i - offset][1] = data[i][1];
            }
        }
        return copyData;
    }

}

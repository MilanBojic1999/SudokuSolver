package Sudoku;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Stage {

    private GraphicsContext gc;
    private Spot[][] spots;
    private Spot selected;

    public App(Spot[][] spots) {
        this.spots=spots;
        Canvas canvas=new Canvas(900,900);
        Button solver=new Button("Solve Sudoku");
        solver.setPrefSize(900,100);
        VBox vBox=new VBox();
        Group group=new Group(solver);
        vBox.getChildren().addAll(canvas,group);
        vBox.setAlignment(Pos.TOP_CENTER);
        Scene scene=new Scene(vBox,900,1000);
        gc=canvas.getGraphicsContext2D();
        for(int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                spots[i][j]=new Spot(gc,i,j);
            }
        }

        scene.setOnKeyReleased(event -> {
            String kc=event.getText();
            if(isNum(kc)) {
                int num = Integer.parseInt(kc);
                if (SudokuRules.chechSpot(spots, selected, num) || num == 0){
                    selected.setNum(num);
                    selected.show();
                }else
                    selected.showMistake();
            }
        });

        scene.setOnMouseClicked(event -> {
            int j=(int) event.getY()/100;
            int i=(int) event.getX()/100;
            for (int i1=0;i1<9;i1++){
                for(int j1=0;j1<9;j1++){
                    spots[i1][j1].setSelected(false);
                }
            }
            spots[i][j].setSelected(true);
            selected=spots[i][j];
            showAll();
            selected.show();
        });

        solver.setOnMouseClicked(event -> {
            SudokuRules.fillSudoku(spots);
            if(selected!=null) {
                selected.setSelected(false);
                selected = null;
            }
            showAll();
        });
        showAll();
        setLine(gc);
        setScene(scene);
        show();
    }

    private void showAll(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                spots[i][j].show();
            }
        }
        setLine(gc);
    }

    private void setLine(GraphicsContext gc){
        gc.setLineWidth(10);
        gc.strokeLine(300,0,300,900);
        gc.strokeLine(600,0,600,900);
        gc.strokeLine(0,300,900,300);
        gc.strokeLine(0,600,900,600);
    }

    private static boolean isNum(String str){
        try{
            Integer.parseInt(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}

package Sudoku;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    private static Spot[][] spots;
    private Spot selected;
    private GraphicsContext gc;
    private Stack<Spot> stack=new Stack<>();
    @Override
    public void start(Stage primaryStage) throws Exception {
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
            if(isNum(kc)){
                int num=Integer.parseInt(kc);
                if(chechSpot(selected,num) || num==0)
                    selected.setNum(num);
                else
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
        });

        solver.setOnMouseClicked(event -> {
            fillSudoku();
            if(selected!=null) {
                selected.setSelected(false);
                selected = null;
            }
        });

        setLine(gc);

        primaryStage.setScene(scene);
        primaryStage.show();
        stack.push(spots[0][0]);
        at.start();
    }

    public static void main(String[] args) {
        spots=new Spot[10][10];
        launch(args);
    }

    AnimationTimer at=new AnimationTimer() {

        @Override
        public void handle(long now) {
            if(now % 10 != 0) {
                return;
            }
            show();
            //fillSudoku();

        }
    };

    public boolean fillSudoku(){
        Spot spot=isEmpty();
        if(spot==null)
            return true;
        for(int num=1;num<10;num++){
            if(chechSpot(spot,num)){
                spot.setNum(num);

                if(fillSudoku())
                    return true;

                spot.setNum(0);
            }
        }
        return false;
    }

    private void show(){
        for(int i=0;i<9;i++){
            for (int j=0;j<9;j++){
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
    private boolean isNum(String str){
        try{
            Integer.parseInt(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public Spot isEmpty(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(spots[i][j].getNum()==0)
                    return spots[i][j];
            }
        }
        return null;
    }

    public boolean chechSpot(Spot spot,int num){
        int i=spot.getI();
        int j=spot.getJ();
        int ni=i - i % 3;
        int nj=j - j % 3;
        for(int i1=0;i1<9;i1++){
            if(spots[i1][j].getNum()==num){
                return false;
            }
        }

        for(int j1=0;j1<9;j1++){
            if(spots[i][j1].getNum()==num)
                return false;
        }

        for(int i1=ni;i1<ni+3;i1++){
            for(int j1=nj;j1<nj+3;j1++){
                if(i1==i || j1==j)
                    continue;
                if(spots[i1][j1].getNum()==num)
                    return false;
            }
        }

        return true;
    }
}

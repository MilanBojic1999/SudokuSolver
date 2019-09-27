package Sudoku;


import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class Main extends Application {

    private Spot[][] spots;

    @Override
    public void start(Stage primaryStage) throws Exception {
        spots=new Spot[9][9];
        new App(spots);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

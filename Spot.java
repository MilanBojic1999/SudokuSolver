package Sudoku;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Spot {
    private int w,h;
    private int num;
    private GraphicsContext gc;
    private int i,j;
    private boolean selected;

    public Spot(GraphicsContext gc,int i,int j) {
        this.gc = gc;
        this.i=i;
        this.j=j;
        w=h=100;
        selected=false;
        gc.setFont(Font.font(50));
    }

    public void show(){
        if(selected){
            gc.setFill(Color.SILVER);
            gc.setLineWidth(7);
        }else{
            gc.setFill(Color.SKYBLUE);
            gc.setLineWidth(2);
        }
        gc.strokeRect(i*100,j*100,h,w);
        gc.fillRect(i*100,j*100,h,w);
        gc.setFill(Color.BLACK);
        if(num!=0){
            gc.fillText(Integer.toString(num),i*100+40,j*100+60);
        }
    }

    public void showMistake(){
        gc.setLineWidth(7);
        gc.setFill(Color.INDIANRED);
        gc.strokeRect(i*100,j*100,h,w);
        gc.fillRect(i*100,j*100,h,w);
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return i+" "+j;
    }

    public boolean isEmpty(){
        return num==0;
    }

    public int getNum() {
        return num;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}

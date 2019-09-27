package Sudoku;

public class SudokuRules {


    public static boolean fillSudoku(Spot[][] spots){
        Spot spot=isEmpty(spots);
        if(spot==null)
            return true;
        for(int num=1;num<10;num++){
            if(chechSpot(spots,spot,num)){
                spot.setNum(num);

                if(fillSudoku(spots))
                    return true;

                spot.setNum(0);
            }
        }
        return false;
    }



    public static Spot isEmpty(Spot[][] spots){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(spots[i][j].getNum()==0)
                    return spots[i][j];
            }
        }
        return null;
    }

    public static boolean chechSpot(Spot[][] spots,Spot spot,int num){
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

package com.example.ferhat.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PaintGrid extends View {

    private int size;
    private char[][] gameCells;
    public PaintGrid(Context context, char[][] cells, int dim){
        super(context);
        setFocusable(true);
        gameCells =cells;
        size =dim;
    }

    protected void onDraw(Canvas c) {

        int startX,startY,diameter,blank;
        startX =50; startY =100; diameter =40;
        blank =2*diameter;

        Paint paintBlack = new Paint();
        paintBlack.setColor(getContext().getColor(R.color.black));
        Paint paintRed =new Paint();
        paintRed.setColor(getContext().getColor(R.color.red));
        Paint paintBlue =new Paint();
        paintBlue.setColor(getContext().getColor(R.color.blue));
        Paint paintGreen =new Paint();
        paintGreen.setColor(getContext().getColor(R.color.green));

        Paint paintGrid =new Paint();
        paintGrid.setColor(getContext().getColor(R.color.gridBackground));

        Paint paintBorder =new Paint();
        paintBorder.setColor(getContext().getColor(R.color.border));

        c.drawRoundRect(startX-15,startY-15,(size*blank)-diameter+startX+15,(size*blank)-diameter+startY+15,10,10,paintBorder);
        c.drawRoundRect(startX-10,startY-10,(size*blank)-diameter+startX+10,(size*blank)-diameter+startY+10,10,10,paintGrid);

        for (int j = startY,gameRow=0; j < (size*blank)+startY; j += blank,++gameRow) {
            for (int i = startX,gameCol=0; i < (size*blank)+startX; i += blank,++gameCol) {
                if (gameCells[gameRow][gameCol] == 'X') {
                    c.drawOval(i, j, i + diameter, j + diameter, paintRed);
                } else if (gameCells[gameRow][gameCol] == 'O') {
                    c.drawOval(i, j, i + diameter, j + diameter, paintBlue);
                }
                else if(gameCells[gameRow][gameCol] == 'x' || gameCells[gameRow][gameCol] == 'o'){
                    c.drawOval(i, j, i + diameter, j + diameter, paintGreen);
                }
                else if(gameCells[gameRow][gameCol] == '.') {
                    c.drawOval(i, j, i + diameter, j + diameter, paintBlack);
                }
            }
        }
    }
}

package com.example.ferhat.connectfour;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ImageButton left;
    private ImageButton right;
    private ImageButton single;
    private ImageButton multi;
    private ImageButton settingB;
    private ImageButton returnB;
    private ImageButton homeB;
    private ImageButton undoB;
    private ImageButton againB;
    private PaintGrid grid;
    private TextView timeText;
    private TimeHandler timer;
    private int size;
    private char[][] gameCells;
    private char[][][] backupGameCells;
    private String[] backupPlayer;
    private int backupCount;
    private int backupCapacity;
    private String currentPlayer;
    char gameType;
    private final int ToWin =4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        left =(ImageButton)findViewById(R.id.leftB);
        left.setOnClickListener(new ButtonHandler("left"));
        right =(ImageButton)findViewById(R.id.rightB);
        right.setOnClickListener(new ButtonHandler("right"));
        single =(ImageButton)findViewById(R.id.singleB);
        single.setOnClickListener(new ButtonHandler("single"));
        multi =(ImageButton)findViewById(R.id.multiB);
        multi.setOnClickListener(new ButtonHandler("multi"));

        timer =new TimeHandler(30000,1000);
    }

    private void updateBoard(int colNum){

        char letter;
        if("User1".equals(getCurrentPlayer())){
            letter ='X';
        }
        else{
            letter ='O';
        }
        backup();
        for(int i=getSize()-1;0<=i;--i){
            if(gameCells[i][colNum] =='.'){
                gameCells[i][colNum] =letter;
                break;
            }
        }
        grid.invalidate();

        if(isWin(colNum)){

            Handler handler =new Handler();
            handler.postDelayed(new Runnable(){
                public void run(){
                    setContentView(R.layout.result_screen);

                    ImageView result =(ImageView)findViewById(R.id.resultView);
                    if(getCurrentPlayer().equals("User1")){
                        result.setImageResource(R.drawable.red1);
                    }
                    else{
                        result.setImageResource(R.drawable.blue1);
                    }

                    againB =(ImageButton)findViewById(R.id.again);
                    againB.setOnClickListener(new ButtonHandler("again"));
                    homeB =(ImageButton)findViewById(R.id.home);
                    homeB.setOnClickListener(new ButtonHandler("home"));
                }

            },1500);

        }
        else if(isGameOver()){

            Handler handler =new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    setContentView(R.layout.result_screen);
                    ImageView result =(ImageView)findViewById(R.id.resultView);
                    result.setImageResource(R.drawable.drawn);

                    againB =(ImageButton)findViewById(R.id.again);
                    againB.setOnClickListener(new ButtonHandler("again"));
                    homeB =(ImageButton)findViewById(R.id.home);
                    homeB.setOnClickListener(new ButtonHandler("home"));
                }
            },1500);
        }
        else{
            switchUser();
            timer.start();
        }
    }

    void backup(){

        if(backupCapacity <= backupCount)
            increaseCapacity();
        for(int i=0;i<getSize();++i){
            for(int j=0;j<getSize();++j){
                backupGameCells[backupCount][i][j]=gameCells[i][j];
            }
        }
        backupPlayer[backupCount] =getCurrentPlayer();
        ++backupCount;
    }
    void undo(){
        if(0 < backupCount) {
            if(gameType == 'C'){
                --backupCount;
            }
            --backupCount;
            for (int i = 0; i < getSize(); ++i) {
                for (int j = 0; j < getSize(); ++j) {
                    gameCells[i][j] = backupGameCells[backupCount][i][j];
                }
            }
            currentPlayer =backupPlayer[backupCount];
            grid.invalidate();
        }
    }
    void increaseCapacity(){

        char[][][] temp =new char[2*backupCapacity][getSize()][getSize()];
        String[] tempPlayer =new String[2*backupCapacity];
        for(int k=0;k<backupCapacity;++k){
            temp[k] =backupGameCells[k];
            tempPlayer[k] =backupPlayer[k];
        }
        backupGameCells =temp;
        backupPlayer =tempPlayer;
        backupCapacity *=2;
    }

    private boolean isWin(int LastMove) {

        int row = 0,col;                          //for last move coordinates
        col = LastMove;
        for(int i=0;i<getSize();++i){
            if(gameCells[i][col] !='.'){
                row =i;
                break;
            }
        }

        if(ToWin <= vertical(row,col))
            return true;

        if(ToWin <= horizontal(row,col))
            return true;

        if(ToWin <= diagonal(row,col))
            return true;

        return false;

    }

    private int vertical(int row,int col){  /* checks for vertical */

        int sum,up,down,count;
        sum =1; up =0; down =0;

        for(int i=row+1;i<getSize() && gameCells[row][col] == gameCells[i][col] ;++i){
            ++down;
        }

        for(int i=row-1; 0<= i && gameCells[row][col] ==gameCells[i][col] ;--i){
            ++up;
        }

        sum +=up+down;
        count =0;
        if(ToWin <= sum){
            gameCells[row][col] = Character.toLowerCase(gameCells[row][col]);  //to lowercase
            ++count;

            for(int i=1;i<=up && count <ToWin;++i,++count){

                gameCells[row-i][col] =Character.toLowerCase(gameCells[row][col]);
            }
            for(int i=1; i<=down && count < ToWin ;++i,++count){

                gameCells[i+row][col] =Character.toLowerCase(gameCells[row][col]);
            }
        }
        return sum;
    }

    private int horizontal(int row,int col){   // check for horizontal

        int sum,right,left,count;
        sum =1; right =0; left =0;

        for(int i=col+1; i<getSize() && gameCells[row][col] == gameCells[row][i]; ++i){  // for right side
            ++right;
        }

        for(int i=col-1; 0<= i && gameCells[row][col] == gameCells[row][i] ;--i){  // for left side
            ++left;
        }

        sum +=right+left;
        count =0;
        if(ToWin <= sum){
            gameCells[row][col] =Character.toLowerCase(gameCells[row][col]);       // if win then make them lower case
            ++count;
            for(int i=1; i<=right && count< ToWin ;++i,++count){
                gameCells[row][col+i] =Character.toLowerCase(gameCells[row][col]);
            }


            for(int i=1; i<=left && count < ToWin ;++i,++count){
                gameCells[row][col-i] =Character.toLowerCase(gameCells[row][col]);
            }
        }
        return sum;
    }

    private int diagonal(int row,int col){        // check for diagonal

        int rightToLeft,leftToRight,rightUp,rightDown,leftUp,leftDown;
        int i,j;
        int ret,count;

        rightToLeft =1; leftToRight =1; rightUp=0; rightDown=0; leftUp =0; leftDown =0;

        for(i=row-1,j=col+1; 0<=i&& 0<=j && i<getSize() && j<getSize() && gameCells[row][col] == gameCells[i][j] ;--i,++j){
            ++rightUp;
        }

        for(i=row+1,j=col+1; 0<=i&& 0<=j && i<getSize() && j<getSize() && gameCells[row][col] == gameCells[i][j] ;++i,++j){
            ++rightDown;
        }

        for(i=row-1,j=col-1; 0<=i&& 0<=j && i<getSize() && j<getSize() && gameCells[row][col] == gameCells[i][j] ;--i,--j){
            ++leftUp;
        }

        for(i=row+1,j=col-1; 0<=i&& 0<=j && i<getSize() && j<getSize() && gameCells[row][col] == gameCells[i][j] ;++i,--j){
            ++leftDown;
        }

        rightToLeft +=rightUp+leftDown;
        leftToRight +=leftUp+rightDown;
        count =0;

        if(ToWin <= rightToLeft){                                                  // if win then make them lower case
            gameCells[row][col] =Character.toLowerCase(gameCells[row][col]);
            ++count;
            for(i=1;i<=rightUp && count <ToWin; ++i,++count){

                gameCells[row-i][col+i] =Character.toLowerCase(gameCells[row][col]);
            }
            for(i=1;i<=leftDown && count <ToWin;++i,++count){
                gameCells[row+i][col-i] =Character.toLowerCase(gameCells[row][col]);
            }
            ret =rightToLeft;
        }

        else if(ToWin <= leftToRight){
            gameCells[row][col] =Character.toLowerCase(gameCells[row][col]);
            ++count;

            for(i=1;i<=leftUp && count<ToWin;++i,++count){
                gameCells[row-i][col-i] =Character.toLowerCase(gameCells[row][col]);
            }
            for(i=1;i<=rightDown &&count <ToWin;++i,++count){

                gameCells[row+i][col+i] =Character.toLowerCase(gameCells[row][col]);
            }
            ret =leftToRight;
        }
        else
            ret =0;

        return ret;
    }

    private void play(){    /* this is for computer to play */

        int max; int move = 0;
        boolean used =false;

        for(int i=0; i <getSize() && !used ;++i){           // blocking the opponent
            for(int j=0;j<getSize() && !used;++j){

                if(gameCells[i][j] == 'X'){         /* looking if there is 3 element of opponent  if there is then block it */
                    if(ToWin -1 == vertical(i,j)){
                        if(0 <=(i-1) && gameCells[i-1][j] == '.'){
                            move = j;
                            used =true;
                        }
                    }
                    if(!used && ToWin -1 == horizontal(i,j)){
                        if((j+1) < getSize() && gameCells[i][j+1] =='.'){
                            move = j+1;
                            used =true;
                        }
                        if(0 <= (j-1) && gameCells[i][j-1] == '.'){
                            move = j-1;
                            used =true;
                        }
                    }
                }
            }
        }

        max =0;
        if(!used){                                                   //to win
            for(int i=0;i<getSize();++i){                          // if there is 3 element of its own then try to win
                for(int j=0;j<getSize();++j){

                    if(gameCells[i][j] =='O'){
                        if(1 <= vertical(i,j) && max < vertical(i,j)){           /* for vertical */
                            if( 0<=(i-1) && gameCells[i-1][j] =='.'){
                                move =j;
                                used =true;
                                max =vertical(i,j);
                            }
                        }
                    }
                }
            }

            for(int i=0;i<getSize();++i){
                for(int j=0;j<getSize();++j){
                    if(gameCells[i][j] == 'O'){
                        if(1 <=horizontal(i,j) && max < horizontal(i,j)){
                            if((j+1)<getSize() && gameCells[i][j+1] == '.'){       /* if used in vertical then to be used in horizontal it must be bigger than max */
                                move =j+1;
                                used =true;
                                max =horizontal(i,j);
                            }
                            if(0 <=(j-1) && gameCells[i][j-1] =='.'){
                                move =j-1;
                                used =true;
                                max =horizontal(i,j);
                            }
                        }
                    }
                }
            }
        }

        if(!used){    // if couldn't find a logical move then make a random move

            Random rand =new Random();
            do{
                move =rand.nextInt(getSize());

            }while(!isAvailable(move));
        }
        updateBoard(move);
    }

    private boolean isGameOver(){
        for(int i=0;i<getSize();++i){            // looking at the top side of the board
            if(gameCells[0][i] =='.')
                return false;
        }
        return true;
    }

    private void switchUser(){

        if("User1".equals(currentPlayer)){

            if(gameType =='P'){
                currentPlayer ="User2";
            }
            else{
                currentPlayer ="Computer";
                play();
            }
        }
        else{
            currentPlayer ="User1";
        }
    }

    private String getCurrentPlayer(){

        return currentPlayer;
    }

    private int getSize(){

        return size;
    }

    private void makeBoard(){

        gameCells =new char[size][size];
        backupGameCells =new char[1][size][size];
        backupPlayer =new String[1];
        backupCapacity =1;
        backupCount =0;
        currentPlayer ="User1";
        for(int i=0;i<getSize();++i){
            for(int j=0;j<getSize();++j){
                gameCells[i][j] ='.';
            }
        }
    }
    private boolean isAvailable(int colNum){

        if(gameCells[0][colNum] == '.'){
            return true;
        }

        return false;
    }

    private void setGameScreen(){

        setContentView(R.layout.game_screen);
        grid =new PaintGrid(MainActivity.this,gameCells,size);
        ViewGroup gameLayout=(ViewGroup) findViewById(R.id.game_screen);
        gameLayout.addView(grid,0,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        gameLayout.setOnTouchListener(new PlayGame());

        settingB =(ImageButton)findViewById(R.id.setting);
        settingB.setOnClickListener(new ButtonHandler("setting"));
        undoB =(ImageButton)findViewById(R.id.undo);
        undoB.setOnClickListener(new ButtonHandler("undo"));
        timeText =(TextView)findViewById(R.id.timer);
        timer.start();

        if(gameType == 'C'){
            ImageView temp =(ImageView)findViewById(R.id.player);
            temp.setImageResource(R.drawable.player);
            temp =(ImageView)findViewById(R.id.computer);
            temp.setImageResource(R.drawable.computer);
        }
        else{
            ImageView temp =(ImageView)findViewById(R.id.player);
            temp.setImageResource(R.drawable.player1);
            temp =(ImageView)findViewById(R.id.computer);
            temp.setImageResource(R.drawable.player2);
        }
    }

    private class ButtonHandler implements View.OnClickListener{

        private String id;

        ButtonHandler(String name){

            id =name;
        }

        @Override
        public void onClick(View view) {
            if(id.equals("left")){
                TextView sizeText =(TextView)findViewById(R.id.sizeText);
                int count =Integer.parseInt(sizeText.getText().toString());
                if(4 < count ) {
                    --count;
                    sizeText.setText(Integer.toString(count));
                }
            }
            else if(id.equals("right")){
                TextView sizeText =(TextView)findViewById(R.id.sizeText);
                int count =Integer.parseInt(sizeText.getText().toString());
                if(count < 8){
                    ++count;
                    sizeText.setText(Integer.toString(count));
                }

            }
            else if(id.equals("single")){

                TextView sizeText =(TextView)findViewById(R.id.sizeText);
                size =Integer.parseInt(sizeText.getText().toString());
                makeBoard();
                gameType ='C';

                setGameScreen();
            }
            else if(id.equals("multi")){
                TextView sizeText =(TextView)findViewById(R.id.sizeText);
                size =Integer.parseInt(sizeText.getText().toString());
                makeBoard();
                gameType ='P';

                setGameScreen();
            }
            else if(id.equals("setting")){
                setContentView(R.layout.setting_screen);
                returnB =(ImageButton)findViewById(R.id.returnB);
                returnB.setOnClickListener(new ButtonHandler("return"));
                homeB =(ImageButton)findViewById(R.id.homeB);
                homeB.setOnClickListener(new ButtonHandler("home"));
                timer.cancel();
            }
            else if(id.equals("return")){

                setGameScreen();
            }
            else if(id.equals("home")){
                setContentView(R.layout.activity_main);
                left =(ImageButton)findViewById(R.id.leftB);
                left.setOnClickListener(new ButtonHandler("left"));
                right =(ImageButton)findViewById(R.id.rightB);
                right.setOnClickListener(new ButtonHandler("right"));
                single =(ImageButton)findViewById(R.id.singleB);
                single.setOnClickListener(new ButtonHandler("single"));
                multi =(ImageButton)findViewById(R.id.multiB);
                multi.setOnClickListener(new ButtonHandler("multi"));
                timer.cancel();
            }
            else if(id.equals("again")){

                makeBoard();
                setGameScreen();
            }
            else if(id.equals("undo")){
                undo();
                timer.start();
            }
        }
    }

    private class PlayGame implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view,MotionEvent event) {

            if(event.getAction() ==MotionEvent.ACTION_UP) {
                double startX,startY,diameter,blank;
                startX =  50.0; startY =80.0; diameter =40.0;
                blank =2.0*diameter;
                int colNum;

                float x = event.getX();
                float y = event.getY();

                if(startX-10 <x && x < (size*blank)-diameter+startX+10 &&
                        startY-10 < y && y < (size*blank)-diameter+startY+10) {

                    int count=1; float fixed = (float) (startX+diameter/2.0);
                    if(x <=fixed+diameter ){
                        colNum =0;
                    }
                    else {
                        while (!(fixed + count * diameter < x && x <= fixed + (count + 2) * diameter)) {
                            count += 2;
                        }
                        colNum = (int) Math.round((count+1)/2.0);
                    }
                    if(isAvailable(colNum)){
                        timer.cancel();
                        updateBoard(colNum);
                    }
                }
            }
            return true;
        }
    }
    private class TimeHandler extends CountDownTimer{

        public TimeHandler(long startPoint,long interval){

            super(startPoint,interval);
        }

        @Override
        public void onTick(long l) {

            if(9 < TimeUnit.MILLISECONDS.toSeconds(l)) {
                timeText.setTextColor(getBaseContext().getColor(R.color.colorPrimaryDark));
                timeText.setTextSize(18);
                timeText.setText(String.format("%2d", TimeUnit.MILLISECONDS.toSeconds(l)));
            }
            else if(1 < TimeUnit.MILLISECONDS.toSeconds(l)){
                timeText.setTextColor(getBaseContext().getColor(R.color.red));
                timeText.setTextSize(24);
                timeText.setText(String.format("%d", TimeUnit.MILLISECONDS.toSeconds(l)));
            }
            else{
                timeText.setTextColor(getBaseContext().getColor(R.color.red));
                timeText.setTextSize(14);
                timeText.setText("Time is up!!!");
            }
        }

        @Override
        public void onFinish() {
            play();
        }
    }
}

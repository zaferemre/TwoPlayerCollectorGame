package application;
import javafx.scene.shape.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.geometry.Orientation;
import javafx.event.EventHandler;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;


public class Main extends Application 
{ 
  Stage stage = new Stage();
  TilePane tilePane = new TilePane();
  Scene scene = new Scene(tilePane, 800,400);
  Rectangle rectangle = new Rectangle();
  Rectangle[][] rectArray = new Rectangle[40][20];
  List<Integer> colRed = new ArrayList<Integer>();
  List<Integer> rowRed = new ArrayList<Integer>();
  Random rand = new Random();
  List<Integer> colBlue = new ArrayList<Integer>();
  List<Integer> rowBlue = new ArrayList<Integer>();
  int[][] eggArray = new int[40][20];
  int[][] targetArray = new int[40][20];
    //1 = occupied
  int[][] playArray = new int[40][20];
  int score = 0;
 

  public void start(Stage primaryStage) {
    this.stage = primaryStage;
    playArrayFiller();
    eggArrayFiller();
    targetArray = eggArray.clone();
    
    stage.setTitle("game");
    stage.setScene(scene);
    buildUI();
    stage.show();
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent event){
       // int scoreRed;
       // scoreRed = scoreCalc(playArray[40][20],eggArray[40][20],score);
      //  int scoreBlue = 40 - scoreRed;
        if (colRed.size() + colBlue.size() >= 800) {  
          scene2Creator(primaryStage);
          
       // root.getChildren().add(scoreLabel); 
        
          }
      redPlayer();
      bluePlayer();
      
      }
    });
    //for (int i = 0 ; i < 200 ; i++) redPlayer();
   // for (int i = 0 ; i < 200 ; i++) bluePlayer();
    
    
    //stage.show();
  //  System.out.println(Arrays.deepToString(playArray));
  } 
  public void buildUI(){

    for (int i = 0 ; i < 40 ; i++){
      for(int j = 0 ; j < 20 ; j++){
      Rectangle gridRect = new Rectangle();
      gridRect.setHeight(19);
      gridRect.setWidth(19);
      gridRect.setFill(Color.GRAY);
      tilePane.getChildren().add(gridRect);
      rectArray[i][j] = gridRect;
       }
    }

    tilePane.setOrientation(Orientation.VERTICAL);

    tilePane.setHgap(1);
    tilePane.setVgap(1);

    tilePane.setPrefColumns(40);
    tilePane.setPrefRows(20);


    //start red
    rectArray[39][0].setFill(Color.RED);
    colRed.add(39);
    rowRed.add(0);
    playArray[39][0] = 1;


    //start blue
    rectArray[0][19].setFill(Color.DODGERBLUE);
    colBlue.add(0);
    rowBlue.add(19);
    playArray[0][19] = 2;


    eggBuilder();
  }
  public void eggArrayFiller(){
    for (int i = 0 ; i < 40 ; i ++){
      for (int j = 0 ; j <20 ; j++){
        playArray[i][j] = 0;
      }
    }
  }

  public void eggBuilder(){
    for (int i = 0 ; i < 40 ; i++){
      
      int col = rand.nextInt(38) + 1;
      int row = rand.nextInt(18) + 1;
      rectArray[col][row].setFill(Color.ORANGE);
      eggArray[col][row] = 1;
    }
  }

  public void playArrayFiller(){
    for (int i = 0 ; i < 40 ; i ++){
      for (int j = 0 ; j <20 ; j++){
        playArray[i][j] = 0;
      }
    }
  }

  public void redPlayer(){
  int emptyChecker = 0;
  int lastBox = colRed.size()-1;
    // finding the closest points
    boolean point = false;
    
    int targetI = 0 , targetJ = 0;
   
     List<Integer> targetList = new ArrayList<Integer>();
 for(int i = 0 ; i < 40 ; i++){
   for (int j =  0; j<20 ; j++){
     targetList.add(targetArray[i][j]); 
     
   }
 }
 int count = Collections.frequency(targetList, 1);
     if(count == 0 ) emptyChecker = 1; 
  
  //Empty checker


  
  //System.out.println(emptyChecker);

switch (emptyChecker) {
  case 0:  
  
  while(point == false){
  for (int i = 0 ; i < 40 ; i ++){
    for (int j = 0 ; j < 20 ; j++){
        if(targetArray[i][j] != 0){
          targetI = i;
          targetJ = j;
          point = true;
        
        }
      }
    }
  }
              if( targetI - colRed.get(lastBox) != 0 ){
              int move = ((targetI - colRed.get(lastBox)) / Math.abs(targetI - colRed.get(lastBox)));
              playArray[colRed.get(lastBox) + move][rowRed.get(lastBox)] = 1;
              colRed.add(colRed.get(lastBox) + move);
              rowRed.add(rowRed.get(lastBox));
              rectArray[colRed.get(lastBox) + move][rowRed.get(lastBox)].setFill(Color.RED);
          }
            else if( targetJ - rowRed.get(lastBox) != 0 ){
              int move = ((targetJ - rowRed.get(lastBox)) / Math.abs(targetJ - rowRed.get(lastBox)));
              playArray[colRed.get(lastBox) ][rowRed.get(lastBox) + move] = 1;
              colRed.add(colRed.get(lastBox));
              rowRed.add(rowRed.get(lastBox) + move);
              rectArray[colRed.get(lastBox) ][rowRed.get(lastBox) + move].setFill(Color.RED);
            }
            else {
            targetArray[targetI][targetJ] = 0;
            point = false;
            }
    
    break;
    case 1: redPlayerBot();
    
    break;
    
  default:
    break;
}            
            

      }

public void redPlayerBot() {
  int putRect = 0;
  while (putRect == 0) {

   int picker = rand.nextInt(colRed.size());

   //     if left                                                            down
   if (playArray[colRed.get(picker) - 1][rowRed.get(picker)] == 0 && playArray[colRed.get(picker)][rowRed.get(picker) + 1] == 0 ){
     int placer = rand.nextInt(2);
     switch (placer) {
       case 0:     
               playArray[colRed.get(picker) - 1][rowRed.get(picker)] = 1;
               colRed.add(colRed.get(picker) - 1);
               rowRed.add(rowRed.get(picker));
               rectArray[colRed.get(picker) - 1][rowRed.get(picker)].setFill(Color.RED);
         break;
         case 1:
               playArray[colRed.get(picker)][rowRed.get(picker)+1] = 1;
               colRed.add(colRed.get(picker));
               rowRed.add(rowRed.get(picker) + 1);
               rectArray[colRed.get(picker)][rowRed.get(picker) + 1].setFill(Color.RED);
         
         break;
       default:
         break;
     }
     putRect++;
   }
   else if(playArray[colRed.get(picker) - 1][rowRed.get(picker)] == 0){
     playArray[colRed.get(picker) - 1][rowRed.get(picker)] = 1;
               colRed.add(colRed.get(picker) - 1);
               rowRed.add(rowRed.get(picker));
               rectArray[colRed.get(picker) - 1][rowRed.get(picker)].setFill(Color.RED);
               putRect++;
   }
   else if(playArray[colRed.get(picker)][rowRed.get(picker)+1] == 0 ){
               playArray[colRed.get(picker)][rowRed.get(picker)+1] = 1;
               colRed.add(colRed.get(picker));
               rowRed.add(rowRed.get(picker) + 1);
               rectArray[colRed.get(picker)][rowRed.get(picker) + 1].setFill(Color.RED);
               putRect++;
   }
   else System.out.println("Trying new red point");
   }
  
}

      
   
  
 public void bluePlayer(){
  int putRect = 0;
 while (putRect == 0) {

  int picker = rand.nextInt(colBlue.size());
  //     if left                                                            down
  if (playArray[colBlue.get(picker) +1][rowBlue.get(picker)] == 0 && playArray[colBlue.get(picker)][rowBlue.get(picker) -1] == 0 ){
    int placer = rand.nextInt(2);
    switch (placer) {
      case 0: 
              playArray[colBlue.get(picker) +1][rowBlue.get(picker)] = 2;
              colBlue.add(colBlue.get(picker) +1);
              rowBlue.add(rowBlue.get(picker));
              rectArray[colBlue.get(picker) +1][rowBlue.get(picker)].setFill(Color.DODGERBLUE);
              if(eggArray[colBlue.get(picker) +1][rowBlue.get(picker)] == 1) targetArray[colBlue.get(picker) +1][rowBlue.get(picker)] = 0;
        break;
        case 1:
              playArray[colBlue.get(picker)][rowBlue.get(picker)-1] = 2;
              colBlue.add(colBlue.get(picker));
              rowBlue.add(rowBlue.get(picker) -1);
              rectArray[colBlue.get(picker)][rowBlue.get(picker) -1].setFill(Color.DODGERBLUE);
              if(eggArray[colBlue.get(picker)][rowBlue.get(picker)-1] == 1) targetArray[colBlue.get(picker)][rowBlue.get(picker)-1]=0;
        break;
      default:
        break;
    }
    putRect++;
  }
  else if(playArray[colBlue.get(picker) +1][rowBlue.get(picker)] == 0){
    playArray[colBlue.get(picker) +1][rowBlue.get(picker)] = 2;
              colBlue.add(colBlue.get(picker) +1);
              rowBlue.add(rowBlue.get(picker));
              rectArray[colBlue.get(picker) +1][rowBlue.get(picker)].setFill(Color.DODGERBLUE);
              putRect++;
              if(eggArray[colBlue.get(picker) +1][rowBlue.get(picker)] == 1) targetArray[colBlue.get(picker) +1][rowBlue.get(picker)]=0;
  }
  else if(playArray[colBlue.get(picker)][rowBlue.get(picker)-1] == 0 ){
              playArray[colBlue.get(picker)][rowBlue.get(picker)-1] = 2;
              colBlue.add(colBlue.get(picker));
              rowBlue.add(rowBlue.get(picker) -1);
              rectArray[colBlue.get(picker)][rowBlue.get(picker) -1].setFill(Color.DODGERBLUE);
              putRect++;
              if(eggArray[colBlue.get(picker) ][rowBlue.get(picker)-1] == 1) targetArray[colBlue.get(picker) ][rowBlue.get(picker)-1]=0;
  }
  else System.out.println("Trying new blue point");
  }
}  

  public int scoreCalc(int sumRed){
    for(int i = 0 ; i < 40 ; i++ ){
      for (int j = 0 ; j < 20 ; j++){
          if(eggArray[i][j] == 1){
          
          if (playArray[i][j] == 1) sumRed++;
          }
        }
      }
          return sumRed;
  }
  
 //waiter wait(ms)
  public static void wait(int ms)
{
    try
    {
        Thread.sleep(ms);
    }
    catch(InterruptedException ex)
    {
        Thread.currentThread().interrupt();
    }
}

  public void scene2Creator(Stage primaryStage){
    
        //Set a new scene
        Button replayButton = new Button("Replay");
        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root,200,300);
        stage.setScene(scene2);
        stage.setTitle("Game Over Score On Panel");
        
        int scoreRed;
        scoreRed = scoreCalc(score);
        int scoreBlue = 40 - scoreRed;
          
        System.out.println(colRed.size() + colBlue.size());
        
        Label scoreLabel = new Label("GAMEOVER" + "\nRed Score Is = "+ scoreRed+ "\nBlue Score Is = " + scoreBlue);
        scoreLabel.setFont(new Font("Roboto",18));
        root.setCenter(scoreLabel); 
        root.setBottom(replayButton);
        replayButton.setOnAction(new EventHandler <ActionEvent>(){
          public void handle(ActionEvent event){
          System.out.println("Replaying....");
          colRed.clear();
          rowRed.clear();
          colBlue.clear();
          rowBlue.clear();
          start(primaryStage);
          }
        });
        
  }
  
  public static void main(String[] args) {
    launch(args);
  }
} 

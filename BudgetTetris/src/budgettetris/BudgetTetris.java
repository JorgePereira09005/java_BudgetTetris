/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgettetris;

import Piece.Piece;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jorge
 */
public class BudgetTetris {
    
    public static final int MOVE_LENGTH = 24;
    public static final int BLOCK_SIDE_LENGTH = MOVE_LENGTH; // move_length and block_side_length are the same
    public static int battlefieldWidth; // width in blocks
    public static int battlefieldHeight; // height in blocks
    public static int xMax;
    public static int yMax;
    public static int[][] grid; // grid representing the game area. Rows increase from top to bottom (y coordinate), columns increase from left to right (x).
    public static Pane gameArea = new Pane();
    private static Scene sceneGame;
    public static int score = 0;
    private static boolean gameOn = true;
    private static Piece piece;
    public static int numLines = 0;
    private static int turnsOnTop = 0;
    
    public static void display(int width, int height) {
        
        battlefieldWidth = width; 
        battlefieldHeight = height;
        xMax = MOVE_LENGTH*battlefieldWidth;
        yMax = MOVE_LENGTH*battlefieldHeight;
        grid = new int[battlefieldHeight][battlefieldWidth];
        sceneGame = new Scene(gameArea, xMax + 150, yMax);
        piece = TetrisLogic.spawnPiece();
        
        Stage window = new Stage();        
        
        Line line;
        Text linesText;
        Text scoreText;
        //size of the game area will depend on the button pressed
        if (width==15) {
            line = new Line(xMax+26, 0, xMax+26, yMax );
            scoreText = new Text("Score: ");
            scoreText.setStyle("-fx-font: 14 verdana;");
            scoreText.setY(25);
            scoreText.setX(xMax + 30);
        
            linesText = new Text("Lines :");
            linesText.setStyle("-fx-font: 14 verdana;");
            linesText.setY(100);
            linesText.setX(xMax + 30);
            linesText.setFill(Color.CHOCOLATE);
        }
        else {
            line = new Line(xMax, 0, xMax, yMax );
            scoreText = new Text("Score: ");
            scoreText.setStyle("-fx-font: 20 verdana;");
            scoreText.setY(25);
            scoreText.setX(xMax+5);
        
            linesText = new Text("Lines :");
            linesText.setStyle("-fx-font: 20 verdana;");
            linesText.setY(100);
            linesText.setX(xMax+5);
            linesText.setFill(Color.CHOCOLATE);
        }

        gameArea.getChildren().addAll(scoreText, linesText, line);
        
        //adding the piece to the game area
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block: row) {
                if (block != null) {
                    gameArea.getChildren().add(block);
                }
            }
        }
        
        movePiece(piece);
        
        window.setScene(sceneGame);
        window.setTitle("Budget Tetris");
        window.show();
        
        //timer to pace the game logic
        Timer drop = new Timer();
        TimerTask task = new TimerTask() {
            // creating a new thread to run the following functions
            public void run() {  
                
                Platform.runLater(new Runnable() {
                    public void run() {
                        
                        boolean possiblyStuck = false;
                        
                        for(Rectangle block : piece.getLayout()[0]) {
                            if ( block != null && block.getY() == 0) {
                                possiblyStuck = true;
                                break;
                            }   
                        }
                        
                        if(possiblyStuck) {
                            turnsOnTop++;
                        }
                        else {
                            turnsOnTop = 0;
                        }
                        
                        if(turnsOnTop == 2) {
                            Text over = new Text("Game Set");
                            over.setFill(Color.DARKRED);
                            over.setStyle("-fx-font: 60 verdana;");
                            over.setY((int)yMax/2);
                            over.setX(30);
                            gameArea.getChildren().add(over);
                            gameOn = false;
                        }
                        
                        if(turnsOnTop == 20) {
                            System.exit(0);
                        }
                        
                        if (gameOn) {
                            TetrisLogic.moveDown(piece);
                            //movePiece(piece);
                            
                            if(TetrisLogic.settlePiece(piece)) {
                                TetrisLogic.scoreRow();
                                
                                piece = TetrisLogic.spawnPiece();
                                for(Rectangle[] row : piece.getLayout()) {
                                    for(Rectangle block: row) {
                                        if (block != null) {
                                        gameArea.getChildren().add(block);
                                        }
                                    }
                                }
                                movePiece(piece);
                            }
                            
                            scoreText.setText("Score: " + Integer.toString(score));
                            linesText.setText("Lines: " + Integer.toString(numLines));
                            
                        }
                    }
                });
            }
        };
        drop.schedule(task, 0, 300);
    } 
    
    private static void movePiece(Piece piece) {
        
        sceneGame.setOnKeyPressed( new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                
                switch(event.getCode()) {
                    case RIGHT:
                        TetrisLogic.moveRight(piece);
                        break;
                    case DOWN:
                        TetrisLogic.moveDown(piece);
                        score++; 
                        break;
                    case LEFT:
                        TetrisLogic.moveLeft(piece);
                        break;
                    case UP:
                        TetrisLogic.rotate(piece);
                        break;
                }
            }
        });
    }
    
    
    
}

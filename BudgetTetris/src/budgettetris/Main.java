/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgettetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application{
    
    Stage window, window2;
    Scene intro, battlefield;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = primaryStage;
        
        Label label1 = new Label("Budget Tetris, made by Jorge Carvalho.\nPlease choose a battlefield size.");
        Button buttonSize1 = new Button("10x24");
        Button buttonSize2 = new Button("15x36");
        
        buttonSize1.setOnAction(e -> {
            window.close();
            BudgetTetris.display(10,24);
            
        }
        );
        
        buttonSize2.setOnAction(e -> {
            window.close();
            BudgetTetris.display(15,36);
        }
        );
        
        HBox layoutButtons = new HBox(20);
        layoutButtons.getChildren().addAll(buttonSize1, buttonSize2);
        layoutButtons.setAlignment(Pos.CENTER);
        
        VBox layout1 = new VBox(30);
        layout1.getChildren().addAll(label1, layoutButtons);
        layout1.setAlignment(Pos.CENTER);
        
        intro = new Scene(layout1, 300, 200);
        
        window.setScene(intro);
        window.setTitle("Budget Tetris");
        window.show();
        
    }   
}

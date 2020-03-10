/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budgettetris;

import Piece.Piece;
import Piece.PieceShape;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jorge
 */
public class TetrisLogic {
    
    public static final int MOVE = BudgetTetris.MOVE_LENGTH;
    public static final int SIZE = BudgetTetris.BLOCK_SIDE_LENGTH;
    
    public static void moveRight(Piece piece) {
        
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                if( block != null && (!(block.getX() + MOVE < BudgetTetris.xMax )) ) {
                    return;
                }
            }
        }
        
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                try {
                    // checks to see if the value in that slot of the grid is non-zero (occupied)
                    if( block != null && BudgetTetris.grid[ (int) block.getY() / SIZE ][ (int) block.getX() / SIZE + 1 ] == 1 ) { 
                        return;
                    }
                }
                catch (Exception e) {
                    
                }
            }
        }
        
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                if(block != null) { 
                    block.setX(block.getX() + MOVE);
                }    
            }
        }   
    }
    
    public static void moveLeft(Piece piece) {
        
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                if( block != null && !(block.getX() - MOVE >= 0) ) {
                    return;
                }
            }
        }
        // checks to see if the value in the slot of the grid to the left is non-zero (occupied)
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                try {
                    if( block != null && BudgetTetris.grid[ (int) block.getY() / SIZE ][ (int) block.getX() / SIZE - 1] == 1 ) { 
                        return;
                    }
                }
                catch (Exception e) {
                    
                }
            }
        }
        
        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                if(block != null) {
                    block.setX(block.getX() - MOVE);
                } 
            }
        }   
    }
    
    public static void moveDown(Piece piece) {
        
        int lastRow = piece.getLayout().length - 1;
        //make sure the piece does not move lower than the bottom of the game area
        for(Rectangle block : piece.getLayout()[lastRow]) {
            if(  block != null && block.getY() + MOVE >= BudgetTetris.yMax ) {
                
                return;
            }
        }
        // checks to see if the value in that slot of the grid is 1 (occupied)
        for(Rectangle[] row : piece.getLayout()) {
            for (Rectangle block : row) {
                try {
                    if( block != null && BudgetTetris.grid[ (int) block.getY() / SIZE  + 1][ (int) block.getX() / SIZE  ] == 1 ) { 

                        return;
                    }
                }
                catch (Exception e) {

                }
            }    
        }

        for(Rectangle[] row : piece.getLayout()) {
            for(Rectangle block : row) {
                if(block != null) { 
                    block.setY(block.getY() + MOVE);
                }
            }
        }
    }
    
    public static void rotate(Piece piece) { //function to rotate the piece clockwise
        
        if(piece.getShape() == PieceShape.SQUARE) {
            return;
        }
        
        int newRowNumber = piece.getLayout()[0].length;
        int newColNumber = piece.getLayout().length;
        Rectangle[][] tempLayout = new Rectangle [newRowNumber][newColNumber]; // layout representing the new piece. switch rows and columns, eg. 2x3 -> 3x2
        
        for(int row = piece.getLayout().length-1; row >= 0; row--) {
            for(int col = 0; col < piece.getLayout()[0].length; col++) {
                tempLayout[col][piece.getLayout().length-1 - row] = piece.getLayout()[row][col]; //switches rows and columns (rotates clockwise)       
            }
        }    

        
        int x1, newX, newY, originX=0, originY=0;
        //assigns the coordinates of the first block of the last first of the piece layout to the first block of first row of the temp layout (top to bottom, left to right).
        //Needs to check to make sure both are non-null
        outerloop:
        for (x1 = 0; x1 < tempLayout[0].length ; x1++) {  
            for ( int x2 = 0; x2 < piece.getLayout()[0].length ; x2++) {
                if (tempLayout[0][x1] != null && piece.getLayout()[0][x2] != null) {
                    originX = (int)piece.getLayout()[0][x2].getX();
                    originY = (int)piece.getLayout()[0][x2].getY();
                    break outerloop;
                }
            }    
        }
        
        //sets coordinates for the remaining blocks based on the coordinates of the first block [0][x1]. First we perform collision detection
        for ( int row = 0; row < tempLayout.length ; row ++) { 
            for ( int col = 0; col < tempLayout[row].length ; col ++) {
                if( tempLayout[row][col] != null ) {
                    newX = originX + (col - x1) * SIZE;
                    newY = originY + row * SIZE;
                    //collision detection by comparing the coordinates of the blocks of the piece with the elements in the grid of the battlefield and its limits
                    if(newX < 0 || newX >= BudgetTetris.xMax || newY < 0 || newY >= BudgetTetris.yMax || BudgetTetris.grid[newY/SIZE][newX/SIZE] == 1) {
                        return;
                    }
                    
                }    
            }
        }
        
        tempLayout[0][x1].setX(originX);
        tempLayout[0][x1].setY(originY);
        //moving every block except for the first
        for ( int row = 0; row < tempLayout.length ; row ++) { 
            for ( int col = 0; col < tempLayout[row].length ; col ++) {
                if( tempLayout[row][col] != null ) {
                    newX = (int)tempLayout[0][x1].getX() + (col - x1) * SIZE;
                    newY = (int)tempLayout[0][x1].getY() + row * SIZE;
                    tempLayout[row][col].setX(newX);
                    tempLayout[row][col].setY(newY);  
                }    
            }
        }
       
        piece.setLayout(tempLayout);
    }
    
    public static boolean settlePiece(Piece piece) { 
    //checks if there's any block bellow the piece at the end of a "turn". If yes, settle the piece and fill the grid at those positions with 1.
        
        for(int row = piece.getLayout().length - 1; row >= 0; row--) {
            for(Rectangle block : piece.getLayout()[row]) {    
                try {
                    if ( block!=null && BudgetTetris.grid[ (int)block.getY() / SIZE + 1] [(int)block.getX() / SIZE] == 1  ) {

                        for (Rectangle[] rowToSettle : piece.getLayout()) {
                            for ( Rectangle b : rowToSettle ) {
                                if(b != null) {
                                    BudgetTetris.grid[ ((int)b.getY()) / SIZE ][(int)b.getX() / SIZE] = 1;
                                }    
                            } 
                        }

                    return true;
                    }
                }
                catch(Exception e) { //in case the piece hits the bottom of the battlefield/game area
                    for (Rectangle[] rowToSettle : piece.getLayout()) {
                        for ( Rectangle b : rowToSettle) {
                            if(b != null) {
                             BudgetTetris.grid[ (int)b.getY() / SIZE ][(int)b.getX() / SIZE] = 1;
                            }    
                        } 
                    }

                    return true;
                }
            }
        }    
        return false;
    }
    
    public static void scoreRow() { 
    // row is scored (if completely filled, must be eliminated and score awared).
        ArrayList<Node> blocks = new ArrayList<>();
        ArrayList<Node> newBlocks = new ArrayList<>();
        ArrayList<Integer> deletedRows = new ArrayList<>();
        
        int full;
            
        //checking which lines are full
        for(int row = BudgetTetris.battlefieldHeight - 1; row >= 0; row--) {
            full = 0;
            for(int col=0; col < BudgetTetris.battlefieldWidth; col++) {
                if (BudgetTetris.grid[row][col] == 1) {
                    full++;
                }
            }
            if( full == BudgetTetris.battlefieldWidth) {
                deletedRows.add(row);
                BudgetTetris.score += 50;
                BudgetTetris.numLines++;
                BudgetTetris.grid[row] = null;
            }
        }
        
        //remove the scored blocks from the screen
        if(deletedRows.size() > 0) {
            for (Node block : BudgetTetris.gameArea.getChildren()) {
                if (block instanceof Rectangle) {
                    blocks.add(block);
                }
            }
            
            for(Node block: blocks) {
                Rectangle recBlock = (Rectangle)block;
                
                
                for(int row : deletedRows) {
                    if ( recBlock.getY() == row * SIZE) {
                        BudgetTetris.gameArea.getChildren().remove(block);
                    }

                }
                newBlocks.add(block);
            }
            
            int rowModifier = 0 ; //takes into account the deleted rows
            for(int row : deletedRows) {
                              
                    for (Node block : newBlocks) {
                        Rectangle recBlock = (Rectangle)block;

                        if (recBlock.getY() < (row + rowModifier) * SIZE) {
                            recBlock.setY(recBlock.getY() + SIZE); //if the block is above the deleted row, we drop it by one row
                        }
                    }
                
                rowModifier++;
            }       
                
            //drop lines above empty rows in the layout grid. Delete the rows previously nulled
            int[][] newGrid = new int[BudgetTetris.battlefieldHeight][BudgetTetris.battlefieldWidth];
            int newGridRow = 0; // need to discount the null rows
            
            for (int row = BudgetTetris.grid.length - 1; row >= 0; row--) {
                if(BudgetTetris.grid[row] == null) {
                    newGridRow++;
                }
                else {
                    newGrid[row+newGridRow] = BudgetTetris.grid[row];
                }
            }
            BudgetTetris.grid = newGrid;     
        }
    }
    
    public static Piece spawnPiece() { 
    // creates a new piece and gives each coordinates to each block that composes it

        Piece newPiece = new Piece();
        
        if (newPiece.getLayout()[0].length > 2) { //wider pieces spawn farther to the left
            for(int row = 0; row < newPiece.getLayout().length; row++) {
                for(int col = 0; col < newPiece.getLayout()[row].length; col++) {
                    if(newPiece.getLayout()[row][col] != null) {
                        newPiece.getLayout()[row][col].setX( (BudgetTetris.battlefieldWidth*SIZE/2 - SIZE*2) + SIZE*col);
                        newPiece.getLayout()[row][col].setY( (0 ) + SIZE*row) ;
                    }
                }
            }
        } 
        else {
           for(int row = 0; row < newPiece.getLayout().length; row++) {
                for(int col = 0; col < newPiece.getLayout()[row].length; col++) {
                    if(newPiece.getLayout()[row][col] != null) {
                        newPiece.getLayout()[row][col].setX( (BudgetTetris.battlefieldWidth*SIZE/2 - SIZE) + SIZE*col);
                        newPiece.getLayout()[row][col].setY( (0) + SIZE*row);
                    }    
                }
            } 
        }
        return newPiece;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;

import budgettetris.BudgetTetris;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jorge
 */
public class Piece {
    
    private int BLOCK_SIDE = BudgetTetris.MOVE_LENGTH - 1;
    private Rectangle[][] pieceLayout;
    private Color color;
    private PieceShape shape;
    
    public Piece() {
        this.shape = PieceShape.randomType();
        
        Color[] colors = new Color[] { Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.GRAY, Color.BLUE, Color.RED, Color.DARKGREEN };
        this.color = colors[new Random().nextInt(colors.length)];
        
        // create and array with the layout of the piece's shape and populate it with rectangular blocks or with nothing (using "null").
        // These arrays represent the pieces, just the way they actually look like
        switch(this.shape) { 
            case I:      
                this.pieceLayout = new Rectangle[][]{ {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE) } };
                break;
            case SQUARE:
                this.pieceLayout = new Rectangle[][] { {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)}, 
                                                     {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)} 
                                           };
                break;
            case T:
                this.pieceLayout = new Rectangle[][] { {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)},
                                                     {null, new Rectangle(BLOCK_SIDE,BLOCK_SIDE), null}
                                            };    
                break;        
            case J:
                this.pieceLayout = new Rectangle[][] { {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)},
                                                     {null, null, new Rectangle(BLOCK_SIDE,BLOCK_SIDE)}
                                            }; 
                break;
            case L:
                this.pieceLayout = new Rectangle[][] { {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)},
                                                     {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), null, null}
                                            };
                break;
            case S:
                this.pieceLayout = new Rectangle[][] { {null, new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)},
                                                     {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), null}
                                            };
                break;        
            case Z:
                this.pieceLayout = new Rectangle[][] { {new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE), null},
                                                     {null, new Rectangle(BLOCK_SIDE,BLOCK_SIDE), new Rectangle(BLOCK_SIDE,BLOCK_SIDE)}
                                            };
                break;
        }
        
        for (Rectangle[] row : this.pieceLayout) {
            for(Rectangle block : row) {
                if(block != null) { 
                block.setFill(this.color);
                } 
            }
        }
    }
    
    public Rectangle[][] getLayout() {
        return this.pieceLayout;
    }
    
    public void setLayout(Rectangle[][] layout) {
        this.pieceLayout = layout;
    }
    
    public PieceShape getShape() {
        return this.shape;
    }
    
}

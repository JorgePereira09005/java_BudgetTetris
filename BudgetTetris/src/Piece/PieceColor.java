/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;

import java.util.Random;

/**
 *
 * @author Jorge
 */
public enum PieceColor {
    
    CYAN, MAGENTA, ORANGE, GRAY, BLUE, RED;
    
    public static PieceColor randomColor() {
        return PieceColor.values()[new Random().nextInt(PieceColor.values().length)];
    }
}

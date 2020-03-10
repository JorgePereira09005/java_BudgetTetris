/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Piece;

import java.util.Random;

//Enum that represents the shapes the tetris pieces can take
public enum PieceShape {
    
    I, SQUARE, T, J, L, S, Z;
    
    public static PieceShape randomType() {
        return PieceShape.values()[new Random().nextInt(PieceShape.values().length)];
    }
}

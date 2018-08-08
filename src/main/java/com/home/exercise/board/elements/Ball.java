package com.home.exercise.board.elements;

/**
 * Represents a ball of the ping pong game.
 * @author rodrigo.mendoza
 */
public final class Ball {
  /** The ball needs thhis reference in order to do its calculations in the next step. */
  private Stick leftStick;
  private int row;
  private int col;
  private boolean up;
  private boolean right;
  
  /** Creates a new ball with a initial position. */
  public Ball(int row, int col, Stick leftStick) {
    this.leftStick = leftStick;
    this.row = row;
    this.col = col;
    up = false;
    right = true;
  }
  
  /**
   * Change the status of the ball to the nextStep
   * @param rows bound number of rows
   * @param cols bound number of columns
   * @return true if the ball haven't hit any board limit
   */
  public boolean advance(final int rows, final int cols) {
    // We check the next row considering the bounds and the direction
    if(up) {
      int nextRow = row - 1;
      if(nextRow >= 0) {
        row--;
      } else {
        row++;
        up = false;
      }
    } else {
      int nextRow = row + 1;
      if(nextRow < rows) {
        row++;
      } else {
        row--;
        up = true;
      }
    }
    
    // We check the next column considering the bounds and the direction
    if(right) {
      int nextCol = col + 1;
      if(nextCol < cols) {
        col++;
      } else {
        col--;
        right = false;
      }
    } else {
      int nextCol = col - 1;
      if(nextCol >= 0) {
        col--;
      } else {
        col++;
        right = true;
      }
    }
    
    // We check if the ball reaches the left stick and calculates the next position
    if(col == leftStick.getCol()) {
      for(int i = 0; i < leftStick.getSize(); i++) {
        if(row == leftStick.getRow() + i) {
          // because is the left stick we can asume the direction was to the left
          right = true;
          col += 2;
        }
      }
    }
    
    // We check if the ball hit the left board limit
    boolean hasNextMove = true;
    if(col == 0) {
      hasNextMove = false;
    }
    return hasNextMove;
  }
  
  // Getters and setters
  public int getRow() {
    return row;
  }
  
  public void setRow(int row) {
    this.row = row;
  }
  
  public int getCol() {
    return col;
  }
  
  public void setCol(int col) {
    this.col = col;
  }
  
}

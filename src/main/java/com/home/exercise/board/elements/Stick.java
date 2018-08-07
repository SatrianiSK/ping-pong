package com.home.exercise.board.elements;

/**
 * Represents the bar or stick that is needed to hit the ball.
 * @author rodrigo.mendoza
 */
public final class Stick {
  private final int size;
  private final int col;
  private int row;
  
  /**
   * Creates a new Stick with the given characteristics.
   * @param size defines the size of the stick
   * @param row the initial row in wich this stick is going to be
   * @param col it's a final value that represents in wich column of the board is going to be displayed this stick
   */
  public Stick(int size, int row, int col) {
    this.size = size;
    this.row = row;
    this.col = col;
  }
  
  // Getters and setters
  public int getRow() {
    return row;
  }
  
  public void setRow(int row) {
    this.row = row;
  }
  
  public int getSize() {
    return size;
  }
  
  public int getCol() {
    return col;
  }
  
}

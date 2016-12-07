package com.game_module;

import java.util.Random;
import java.awt.Color;

public class Shape {
  private int type;
  private int x;
  private int y;
  private int color;
  private int direction;

  public Shape(int type, int x, int y, int direction, int color){
    this.type = type;
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.color = color;
  }

  public int getColor() {
    return this.color;
  }

  public void setColor(int color){
    this.color = color;
  }

  public int getType() {
    return type;
  }

  public int getDirection() {
    return direction;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}

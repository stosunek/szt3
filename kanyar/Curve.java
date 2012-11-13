package kanyar;

import java.lang.Math;

public class Curve{

  private float x;
  private float y;
  private float angle;
  private float turn;
  private float turning_sensibility;
  private boolean life;

  public Curve(){
    x = 200;
    y = 200;
    angle = 1;
    turn = 0f;
    turning_sensibility = 0.02f;
  }

  public boolean lives(){
    return life;
  }

  public int x(){
    return Math.round(x);
  }

  public int y(){
    return Math.round(y);
  }

  public void nextPixel()
  {
    x += Math.cos(angle);
    y += Math.sin(angle);
    angle += turn;
  }

  public void turnLeft()
  {
    turn = -1 * turning_sensibility;
  }

  public void turnRight()
  {
    turn = turning_sensibility;
  }

  public void turnForwardFromLeft()
  {
    if(turn < 0)
    {
      turn = 0f;
    }
  }

  public void turnForwardFromRight()
  {
    if(turn > 0)
    {
      turn = 0f;
    }
  }
}

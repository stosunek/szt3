package kanyar;


import java.lang.Math;
import java.util.Random;

public class Curve{

  private float x;
  private float y;
  private float angle;
  private float turn;
  private float turning_sensibility;
  private boolean life;
  private Random random;

  public Curve(){
    random = new Random();
    x = 100 + random.nextInt(600);
    y = 100 + random.nextInt(400);
    angle = (float)Math.random()*6.283f; //from zero (straight right) to 2*pi (pi is straight left)
    turn = 0f;
    turning_sensibility = 0.02f;
    life = true;
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

  public float angle(){
    return angle; //angle mod 2*pi
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

  public void turnForward()
  {
    turn = 0f;
  }

  public void die()
  {
    life = false;
  }
}

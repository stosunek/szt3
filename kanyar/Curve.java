package kanyar;

public class Curve{

  private double x;
  private double y;
  private double angle; //TODO
  private boolean life;

  public Curve(){
    x = 100;
    y = 100;
    angle = 1;
  }

  public boolean lives(){
    return life;
  }

  public int x(){
    return (int)x; //TODO round
  }

  public int y(){
    return (int)y;
  }

  public void nextPixel()
  {
    x += Math.cos(angle);
    y += Math.sin(angle);
    angle += 0.07; //TODO
  }

}

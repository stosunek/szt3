package kanyar;

import java.lang.Math;
import java.util.Random;

public class AI{

  private Curve c;
  private static final int XX = 800;
  private static final int YY = 600;

  public AI(Curve curve)
  {
    c = curve;
  }

  public void move(int[][] b)
  {
    if(this.countForwardBlocks(b, c.angle()) > 0)
    {
      int left = this.firstBlock(b, (c.angle()-0.02f));
      int right = this.firstBlock(b, (c.angle()+0.02f));
      if(left < right) //If there are more blocks on left
      {
        c.turnRight();
      }
      else
      {
        c.turnLeft();
      }
    }
    else
    {
      int right = this.countForwardBlocks(b, (c.angle()+0.2f));
      int left = this.countForwardBlocks(b, (c.angle()-0.2f));
      if(left == 0 && right > 0)
      {
        c.turnLeft();
      }
      else if(right == 0 && left > 0)
      {
        c.turnRight();
      }
      else
      {
        c.turnForward();
      }
    }
  }

  private int countForwardBlocks(int[][] b, float angle)
  {
    float x = c.x();
    float y = c.y();
    int intx = 0;
    int inty = 0;
    int blocknum = 0;
    for(int i = 0; i < 70; i++) 
    {
      x += Math.cos(angle);
      y += Math.sin(angle);
      intx = (int)x;
      inty = (int)y;
      intx = Math.min(intx,XX-1);
      intx = Math.max(intx,0);
      inty = Math.min(inty,YY-51);
      inty = Math.max(inty,0);
      blocknum += b[intx][inty];
      if(i > 4 && intx != 0 && inty != 0 && intx != XX-1 && inty != YY-51)
      {
        blocknum += b[intx-1][inty-1];
        blocknum += b[intx-1][inty+1];
        blocknum += b[intx+1][inty-1];
        blocknum += b[intx+1][inty+1];
      }
    }
    return blocknum;
  }

  private int firstBlock(int[][] b, float angle)
  {
    float x = c.x();
    float y = c.y();
    int intx = (int)x;
    int inty = (int)y;
    int i = 0;
    while(b[intx][inty] == 0)
    {
      x += Math.cos(angle);
      y += Math.sin(angle);
      intx = (int)x;
      inty = (int)y;
      intx = Math.min(intx,XX-1);
      intx = Math.max(intx,0);
      inty = Math.min(inty,YY-51);
      inty = Math.max(inty,0);
      i++;
    }
    return i;
  }

}

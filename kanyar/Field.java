package kanyar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel implements KeyListener, ActionListener{

  private Timer timer;
  private Curve[] player;
  private int[][] players;
  private int playerNum;
  private int[][] board;
  private int[][] tempx;
  private int[][] tempy;
  private static final int MEM = 2;
  private static final int XX = 800;
  private static final int YY = 600;

  public enum Player
  {
    RACE, CONTROL, COLOR
  }

  public enum CurveRace
  {
    HUMAN, CPU
  }

  public enum CurveControl
  {
    LEFT, ONE, N
  }

  public enum CurveColor
  {
    WHITE, RED, BLUE
  }

  public Field(int[][] p)
  {
    players = p;
    setFocusable(true);
    addKeyListener(this);
    Timer timer = new Timer(10, this);
    timer.start();
    tempx = new int[3][MEM+1];
    tempy = new int[3][MEM+1];
    for(int k = 0; k < 3; k++) //Fill pathy memory
    {
      for(int l = 0; l < MEM+1; l++)
      {
        tempx[k][l] = 0; tempy[k][l] = 0;
      }
    }
    board = new int[XX][YY-50];
    for(int k = 0; k < XX; k++) //Fill game board
    {
      for(int l = 0; l < (YY-50); l++)
      {
        if(k == 0 || l == 0 || k == (XX-1) || l == (YY-51))
        {
          board[k][l] = 1;
        }
        else
        {
          board[k][l] = 0;
        }
      }
    }
    player = new Curve[3];
    int j = 0;
    /*for(int i = 0; i < 3; i++) //Initialize players
    {
      if(p[i][Player.RACE.ordinal()] == CurveRace.HUMAN.ordinal())
      {
        player[j] = new Curve(true);
      }
      else
      {
        player[j] = new Curve(false);
      }
        j++;
    }*/
    for(int i = 0; i < 3; i++){player[i] = new Curve();}
    playerNum = 3;
  }


  public void move()
  {
    for(int i = 0; i < playerNum; i++)
    {
      if(player[i].lives())
      {
        for(int j = 1; j <= MEM; j++ ) //Refresh path memory
        {
          tempx[i][j-1] = tempx[i][j];
          tempy[i][j-1] = tempy[i][j];
        }
        tempx[i][MEM] = player[i].x();
        tempy[i][MEM] = player[i].y();
        board[tempx[i][0]][tempy[i][0]] = 1;
        board[tempx[i][0]+1][tempy[i][0]] = 1;
        board[tempx[i][0]][tempy[i][0]+1] = 1;
        board[tempx[i][0]+1][tempy[i][0]+1] = 1;
        player[i].nextPixel();
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) //FIXME curves can be erased by other windows
  {
    for(int i = 0; i < playerNum; i++)
    {
      switch(players[i][Player.COLOR.ordinal()])
      {
        case 0:
          g.setColor(Color.white);
          break;
        case 1:
          g.setColor(Color.red);
          break;
        case 2:
          g.setColor(Color.blue);
          break;
      }
      if(board[player[i].x()][player[i].y()] == 1 || board[player[i].x()+1][player[i].y()] == 1 || board[player[i].x()][player[i].y()+1] == 1 || board[player[i].x()+1][player[i].y()+1] == 1) // Collision check
      {
        player[i].die();
        restartIfEnded();
      }
      else
      {
        if(player[i].lives())
        {
          g.fillRect(player[i].x(), player[i].y(), 2, 2); //Paint next pixels of player[i]
        }
      }
      g.setColor(Color.white);
      g.fillRect(0, (YY-51), XX, 1);
    }
  }

  private void restartIfEnded()
  {
    int j = 0;
    for(int i = 0; i < playerNum; i++)
    {
      if(player[i].lives())
      {
        j++;
      }
    }
    if(j <= 1)
    {
      restart();
    }
  }

  private void restart()
  {
    
  }

  private Curve getPlayerByControl(int con)
  {
    for(int i = 0; i < playerNum; i++)
    {
      if(players[i][Player.CONTROL.ordinal()] == con)
      {
        return player[i];
      }
    }
    return  player[0];
  }

  public void actionPerformed(ActionEvent e) {
    this.move();
    this.paint(this.getGraphics());
  }

  public void keyPressed(KeyEvent e)
  {
    int keyCode = e.getKeyCode();
    switch(keyCode)
    { 
      case KeyEvent.VK_LEFT:
        getPlayerByControl(CurveControl.LEFT.ordinal()).turnLeft();
        break;
      case KeyEvent.VK_RIGHT:
        getPlayerByControl(CurveControl.LEFT.ordinal()).turnRight();
        break;
      case KeyEvent.VK_1:
        getPlayerByControl(CurveControl.ONE.ordinal()).turnLeft();
        break;
      case KeyEvent.VK_Q:
        getPlayerByControl(CurveControl.ONE.ordinal()).turnRight();
        break;
      case KeyEvent.VK_N:
        getPlayerByControl(CurveControl.N.ordinal()).turnLeft();
        break;
      case KeyEvent.VK_M:
        getPlayerByControl(CurveControl.N.ordinal()).turnRight();
        break;
    }
  }

  public void keyReleased(KeyEvent e)
  {
    int keyCode = e.getKeyCode();
    switch(keyCode)
    {
      case KeyEvent.VK_LEFT:
        getPlayerByControl(CurveControl.LEFT.ordinal()).turnForwardFromLeft();
        break;
      case KeyEvent.VK_RIGHT:
        getPlayerByControl(CurveControl.LEFT.ordinal()).turnForwardFromRight();
        break;
      case KeyEvent.VK_1:
        getPlayerByControl(CurveControl.ONE.ordinal()).turnForwardFromLeft();
        break;
      case KeyEvent.VK_Q:
        getPlayerByControl(CurveControl.ONE.ordinal()).turnForwardFromRight();
        break;
      case KeyEvent.VK_N:
        getPlayerByControl(CurveControl.N.ordinal()).turnForwardFromLeft();
        break;
      case KeyEvent.VK_M:
        getPlayerByControl(CurveControl.N.ordinal()).turnForwardFromRight();
        break;
     }
  }

  public void keyTyped(KeyEvent e)
  {
  }
}

package kanyar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FlowLayout;

public class Field extends JPanel implements KeyListener, ActionListener{

  private Timer timer;
  private int prepare;
  private Curve[] player;
  private AI[] ai;
  private int[][] players;
  private int playerNum;
  private int[][] board;
  private int[][] tempx;
  private int[][] tempy;
  private JLabel[] labels;
  private Integer[] points;
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
    //this.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.setLayout(null);
    playerNum = 3; // TODO
    tempx = new int[playerNum][MEM+1];
    tempy = new int[playerNum][MEM+1];
    points = new Integer[playerNum];
    labels = new JLabel[playerNum];
    for(int i = 0; i < playerNum; i++)
    {
      points[i] = 0;
      labels[i] = new JLabel("0"); //TODO make label look cooler
      labels[i].setForeground(getColor(i));
      this.add(labels[i]);
      //labels[i].setBounds(((i+1)*150), (YY-270), -100 ,-100);
      labels[i].setLocation((i+1)*50, (YY-70));
      labels[i].setSize(40,60);
      labels[i].setVisible(true);
    }
    setup();
  }

  private void setup()
  {
    prepare = 0;
    Timer timer = new Timer(10, this);
    timer.start();
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
    player = new Curve[playerNum];
    ai = new AI[playerNum];
    int j = 0;
    for(int i = 0; i < 3; i++)
    {
      player[i] = new Curve();
      if(players[i][Player.RACE.ordinal()] == CurveRace.CPU.ordinal())
      {
        ai[i] = new AI(player[i]);
      }
    }
  }

  private Color getColor(int playerNum)
  {
    Color color = Color.white;
    switch(players[playerNum][Player.COLOR.ordinal()])
    {
      case 0:
        color = Color.white;
        break;
      case 1:
        color = Color.red;
        break;
      case 2:
        color = Color.blue;
        break;
    }
    return color;
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
    //super.paintComponent(g); //FIXME here we can fix label error
    for(int i = 0; i < playerNum; i++)
    {
      g.setColor(getColor(i));
      if(player[i].lives() && (board[player[i].x()][player[i].y()] == 1 || board[player[i].x()+1][player[i].y()] == 1 || board[player[i].x()][player[i].y()+1] == 1 || board[player[i].x()+1][player[i].y()+1] == 1)) // Collision check
      {
        player[i].die();
        distributePoints();
        if(ended()) //Next round
        {
           g.setColor(Color.black);
           g.fillRect(0, 0, XX, YY);
           setup();
        }
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

  private void distributePoints()
  {
    for(int i = 0; i < playerNum; i++)
    {
      if(player[i].lives())
      {
        points[i]++;
        labels[i].setText(points[i].toString());
      }
    }
  }

  private boolean ended()
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
      return true;
    }
    else
    {
      return false;
    }
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
    if(prepare < 100) // wait x/100 seconds
    {
      prepare++;
    }
    else
    {
      for(int i = 0; i < playerNum; i++)
      {
        if(players[i][Player.RACE.ordinal()] == CurveRace.CPU.ordinal())
        {
          ai[i].move(board);
        }
      }
      this.move();
    }
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

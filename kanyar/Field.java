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
  private Curve player = new Curve();

  public Field()
  {
    setFocusable(true);
    addKeyListener(this);
    Timer timer = new Timer(10, this);
    timer.start();
  }


  public void move()
  {
    player.nextPixel();
  }

  @Override
  public void paintComponent(Graphics g) //FIXME curves can be erased by other windows
  {
    g.setColor(Color.white); //TODO for all curves
    g.fillRect(player.x(), player.y(), 2, 2);
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
        player.turnLeft();
        break;
      case KeyEvent.VK_RIGHT:
        player.turnRight();
        break;
     }
  }

  public void keyReleased(KeyEvent e)
  {
    int keyCode = e.getKeyCode();
    switch(keyCode)
    {
      case KeyEvent.VK_LEFT:
        player.turnForwardFromLeft();
        break;
      case KeyEvent.VK_RIGHT:
        player.turnForwardFromRight();
        break;
     }
  }

  public void keyTyped(KeyEvent e)
  {
  }
}

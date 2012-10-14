package kanyar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel implements ActionListener{

  private Timer timer;
  private Curve player = new Curve();

  public Field(){
    this.setBackground(Color.black);
    //setFocusable(true);

    //TODO START
    Timer timer = new Timer(40, this);
    timer.start();
  }


  public void move()
  {
    player.nextPixel();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    g.drawLine(player.x(), player.y(), player.x(), player.y()); 
  }

  public void actionPerformed(ActionEvent e) {
    this.move();
    repaint(); //FIXME
  }
}

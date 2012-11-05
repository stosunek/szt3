package kanyar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel implements ActionListener{

  private Timer timer;
  private Curve player = new Curve();

  public Field()
  {
    //setFocusable(true);

    //TODO START
    Timer timer = new Timer(15, this);
    timer.start();
  }


  public void move()
  {
    player.nextPixel();
  }

  @Override
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.white); //TODO for all curves
    g.fillOval(player.x(), player.y(), 2, 2);
  }

  public void actionPerformed(ActionEvent e) {
    this.move();
    this.paint(this.getGraphics());
  }
}

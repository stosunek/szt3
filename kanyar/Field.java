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
    Timer timer = new Timer(30, this);
    timer.start();
  }


  public void move()
  {
    player.nextPixel();
  }

  public void paintCurves(Graphics g) {
    g.setColor(Color.white); //TODO for all curves
    g.fillRect(player.x(), player.y(), 2, 2); //TODO optimal width
  }

  public void actionPerformed(ActionEvent e) {
    this.move();
    this.paintCurves(this.getGraphics()); //FIXME make it faster http://stackoverflow.com/questions/12824929/java-drawing-program-slows-after-several-polygons-drawn
  }
}

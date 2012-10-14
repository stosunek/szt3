package kanyar;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 *
 */

public class Kanyar extends JFrame{

  public Kanyar(){
    this.setTitle("Kanyar");
    this.setSize(400, 400);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new Field());
    this.setVisible(true);
  }

  public static void main(String[] args){
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        new Kanyar();
      }
    });
  }
}

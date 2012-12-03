package kanyar;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;
/*
 *
 */

public class Kanyar extends JFrame{

  JDialog dialog;
  int[][] players;

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
    LEFT, ONE, N, NONE
  }

  public enum CurveColor
  {
    WHITE, RED, BLUE
  }

  public Kanyar(){
    this.setTitle("Kanyar");
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Options a = new Options();
    //n = a.Ii();
    //SelectPlayer myDialog = new SelectPlayer(this);
    //myDialog.setSize(400, 400);
    //myDialog.setModal(true);
    //myDialog.setVisible(true);
    players = new int[3][3];
    players[0][Player.RACE.ordinal()]    = CurveRace.HUMAN.ordinal();
    players[0][Player.CONTROL.ordinal()] = CurveControl.LEFT.ordinal();
    players[0][Player.COLOR.ordinal()]   = CurveColor.WHITE.ordinal();
    players[1][Player.RACE.ordinal()]    = CurveRace.CPU.ordinal();
    players[1][Player.CONTROL.ordinal()] = CurveControl.NONE.ordinal();//CurveControl.ONE.ordinal();
    players[1][Player.COLOR.ordinal()]   = CurveColor.RED.ordinal();
    players[2][Player.RACE.ordinal()]    = CurveRace.CPU.ordinal();
    players[2][Player.CONTROL.ordinal()] = CurveControl.NONE.ordinal(); //CurveControl.N.ordinal();
    players[2][Player.COLOR.ordinal()]   = CurveColor.BLUE.ordinal();
    this.setContentPane(new Field(players));
    this.setVisible(true);
    this.setBackground(Color.black);
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

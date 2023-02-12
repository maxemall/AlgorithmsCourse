package org.algo;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class MagicSquare extends JComponent {

  private int[][] map;
  private int w, h;
  private String symbols = " #<>^vx.";

  public MagicSquare(int w, int h) {
    this.w = w;
    this.h = h;
    map = new int[w][h];
  }

  public static void main(String[] args) {
    int w = 25;
    int h = 25;
    JFrame frame = new JFrame("Paint with Swing");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(300, 300));
    MagicSquare paint = new MagicSquare(w, h);
    frame.add(paint);

    frame.pack();
    frame.setVisible(true);
    paint.drawMagic();
    //        paint.fill(w - 1, h - 1);


    double x = 1.001;
    for (int i = 1; i <= 1000; i++) {
      x = x * x;
    }
  }

  public void setMap(int x, int y, int number) {
    map[x][y] = number;
    repaint();
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void drawMagic() {
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++)
        if (magicFunction(i, j)) {
          setMap(i, j, 1);
        } else setMap(i, j, 7);
    }
  }

  private boolean magicFunction(int i, int j) {
    //        return i > j
    //        return j == i;
    //        return i == 24 - j;
    //        return i < 30 - j;
    //        return j == Math.round(i/2);
    return (i < 10) || (j < 10);
  }

  public void fill(int x, int y) {
    if (x < 0 || x >= w || y < 0 || y >= h || map[x][y] != 0) {
      return;
    }
    setMap(x, y, 2);
    fill(x - 1, y);
    setMap(x, y, 3);
    fill(x + 1, y);
    setMap(x, y, 4);
    fill(x, y - 1);
    setMap(x, y, 5);
    fill(x, y + 1);
    setMap(x, y, 6);
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        int number = map[x][y];
        char symbol = symbols.charAt(number);
        g.setColor(getColor(number));
        g.drawString(String.valueOf(symbol), (x * 10) + 10, (y * 10) + 10);
      }
    }
  }

  private Color getColor(int number) {
    switch (number) {
      case 0:
        return Color.BLACK;
      case 1:
        return Color.DARK_GRAY;
      case 2:
        return Color.RED;
      case 3:
        return Color.RED;
      case 4:
        return Color.RED;
      case 5:
        return Color.RED;
      case 6:
        return Color.BLUE;
      default:
        return Color.BLACK;
    }
  }
}

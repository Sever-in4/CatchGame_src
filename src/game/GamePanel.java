package game;

import objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Basket basket;
    private ArrayList<Star> stars;
    private ArrayList<Bomb> bombs;
    private int score;
    private boolean gameOver;

    public GamePanel() {
        setPreferredSize(new Dimension(500, 700));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        basket = new Basket(225, 650);
        stars = new ArrayList<>();
        bombs = new ArrayList<>();
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        basket.draw(g);
        for (Star star : stars) star.draw(g);
        for (Bomb bomb : bombs) bomb.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over", 160, 350);
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        if (new Random().nextInt(25) == 0)
            stars.add(new Star(new Random().nextInt(470), 0));

        if (new Random().nextInt(100) == 0)
            bombs.add(new Bomb(new Random().nextInt(470), 0));

        Iterator<Star> starIter = stars.iterator();
        while (starIter.hasNext()) {
            Star s = starIter.next();
            s.update();
            if (s.getY() > 700) starIter.remove();
            else if (s.getBounds().intersects(basket.getBounds())) {
                score += 10;
                starIter.remove();
            }
        }

        Iterator<Bomb> bombIter = bombs.iterator();
        while (bombIter.hasNext()) {
            Bomb b = bombIter.next();
            b.update();
            if (b.getY() > 700) bombIter.remove();
            else if (b.getBounds().intersects(basket.getBounds())) {
                gameOver = true;
            }
        }

        repaint();
    }

    @Override public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) basket.move(-20);
        else if (key == KeyEvent.VK_RIGHT) basket.move(20);
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
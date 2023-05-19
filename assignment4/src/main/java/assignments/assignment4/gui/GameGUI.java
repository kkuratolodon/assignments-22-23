package assignments.assignment4.gui;

import javax.swing.*;

import assignments.assignment4.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GameGUI extends JPanel {
    public static final String KEY = "GAME";
    private int spawnTimer;
    private int shootTimer;
    private int speedCounter;
    private int speed;
    private boolean canShoot;
    private ArrayList<JLabel> bullets;
    private ArrayList<JTextField> enemies;
    private ArrayList<Integer> keyPressed;
    private static Timer timer;
    private JButton restartButton;
    private JButton backButton;
    private JButton player;

    public GameGUI(){
        super();
        spawnTimer = 0;
        shootTimer = 0;
        speedCounter = 0;
        speed = 0;
        canShoot = true;
        bullets = new ArrayList<JLabel>();
        enemies = new ArrayList<JTextField>();
        keyPressed = new ArrayList<Integer>();
        setLayout(null);
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        setBackground(Color.BLACK);
        restartButton = new JButton("Restart");
        restartButton.setSize(80, 35);
        restartButton.setBackground(Color.DARK_GRAY);
        restartButton.setForeground(Color.WHITE);
        restartButton.setLocation(606, 0);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = null;
                MainFrame.getInstance().restart();
                String text = "The game has been restarted!";
                JOptionPane.showMessageDialog(null, text, "Game Restarted", JOptionPane.INFORMATION_MESSAGE);
                MainFrame.getInstance().navigateTo(GameGUI.KEY);
                GameGUI.startTimer();
            }
        });
        add(restartButton);

        backButton = new JButton("Back");
        backButton.setSize(80, 35);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setLocation(526, 0);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = null;
                MainFrame.getInstance().restart();
                MainFrame.getInstance().navigateTo(HomeGUI.KEY);
            }
        });
        add(backButton);

        initGame();
    }
    private void initGame() {
        
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(++speedCounter >= 200){
                    speed++;
                    speedCounter = 0;
                }
                if(++shootTimer >= 15){
                    shootTimer = 0;
                    canShoot = !canShoot;
                }
                if(++spawnTimer >= 150 - speed){
                    spawnTimer = 0;
                    JTextField enemy = new JTextField("5");
                    enemy.setSize(50,50);
                    enemy.setLocation(700, 50 + new Random().nextInt(294));
                    enemy.setFont(new Font("Arial", Font.BOLD, 30));
                    enemy.setHorizontalAlignment(SwingConstants.CENTER);
                    enemy.setBackground(Color.DARK_GRAY);
                    enemy.setForeground(Color.black);
                    enemy.setEditable(false);

                    enemies.add(enemy);

                    add(enemy);
                    repaint();
                    revalidate();
                }
                for(JLabel bullet : bullets){
                    bullet.setLocation(bullet.getLocation().x + 10, bullet.getLocation().y);
                    for(JTextField enemy : enemies){
                        if(bullet == null || enemy == null){
                            continue;
                        }
                        int xb = bullet.getLocation().x, yb = bullet.getLocation().y;
                        int xe = enemy.getLocation().x, ye = enemy.getLocation().y;
                        if(xb >= xe && xb <= xe + 50 ){
                            if(yb+50 >= ye && yb + 50 <= ye + 50){    
                                if(enemy.isVisible() && bullet.isVisible()){
                                    bullet.setVisible(false);
                                    enemy.setText("" + (char)(enemy.getText().charAt(0) - 1));
                                    if(enemy.getText().equals("0")){
                                        enemy.setVisible(false);
                                        player.setText("" + (Integer.parseInt(player.getText()) + 1));
                                    }
                                
                                }
                            }
                        }
                    }
                }
                for(JTextField enemy : enemies){
                    if(enemy == null || player == null)
                        continue;
                    enemy.setLocation(enemy.getLocation().x - 3, enemy.getLocation().y);
                    if(enemy.getLocation().getX() <= 75 && enemy.isVisible()){
                        player.setForeground(Color.RED);
                        timer.stop();
                        MainFrame.getInstance().restart();
                        String text = "Kamu mendapatkan " + player.getText() + " poin!";
                        JOptionPane.showMessageDialog(null, text, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        player = null;
                        MainFrame.getInstance().navigateTo(GameGUI.KEY);
                        GameGUI.startTimer();
                    }
                }
            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(!keyPressed.contains(key)){
                    keyPressed.add(key);
                }
                int newy = player.getLocation().y; 
                
                for(int keys : keyPressed){
                    if(!timer.isRunning())
                        break;
                    switch (keys) {
                        case 'W' : 
                            newy -= 10;
                            break;
                        case 'S':
                            newy += 10;
                            break;
                        case 32:
                            if(!canShoot)
                                break;
                            JLabel bullet = new JLabel(">");
                            bullet.setSize(100,100);
                            bullet.setLocation(75, player.getLocation().y - 25);
                            bullet.setForeground(Color.WHITE);
                            bullet.setFont(new Font("Arial", Font.BOLD, 30));
        
                            bullets.add(bullet);
        
                            add(bullet);
                            repaint();
                            revalidate();
                            canShoot = false;
                    }
                }
                if(newy >= 0 && newy <= 345)
                    player.setLocation(0, newy);
                else if(newy < 0)
                    player.setLocation(0, 0);
                else if(newy > 345)
                    player.setLocation(0, 345);

                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed.remove(Integer.valueOf(e.getKeyCode()));
            }
            @Override
            public void keyTyped(KeyEvent e) {
            
            }
        });
        startGame();
    }
    private void startGame(){
        enemies.clear();
        bullets.clear();
        spawnTimer = 0;
        shootTimer = 0;
        speedCounter = 0;
        speed = 0;
        setFocusable(true);
        requestFocusInWindow();
        player = new JButton("0");
        setComponentZOrder(player, 0);
        player.setSize(75,50);
        player.setFont(new Font("Arial", Font.BOLD, 30));
        player.setHorizontalAlignment(SwingConstants.CENTER);
        player.setLocation(0,345);
        add(player);  
    }
    public static void startTimer(){
        timer.start();
    }

}

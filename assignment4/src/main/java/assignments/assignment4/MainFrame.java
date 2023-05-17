package assignments.assignment4;
import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment4.gui.HomeGUI;
import assignments.assignment4.gui.LoginGUI;
import assignments.assignment4.gui.RegisterGUI;
import assignments.assignment4.gui.member.Loginable;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.CreateNotaGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class MainFrame extends JFrame{
    private static MainFrame instance;
    private final Loginable[] loginablePanel;
    private final MemberSystem memberSystem = new MemberSystem();
    private final EmployeeSystem employeeSystem = new EmployeeSystem();
    private final CardLayout cards = new CardLayout();
    private final JPanel menu = new JPanel(cards);
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel gamePanel = new JPanel(null);
    private final LoginManager loginManager = new LoginManager(employeeSystem, memberSystem);
    private final HomeGUI homeGUI = new HomeGUI();
    private final RegisterGUI registerGUI = new RegisterGUI(loginManager);
    private final LoginGUI loginGUI = new LoginGUI(loginManager);
    private final EmployeeSystemGUI employeeSystemGUI = new EmployeeSystemGUI(employeeSystem);
    private final MemberSystemGUI memberSystemGUI = new MemberSystemGUI(memberSystem);
    private final CreateNotaGUI createNotaGUI = new CreateNotaGUI(memberSystemGUI);

    private MainFrame(){
        super("CuciCuciSystem");
        setUndecorated(true);
        employeeSystem.addEmployee(new Employee[]{
                new Employee("delta Epsilon Huha Huha", "ImplicitDiff"),
                new Employee("Regret", "FansBeratKanaArima")
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 432);
        setVisible(true);
        loginablePanel = new Loginable[]{
                employeeSystemGUI,
                memberSystemGUI,
        };
        initGUI();
        add(mainPanel);
        cards.show(menu, HomeGUI.KEY);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        menu.add(homeGUI, HomeGUI.KEY);
        menu.add(registerGUI, RegisterGUI.KEY);
        menu.add(loginGUI, LoginGUI.KEY);
        menu.add(employeeSystemGUI, EmployeeSystemGUI.KEY);
        menu.add(memberSystemGUI, MemberSystemGUI.KEY);
        menu.add(createNotaGUI, CreateNotaGUI.KEY);
        mainPanel.add(menu, BorderLayout.WEST);
        startGame();
        mainPanel.add(gamePanel, BorderLayout.CENTER);
    }

    // abaikan block ini, hanya iseng buat game pakai gui
    int spawnTimer = 0;
    int shootTimer = 0;
    int speedCounter = 0;
    int speed = 0;
    boolean canShoot = true;
    ArrayList<JLabel> bullets = new ArrayList<JLabel>();
    ArrayList<JTextField> enemies = new ArrayList<JTextField>();
    ArrayList<Integer> keyPressed = new ArrayList<Integer>();
    Timer timer;
    private void startGame(){
        gamePanel.setBackground(Color.BLACK);
        JButton player = new JButton("0");
        player.setSize(75,50);
        player.setFont(new Font("Arial", Font.BOLD, 30));
        player.setHorizontalAlignment(SwingConstants.CENTER);
        player.setLocation(0,382);
        gamePanel.add(player);

        menu.addKeyListener(new KeyListener() {
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
        
                            gamePanel.add(bullet);
                            gamePanel.repaint();
                            gamePanel.revalidate();
                            canShoot = false;
                    }
                }
                if(newy >= 0 && newy <= 382)
                    player.setLocation(0, newy);
                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed.remove(Integer.valueOf(e.getKeyCode()));
            }
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
        });
        menu.setFocusable(true);
        menu.requestFocusInWindow();
        
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(++speedCounter >= 200){
                    speed++;
                    speedCounter = 0;
                }
                if(++shootTimer >= 15){
                    shootTimer = 0;
                    canShoot = true;
                }
                if(++spawnTimer >= 150 - speed){
                    spawnTimer = 0;
                    JTextField enemy = new JTextField("5");
                    enemy.setSize(50,50);
                    enemy.setLocation(900, new Random().nextInt(383));
                    enemy.setFont(new Font("Arial", Font.BOLD, 30));
                    enemy.setHorizontalAlignment(SwingConstants.CENTER);
                    enemy.setBackground(Color.DARK_GRAY);
                    enemy.setForeground(Color.black);
                    enemy.setEditable(false);

                    enemies.add(enemy);

                    gamePanel.add(enemy);
                    gamePanel.repaint();
                    gamePanel.revalidate();
                    canShoot = false;
                }
                for(JLabel bullet : bullets){
                    bullet.setLocation(bullet.getLocation().x + 10, bullet.getLocation().y);
                    for(JTextField enemy : enemies){
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
                    enemy.setLocation(enemy.getLocation().x - 2, enemy.getLocation().y);
                    if(enemy.getLocation().getX() <= 75 && enemy.isVisible()){
                        player.setForeground(Color.RED);
                        timer.stop();
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * Method untuk mendapatkan instance MainFrame.
     * Instance Class MainFrame harus diambil melalui method ini agar menjamin hanya terdapat satu Frame pada program.
     *
     * @return instance dari class MainFrame
     * */
    public static MainFrame getInstance(){
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    /**
     * Method untuk pergi ke panel sesuai dengan yang diberikan pada param.
     *
     * @param page -> key dari halaman yang diinginkan.
     * */
    public void navigateTo(String page){
        cards.show(menu, page);
    }

    /**
     * Method untuk login pada sistem.
     * Jika gagal login akan mengembalikan boolean false dan jika berhasil login: <p>
     * - return boolean true <p>
     * - menampilkan halaman yang sesuai <p>
     *
     * @param id -> ID dari pengguna
     * @param password -> password dari pengguna
     * @return boolean yang menandakan apakah login berhasil atau gagal.
     * */
    public boolean login(String id, String password){
        // login di employee & member, kalau berhasil salah satu pindah page
        for (Loginable panel:
                loginablePanel) {
            if(panel.login(id, password)){
                navigateTo(panel.getPageName());
                return true;
            }
        }
        return false;
    }


    /**
     * Method untuk logout dari sistem, kemudian menampilkan halaman Home.
     * */
    public void logout(){
        for (Loginable panel:
                loginablePanel) {
            panel.logout();
        }
        navigateTo(HomeGUI.KEY);
    }
    public static void main(String[] args) {
        // menampilkan GUI kalian.
        // Jika ingin tau lebih lanjut mengapa menggunakan SwingUtilities.invokeLater
        // silakan akses https://stackoverflow.com/questions/6567870/what-does-swingutilities-invokelater-do
        // Tapi in general kalian tidak usah terlalu overthinking line ini selain fungsi utamanya adalah menampilkan GUI
        SwingUtilities.invokeLater(MainFrame::getInstance);
    }
}

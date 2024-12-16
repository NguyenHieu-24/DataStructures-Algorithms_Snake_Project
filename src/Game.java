// MAIN  CLASS. 
// CHỨA CÁC JPanel KHÁC NHAU ĐẠI DIỆN CHO CÁC MÀN HÌNH. 
// CHỨA MỘT SỐ BIẾN -> CÓ THỂ TRUY CẬP BỞI TẤT CẢ CÁC LỚP


/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    // Initializes a panel to switch the menus
    public static CardLayout cards = new CardLayout();
    public static JPanel menus = new JPanel(cards);
    public static String difficulty = "Easy";
    public static boolean gridOn = true;
    public static int score = 0;
    
    public void run() {
        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        JFrame frame = new JFrame("Snake");
        GamePanel gamePanel = new GamePanel();
        frame.setLocation(300, 300);
        frame.setLayout(new BorderLayout());
        frame.setResizable(true); // false
        
        // Initializes the game court
        final GameLogic game_court = new GameLogic();
        game_court.setPreferredSize(new Dimension(800, 600)); // Đảm bảo kích thước

        // Khởi tạo kích thước cửa sổ cho các menu khác nhau
        int height = 800; // Đặt kích thước cố định
        int width = 600;  // Đặt kích thước cố định
        
        // Initializes the start menu
        final StartMenu start_menu = new StartMenu(height, width);
        start_menu.setPreferredSize(new Dimension(width, height));
        
        // Initializes the help menu
        final HelpMenu help_menu = new HelpMenu(height, width);
        help_menu.setPreferredSize(new Dimension(width, height));
        
        // Initializes the settings menu
        final SettingsMenu settings = new SettingsMenu(height, width);
        settings.setPreferredSize(new Dimension(width, height));
        
        // Initializes the end menu
        final EndMenu end_menu = new EndMenu(height, width);
        end_menu.setPreferredSize(new Dimension(width, height));
        
        menus.setLayout(cards);
        menus.add(start_menu, "StartMenu");
        menus.add(help_menu, "HelpMenu");
        menus.add(settings, "Settings");
        menus.add(game_court, "GameCourt");
        menus.add(end_menu, "EndMenu");

        // Hiển thị panel ban đầu
        cards.show(menus, "StartMenu");

        // Cập nhật lại giao diện
        menus.revalidate();
        menus.repaint();
        
        frame.add(gamePanel);
        frame.setSize(650, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(menus);
        frame.setVisible(true);
    }
    
    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}

// MENU MÀ NGƯỜI DÙNG CÓ THỂ CHỌN SAU KHI TRÒ CHƠI KẾT THÚC! 
// KHI TRÒ CHƠI KẾT THÚC, NGƯỜI DÙNG CÓ THỂ CHỌN -> LƯU ĐIỂM SỐ 
   // VÀ SAU ĐÓ VÀO EndMenu, NƠI NGƯỜI DÙNG CÓ THỂ NHẬP "TÊN NGƯỜI DÙNG" / LƯU "ĐIỂM SỐ" / XEM TOP 5 ĐIỂM CAO NHẤT(Easy, Medium, Hard) / THOÁT TRÒ CHƠI 

// Import thư viện đồ họa cho giao diện
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;

// Import thư viện cho các sự kiện như nhấn chuột
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Import cho việc đọc/ghi dữ liệu từ file
import java.io.IOException;

// Import thư viện cho danh sách, sắp xếp,...
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

// Import thư viện để tạo giao diện Swing
import javax.swing.*;

// kế thừa lớp JPanel: chứa các phần tử trong giao diện trò chơi, không có title như JFrame
public class EndMenu extends JPanel {
    
    private static final long serialVersionUID = -3893041218932094419L;
    // tạo obj trong lớp HighScoreUpdater: quản lý và lưu trữ điểm số cao nhất
    private HighScoreUpdater highscores;
    
    // tạo các nút bấm
    private JButton exit = new JButton ("");
    private JButton save = new JButton("");
    private JButton show_scores = new JButton("");
    
    // tạo ô để user nhập tên
    private JTextField enter = new JTextField("");
    
    // các hình ảnh cho các nút bấm
    private ImageIcon exit_button = new ImageIcon("images\\button_exit.png");
    private ImageIcon save_button = new ImageIcon("images\\button_save.png");
    private ImageIcon show_button = new ImageIcon("images\\button_show-highscores.png");
    
    // 1 hình nền chứa các nút: Save, Exit, Show Highscores và nhập Enter Username, sau khi kết thúc trò chơi
    private ImageIcon bkg = new ImageIcon("images\\snake_background.png");
    
    private JLabel background = new JLabel("");
    private JLabel enter_text = new JLabel("Enter Username: ");
   
    private JPanel center = new JPanel();
    private JPanel vertical = new JPanel();
    private JPanel userInput = new JPanel();
    
    private String username;
    
    // hàm khởi tạo, thiết lập height và width của EndMenu
    public EndMenu(int height, int width){
        setPreferredSize(new Dimension(width, height));
        
        try {
            highscores = new HighScoreUpdater();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HighScoreUpdater.FormatException e) {
            e.printStackTrace();
        }
        
        enter_text.setForeground(Color.WHITE);
        enter_text.setFont(new Font("Verdana", Font.BOLD, 10));
        
        // Cài đặt giao diện cho phần nhập tên người chơi
        userInput.setLayout(new BoxLayout(userInput, BoxLayout.X_AXIS));
        userInput.setOpaque(false);
        userInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userInput.add(enter_text);
        userInput.add(enter);
        
        // thiết lập biểu tượng cho các nút
        save.setIcon(save_button);
        exit.setIcon(exit_button);
        show_scores.setIcon(show_button);
        
        // xóa khung viền của các nút
        save.setBorder(BorderFactory.createEmptyBorder());
        exit.setBorder(BorderFactory.createEmptyBorder());
        show_scores.setBorder(BorderFactory.createEmptyBorder());
        
        // thêm sự kiện cho các nút khi nhấp chuột: 
          // addMouseListener là một phương thức của JButton trong thư viện Swing
          // gọi obj.addMouseListener(new Clicked());, đang thêm một trình nghe sự kiện chuột (mouse listener) vào nút đó
        save.addMouseListener(new Clicked());
        exit.addMouseListener(new Clicked());
        show_scores.addMouseListener(new Clicked());
        
        show_scores.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // cài đặt bố cục cho các nút và văn bản
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.setOpaque(false);
        center.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        center.add(save);
        // khoảng trống giữa các nút
        center.add(Box.createHorizontalStrut(20));
        center.add(exit);
        
        vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
        vertical.setOpaque(false);
        
        // add các thành phần vào bảng chính
        vertical.add(userInput);
        vertical.add(Box.createVerticalStrut(20));
        vertical.add(center);
        vertical.add(Box.createVerticalStrut(20));
        vertical.add(show_scores);
        
        setLayout(new GridBagLayout());
        
        background.setIcon(bkg);
        background.setLayout(new GridBagLayout());
        add(background);
        background.add(vertical);
    }
    
    // xử lý sự kiện chuột bằng cách kế thừa lớp MouseAdapter
    class Clicked extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == save){
                saveScore();
            } else if (e.getSource() == exit) {
                System.exit(0);
            } else if (e.getSource() == show_scores){
                showScore();
            }
        }
    }

    // lưu điểm 
    public void saveScore() {
        try {
            username = enter.getText();
            highscores.write(username, Game.score, Game.difficulty);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HighScoreUpdater.FormatException e) {
            e.printStackTrace();
        }
    }
    
    // hiển thị điểm cao
    public void showScore() {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        if (Game.difficulty.equals("Easy")){
            map = highscores.getEasy();
        } else if (Game.difficulty.equals("Medium")) {
            map = highscores.getMedium();
        } else if (Game.difficulty.equals("Hard")) {
            map = highscores.getHard();
        }

        // sắp xếp điểm số từ cao -> thấp
        Set<Entry<String, Integer>> set = map.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        
        // hiển thị top5 điểm cao nhất bằng hộp thoại
        String output = "";
        
        int size = 5;
        if(list.size() < size){
            size = list.size();
        }
        
        for(int i = 1; i <= size; i++){
            output = output + "#" + i + ": " + list.get(i-1).getKey() + ", " + list.get(i-1).getValue() + "\n";
        }
        JOptionPane.showMessageDialog(this, output, "Top Highscores", JOptionPane.PLAIN_MESSAGE);
    }
    
    // vẽ nền Menu (sử dụng snake_ackground.png)
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bkg.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}


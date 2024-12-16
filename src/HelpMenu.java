// HIỂN THỊ CÁC ĐIỀU KHIỂN TRÒ CHƠI VÀ CÓ NÚT QUAY LẠI ĐỂ QUAY VỀ MENU BẮT ĐẦU.

// Cung cấp các lớp để tạo giao diện người dùng, bao gồm các kích thước, bố cục, đồ họa
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;

// Chứa các lớp xử lý sự kiện, như MouseEvent
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Chứa các lớp để xây dựng giao diện đồ họa người dùng (GUI) như JPanel, JButton, và ImageIcon
import javax.swing.*;

// là một bảng điều khiển tùy chỉnh được sử dụng để hiển thị thông tin trợ giúp
// bảng điều khiển này bao gồm một hình nền và một nút để quay trở lại menu chính
public class HelpMenu extends JPanel {
    
    private static final long serialVersionUID = -2837861830664741390L;
    
    // đóng vai trò là nút để quay lại StartMenu
    private JButton back = new JButton("");

    private ImageIcon back_button = new ImageIcon("images\\button_back.png");
    private ImageIcon bkg = new ImageIcon("images\\help_background.png");

    // 1 JLabel chứa hình nền và cho phép thêm các thành phần khác lên trên
    private JLabel background = new JLabel("");
    
    // 1 JPanel đóng vai trò là container cho nút quay lại, đặt nó ở vị trí trung tâm của màn hình
    private JPanel center = new JPanel();
    
    // Constructor
    // khởi tạo bảng điều khiển và các thành phần của HelpMenu
    public HelpMenu(int height, int width){

        // Đặt Kích Thước Cho Bảng Điều Khiển
        setPreferredSize(new Dimension(width, height));
        
        // Cấu Hình Nút Quay Lại
        back.setIcon(back_button);
            // Viền của nút được loại bỏ bằng
        back.setBorder(BorderFactory.createEmptyBorder());
            // 1 MouseAdapter được thêm vào để xử lý các lần nhấp chuột vào nút
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                Game.cards.show(Game.menus, "StartMenu");
            }
        });
        
        // Cấu Hình Bảng center
            // Bảng center sử dụng BoxLayout để căn chỉnh các thành phần theo chiều dọc (BoxLayout.Y_AXIS)
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            // nền của bảng được đặt trong suốt
        center.setOpaque(false);
        center.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Khoảng cách dọc 380 pixel được thêm vào bằng
        center.add(Box.createVerticalStrut(380));
        // nút quay lại được thêm vào bảng center
        center.add(back);
        
        // Thiết Lập Bố Cục bảng điều khiển HelpMenu được đặt bằng GridBagLayout
        setLayout(new GridBagLayout());
        
        // Biểu tượng của nhãn background được đặt thành hình nền (bkg)
        background.setIcon(bkg);
        // sử dụng GridBagLayout để căn giữa các thành phần
        background.setLayout(new GridBagLayout());
        add(background);
        // Bảng center được thêm vào nhãn background
        background.add(center);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Vẽ hình ảnh nền trực tiếp
        g.drawImage(bkg.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}

// sử dụng thư viện Swing
// quản lý hiển thị đồ họa của trò chơi, logic của trò chơi, và tương tác người dùng

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;

// xử lý các thao tác từ bàn phím (điều khiển hướng đi của rắn).
// cập nhật logic trò chơi (chuyển động của rắn, va chạm, và tương tác với thức ăn).
public class GamePanel extends JPanel implements ActionListener {

    // Thiết lập kích thước của bảng trò chơi (800x600 pixel).
    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 600;

    // Kích thước của mỗi đơn vị (đoạn) của con rắn.
    private final int DOT_SIZE = 10;    
    
    // Chiều dài tối đa của con rắn.
    private final int ALL_DOTS = 900;  
    
    // Dùng để xác định vị trí ngẫu nhiên của quả táo.
    private final int RAND_POS = 29; 
    
    // Thiết lập tốc độ trò chơi bằng cách xác định độ trễ giữa mỗi lần cập nhật trò chơi (140 ms).
    private final int DELAY = 140;         

    // x[] và y[]: Mảng để lưu trữ tọa độ của mỗi đoạn của con rắn.
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots; // chiều dài hiện tại của rắn
    // tọa độ quả táo trên bảng trò chơi
    private int apple_x;
    private int apple_y;

    // Các biến boolean để xác định hướng hiện tại của rắn
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    // Theo dõi xem trò chơi đang diễn ra hay đã kết thúc.
    private boolean inGame = true;

    // Điều khiển các lần cập nhật trò chơi theo khoảng thời gian cố định (tốc độ).
    private Timer timer;
    // Hình ảnh đại diện cho thân rắn, quả táo và đầu của rắn.
    private Image ball;
    private Image apple;
    private Image head;

    // Constructor
    public GamePanel() {
        initBoard(); // phương thức thiết lập các cài đặt cho bảng trò chơi
    }

    // Thêm KeyListener (TAdapter) để xử lý các sự kiện từ bàn phím.
    // Đặt màu nền là màu đen.
    // Gọi loadImages() để tải các hình ảnh cho rắn và quả táo.
    // Gọi initGame() để thiết lập trạng thái ban đầu của trò chơi.
    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        // Tải các hình ảnh (có thể tùy chỉnh lại đường dẫn)
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    private void initGame() {
        dots = 3; // Chiều dài ban đầu của rắn

        // Khởi tạo vị trí ban đầu của rắn
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        // timer để kích hoạt các cập nhật trò chơi
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        // vẽ lại bảng
        super.paintComponent(g);
        // vẽ rắn và quả táo
        doDrawing(g);
    }

    // Vẽ rắn (đầu và thân) và quả táo nếu trò chơi đang diễn ra.
    // ngược lại, gọi gameOver(g) nếu trò chơi kết thúc.
    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (PANEL_WIDTH - metr.stringWidth(msg)) / 2, PANEL_HEIGHT / 2);
    }

    // Kiểm tra xem đầu rắn có chạm vào quả táo hay không, tăng kích thước của rắn và đặt lại vị trí của quả táo.
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    // cập nhật vị trí của rắn dựa trên hướng hiện tại
    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    // Kiểm tra xem rắn có tự va chạm hoặc va vào các cạnh của bảng trò chơi không, nếu có thì kết thúc trò chơi.
    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= PANEL_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= PANEL_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    // Đặt ngẫu nhiên vị trí mới cho quả táo trên bảng.
    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) { // kiểm tra trò chơi có đang tiếp tục không
            // thực hiện các bước cập nhật trạng thái của trò chơi
            checkApple();
            checkCollision();
            move();
        }

        // vẽ lại bảng trò chơi sau các thay đổi mới
        repaint();
    }

    // Xử lý các sự kiện từ phím mũi tên để thay đổi hướng của rắn mà không cho phép rắn quay đầu trực tiếp ngược lại.
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}


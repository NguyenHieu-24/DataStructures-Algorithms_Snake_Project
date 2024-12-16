// BẢNG THÔNG TIN Ở PHÍA DƯỚI MÀ NGƯỜI DÙNG NHÌN THẤY KHI BẮT ĐẦU TRÒ CHƠI
// CHỨA THÔNG TIN VỀ "ĐIỂM SỐ HIỆN TẠI" VÀ "MỨC ĐỘ KHÓ" CỦA TRÒ CHƠI

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Board
 * 
 * The class that displays and manages the score
 */
public class InfoPanel{

    // Lưu trữ điểm số hiện tại của người chơi
    private int score;
    // Chiều rộng của bảng thông tin
    private int width; 
    // Chiều rộng của bảng thông tin
    private int height; 
    // Chiều cao của bảng trò chơi chính
    private int boardHeight;
    
    /**
     * Creates a new Board instance.
     */
    public InfoPanel(int score, int width, int height, int h) {
        this.score = score;
        this.width = width;
        this.boardHeight = height;
        this.height = h;
    }
      
    // để cập nhật điểm số
    public void setScore(int s) {
        score = s;
    }
    
    // vẽ bảng thông tin lên giao diện đồ họa
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, boardHeight, width, height);
        
        g.setColor(Color.WHITE);
        
        int y = 30 + boardHeight; //the y coordinate of the first string printed
        
        //Draws the title
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        String title = "Snake Game";
        g.drawString(title, width / 2 - g.getFontMetrics().stringWidth(title) / 2, y);
        
        //Draws the headers
        g.setFont(new Font("Verdana", Font.BOLD, 15));
        
        String level = "Difficulty: " + Game.difficulty;
        g.drawString(level, width / 2 - g.getFontMetrics().stringWidth(level) / 2, y += 25);
        
        String totalScore = "Total Score: " + score;
        g.drawString(totalScore, width / 2 - g.getFontMetrics().stringWidth(totalScore) / 2, y += 25);
    }
}

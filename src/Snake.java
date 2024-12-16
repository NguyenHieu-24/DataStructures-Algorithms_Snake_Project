// MỞ RỘNG TỪ LỚP GameObj. GHI ĐÈ CÁC PHƯƠNG THỨC nextLocation(), move(), và draw() CỦA GameObj.java 
// VẼ ĐẦU VÀ THÂN RẮN, TÙY THUỘC VÀO LOẠI ĐƯỢC SỬ DỤNG ĐỂ TẠO ĐỐI TƯỢNG RẮN 

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Snake extends GameObj {

    // Constructor
    public Snake(int pos_x, int pos_y, int size, int board_width, int board_height, Direction d, Type t) {
        super(pos_x, pos_y, size, board_width - 1, board_height - 1, d, t);
    }

    @Override
    public Point nextLocation() {
        Snake temp = new Snake(pos_x, pos_y, size, 
            max_x, max_y, dir, type);
        temp.move();

        // phương thức trả về vị trí mới của temp dưới dạng một đối tượng Point, sau khi di chuyển
        return new Point(temp.pos_x, temp.pos_y);
    }
    
    // cập nhật vị trí của rắn dựa trên hướng hiện tại (dir)
    @Override
    public void move() {
            if (dir == Direction.LEFT){
                pos_x--;
            }
            else if (dir == Direction.RIGHT){
                pos_x++;
            }
            else if (dir == Direction.DOWN){
                pos_y++;
            }
            else if (dir == Direction.UP){
                pos_y--;
            }
    }
    
    // vẽ các phần của rắn trên bảng
    @Override
    public void draw(int x, int y, Graphics g) {
        // thiết lập màu xanh lá cây để tô màu cho thân rắn
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
        
        // thiết lập màu đen để vẽ viền xung quanh hình chữ nhật
        g.setColor(Color.RED);
        g.drawRect(x, y, size, size);
        
        if (type == Type.HEAD) {
            int EYE_RADIUS = size / 5;
            switch(dir) {
            case UP: {
                g.fillOval(x + EYE_RADIUS - 2, y + EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                g.fillOval(x + size - EYE_RADIUS - 2, y + EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                break;
            }
                
            case DOWN: {
                g.fillOval(x + EYE_RADIUS - 2, y + size - EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                g.fillOval(x + size - EYE_RADIUS - 2, y + size - EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                break;
            }
            
            case LEFT: {
                g.fillOval(x + EYE_RADIUS - 2, y + EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                g.fillOval(x + EYE_RADIUS - 2, y + size - EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                break;
            }
                
            case RIGHT: {
                g.fillOval(x + size - EYE_RADIUS - 2, y + EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                g.fillOval(x + size - EYE_RADIUS - 2, y + size - EYE_RADIUS - 2, EYE_RADIUS, EYE_RADIUS);
                break;
            }
            
            }
        } 
    }

}

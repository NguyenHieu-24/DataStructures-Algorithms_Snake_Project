// BẢNG CỦA TRÒ CHƠI. ĐƯỢC NGƯỜI DÙNG NHÌN THẤY, ĐƯỢC CẬP NHẬT MỖI KHI NGƯỜI DÙNG DI CHUYỂN
// LÀ MỘT MẢNG 2D TẠO RA LƯỚI. 
// MỖI Ô VUÔNG CÓ THỂ CÓ 1 ĐỐI TƯỢNG TRÒ CHƠI (GameObj)


import java.awt.Color;
import java.awt.Graphics;

/**
 * Board
 * 
 * The class that displays and manages the Objects of the game
 */
public class Board {

    // số dòng, cột, kích thước của từng ô vuông trên Board
    public int COL_NUM;
    public int ROW_NUM;
    public int OBJECT_SIZE;
      
    // mảng 2D, chứa các đối tượng (GameObj) hoặc chứa rỗng (không có gì)
    private GameObj[][] objects;
    
    // Constructor
    public Board(int row, int col, int objSize) {
        ROW_NUM = row;
        COL_NUM = col;
        OBJECT_SIZE = objSize;
        objects = new GameObj[ROW_NUM] [COL_NUM];
    }
    
    /**
     * Sets the tile at the desired location.
     * @param col The column coordinate (tọa độ) of the object.
     * @param row The row coordinate of the object.
     * @param o The game object to place.
     */
    // đặt một đối tượng vào bảng (nói rõ nó sẽ nằm ở đâu)
    public void setObject(GameObj o) {
        objects[o.pos_y][o.pos_x] = o;
    }
    
    /**
     * Gets the tile at the desired coordinate.
     * @param x The x coordinate of the tile.
     * @param y The y coordinate of the tile.
     * @return
     */
    public GameObj getObject(int x, int y) {
        return objects[y][x];
    }
    
    public void resetBoard() {
        for(int i = 0; i < COL_NUM; i++) {
            for(int j = 0; j < ROW_NUM; j++) {
                // trở thành rỗng
                objects[j][i] = null;
            }
        }
    }
    
    // vẽ Board
    public void draw(Graphics g) {
        int width = COL_NUM * OBJECT_SIZE;
        int height = ROW_NUM * OBJECT_SIZE;
        
        //Draw the grid on the window.
        // nền Board được vẽ một màu DARK_GRAY
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width + 1, height + 1);
         
        // khung Board được vẽ một màu GRAY
        g.setColor(Color.GRAY);
        g.drawRect(0, 1, width - 1, height - 1);
        
        // Draw the objects
        for(int i = 0; i < COL_NUM; i++) {
            for(int j = 0; j < ROW_NUM; j++) {
                GameObj o = getObject(i, j);
                if(getObject(i, j) != null) {
                    // nếu ô không trống, hàm o.draw(..) được gọi
                    // kiểm tra không trống -> vẽ để người dùng có thể nhìn thấy được đối tượng đó
                    o.draw(i * OBJECT_SIZE, j * OBJECT_SIZE, g);
                    // đối tượng Graphics dùng để vẽ đối tượng lên màn hình
                }
            }
        }
    }
}

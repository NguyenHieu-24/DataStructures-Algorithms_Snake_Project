// MỞ RỘNG TỪ LỚP GameObj. 
// VẼ 1 VÒNG TRÒN MÀU VÀNG TẠI 1 VỊ TRÍ CỤ THỂ.

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a yellow circle, starting in the upper left
 * corner of the game court.
 * 
 */
public class FoodObj extends GameObj {

	// Constructor
	  // pos_x và pos_y: Vị trí ban đầu của đối tượng trên bảng trò chơi
	  // size: Kích thước của đối tượng (có thể là đường kính của vòng tròn)
	  // board_width và board_height: Kích thước của bảng trò chơi
	  // d: Hướng đi ban đầu của đối tượng (có thể là một enum hoặc kiểu lớp có tên là Direction)
	  // t: Loại của đối tượng (có thể là một enum hoặc kiểu lớp có tên là Type)
	public FoodObj(int pos_x, int pos_y, int size, int board_width, int board_height, Direction d, Type t) {
	    super(pos_x, pos_y, size, board_width, board_height, d, t);
	}
	
	@Override
	public void draw(int x, int y, Graphics g) {
		// x + 2 và y + 2: Góc trên bên trái của hình oval, được điều chỉnh lệch đi 2 pixel từ vị trí xác định để tạo khoảng trống nhỏ
    	// size - 4: Chiều rộng và chiều cao của hình oval, được điều chỉnh giảm đi 4 pixel để tạo khoảng cách
		g.setColor(Color.YELLOW);
		g.fillOval(x + 2, y + 2, size - 4, size - 4);
	}
}

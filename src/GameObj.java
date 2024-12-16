// 1 ABSTRACT CLASS CHỨA DỮ LIỆU (VỊ TRÍ, TỌA ĐỘ X VÀ Y TỐI ĐA, HƯỚNG, VÀ LOẠI)
// BAO GỒM CÁC PHƯƠNG THỨC nextLocation(), move(), VÀ draw() KHUNG SƯỜN 
   // CŨNG NHƯ 1 PHƯƠNG THỨC XÁC ĐỊNH ĐỐI TƯỢNG CÓ VA VÀO TƯỜNG KO?.

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Graphics;
import java.awt.Point;

public class GameObj {
    
	// đại diện cho loại đối tượng
    public Type type;
    
	// kích thước đối tượng
    public int size;

	// tọa độ hiện tại của đối tượng trên bảng trò chơi
	public int pos_x; 
	public int pos_y;
	
	// vị trí mà đối tượng không vượt quá bảng
	public int max_x;
	public int max_y;
	
	// Direction: the direction of the object 
	public Direction dir;
	
	// Constructor
	public GameObj(int pos_x, int pos_y, int size, 
	        int board_width, int board_height, Direction d, Type t){
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.size = size;
		this.dir = d;
		this.type = t;
		
		this.max_x = board_width;
		this.max_y = board_height;
	}
	
	/**
     * Determine whether this game object will hit the wall in the
     * next time step, assuming that both objects continue with their 
     * current velocity.
     * 
     * @return whether an intersection will occur.
     */
	// kiểm tra đối tượng có va vào tường trong bước di chuyển tiếp theo ko, dựa trên hướng đi của nó (nextLocation())
    public boolean willHitWall(){
        Point p = nextLocation();
		// nếu vượt quá giới hạn bảng, trả về true
        return ((p.x < 0) || (p.x > max_x)
         || (p.y < 0) || (p.y > max_y));
    }
    
    /**
     * Determine whether this game object will hit the wall in the
     * next time step, assuming that both objects continue with their 
     * current velocity.
     * 
     * @return whether an intersection will occur.
     */
	// kiểm tra xem đối tượng có đang va vào tường hiện tại không
    public boolean hitsWall(){
        return ((pos_x < 0) || (pos_x > max_x)
         || (pos_y < 0) || (pos_y > max_y));
    }
	
    /**
     * Determines the next location of the object. 
     * Will be overridden by child classes.
     * 
     */
	// Phương thức này là một placeholder (giữ chỗ) để tính toán vị trí tiếp theo của đối tượng dựa trên trạng thái hiện tại. 
	// Nó trả về một đối tượng Point chỉ định tọa độ tiếp theo.
    public Point nextLocation() {
        return null;
    }
    
    
    /**
     * Default move method that provides how the object should move
     * in the GUI. This method does not do anything. Subclass should 
     * override this method based on how their object should move.
     */
	// Phương thức này cũng là một placeholder cho cách mà đối tượng di chuyển. 
	// Hiện tại, phương thức này không thực hiện gì, nhưng sẽ được ghi đè trong các lớp con để xác định cách di chuyển cụ thể của đối tượng.
    public void move(){
        
    }
    
	/**
	 * Default draw method that provides how the object should be drawn 
	 * in the GUI. This method does not draw anything. Subclass should 
	 * override this method based on how their object should appear.
	 * 
	 * @param g 
	 *	The <code>Graphics</code> context used for drawing the object.
	 * 	Remember graphics contexts that we used in OCaml, it gives the 
	 *  context in which the object should be drawn (a canvas, a frame, 
	 *  etc.)
	 */
	// một placeholder, đối tượng sẽ được vẽ trên bảng trò chơi
	// nó nhận một đối tượng Graphics làm tham số, được sử dụng để hiển thị đối tượng một cách trực quan
	public void draw(int x, int y, Graphics g) {
	}
	
}

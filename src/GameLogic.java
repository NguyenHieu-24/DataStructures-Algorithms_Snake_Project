// LOGIC CỦA TRÒ CHƠI. 
// CHẠY KHI NGƯỜI DÙNG NHẤN NÚT "BẮT ĐẦU". 
// LỚP NÀY TẠO 1 BẢNG TRÒ CHƠI VÀ CHẠY TOÀN BỘ TRÒ CHƠI.


/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// thư viện swing, tạo giao diện đồ họa
import javax.swing.*;

/**
 * GameCourt
 * chịu trách nhiệm quản lý các logic chính của trò chơi
 * bao gồm tạo bảng trò chơi, quản lý trạng thái rắn, di chuyển, kiểm tra va chạm, tạo và xử lý thức ăn, và cập nhật giao diện khi người dùng tương tác
 * 
 */
public class GameLogic extends JPanel {

    // the food object
    private FoodObj food; 

    // xác định vị trí ngẫu nhiên cho thức ăn
    private int randomXPosition = (int) (Math.random() * (((BOARD_NUM_COL - 1) - MIN_SNAKE_LENGTH) + 1)) + MIN_SNAKE_LENGTH;
    private int randomYPosition = (int) (Math.random() * (((BOARD_NUM_ROW - 1) - MIN_SNAKE_LENGTH) + 1)) + MIN_SNAKE_LENGTH;

    // board object quản lý trạng thái các ô trên bảng
    private Board board;

    // hiển thị thông tin như điểm số
    private InfoPanel info;
    
    // trò chơi đang chạy hay đã kết thúc
    private boolean playing = false; 

    // xác định có phải là trò chơi mới ko
    private boolean newGame = true; 

    // xác định hướng đi hiện tại của rắn
    private Direction direction;

    // list of snake joints
    private ArrayList<Snake> snake;

    // Game constants
        // kích thước bảng trò chơi
    private static final int BOARD_NUM_ROW = 25;
    private static final int BOARD_NUM_COL = 30;
        // kích thước mỗi ô
    private static final int OBJECT_SIZE = 20;
        // chiều rộng, chiều cao bảng trò chơi
    private static final int COURT_WIDTH = BOARD_NUM_COL * OBJECT_SIZE;
    private static final int COURT_HEIGHT = BOARD_NUM_ROW * OBJECT_SIZE;

    private static final int INFO_HEIGHT = Math.abs(COURT_WIDTH - COURT_HEIGHT);

        // chiều dài tối thiểu của rắn = 3 đoạn
    private static final int MIN_SNAKE_LENGTH = 3;
    private static final Direction START_DIRECTION = Direction.RIGHT;

    // Update interval for timer, in milliseconds (khoảng thời gian giữa các bước di chuyển của rắn)
    private static int INTERVAL = 100;

    private static Timer timer;

    // Getter
    public int getHeight() {
        return COURT_HEIGHT;
    }

    public int getWidth() {
        return COURT_WIDTH;
    }

    // GameLogic khởi tạo bảng trò chơi, thiết lập bộ đếm thời gian (Timer) để cập nhật trò chơi định kỳ
    // và thêm một bộ lắng nghe (KeyListener) để xử lý sự kiện khi người dùng nhấn các phím điều khiển rắn

    // INTERVAL xác định khoảng thời gian cập nhật trò chơi mỗi bước (tick)
    public GameLogic() {
        setPreferredSize(new Dimension(COURT_WIDTH, COURT_HEIGHT + INFO_HEIGHT));
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // tick() 
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        setFocusable(true);
        // This key listener allows the square to move as long
        // as an arrow key is pressed, by changing the snake's
        // direction accordingly. (The tick method below actually
        // moves the snake.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                // W hoặc UP: điều khiển rắn đi lên.
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (playing) {
                        if (direction != Direction.DOWN) {
                            direction = Direction.UP;
                        }
                    }
                    break;

                // S hoặc DOWN: Điều khiển rắn đi xuống.
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (playing) {
                        if (direction != Direction.UP) {
                            direction = Direction.DOWN;
                        }
                    }
                    break;

                // A hoặc LEFT: Điều khiển rắn đi sang trái.
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (playing ) {
                        if (direction != Direction.RIGHT) {
                            direction = Direction.LEFT;
                        }
                    }
                    break;

                // D hoặc RIGHT: Điều khiển rắn đi sang phải.
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (playing) {
                        if (direction != Direction.LEFT) {
                            direction = Direction.RIGHT;
                        }
                    }
                    break;
                
                // Bắt đầu trò chơi mới hoặc lưu điểm sau khi trò chơi kết thúc.
                case KeyEvent.VK_ENTER:
                    if (!playing && newGame) {
                        playing = true;
                        newGame = false;
                    } else if (!playing && !newGame){
                        Game.cards.show(Game.menus, "EndMenu");
                    }
                    break;
                
                // ESCAPE: thoát khỏi trò chơi.
                case KeyEvent.VK_ESCAPE:
                    if(playing){
                        playing = false;
                        newGame = false;
                        repaint();
                    } else if (!playing && !newGame){
                        System.exit(0);
                    }
                    break;
                }
            }

        });
    }

    // Đặt lại trạng thái ban đầu của trò chơi. Tạo lại bảng, rắn, thức ăn, và thiết lập điểm số về 0. Hướng di chuyển của rắn sẽ là hướng RIGHT (phải).
    // Khởi động lại bộ đếm thời gian dựa vào độ khó của trò chơi (dễ, trung bình, khó).
    public void reset() {
        if (Game.difficulty.equals("Easy")){
            timer.setDelay(100);
        } else if (Game.difficulty.equals("Medium")){
            timer.setDelay(75);
        } else if (Game.difficulty.equals("Hard")){
            timer.setDelay(50);
        }
        
        board = new Board(BOARD_NUM_ROW, BOARD_NUM_COL, OBJECT_SIZE);
        info = new InfoPanel(Game.score, COURT_WIDTH, COURT_HEIGHT, INFO_HEIGHT);
        
        food = new FoodObj(randomXPosition, randomYPosition, OBJECT_SIZE, BOARD_NUM_COL - 1, BOARD_NUM_ROW - 1, START_DIRECTION, Type.FOOD);

        snake = new ArrayList<Snake>();
        snake.add(new Snake(MIN_SNAKE_LENGTH - 1, 0, OBJECT_SIZE, BOARD_NUM_COL, BOARD_NUM_ROW, START_DIRECTION, Type.HEAD));
        for (int i = MIN_SNAKE_LENGTH - 2; i >= 0; i--) {
            Snake s = new Snake(i, 0, OBJECT_SIZE, BOARD_NUM_COL, BOARD_NUM_ROW, START_DIRECTION, Type.BODY);
            snake.add(s);
        }
        updateBoard();
        
        Game.score = 0;
        playing = false;
        newGame = true;

        direction = START_DIRECTION;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    // Đây là phương thức được gọi mỗi khi bộ đếm thời gian kích hoạt. Nếu trò chơi đang chạy (playing), nó sẽ kiểm tra trạng thái trò chơi và cập nhật bảng.
    // Nếu là trò chơi mới (newGame), nó sẽ gọi phương thức reset() để bắt đầu.
    public void tick() {
        if (playing) {
            // advance the snake in its current direction.
            checkGameStatus();
            // updates the board
            updateBoard();
            repaint();
        } else if (newGame){
            reset();
        }
    }

    // di chuyển các đoạn của rắn theo hướng hiện tại
    public void moveObjects() {
        for (int i = snake.size() - 1; i >= 0; i--) {
            Snake s = snake.get(i);
            s.move();
            snake.set(i, s);
        }
    }

    // Cập nhật hướng di chuyển của các đoạn trong rắn. 
    // Hướng của đầu rắn là hướng người chơi điều khiển, các đoạn còn lại sẽ theo hướng của đoạn trước nó.
    public void updateDirection() {
        for (int i = snake.size() - 1; i >= 0; i--) {
            Snake s = snake.get(i);
            if (i == 0) {
                s.dir = direction;
            } else {
                s.dir = snake.get(i - 1).dir;
            }
            snake.set(i, s);
        }
    }

    // Kiểm tra xem đầu rắn có va chạm với tường, ăn thức ăn, hay va vào chính mình không. 
    // Nếu đầu rắn chạm vào tường hoặc thân, trò chơi kết thúc (playing = false).
    // Nếu rắn ăn thức ăn, điểm sẽ tăng lên và rắn sẽ dài thêm một đoạn.
    public void checkGameStatus() {
        updateDirection();
        Snake head = snake.get(0);
        if (head.willHitWall()) {
            playing = false;
            newGame = false;
        } else {
            Point next_loc = head.nextLocation();
            GameObj nextObject = board.getObject(next_loc.x, next_loc.y);
            if (nextObject != null) {
                switch (nextObject.type) {
                case FOOD:
                    Game.score++;
                    Snake lastJoint = snake.get(snake.size()-1);
                    Snake newJoint = new Snake(lastJoint.pos_x, lastJoint.pos_y, OBJECT_SIZE, BOARD_NUM_COL - 1,
                            BOARD_NUM_ROW - 1, START_DIRECTION, Type.BODY);
                    moveObjects();
                    snake.add(newJoint);
                    updateBoard();
                    respawnFood();
                    break;
                case BODY:
                    playing = false;
                    newGame = false;
                    break;
                case HEAD:
                    break;
                }
            } else {
                moveObjects();
            }
        }
    }

    // tạo lại thức ăn trên bảng sau khi rắn ăn
    public void respawnFood() {
        // finds number of empty spaces
        int numEmptySpaces = BOARD_NUM_ROW * BOARD_NUM_COL - snake.size();

        // generates a random nth free index on the board from 0 to numEmptySpaces
        int randomIndex = (int) (Math.random() * numEmptySpaces);

        /*
         * Finds the nth free space on the board and assigns the position to the
         * food.
         */
        for (int i = 0; i < BOARD_NUM_COL; i++) {
            for (int j = 0; j < BOARD_NUM_ROW; j++) {
                GameObj object = board.getObject(i, j);
                if (object == null) {
                    randomIndex--;
                    if (randomIndex == -1) {
                        food.pos_x = i;
                        food.pos_y = j;
                        break;
                    } 
                }
            }
        }

    }

    // Cập nhật trạng thái của bảng trò chơi, bao gồm vị trí của rắn và thức ăn.
    public void updateBoard() {
        board.resetBoard();
        board.setObject(food);
        for (int i = snake.size() - 1; i >= 0; i--) {
            Snake s = snake.get(i);
            board.setObject(s);
        }
        info.setScore(Game.score);
    }

    public int getScore() {
        return Game.score;
    }
    
    // Phương thức này chịu trách nhiệm vẽ lại giao diện trò chơi.
    // Nếu trò chơi mới bắt đầu (newGame), nó sẽ hiển thị một thông báo "Press Enter to start!".
    // Nếu trò chơi kết thúc, nó sẽ hiển thị thông báo "Game over!" và hướng dẫn người chơi cách lưu điểm hoặc thoát trò chơi.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        info.draw(g);
        board.draw(g);
        if(newGame){
            g.setColor(new Color(47, 79, 79, 179));
            g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.BOLD, 20));
            String message = "Press Enter to start!";
            g.drawString(message, 
             COURT_WIDTH / 2 - g.getFontMetrics().stringWidth(message)/2, COURT_HEIGHT / 2);
        }
        if(!playing && !newGame) {
            g.setColor(new Color(47, 79, 79, 179));
            g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.BOLD, 20));
            String message = "Game over!";
            g.drawString(message, 
                    COURT_WIDTH / 2 - g.getFontMetrics().stringWidth(message)/2, COURT_HEIGHT / 2 - 50);
            message = "Press 'Enter' to save your score.";
            g.drawString(message, 
                    COURT_WIDTH / 2 - g.getFontMetrics().stringWidth(message)/2, COURT_HEIGHT / 2);
            message = "Press 'ESC' to quit.";
            g.drawString(message, 
                    COURT_WIDTH / 2 - g.getFontMetrics().stringWidth(message)/2, COURT_HEIGHT / 2 + 50);
        }
    }
}

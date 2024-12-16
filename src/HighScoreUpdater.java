// LỚP ĐỌC VÀ GHI ĐIỂM CAO TỪ TỆP highscores.txt VÀ TẠO CÁC TreeMap (lưu trữ key-value, không trùng lặp, key tăng dần) ÁNH XẠ "TÊN NGƯỜI DÙNG" VỚI "ĐIỂM SỐ". 
// CŨNG NHẬN "TÊN NGƯỜI DÙNG", "ĐIỂM SỐ", VÀ "ĐỘ KHÓ" RỒI GHI VÀO TỆP highscores.txt

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HighScoreUpdater {
    // lưu trữ điểm số
    int score;
    
    // không phân biệt chữ hoa chữ thường nhờ sử dụng String.CASE_INSENSITIVE_ORDER
    private Map<String, Integer> easy = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Integer> medium = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Integer> hard = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
    
    // được dùng để xử lý các lỗi định dạng khi đọc tệp, đặc biệt nếu các dòng trong tệp không khớp với định dạng mong đợi (username,score,difficulty)
    public static class FormatException extends Exception {
        private static final long serialVersionUID = 390458644716076032L;

        public FormatException(String msg) {
            super(msg);
        }
    }
    
    // Constructor
    public HighScoreUpdater() throws IOException, FormatException {
        // để tải dữ liệu điểm từ tệp highscores.txt
        updateData();
    }
    
    // Ghi một điểm cao mới vào highscores.txt cho tên người dùng, điểm và độ khó
    public void write(String username, int score, String difficulty) throws IOException, FormatException {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter("highscores.txt", true));
            // Nếu điểm hợp lệ (cao hơn điểm đã có hoặc là người dùng mới), nó thêm dữ liệu vào tệp insertData(...)
            if (insertData(username, score, difficulty)){
            output.append(username + "," + score + "," + difficulty);
            output.newLine();
            output.close();
            }
        } catch (IOException e) {
            System.out.printf("ERROR writing score to file: %s\n", e);
        }
        // để cập nhật lại dữ liệu trong bộ nhớ từ tệp
        updateData();
    }
    
    // Đọc điểm cao từ tệp highscores.txt và cập nhật vào TreeMap cho từng cấp độ
    // Ném ra FormatException nếu định dạng tệp không chính xác
    public void updateData() throws IOException, FormatException {
        String str = "";
        // BufferedReader: để đọc từng dòng của tệp, phân tích và thêm dữ liệu bằng insertData()
        try(BufferedReader in = new BufferedReader(new FileReader("highscores.txt"))) {
            while ((str = in.readLine()) != null) {
                if (str.trim().length() > 0) {
                    List<String> parsed = Arrays.asList(str.trim().split(","));
                    if (parsed.size() != 3) {
                        throw new HighScoreUpdater.FormatException("invalid file format");
                    } else {
                        String key = parsed.get(0).trim();
                        int value = Integer.parseInt(parsed.get(1).trim());
                        String level = parsed.get(2).trim();
                        // mỗi tệp cần có định dạng (username,score,difficulty)
                        insertData(key, value, level);
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf("ERROR reading score file: %s\n", e);
        }
    }

    // Xử lý việc thêm dữ liệu vào bản đồ tương ứng với cấp độ khó (easy, medium hoặc hard)
    // Nếu tên người dùng đã tồn tại và điểm mới cao hơn điểm hiện tại, nó sẽ cập nhật bản đồ với điểm mới
    // Trả về true nếu điểm được thêm hoặc cập nhật thành công, và false nếu không
    public boolean insertData(String username, int score, String difficulty){
        if (difficulty.equals("Easy")){
            if(easy.containsKey(username)){
                if(easy.get(username) < score){
                    easy.put(username, score);
                    return true;
                }
            } else {
                easy.put(username, score);
                return true;
            }
        } else if (difficulty.equals("Medium")) {
            if(medium.containsKey(username)){
                if(medium.get(username) < score){
                    medium.put(username, score);
                    return true;
                }
            } else {
                medium.put(username, score);
                return true;
            }
        } else if (difficulty.equals("Hard")) {
            if(hard.containsKey(username)){
                if(hard.get(username) < score){
                    hard.put(username, score);
                    return true;
                }
            } else {
                hard.put(username, score);
                return true;
            }
        }
        return false;
    }
    
    // Getter: trả về TreeMap tương ứng chứa điểm của từng cấp độ
    public Map<String, Integer> getEasy(){
        return easy;
    }
    
    public Map<String, Integer> getMedium(){
        return medium;
    }
    
    public Map<String, Integer> getHard(){
        return hard;
    }
}

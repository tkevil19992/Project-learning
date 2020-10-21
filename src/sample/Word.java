package sample;
/**|--------------------------------------------------------------------------------------------------------------------|
*  | class này có nhiệm vụ là tạo danh sách từ tiếng anh, lấy phát âm, lấy nghĩa của từ trong file patch (file từ điển) |
*  |--------------------------------------------------------------------------------------------------------------------|
 */
import java.io.*;
import java.util.ArrayList;

public class Word {

    private String patch = "";

    public Word(String patch) {
        this.patch = patch;
    }

    public Word() {
    }
    /**|-------------------------------------------------------------------------------------------|
    *  | Phương thức creatWordList trả về 1 ArrayList chứa các từ tiếng anh được lấy từ file patch |
    *  |-------------------------------------------------------------------------------------------|
     */
    public ArrayList<String> creatWordList() {
        ArrayList<String> wordList = new ArrayList<>();

        try {
            File file = new File(patch);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null)
            /**đọc liên tục đến cuối file patch nếu dòng nào có ký tự '@' thì cắt chuỗi từ sau dấu '@' đến trc dấu '/'
             * (đối với các từ có phần phát âm) hoặc đến cuối dòng (đối với những từ ko có phần phát âm)
            *  sau đó add vào wordList
             */
            {
                if (line.length() == 0) continue;
                else {
                    if (line.charAt(0) == '@') {
                        String considerWord = "";
                        int i = 1;
                        while (i < line.length() && line.charAt(i) != '/') {
                            considerWord += line.charAt(i);
                            i++;
                        }
                        wordList.add(considerWord.trim());
                    }
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Lấy từ điển có vấn đề");
        } catch (IOException e) {
            System.out.println("Lấy từ điển có vấn đề");
        }

        return wordList;
    }
    /**|-------------------------------------------------------------------------------------------|
    *  | Phương thức getWord trả về 1 chuỗi là nghĩa của từ tiếng anh(name)                        |
    *  |-------------------------------------------------------------------------------------------|
     */
    public String getWord(String name) {
        String word = "";
        try {
            File file = new File(patch);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            boolean getWord = false;
            while ((line = br.readLine()) != null)
            /**đọc liên tục đến cuối file patch nếu dòng nào có dâu '@' thì tách chuỗi từ sau dấu '@' đến trc dấu '/'
            *  (đối với các từ có phần phát âm) hoặc đến cuối dòng (đối với những từ ko có phần phát âm)
            *  sau đó đối chiếu với name nếu đúng thì trả về giá trị true cho getWord
             */
            {
                if (line.length() == 0) continue;
                else {
                    if (getWord)
                    // nếu getWord = true thì lấy nội dung file đến khi gặp dấu '@' tiếp theo và gán vào word.
                    {
                        if (line.charAt(0) == '@') break;
                        else {
                            word += line + "\n";
                        }
                    } else {
                        if (line.charAt(0) == '@') {
                            if (line.contains("/")) {
                                String check = "";
                                int i = 1;
                                while (i < line.length() && line.charAt(i) != '/') {
                                    check += line.charAt(i);
                                    i++;
                                }
                                check = check.trim();
                                if (check.equals(name)) {
                                    getWord = true;
                                }
                            } else {
                                String check = "";
                                int i = 1;
                                while (i < line.length() && line.charAt(i) != '\n') {
                                    check += line.charAt(i);
                                    i++;
                                }
                                check = check.trim();
                                if (check.trim().equals(name)) {
                                    getWord = true;
                                }
                            }
                        }
                    }
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Tra từ điển có vấn đề");
        } catch (IOException e) {
            System.out.println("Tra từ điển có vấn đề");
        }
        return word.trim();
    }

    /**|-------------------------------------------------------------------------------------------|
    *  | Phương thức getPronounce trả về 1 chuỗi là phát âm của từ tiếng anh(name)                 |
    *  |-------------------------------------------------------------------------------------------|
     */
    public String getPronounce(String name) {
        String spell = "";
        try {
            File file = new File(patch);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                /**đọc liên tục đến cuối file patch nếu gặp dấu '@' thì bắt đầu tách chuỗi từ sau dấu '@' đến trc dấu '/' và gán vào check
                *  check giống name thì bắt đầu tách chuỗi từ dấu '/' đến dấu '/' tiếp theo và gán vào spell
                 */
                if (line.length() == 0) continue;
                else if (line.charAt(0) == '@') {
                    if (line.contains("/")) {
                        int i = 1;
                        String check = "";
                        while (i < line.length() && line.charAt(i) != '/') {
                            check += line.charAt(i);
                            i++;
                        }
                        check = check.trim();
                        if (check.equals(name)) {
                            for (int j = i; j < line.length(); j++) {
                                spell += line.charAt(j);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return spell;
    }
}


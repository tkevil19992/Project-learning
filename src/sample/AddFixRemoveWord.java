package sample;
/**|--------------------------------------------------------------------------------------------|
*  | class này có nhiệm vụ thục hiện việc thêm, sửa, xóa từ trong file patch (file txt từ điển) |
*  |--------------------------------------------------------------------------------------------|
 */
import java.io.*;
import java.util.Scanner;

public class AddFixRemoveWord {
    private String patch;

    public AddFixRemoveWord(String patch) {
        this.patch = patch;
    }

    /**|------------------------------------------------------------------------------------|
    *  | Phương thức AddWordAction có nhiệm vụ là ghi ghi vào cuối file patch chuỗi newWord |
    *  |------------------------------------------------------------------------------------|
     */

    public void AddWordAction(String newWord)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(patch,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(newWord);
            bw.close();
            osw.close();
            fos.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**|----------------------------------------------------------------|
    *  | Phương thức FixOrRemoveWordAction có nhiệm vụ là sao chép chép |
    *  | nội dung file patch sang file tempFile.txt nếu gặp từ cần sửa, |
    *  | xóa là wordNeedFixing thì sẽ bỏ qua và thay thế bằng chuỗi     |
    *  | fixedWord sau đó sao chép tiếp đến cuối file patch cuối cùng   |
    *  | xóa file patch và đổi tên file tempFile.txt thành tên file     |
    *  | patch.                                                         |
    *  |----------------------------------------------------------------|
     */

    public void FixOrRemoveWordAction(String wordNeedFixing,String fixedWord) {
        Scanner scan;
        File tempFile = new File("tempFile.txt");
        File oldFile = new File(patch);
        String line;
        String linecheck = " ";
        try {
            FileOutputStream fos = new FileOutputStream(tempFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            scan = new Scanner(new File(patch));
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.length() == 0)
                    continue;
                else {
                    if (line.charAt(0) == '@') /** Đọc nội dung file patch nếu dòng nào chứa dấu '@' thì bắt đầu tách và lấy chuỗi
                                                * từ sau dấu '@' đến dấu '/' (đối với những từ có chứa cả phát âm) hoặc đến cuối dòng
                                                * đối với những từ ko có phát âm sau đó gán sang chuỗi check và đối chiếu với từ wordNeedFixing.
                                                */
                    {
                        String check = "";
                        if (line.contains("/")) {
                            int i = 1;
                            while (i < line.length() && line.charAt(i) != '/') {
                                check += line.charAt(i);
                                i++;
                            }
                        } else {
                            int i = 1;
                            while (i < line.length() && line.charAt(i) != '\n') {
                                check += line.charAt(i);
                                i++;
                            }
                        }
                        check = check.trim();

                        if (check.equals(wordNeedFixing)) /**nếu check giống wordNeedFixing thì sao chép fixedWord và bỏ qua nội dung từ + phát âm + nghĩa của từ wordNeedFixing
                                                          * nếu ko giống thì tiếp tục sao chép
                                                            */
                        {
                            bw.write(fixedWord);
                            line = scan.nextLine();
                            if(scan.hasNextLine())
                                linecheck = scan.nextLine();
                            while ((line.charAt(line.length() - 1) != '\n' && linecheck.charAt(0) != '@')) {
                                if (scan.hasNextLine()) {
                                    line=linecheck;
                                    linecheck = scan.nextLine();
                                }
                                else {
                                    linecheck="";
                                    break;
                                }
                                if (line.length() == 0)
                                    break;
                            }
                            if(scan.hasNextLine())
                                linecheck=linecheck+"\n";
                            bw.write(linecheck);
                        }
                    else {
                            bw.write(line);
                            bw.newLine();
                        }
                    }
                         else{
                            bw.write(line);
                            bw.newLine();
                        }
                    }
                }
                bw.close();
                osw.close();
                fos.close();
                scan.close();
                oldFile.delete();
                File dump = new File(patch);
                tempFile.renameTo(dump);
        }
        catch(Exception ex)
            {
                ex.printStackTrace();
            }
    }
}

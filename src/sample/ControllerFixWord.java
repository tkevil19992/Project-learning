package sample;
/** |--------------------------------------------------------------------------------------------------|
 *  |                         Class có nhiệm vụ quản lý của sổ sửa từ                                  |
 *  |--------------------------------------------------------------------------------------------------|
 * */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerFixWord implements Initializable {

    @FXML
    private TextField textFieldFixWord;

    @FXML
    private CheckBox checkBoxPronounce;

    @FXML
    private TextField wordFixed;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonCheck;

    @FXML
    private TextField textFieldPronounce;

    @FXML
    private CheckBox checkBoxFixMeanning;

    @FXML
    private CheckBox checkBoxFixWordEng;

    @FXML
    private Button buttonOK;

    @FXML
    private TextArea textAreaFixMeaning;

    AddFixRemoveWord  handlingWord = new AddFixRemoveWord("data.txt");

    Word word = new Word("data.txt");
    ArrayList<String> arr = word.creatWordList();


    public void clickOnFixWordEng()
    {
        checkBoxFixWordEng.setOnAction(e->{
            if(checkBoxFixWordEng.isSelected())
                wordFixed.setEditable(true);
        });
    }

    public void clickOnFixPronounce()
    {
        checkBoxPronounce.setOnAction(e->{
            if(checkBoxPronounce.isSelected())
                textFieldPronounce.setEditable(true);
        });
    }

    public void clickOnFixMeanning()
    {
        checkBoxFixMeanning.setOnAction(e->{
            if(checkBoxFixMeanning.isSelected())
                textAreaFixMeaning.setEditable(true);
        });
    }

    public void clickOnCheck()
    {
        buttonCheck.setOnAction(e->{
            textAreaFixMeaning.setText(word.getWord(textFieldFixWord.getText()));

        });
    }

    public void clickOnCacel()
    {
        buttonCancel.setOnAction(e->{
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }

    public void fixWord()
            /** Tạo đinh dạng chuỗi str theo định dang của từ điển vào thực hiện sửa từ với chuỗi truyền vào str*/
    {
        String str = "";
        if(checkBoxFixWordEng.isSelected())
            str+="@"+wordFixed.getText();
        else
            str+="@"+textFieldFixWord.getText();
        if(checkBoxPronounce.isSelected())
            str+=" /"+textFieldPronounce.getText()+"/\n";
        else
            str+= " " + word.getPronounce(textFieldFixWord.getText()) + "\n";
        if(checkBoxFixMeanning.isSelected())
            str+=textAreaFixMeaning.getText() + "\n";
        else
            str+=word.getWord(textFieldFixWord.getText()) + "\n";
        handlingWord.FixOrRemoveWordAction(textFieldFixWord.getText(),str);

    }

    public void setWindowFixWordAgain()
            /** Đặt lại về ban đầu*/
    {
        textFieldFixWord.setText("");
        wordFixed.setText("");
        textAreaFixMeaning.setText("");
        textFieldPronounce.setText("");
        checkBoxPronounce.setSelected(false);
        checkBoxFixWordEng.setSelected(false);
        checkBoxFixMeanning.setSelected(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFields.bindAutoCompletion(textFieldFixWord,arr);
        wordFixed.setEditable(false);
        textFieldPronounce.setEditable(false);
        textAreaFixMeaning.setEditable(false);
        clickOnFixWordEng();
        clickOnFixPronounce();
        clickOnFixMeanning();
        clickOnCheck();
        clickOnCacel();
        /** khi sửa từ thành công hiện thông báo*/
        buttonOK.setOnAction(e->{
            fixWord();
            setWindowFixWordAgain();
            try {
                Stage notificationWindow = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("notificationWindowFixWord.fxml"));
                notificationWindow.setTitle("Thông báo");
                notificationWindow.setScene(new Scene(root, 300, 80));

                notificationWindow.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

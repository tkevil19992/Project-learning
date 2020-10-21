package sample;
/** |--------------------------------------------------------------------------------------------------|
 *  |                         Class có nhiệm vụ quản lý của sổ xóa từ                                  |
 *  |--------------------------------------------------------------------------------------------------|
 * */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerRemoveWord implements Initializable {

    @FXML
    private Button buttonCancel;

    @FXML
    private TextArea textAreaMeanningWord;

    @FXML
    private Button buttonCheck;

    @FXML
    private TextField textFieldWord;

    @FXML
    private Button buttonOK;

    Word word = new Word("data.txt");
    ArrayList<String> arr = word.creatWordList();
    AddFixRemoveWord  handlingWord = new AddFixRemoveWord("data.txt");

    public void suggestions()
    {
        TextFields.bindAutoCompletion(textFieldWord,arr);
    }

    public void clickOnCheck()
    {
        buttonCheck.setOnAction(e->{
            textAreaMeanningWord.setText(word.getWord(textFieldWord.getText()));

        });
    }

    public void clickOnCacel()
    {
        buttonCancel.setOnAction(e->{
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }

    public void remomeWord()
    {
        handlingWord.FixOrRemoveWordAction(textFieldWord.getText(),"");
    }

    public void setWindowRemoveWordAgain()
    {
        textFieldWord.setText("");
        textAreaMeanningWord.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaMeanningWord.setEditable(false);
        suggestions();
        clickOnCheck();
        clickOnCacel();
        buttonOK.setOnAction(e->{
            remomeWord();
            setWindowRemoveWordAgain();
            arr = word.creatWordList();
            try {
                Stage notificationWindow = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("notificationWindowRemoveWord.fxml"));
                notificationWindow.setTitle("Thông báo");
                notificationWindow.setScene(new Scene(root, 300, 80));

                notificationWindow.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

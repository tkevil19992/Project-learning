package sample;
/** |--------------------------------------------------------------------------------------------------|
 *  |                         Class có nhiệm vụ quản lý của sổ thêm từ                                 |
 *  |--------------------------------------------------------------------------------------------------|
 * */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddWord implements Initializable {
    @FXML
    private Button buttonCancel;

    @FXML
    private TextArea textAreaNewWordMeanning;

    @FXML
    private TextField newWord;

    @FXML
    private TextField textFieldPronounce;

    @FXML
    private Button buttonOK;

    AddFixRemoveWord  handlingWord = new AddFixRemoveWord("data.txt");

    public void AddWord()
    {
        String wordNeedAdd = "@" + newWord.getText() + " /"+textFieldPronounce.getText()+"/\n"+textAreaNewWordMeanning.getText()+"\n";
        handlingWord.AddWordAction(wordNeedAdd);
    }

    public void setWindowAddWordAgain()
    {

        newWord.setText("");
        textFieldPronounce.setText("");
        textAreaNewWordMeanning.setText("");
    }

    public void clickOnCacel()
    {
        buttonCancel.setOnAction(e->{
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonOK.setOnAction(e -> {
            AddWord();
            setWindowAddWordAgain();
            try {
                Stage notificationWindow = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("notificationWindowAddWord.fxml"));
                notificationWindow.setTitle("Thông báo");
                notificationWindow.setScene(new Scene(root, 300, 80));

                notificationWindow.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        clickOnCacel();
    }
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TextField textfeildSearch;

    @FXML
    private Button buttonRemoveWord;

    @FXML
    private Button buttonTranslate;

    @FXML
    private Button buttonFixWord;

    @FXML
    private Button buttonExportFile;

    @FXML
    private Button buttonAddWord;

    @FXML
    private TextField textfeildPronounce;

    @FXML
    private ListView listWord;

    @FXML
    private TextArea textArea;

    @FXML
    private ChoiceBox choiceBoxDestination;

    @FXML
    private TextArea textAreaDestination;

    @FXML
    private ChoiceBox choiceBoxSource;

    @FXML
    private TextArea textAreaSource;

    @FXML
    private Button buttonTranslateOnline;

    @FXML
    private ImageView speakerOffline;

    @FXML
    private ImageView speakerSource;

    @FXML
    private ImageView speakerDestination;

    TranslateOnline translateOnline = new TranslateOnline();

    Word word = new Word("data.txt");
    ArrayList<String> arr = word.creatWordList(); /** Tạo danh sách từ tiếng anh*/

    Stage primaryStage1 = new Stage();

    public void listViewMouseClick()
    /**
     * đưa nội dung từ từ list View lên textfeildSearch khi click chuột vào 1 vị trí trong list View:listWord
     */
    {
        int selectedIndex = listWord.getSelectionModel().getSelectedIndex();
        textfeildSearch.setText(arr.get(selectedIndex));
    }

    public void loadWord()
            /** Đặt danh sách từ vào list View*/
    {
        ObservableList<String> items = FXCollections.observableArrayList(arr);
        listWord.setItems(items);
    }

    public void suggestions()
            /** gợi ý từ khi nhập tìm kiếm*/
    {
        TextFields.bindAutoCompletion(textfeildSearch,arr);
    }

    public void refresh()
    /**
     * Đặt lại textArea, textfeildPronounce, textfeildSearch, cập nhập lại danh sach từ
     */
    {
        textArea.setText("");
        textfeildPronounce.setText("");
        textfeildSearch.setText("");
        textfeildSearch.setPromptText("Nhập từ cần tìm");
        arr = word.creatWordList();
        loadWord();
    }

    public void translate()
            /** lấy nghĩa, phát âm của từ đặt vào textArea, textfeildPronounce*/
    {
        buttonTranslate.setOnAction(e->{
            String meanning = word.getWord(textfeildSearch.getText());
            textArea.setText(meanning);
            String pronounce = word.getPronounce(textfeildSearch.getText());
            textfeildPronounce.setText(pronounce);
        });
    }

    public void openWindowAddWord()
    {
        /** tạo cửa sổ "thêm từ" */
        buttonAddWord.setOnAction(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("windowAddWord.fxml"));
                primaryStage1.setTitle("Thêm từ");
                primaryStage1.setScene(new Scene(root, 530, 500));
                primaryStage1.show();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
    }

    public void openWindowFixWord()
    /** tạo cửa sổ "sửa từ" */
    {
        buttonFixWord.setOnAction(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("windowFixWord.fxml"));
                primaryStage1.setTitle("Sửa từ");
                primaryStage1.setScene(new Scene(root, 700, 700));


                primaryStage1.show();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
    }

    public void openWindowRemoveWord()
    /** tạo cửa sổ "xóa từ" */
    {
        buttonRemoveWord.setOnAction(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("windowRemoveWord.fxml"));
                primaryStage1.setTitle("Xóa từ");
                primaryStage1.setScene(new Scene(root, 531, 474));


                primaryStage1.show();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
    }

    public void openwindowExportFile()
    /** tạo cửa sổ "Xuất File" */
    {
        buttonExportFile.setOnAction(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("windowExportFile.fxml"));
                primaryStage1.setTitle("Xuất File");
                primaryStage1.setScene(new Scene(root, 581, 284));
                primaryStage1.show();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
    }

    public void setChoiceBoxLanguage()
            /** Tab dịch Online*/
    {
        choiceBoxDestination.getItems().add("Tiếng Việt");
        choiceBoxDestination.getItems().add("Tiếng Anh");
        choiceBoxDestination.setValue("Tiếng Việt");

        choiceBoxSource.getItems().add("Tiếng Việt");
        choiceBoxSource.getItems().add("Tiếng Anh");
        choiceBoxSource.setValue("Tiếng Anh");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        /*  XỬ LÝ SỰ KIỆN TAB DỊCH OFFLINE*/

        textfeildPronounce.setEditable(false);
        textArea.setEditable(false);
        loadWord();
        suggestions();
        refresh();
        translate();
        openWindowAddWord();
        openWindowFixWord();
        openWindowRemoveWord();
        openwindowExportFile();

        primaryStage1.setOnCloseRequest(event->{
            refresh();
        });

        /*  XỬ LÝ SỰ KIỆN TAB DỊCH ONLINE*/

        textAreaDestination.setEditable(false);
        setChoiceBoxLanguage();
        buttonTranslateOnline.setOnAction(e->{
            try {
                if(choiceBoxSource.getValue()=="Tiếng Anh")
                {
                    if(choiceBoxDestination.getValue()=="Tiếng Việt")
                        textAreaDestination.setText(translateOnline.translate("en","vi",textAreaSource.getText()));
                    else
                        textAreaDestination.setText(textAreaSource.getText());
                }
                else
                {
                    if(choiceBoxDestination.getValue()=="Tiếng Việt")
                        textAreaDestination.setText(textAreaSource.getText());
                    else
                        textAreaDestination.setText(translateOnline.translate("vi","en",textAreaSource.getText()));
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
    }
}

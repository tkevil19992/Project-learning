package sample;
/** |--------------------------------------------------------------------------------------------------|
 *  |                         Class có nhiệm vụ quản lý của sổ xuất File                               |
 *  |  -> người dùng muốn xuất từ nào thì sẽ nhập và đưa vào 1 ArrayList say đó xuất ArrayList ra ngoài|
 *  |--------------------------------------------------------------------------------------------------|
 * */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerExportFile implements Initializable {

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textFiledName;

    @FXML
    private TextField texFieldLocation;

    @FXML
    private Button buttonLocation;

    @FXML
    private Button buttonOK;

    @FXML
    private ChoiceBox<String> choiceBoxTypeFile;

    @FXML
    private TextField textFieldWordOut;

    @FXML
    private ListView listViewWordOut;

    @FXML
    private Button buttonAdd;


    Word word = new Word("data.txt");
    ArrayList<String> arr = word.creatWordList();
    ArrayList<String> arrListOut = new ArrayList<>();

    public void setChoiceBoxTypeFile()
    {
        choiceBoxTypeFile.getItems().add(".txt");
        choiceBoxTypeFile.getItems().add(".xls");
        choiceBoxTypeFile.setValue(".txt");
    }

    public String getNameFile()
    {
        if(textFiledName.getText()!="")
            return textFiledName.getText();
        else
            return "Dictionary";
    }

    public void clickLocation()
    {
        buttonLocation.setOnAction(e->{
            Stage stagewindowlocation = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File local = directoryChooser.showDialog(stagewindowlocation);
            texFieldLocation.setText(local.getAbsolutePath());
        });
    }

    public void clickcancel() {
        buttonCancel.setOnAction(e-> {
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        });
    }

    public void suggestions()
    {
        TextFields.bindAutoCompletion(textFieldWordOut,arr);
    }

    public void clickAdd()
    {
        buttonAdd.setOnAction(e->{
            arrListOut.add(textFieldWordOut.getText());
            ObservableList<String> items = FXCollections.observableArrayList(arrListOut);
            listViewWordOut.setItems(items);
        });
    }

    public void exportFile()
    {
        if(choiceBoxTypeFile.getValue() == ".txt")
        /** Xuất File dang text*/
        {
            File tempFile = new File(texFieldLocation.getText()+"\\"+getNameFile()+".txt");
            String line;
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
               for(String wordout:arrListOut) {
                   line = "@"+wordout+" /"+word.getPronounce(wordout)+"/\n"+word.getWord(wordout);
                   bw.write(line);
                   bw.newLine();
                }
                bw.close();
                osw.close();
                fos.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(choiceBoxTypeFile.getValue()==".xls")
        /** Xuất File Excel*/
        {

            try {
                String[] tilte = {"Từ","Phát âm","Nghĩa"};

                File tempFile = new File(texFieldLocation.getText() + "\\" + getNameFile() + ".xls");
                FileOutputStream fos = new FileOutputStream(tempFile);
                Workbook workbook = new HSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");

                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 12);
                titleFont.setColor(IndexedColors.SKY_BLUE.getIndex());

                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setFont(titleFont);

                Row titleRow = sheet.createRow(0);
                for(int i=0;i<tilte.length;i++)
                {
                    Cell cell = titleRow.createCell(i);
                    cell.setCellValue(tilte[i]);
                    cell.setCellStyle(titleStyle);
                }

                for(int i=1;i<=arrListOut.size();i++)
                {
                    Row row = sheet.createRow(i);
                    row.createCell(0).setCellValue(arrListOut.get(i-1));
                    row.createCell(1).setCellValue(word.getPronounce(arrListOut.get(i-1)));
                    row.createCell(2).setCellValue(word.getWord(arrListOut.get(i-1)));
                }
                for (int i=0;i<3;i++)
                    sheet.autoSizeColumn(i);
                workbook.write(fos);
                fos.close();
                workbook.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBoxTypeFile();
        clickLocation();
        clickcancel();
        suggestions();
        clickAdd();
        buttonOK.setOnAction(e->{
            exportFile();
            textFieldWordOut.setText("");
            texFieldLocation.setText("");
            textFiledName.setText("");
            arrListOut.clear();
            ObservableList<String> items = FXCollections.observableArrayList(arrListOut);
            listViewWordOut.setItems(items);
        });
    }
}

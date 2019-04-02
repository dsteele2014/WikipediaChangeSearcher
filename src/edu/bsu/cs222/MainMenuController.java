package edu.bsu.cs222;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainMenuController implements Initializable{

    private edu.bsu.cs222.Main main;
    @FXML
    private TableView<Revision> InfoTable;
    @FXML
    private TableColumn<Revision, String> userTable;
    @FXML
    private TableColumn<Revision, Date> timeStampTable;
    @FXML
    private TextField searchTextField;
    @FXML
    private Text searchedText;

    private HashMap<String, Date> dataStream = new HashMap<>();
    private final ObservableList<Revision> revisionData = FXCollections.observableArrayList();
    private String urlTemp;

    void setMain(edu.bsu.cs222.Main main) {
        this.main = main;
    }

    public void quit(){
        main.close();
    }

    public void search() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        InfoTable.getItems().clear();
        urlTemp = searchTextField.getText();
        String urlString = urlTemp.replace(" ", "_");
        WikipediaRevisionsData data = new WikipediaRevisionsData(urlString);
        fillInfoTable(data);
        searchTextField.clear();
        showingResultsForText(data);
    }

    private void fillInfoTable(WikipediaRevisionsData data){

        dataStream = data.wikipediaDataErrorCatcher();
        Set<String> userKeys = dataStream.keySet();
        Iterator<String> userIterator = userKeys.iterator();
        fillRows(userIterator);
        InfoTable.setItems(revisionData);
    }

    private void fillRows(Iterator<String> userIterator){
        String key;
        Date value;
        while (userIterator.hasNext()){
            key = userIterator.next();
            value = dataStream.get(key);
            Revision entry = new Revision(key, value);
            revisionData.add(entry);
        }
    }

    private void showingResultsForText(WikipediaRevisionsData data){
        String redirectLoc = data.getRedirect();
        if(urlTemp.equals(""))
            searchedText.setText("");
        else if (redirectLoc.equals("No redirect"))
            searchedText.setText("Showing results for " + urlTemp);
        else
            searchedText.setText("Showing results for " + urlTemp + ". " + data.getRedirect() + ".");
    }

    public void clear(){
        InfoTable.getItems().clear();
        searchTextField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTable.setCellValueFactory(new PropertyValueFactory<>("userName"));
        timeStampTable.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        InfoTable.setItems(revisionData);
    }
}

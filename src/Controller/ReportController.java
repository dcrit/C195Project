package Controller;

import DatabaseQuery.ContactQuery;
import DatabaseQuery.ReportQuery;
import Model.Report1;
import Model.Report2;
import Model.Report3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**Report*/
public class ReportController implements Initializable {



    //Table for report 3
    @FXML private TableView<Report1> report1Table;
    @FXML private TableColumn<Report1, String> report1MonthCol;
    @FXML private TableColumn<Report1, String> report1TypeCol;
    @FXML private TableColumn<Report1, Integer> report1TotalCol;

    //Table for report 2
    @FXML private ComboBox<Integer> contactIdComboBox;
    @FXML private TableView<Report2> report2Table;
    @FXML private TableColumn<Report2, Integer> report2AppointmentIdCol;
    @FXML private TableColumn<Report2, String> report2TitleCol;
    @FXML private TableColumn<Report2, String> report2TypeCol;
    @FXML private TableColumn<Report2, String> report2DescriptionCol;
    @FXML private TableColumn<Report2, LocalDateTime> report2StartDateCol;
    @FXML private TableColumn<Report2, LocalDateTime> report2EndDateCol;
    @FXML private TableColumn<Report2, Integer> report2CustomerIdCol;

    //Table for report 3
    @FXML private TableView<Report3> report3Table;
    @FXML private TableColumn<Report3, Integer> report3customerIdCol;
    @FXML private TableColumn<Report3, String> report3DivisionCol;
    @FXML private TableColumn<Report3, String> report3CountryCol;

    //Buttons
    @FXML private Button cancelButton;

    /**
     * Initializer
     * Getting reports when page opens
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            gettingAndSettingContactId();
           fetchReport1();
           fetchReport3();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Lambda Expression
        cancelButton.setOnAction((actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }));


    }

    /**
     * Setting ID's to combo box
     * @throws Exception
     */
   public void gettingAndSettingContactId() throws Exception {

       ObservableList<Integer> contactId = ContactQuery.getContactId();
       contactIdComboBox.setItems(contactId);

   }

    /**
     * Getting report 1 and adding it to table
     */
    public void fetchReport1(){

        ObservableList<Report1> report1 = FXCollections.observableArrayList();

        try{
            report1 = ReportQuery.report1();
        }catch (Exception e){
            e.printStackTrace();
        }

        report1Table.setItems(report1);
        report1MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        report1TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        report1TotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));


    }


    /**
     * Refresh the table with appointments from selected contact ID.
     * @param actionEvent Action event when button is selected.
     */
    public void contactId(ActionEvent actionEvent) {

        ObservableList<Report2> report2 = FXCollections.observableArrayList();

        report2.clear();

        report2Table.setItems(ReportQuery.report2(contactIdComboBox));
        report2AppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        report2TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        report2TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        report2DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        report2StartDateCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        report2EndDateCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        report2CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        report2Table.refresh();

    }

    /**
     * Setting report 3 to table.
     */
    public void fetchReport3(){
        ObservableList<Report3> report3 = FXCollections.observableArrayList();

        try {
            report3 = ReportQuery.report3();
        }catch (Exception e){
            e.printStackTrace();
        }
        report3Table.setItems(report3);
        report3customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        report3DivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        report3CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
    }



}

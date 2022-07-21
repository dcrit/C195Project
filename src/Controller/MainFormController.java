package Controller;

import DatabaseQuery.CustomerQuery;
import Model.Appointment;
import DatabaseQuery.AppointmentQuery;
import Model.Customer;
import Exception.MainFormException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Objects;
import java.util.ResourceBundle;

/**Main Form*/
public class MainFormController implements Initializable {


    //Appointment table
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentAppointmentIdCol;
    @FXML private TableColumn<Appointment, String> appointmentTitleCol;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentStartTimeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentEndTimeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentCreateDateCol;
    @FXML private TableColumn<Appointment, String> appointmentCreatedByCol;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentLastUpdatedCol;
    @FXML private TableColumn<Appointment, String> appointmentLastUpdatedByCol;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIdCol;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIdCol;
    @FXML private TableColumn<Appointment, Integer> appointmentContactIdCol;

    //Customer Table
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerCustomerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> customerAddressCol;
    @FXML private TableColumn<Customer, String> customerPostalCodeCol;
    @FXML private TableColumn<Customer, String> customerPhoneCol;
    @FXML private TableColumn<Customer, LocalDateTime> customerCreateDateCol;
    @FXML private TableColumn<Customer, String> customerCreatedByCol;
    @FXML private TableColumn<Customer, LocalDateTime> customerLastUpdateCol;
    @FXML private TableColumn<Customer, String> customerLastUpdatedByCol;
    @FXML private TableColumn<Customer, Integer>  customerDivisionIdCol;

    //Customer and Appointment lists
    @FXML private ObservableList<Appointment> appointmentListMain = FXCollections.observableArrayList();
    @FXML private ObservableList<Customer> customerListMain = FXCollections.observableArrayList();


    /**
     * Initializer
     * Sets appointments and customers doing load up.
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

        fetchAppointments();
        fetchCustomers();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets table with appointments from database.
     * @throws Exception Throws Exception
     */
    public void fetchAppointments() throws Exception {


        try {

            appointmentListMain = AppointmentQuery.getAppointments();

        }catch (SQLException e){
            e.printStackTrace();
        }

        appointmentTable.setItems(appointmentListMain);
        appointmentAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        appointmentCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        appointmentLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        appointmentLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

    }

    /**
     * Sets customers from database to table.
     * @throws Exception Throws Exception
     */
    public void fetchCustomers() throws Exception {


        try{

            customerListMain = CustomerQuery.getCustomers();
            customerTable.setItems(customerListMain);
            customerCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address") );
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            customerLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Takes user to add appointment page.
     * @param actionEvent Action event when button is selected.
     * @throws IOException Throws IOException
     */
    public void addAppointment(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddAppointment.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Deletes user from table and database
     * @param actionEvent Action event when button is selected.
     * @throws Exception Throws Exception
     */
    public void deleteAppointment(ActionEvent actionEvent) throws Exception {

        Appointment deleteAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        MainFormException.nullValueForDeleteAppointment(deleteAppointment);

        if(deleteAppointment != null) {

            DatabaseQuery.AppointmentQuery.deleteAppointment(deleteAppointment);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Take user to edit appointment page.
     * @param actionEvent Action event when button is selected.
     * @throws IOException Throw IOException
     */
    public void editAppointment(ActionEvent actionEvent) throws IOException {

        Appointment editAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        EditAppointmentController.setPassingAppointmentFromMain(editAppointment);

        MainFormException.nullValueForEditAppointment(editAppointment);

        if(editAppointment != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/EditAppointment.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Edit Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Take user to add customer page.
     * @param actionEvent Action event when button is selected.
     * @throws IOException Throws Exception
     */
    public void addCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddCustomer.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Takes user to edit customer page.
     * @param actionEvent Action event when button is selected.
     * @throws IOException Throw IOException
     */
    public void editCustomer(ActionEvent actionEvent) throws IOException {

       Customer editCustomer = customerTable.getSelectionModel().getSelectedItem();
       EditCustomerController.setPassingCustomerFromMain(editCustomer);

       MainFormException.nullValueForEditCustomer(editCustomer);

       if(editCustomer != null) {
           Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/EditCustomer.fxml")));
           Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
           Scene scene = new Scene(root, 600, 600);
           stage.setTitle("Edit Customer");
           stage.setScene(scene);
           stage.show();
       }

    }

    /**
     * Deletes customer from table and database
     * @param actionEvent Action event when button is selected.
     * @throws Exception Throws Exception
     */
    public void deleteCustomer(ActionEvent actionEvent) throws Exception {

        Customer deleteCustomer = customerTable.getSelectionModel().getSelectedItem();

        MainFormException.nullValueForDeleteCustomer(deleteCustomer);

        if(deleteCustomer != null ) {
            MainFormException.checkIfCustomerHasAnAppointment(deleteCustomer.getCustomerID());
            DatabaseQuery.CustomerQuery.deleteCustomer(deleteCustomer);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Shows all appointments for current month
     * @param actionEvent Action event when button is selected.
     * @throws Exception Throws Exception
     */
    public void monthView(ActionEvent actionEvent) throws Exception {

        LocalDate localDate = LocalDate.now();
        int year = Integer.parseInt(localDate.toString().substring(0, 4));
        int month = Integer.parseInt(localDate.toString().substring(5,7));

        appointmentListMain.clear();

        appointmentListMain.addAll(AppointmentQuery.appointmentByMonth(year,month));

        appointmentTable.setItems(appointmentListMain);
        appointmentAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        appointmentCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        appointmentLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        appointmentLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        appointmentTable.refresh();

    }

    /**
     * Shows all current appointments for current week, M-F.
     * @param actionEvent Action for week view
     * @throws IOException Throws IOException
     */
    public void weekView(ActionEvent actionEvent) throws IOException {


        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
        TemporalField weekOfYear = weekFields.weekOfYear();
        LocalDate date = LocalDate.now();
        int weekNumber = date.get(weekOfYear);

        appointmentListMain.clear();

        appointmentListMain.addAll(AppointmentQuery.appointmentByWeek(weekNumber));

        appointmentTable.setItems(appointmentListMain);
        appointmentAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        appointmentCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        appointmentLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        appointmentLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        appointmentTable.refresh();
    }

    /**
     * Sets retrieved appointments to table.
     * @param actionEvent Action event when button is selected.
     * @throws Exception Throws Exception
     */
    public void allAppointments(ActionEvent actionEvent) throws Exception {

        appointmentListMain.clear();

        try {

            appointmentListMain = AppointmentQuery.getAppointments();

        }catch (SQLException e){
            e.printStackTrace();
        }

        appointmentTable.setItems(appointmentListMain);
        appointmentAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        appointmentCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        appointmentLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        appointmentLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        appointmentTable.refresh();
    }


    /**
     * Takes user to report page
     * @param actionEvent Reports action button
     * @throws IOException Throws IOException
     */
    public void reports(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Report.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 925, 600);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();

    }
}

package Controller;

import DatabaseQuery.*;
import DatabaseConnection.DatabaseConnection;
import Model.Country;
import Model.FirstLevelDivision;
import Exception.CustomerException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.ResourceBundle;

/**Add Customer*/
public class AddCustomerController implements Initializable {


    //Text Fields
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField phoneTextField;

    //Combo Boxes
    @FXML private ComboBox<String> countryComboBoxSelector;
    @FXML private ComboBox<String> divisionComboBoxSelector;

    //Buttons
    @FXML private Button cancelButton;

    //Division ID
    private int divisionID;

    /**
     * Initializer
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCountryComboBox();

        //Lambda Expression
        cancelButton.setOnAction((actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            assert root != null;
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }));

    }


    /**
     * Combo box for countries.
     * @param actionEvent Action event when box is selected.
     */
    public void countryComboBox(ActionEvent actionEvent) {

       setDivisionComboBox(actionEvent);

    }

    /**
     * Combo box for divisions.
     * @param actionEvent Action event when box is selected.
     * @throws Exception Throws Exception
     */
    public void divisionComboBox(ActionEvent actionEvent) throws Exception {

        gettingDivisionID();

    }

    /**
     * Saves the new customer to database.
     * @param actionEvent Action event when button is selected.
     * @throws Exception Throws Exception
     */
    public void save(ActionEvent actionEvent) throws Exception {

        //Checking for null values
        CustomerException.checkForNullValues(nameTextField.getText(), addressTextField.getText(),
                postalCodeTextField.getText(), phoneTextField.getText(),
                countryComboBoxSelector, divisionComboBoxSelector );

        //Placeholders
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        LocalDateTime createDate = LocalDateTime.now(ZoneOffset.UTC);
        String createdBy = UserQuery.user;
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = UserQuery.user;

        //inserting new customer
        DatabaseQuery.CustomerQuery.createNewCustomer(name, address, postalCode, phone, createDate, createdBy,
                lastUpdate, lastUpdatedBy, divisionID);

        //Returning to main form
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 925, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();

        //Clearing Fields
        clearFields();


    }

    /**
     * Setting data to country combo box.
     */
    public void setCountryComboBox(){

        ObservableList<String> countriesList = FXCollections.observableArrayList();
        ObservableList<Country> countries = CountryQuery.getCountries();

        if (countries != null) {
            for (Country country: countries){

                countriesList.add(country.getCountry());
            }
        }

        countryComboBoxSelector.setItems(countriesList);
    }

    /**
     * Setting data to division combo box.
     * @param actionEvent Action event when button is selected.
     */
    public void setDivisionComboBox(ActionEvent actionEvent){

            String country = countryComboBoxSelector.getValue();

            ObservableList<String> divisionList = FXCollections.observableArrayList();
            ObservableList<FirstLevelDivision> firstLevelDivisions = FirstLevelDivisionQuery.getDivision(country);

            if (firstLevelDivisions != null) {

                for (FirstLevelDivision fld : firstLevelDivisions) {

                    divisionList.add(fld.getDivision());
                }

            }
            divisionComboBoxSelector.setItems(divisionList);

    }

    /**
     * Getting division ID's.
     * @throws Exception Throws Exception
     */
    public void gettingDivisionID() throws Exception {

        FirstLevelDivision firstLevelDivision = new FirstLevelDivision();
        String getDivQuery = "SELECT Division_ID FROM first_level_divisions WHERE Division = ? ";
        DatabaseConnection.getConnection();
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(getDivQuery);

        ps.setString(1, divisionComboBoxSelector.getValue());
        ResultSet rs = ps.executeQuery();

        while(rs.next()){

            firstLevelDivision.setDivisionID(rs.getInt("Division_ID"));
            divisionID = firstLevelDivision.getDivisionID();

        }
        DatabaseConnection.closeConnection();
     }

    /**
     * Clearing Fields.
     */
    public void clearFields(){

        nameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        postalCodeTextField.clear();
        countryComboBoxSelector.setValue(null);
        divisionComboBoxSelector.setValue(null);
        divisionID = 0;

     }



}

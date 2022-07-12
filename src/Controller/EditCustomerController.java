package Controller;

import DatabaseQuery.CountryQuery;
import DatabaseQuery.UserQuery;
import DatabaseConnection.DatabaseConnection;
import DatabaseQuery.FirstLevelDivisionQuery;
import Model.Country;
import Model.Customer;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**Edit Customer*/
public class EditCustomerController implements Initializable {

    @FXML private TextField customerIdTextField;
    @FXML private  TextField nameTextField;
    @FXML private  TextField addressTextField;
    @FXML private  TextField postalCodeTextField;
    @FXML private  TextField phoneTextField;

    @FXML private  ComboBox<String> countryComboBoxSelector;
    @FXML private  ComboBox<String> divisionComboBoxSelector;


    @FXML private  Button cancelButton;

    //Holders to show
    private int divisionId;
    private String division;
    private String country;

    /**Holder for passed customer*/
    public static Customer passingCustomerFromMain;
    /**Setter for passed customer*/
    public static void setPassingCustomerFromMain(Customer passingCustomerFromMain) {
        EditCustomerController.passingCustomerFromMain = passingCustomerFromMain;
    }

    /**
     * Initializer
     * Setting values and combo boxes of customer being updated.
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIdTextField.setText(String.valueOf(passingCustomerFromMain.getCustomerID()));
        nameTextField.setText(passingCustomerFromMain.getCustomerName());
        addressTextField.setText(passingCustomerFromMain.getAddress());
        postalCodeTextField.setText(passingCustomerFromMain.getPostalCode());
        phoneTextField.setText(passingCustomerFromMain.getPhone());
        divisionId =  passingCustomerFromMain.getDivisionID();

        try {
            settingComboBoxes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setCountryComboBox();

        divisionComboBoxSelector.setValue(division);
        countryComboBoxSelector.setValue(country);

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
     * Combo box for countries.
     * @param actionEvent Action event when box is selected.
     */
    public void countryComboBox(ActionEvent actionEvent) {

        setDivisionComboBox(actionEvent);

    }

    /**
     * Combo box for divisions.
     * @param actionEvent Action event when box is selected.
     * @throws Exception
     */
    public void divisionComboBox(ActionEvent actionEvent) throws Exception {

        gettingDivisionID();

    }

    /**
     * Updating the customer to database.
     * @param actionEvent Action event when button is selected.
     * @throws Exception
     */
    public void save(ActionEvent actionEvent) throws Exception {


        //Checking null values
        CustomerException.checkForNullValues(nameTextField.getText(), addressTextField.getText(),
                postalCodeTextField.getText(), phoneTextField.getText(),
                countryComboBoxSelector, divisionComboBoxSelector );

        //Placeholders
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = UserQuery.user;

        //Updating
        DatabaseQuery.CustomerQuery.editCustomer(name, address, postalCode, phone, lastUpdate, lastUpdatedBy, divisionId,
                passingCustomerFromMain.getCustomerID());

        //Return user to main form
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 925, 600);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();

        //Clearing Fields
        clearFields();

    }

    /**
     * Getting and setting data for combo boxes.
     * @throws Exception
     */
    public void settingComboBoxes() throws Exception {


        try {
            String divAndCountryQuery = "SELECT first_level_divisions.Division, countries.Country " +
                    "FROM first_level_divisions " +
                    "INNER JOIN countries on  first_level_divisions.COUNTRY_ID = countries.COUNTRY_ID " +
                    "WHERE first_level_divisions.Division_ID = ?";


            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(divAndCountryQuery);
            ps.setString(1, String.valueOf(divisionId));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                division = rs.getString("Division");
                country = rs.getString("Country");

            }
        }catch(SQLException e){

            e.printStackTrace();

        }

        DatabaseConnection.closeConnection();
    }

    /**
     * Setting data to country combo box.
     */
    public void setCountryComboBox(){

        ObservableList<String> countriesList = FXCollections.observableArrayList();

        ObservableList<Country> countries = CountryQuery.getCountries();
        for (Country country: countries){

            countriesList.add(country.getCountry());
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


        for (FirstLevelDivision fld: firstLevelDivisions){

            divisionList.add(fld.getDivision());
        }

        divisionComboBoxSelector.setItems(divisionList);


    }

    /**
     * Getting division ID's.
     * @throws Exception
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
            divisionId = firstLevelDivision.getDivisionID();

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
        divisionId = 0;
        country = null;
        division = null;
        passingCustomerFromMain = null;

    }

}

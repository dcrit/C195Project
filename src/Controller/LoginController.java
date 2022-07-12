package Controller;

import DatabaseConnection.DatabaseConnection;
import DatabaseQuery.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

import java.util.Optional;
import java.util.ResourceBundle;
import DatabaseQuery.UserQuery;
import Exception.LoginException;
import javafx.stage.Stage;

/**Login*/
public class LoginController implements Initializable {

    //Text fields
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordTextField;

    //Labels
    @FXML private Label loginNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label timeZoneTextLabel;

    //Buttons
    @FXML private Button loginButton;
    @FXML private Button cancelButton;


    /**
     * Initializer
     * If the user is region that speaks French, then text will change to French
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            resourceBundle = ResourceBundle.getBundle("Language/Nat", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                loginNameLabel.setText(resourceBundle.getString("username"));
                passwordLabel.setText(resourceBundle.getString("password"));
                loginButton.setText(resourceBundle.getString("login"));
                cancelButton.setText(resourceBundle.getString("cancel"));
                timeZoneTextLabel.setText(resourceBundle.getString("timeZone"));

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ZoneId zone = ZoneId.systemDefault();
        timeZoneTextLabel.setText(String.valueOf(zone));
        UserQuery.zoneId = zone;

        //Lambda expression listener
        cancelButton.setOnAction( actionEvent ->
                System.exit(0));

    }


    /**
     * Login checks the user for entry and will display and error if login is incorrect.
     * Checks for upcoming appointments within 15 minutes.
     * A logged is used for successful and unsuccessful logins.
     * @param actionEvent Action event when button is selected.
     * @throws Exception
     */
    public void login(ActionEvent actionEvent) throws Exception {


        boolean loginSuccess = false;

        //Checking for null values
        LoginException.checkForNullValues(userNameTextField.getText(), passwordTextField.getText());

        //Checks for correct user login info
        if(UserQuery.checkUserNameAndPassword(userNameTextField.getText(), passwordTextField.getText())){

            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Language/Nat", Locale.getDefault());

                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {

                    ButtonType okButton = new ButtonType(resourceBundle.getString("ok"), ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, resourceBundle.getString("successfulLogin"), okButton);
                    alert.setTitle(resourceBundle.getString("successTitle"));
                    alert.setContentText(resourceBundle.getString("loginSuccessful"));
                    Optional<ButtonType> confirmLogin = alert.showAndWait();

                    if (confirmLogin.isPresent() && confirmLogin.get() == okButton) {
                        Parent root = FXMLLoader.load(LoginException.class.getResource("/View/MainForm.fxml"));
                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 925, 600);
                        stage.setTitle("Main Form");
                        stage.setScene(scene);
                        stage.show();

                        //Checking for upcoming appointments
                        AppointmentQuery.upcomingAppointments();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            loginSuccess = true;
            UserQuery.user = userNameTextField.getText();

            //Logs successful login
            userActivity(loginSuccess, userNameTextField.getText());

            DatabaseConnection.closeConnection();
        }
        if(!Objects.equals(userNameTextField.getText(), "") && !Objects.equals(passwordTextField.getText(), "" ) && !loginSuccess){


                //Logs unsuccessful login
                userActivity(false, userNameTextField.getText());


            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Language/Nat", Locale.getDefault());

                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {

                    ButtonType okButton = new ButtonType(resourceBundle.getString("ok"), ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.WARNING, resourceBundle.getString("incorrectInput"), okButton);
                    alert.setTitle(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("loginUnsuccessful" ));
                    alert.showAndWait();

                }

            }catch (Exception e){
                e.printStackTrace();
            }

            }

    }

    /**
     * Logger for user activity
     * @param aBoolean Boolean for successful login
     * @param user Takes string for user and adds to logger
     * @throws IOException
     */
    public void userActivity(Boolean aBoolean, String user) throws IOException {

        try {
            FileWriter userActivityLog = new FileWriter("src/Resources/login_activity.txt", true);
            LocalDateTime localDateTime = LocalDateTime.now();

            if (aBoolean) {

                userActivityLog.write("\nSuccessful login on: " + localDateTime + " By: " + user);

            } else {

                userActivityLog.write("\nUnsuccessful login on: " + localDateTime + " By: " + user);
            }

            userActivityLog.flush();
            userActivityLog.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

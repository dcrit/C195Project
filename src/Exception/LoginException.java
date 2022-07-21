package Exception;


import javafx.scene.control.Alert;

/**Login Exception*/
public class LoginException {

    /**
     * Checks for null values
     * @param username User Name
     * @param password Password
     * @return Returns true or false
     * @throws Exception Throws Exception
     */
    public static boolean checkForNullValues(String username, String password) throws Exception {

        if(username == "" || password == ""){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect Input");
            alert.setContentText("Field can not be blank");
            alert.showAndWait();
            throw new Exception("Check for null values");
        }

        return false;

    }



}

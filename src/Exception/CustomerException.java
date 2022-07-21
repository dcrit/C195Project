package Exception;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import java.util.Objects;

/**Customer Exception*/
public class CustomerException {

    /**
     * Checks for null values
     * @param name Name
     * @param address Address
     * @param postalCode Postal Code
     * @param phone Phone
     * @param country Country
     * @param divisions Divisions
     * @return Return true or false
     * @throws Exception Throws Exception
     */
    public static boolean checkForNullValues(String name, String address, String postalCode, String phone,
                                             ComboBox<String> country, ComboBox<String> divisions) throws Exception {

        if(Objects.equals(name, "") || Objects.equals(address, "") || Objects.equals(postalCode, "") ||
                Objects.equals(phone, "") || Objects.equals(country.getValue(), null) ||
                Objects.equals(divisions.getValue(), null)){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect Input");
            alert.setContentText("Error: Null Value");
            alert.showAndWait();
            throw new Exception("Check for null fields");

        }

        return false;

    }


}

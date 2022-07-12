package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Country Query*/
public class CountryQuery {


    /**
     * Retrieves all countries.
     * @return Returns a list of countries
     */
    public static ObservableList<Country> getCountries(){


        ObservableList<Country> countriesList = FXCollections.observableArrayList();


        try{

            String countryQuery = "SELECT country FROM countries ";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(countryQuery);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Country country = new Country();
                country.setCountry(rs.getString("Country"));

                countriesList.add(country);

            }

            return countriesList;
        } catch (Exception e){

            e.printStackTrace();
        }
        DatabaseConnection.closeConnection();
       return null;
    }



}

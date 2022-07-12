package DatabaseQuery;


import DatabaseConnection.*;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**First Level Division*/
public abstract class FirstLevelDivisionQuery {

    /**
     * Retrieves first level divisions from database
     * @param divCountry Division Country
     * @return Returns a list of first level divisions
     */
    public static ObservableList<FirstLevelDivision> getDivision(String divCountry){

        ObservableList<FirstLevelDivision> firstLevelDivisionList = FXCollections.observableArrayList();

        try{


            String divisionQuery = "SELECT first_level_divisions.Division FROM first_level_divisions \n" +
                    "LEFT JOIN countries on  first_level_divisions.Country_ID = countries.Country_ID\n" +
                    "WHERE countries.Country =  ? \n" +
                    "ORDER BY first_level_divisions.Division ASC;";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(divisionQuery);
            ps.setString(1, divCountry);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setDivision(rs.getString("Division"));
                firstLevelDivisionList.addAll(firstLevelDivision);

            }

            DatabaseConnection.closeConnection();

            return firstLevelDivisionList;

        } catch (Exception e ){

            e.printStackTrace();
        }

        return null;
    }
}

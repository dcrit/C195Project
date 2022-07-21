package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import Model.Report1;
import Model.Report2;
import Model.Report3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**Report Query*/
public class ReportQuery {

    /**
     * Retrieves the report date for report 1.
     * @return Returns a list for report 1
     * @throws Exception Throws Exception
     */
    public static ObservableList<Report1> report1() throws Exception {

         ObservableList<Report1> report1List = FXCollections.observableArrayList();

         try {

             String selectReport1 = "select distinct monthname(Start) AS Month, Type, count(*) as Total from appointments group by Type, month(Start)";
             DatabaseConnection.getConnection();
             PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectReport1);
             ResultSet rs = ps.executeQuery();

             while(rs.next()){

                 report1List.add(new Report1(
                         rs.getString(1),
                         rs.getString(2),
                         rs.getInt(3)

                 ));

             }
         }catch (Exception e){
             e.printStackTrace();
         }
         DatabaseConnection.closeConnection();
        return report1List;

    }

    /**
     * Retrieves contacts for database.
     * @param contactId Contact ID
     * @return Returns a list of contact ID's
     */
    public static ObservableList<Report2> report2(int contactId){

        ObservableList<Report2> report2List = FXCollections.observableArrayList();

        try{
            String selectReport2  = "select appointments.Appointment_ID, \n" +
                    "appointments.Title, \n" +
                    "appointments.Type,\n" +
                    " appointments.Description, \n" +
                    " appointments.Start, \n" +
                    " appointments.End, \n" +
                    " appointments.Customer_ID from appointments where Contact_ID = " + contactId;
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectReport2);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                report2List.add(new Report2(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Type"),
                        rs.getString("Description"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Start").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("End").toLocalDateTime()),
                        rs.getInt("Customer_ID")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        DatabaseConnection.closeConnection();
        return report2List;
    }

    /**
     * Retrieves data for report 3
     * @return Returns a list for report 3
     */
    public static ObservableList<Report3> report3(){

        ObservableList<Report3> report3List = FXCollections.observableArrayList();
        try {

            String selectReport3 = "select customers.Customer_ID, first_level_divisions.Division, countries.Country \n" +
                    "FROM ((customers \n" +
                    "inner join first_level_divisions on  customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "inner join countries on  first_level_divisions.COUNTRY_ID = countries.Country_ID)) order by Country ASC;";

            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectReport3);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                report3List.add(new Report3(
                        rs.getInt("Customer_ID"),
                        rs.getString("Division"),
                        rs.getString("Country")
                ));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        DatabaseConnection.closeConnection();
        return report3List;
    }

    /**
     * Converts UTC to user timezone
     * @param localDateTime Local Date Time
     * @return Returns the converted time in time zone
     */
    public static LocalDateTime timeCovertForLocalDateTimeCurrentZone(LocalDateTime localDateTime){

        ZoneId zoneOfUTC = ZoneId.of("UTC");
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneOfUTC);
        ZonedDateTime convertedZDT = zdt.withZoneSameInstant(ZoneId.of(String.valueOf(zone)));

        return convertedZDT.toLocalDateTime();
    }
}

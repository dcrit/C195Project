package Model;

/**Report 3*/
public class Report3 {


    //Variables
    private int customerId;
    private String division;
    private String country;

    //Constructor
    public Report3(int customerId, String division, String country) {
        this.customerId = customerId;
        this.division = division;
        this.country = country;
    }

    //Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

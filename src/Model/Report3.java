package Model;

/**Report 3*/
public class Report3 {


    //Variables
    private int customerId;
    private String division;
    private String country;

    /**
     *
     * @param customerId Customer ID
     * @param division Division
     * @param country Country
     */
    public Report3(int customerId, String division, String country) {
        this.customerId = customerId;
        this.division = division;
        this.country = country;
    }

    /**
     *
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId set customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division set division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country set country
     */
    public void setCountry(String country) {
        this.country = country;
    }

}

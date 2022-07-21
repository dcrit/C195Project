package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**First Level Division*/
public class FirstLevelDivision {

    //Variables
    private int divisionID;
    private String division;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;


    /**
     * Constructor
     * @param division Division ID
     * @param createDate Create Date
     * @param createdBy Created BY
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last Updated By
     * @param countryID Country ID
     */
    public FirstLevelDivision(int divisionID, String division, LocalDate createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }
    public FirstLevelDivision(){

    }

    /**
     *
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *
     * @param divisionID set division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *
     * @return division ID
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
     * @return create date
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate set create date
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy set created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate set last update
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy set last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @param countryID set country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

}

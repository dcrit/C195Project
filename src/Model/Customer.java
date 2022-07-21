package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**Customer*/
public class Customer {

    //Variables
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;


    /**
     *
     * @param customerID Customer ID
     * @param customerName Customer Name
     * @param address Address
     * @param postalCode Postal Code
     * @param phone Phone
     * @param createDate Create Date
     * @param createdBy Create By
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last Updated By
     * @param divisionID Division ID
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                    int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }
    public Customer(){

    }

    /**
     *
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID set customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName set customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address set address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode set postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;}

    /**
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone set phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate set create date
     */
    public void setCreateDate(LocalDateTime createDate) {
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

}

package Model;

import java.time.LocalDateTime;

/**Appointment*/
public class Appointment {

    //Variables
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Appointment Constructor
     * @param appointmentId Appointment ID
     * @param title Title
     * @param description Description
     * @param location Location
     * @param type Type
     * @param startTime Start Time
     * @param endTime End Time
     * @param createDate Create Date
     * @param createdBy Created By
     * @param lastUpdate Last updated
     * @param lastUpdatedBy Last Updated By
     * @param customerID Customer ID
     * @param userID User ID
     * @param contactID ContactID
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate,
                       String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                       int customerID, int userID, int contactID) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     *
     * @return appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;}

    /**
     *
     * @param appointmentId set appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;}

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;}

    /**
     *
     * @param title set title
     */
    public void setTitle(String title) {
        this.title = title;}

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;}

    /**
     *
     * @param description set description
     */
    public void setDescription(String description) {
        this.description = description;}

    /**
     *
     * @return location
     */
    public String getLocation() {
        return location;}

    /**
     *
     * @param location set location
     */
    public void setLocation(String location) {
        this.location = location;}
    /**
     *
     * @return type
     */
    public String getType() {
        return type;}

    /**
     *
     * @param type set type
     */
    public void setType(String type) {
        this.type = type;}

    /**
     *
     * @return start time
     */
    public LocalDateTime getStartTime() {
        return startTime;}

    /**
     *
     * @param startTime set start time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;}

    /**
     *
     * @return end time
     */
    public LocalDateTime getEndTime() {
        return endTime;}

    /**
     *
     * @param endTime set end time
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;}

    /**
     *
     * @return create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;}

    /**
     *
     * @param createDate set create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;}

    /**
     *
     * @return create by
     */
    public String getCreatedBy() {
        return createdBy;}

    /**
     *
     * @param createdBy created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;}

    /**
     *
     * @return last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;}

    /**
     *
     * @param lastUpdate set last update
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;}

    /**
     *
     * @return last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;}

    /**
     *
     * @param lastUpdatedBy set last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;}

    /**
     *
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;}

    /**
     *
     * @param customerID customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;}

    /**
     *
     * @return user ID
     */
    public int getUserID() {
        return userID;}

    /**
     *
     * @param userID set userID
     */
    public void setUserID(int userID) {
        this.userID = userID;}

    /**
     *
     * @return get Contact ID
     */
    public int getContactID() {
        return contactID;}

    /**
     *
     * @param contactID set contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;}

}

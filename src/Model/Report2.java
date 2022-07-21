package Model;

import java.time.LocalDateTime;

/**Report 2 */
public class Report2 {

    //Variables
    private int appointmentId;
    private String title;
    private String type;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;

    /**
     *
     * @param appointmentId Appointment ID
     * @param title Title
     * @param type Type
     * @param description Description
     * @param start Start
     * @param end End
     * @param customerId Customer ID
     */
    public Report2(int appointmentId, String title, String type, String description, LocalDateTime start, LocalDateTime end, int customerId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    /**
     *
     * @return appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     *
     * @param appointmentId set appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title set title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     *
     * @param start set start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     *
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     *
     * @param end set end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
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

}

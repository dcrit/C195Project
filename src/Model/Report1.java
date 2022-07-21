package Model;

/**Report 1*/
public class Report1 {

    //Variables
    private String month;
    private String type;
    private int total;

    /**
     *
     * @param month Month
     * @param type Type
     * @param total Total
     */
    public Report1(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     *
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @param month set month
     */
    public void setMonth(String month) {
        this.month = month;
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
     * @return total
     */
    public int getTotal() {
        return total;
    }

    /**
     *
     * @param total set total
     */
    public void setTotal(int total) {
        this.total = total;
    }

}

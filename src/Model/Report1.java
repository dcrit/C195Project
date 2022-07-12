package Model;

/**Report 1*/
public class Report1 {

    //Variables
    private String month;
    private String type;
    private int total;

    //Constructor
    public Report1(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    //Getters and setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}

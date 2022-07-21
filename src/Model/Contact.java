package Model;

/**Contact*/
public class Contact {

    //variables
    private int id;
    private String name;
    private String email;

    /**
     * Constructor
     * @param id ID
     * @param name Name
     * @param email Email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public Contact() {

    }

    /**
     *
     * @return contact id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id set contact ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return contact name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name set contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email contact email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

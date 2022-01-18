package model;

/** This class handles the 'Outsourced' extended part.
 *
 * @author Lois Vernon Pua
 */
public class Outsourced extends Part{

    private String companyName;


    /** This constructor builds an Outsourced subclass of Part)
     *
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The quantity on hand of the part.
     * @param min The minimum quantity of the part.
     * @param max The maximum quantity of the part.
     * @param companyName The company name for the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method gets a company name.
     *
     * @return The company name of the part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This method sets a company name.
     *
     * @param companyName The company name to be set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
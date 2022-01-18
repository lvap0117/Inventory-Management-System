package model;

/** This class handles the 'InHouse' extended part.
 *
 * @author Lois Veron Pua
 */
public class InHouse extends Part
{

    private int machineId;


    /** This constructor builds an In-House subclass of Part)
     *
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The quantity on hand of the part.
     * @param min The minimum quantity of the part.
     * @param max The maximum quantity of the part.
     * @param machineId The machine id for the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** This method gets a machineId.
     *
     * @return The machine id of the part.
     */
    public int getMachineId() {
        return machineId;
    }

    /** This method sets a machineId.
     *
     * @param machineId The machine id to be set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

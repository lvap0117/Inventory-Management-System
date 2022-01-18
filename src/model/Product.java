package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class handles the product.
 *
 * @author Lois Vernon Pua
 */
public class Product {

    /** List for containing the associated parts of the product.*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor for building a Product
     *
     * @param id The id of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The quantity on hand of the product.
     * @param min The min amount of the product.
     * @param max The max amount of the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }



    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }




    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }


    /** This method adds associated parts to the observable list associated with the product.
     *
     * @param part The part to be added.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** This method deletes associated parts from the observable list associated with the product.
     *
      * @param part The part to be deleted.
     */
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }

    /** This method gets all parts associated with the product from an observable list.
     *
      * @return All parts associated with the product.
     */
    public  ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}

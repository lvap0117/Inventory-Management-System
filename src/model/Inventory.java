package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class handles the inventory of parts and products.
 *
 * @author Lois Vernon Pua
 */
public class Inventory
{

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     */
    public static int partID;

    /**
     *
     */
    public static int productID;

    /** This method adds a part to the inventory.
     *
     * @param part The part to be added.
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** This method adds a product to the inventory.
     *
     * @param product The product to be added.
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** This method searches for a part in the inventory, by partID.
     *
     * @param partID The part id to be searched.
     * @return A part based on the search.
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == (partID)) {
                return part;
            }
        }
        return null;
    }

    /** This method searches for a product in the inventory, by productID.
     *
     * @param productID The product id to be searched.
     * @return A product based on the search.
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == (productID)) {
                return product;
            }
        }
        return null;
    }

    /** This method searches for a part in the inventory, by partName.
     *
     * @param partName The part name to be searched.
     * @return A part based on the search.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchResults.add(part);
            }
        }

        return searchResults;

    }

    /** This method searches for a product in the inventory, by productName.
     *
     * @param productName The product name to be searched.
     * @return A product based on the search.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                searchResults.add(product);
            }
        }

        return searchResults;

    }

    /** This method updates the inventory with new part data at a specified index.
     *
     * @param index The index of the part to be updated.
     * @param part The part to be updated.
     */
    public static void updatePart (int index, Part part){
        allParts.set(index, part);
    }

    /** This method updates the inventory with new product data at a specified index.
     *
     * @param index The index of the product to be updated.
     * @param product The product to be updated.
     */
    public static void updateProduct (int index, Product product){
        allProducts.set(index, product);
    }

    /** This method removes a part from the inventory.
     *
     * @param part The part to be removed.
     */
    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    /** This method removes a product from the inventory.
     *
     * @param product The product to be removed.
     */
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    /** This method gets all the parts in the inventory.
     *
     * @return All the parts in the inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This method gets all the products in the inventory.
     *
     * @return All the products in the inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
     /**
     * Generates a new part ID.
     *
     * @return A unique part ID.
     */
    public static int getNewPartId() {
        return ++partID;
    }
 /**
     * Generates a new product ID.
     *
     * @return A unique product ID.
     */
    public static int getNewProductId() {
        return ++productID;
    }

}
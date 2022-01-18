package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javax.swing.JOptionPane;

/** The Main class controller for the main window application of the project.
 *
 * @author Lois Vernon Pua
 */

public class MainFormController implements Initializable {

    Parent scene;
    Stage stage;

    
    @FXML
    private TextField partSearchBox;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;

    @FXML
    private TableColumn<Part, Double> partPricePerUnit;

    @FXML
    private TextField productSearchBox;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevel;

    @FXML
    private TableColumn<Product, Double> productPricePerUnit;
    
    /**
     * Get the part object selected by the user.
     * 
     */
    private static Part partToModify;

    /**
     *
     * @return part object, returns if null
     */
    public static Part getPartToModify () {
        return partToModify;
    }
     /**
     * Get the product object selected by the user.
     * 
     */
    private static Product productToModify;

    /**
     *
     * @return product object, returns if null
     */
    public static Product getProductToModify () {
        return productToModify;
    }

    /** Pre-populates the table and initializes the main form.
     *
     * @param url The location.
     * @param resourceBundle The resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));



        productsTable.setItems(Inventory.getAllProducts());

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /** Displays the add part form.
     *
     * @param event The event that is happening in the method 
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionDisplayAddPartForm(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Displays the modify part form.
     *
     * @param event The event that is happening in the method 
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionDisplayModifyPartForm(ActionEvent event) throws IOException
    {
      partToModify = partsTable.getSelectionModel().getSelectedItem();

        
        if (partToModify == null) {
           JOptionPane.showMessageDialog(null,"Error, Please select a part to modify!");
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPartForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        

    }

    /** This method displays deletes the selected part.
     *
     * @param event The event that is happening in the method (deleting the selected part).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) throws IOException {

            try { 
            Inventory.lookupPart(partsTable.getSelectionModel().getSelectedItem().getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            // Checks whether OK button pressed then deletes part
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem()); 
                partsTable.setItems((ObservableList<Part>) Inventory.lookupPart(partSearchBox.getText()));
            }
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a valid part.");
            alert.setTitle("Error Dialog Parts");
            alert.showAndWait();
        } 


    }

    /** This method searches the parts table.
     *
     * @param event The event that is happening in the method (searching the parts table).
     */
    @FXML
    void onActionSearchParts(ActionEvent event) {

        String searchString = partSearchBox.getText();

        ObservableList<Part> searchResults = Inventory.lookupPart(searchString);

        if (searchResults.isEmpty())
        {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchString));
                if (part != null) {
                    searchResults.add(part);
                }
            }
            catch (NumberFormatException ex){
                //ex.printStackTrace();
            }
        }

        if (searchResults.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Part was not found!");
     
            alert.showAndWait();

            return;
        }

        partsTable.setItems(searchResults);


    }

    /** Displays the add product form.
     *
     * @param event The event that is happening in the method 
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionDisplayAddProductForm(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method displays the modify product form.
     *
     * @param event The event that is happening in the method (displaying the modify product form).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionDisplayModifyProductForm(ActionEvent event) throws IOException
    {
    productToModify = productsTable.getSelectionModel().getSelectedItem();
        

        
        if (productToModify == null) {
           JOptionPane.showMessageDialog(null,"Error, Please select product to modify!");
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProductForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        
    }

    /** This method displays deletes the selected product.
     *
     * @param event The event that is happening in the method (deleting the selected product).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {

         if (productsTable.getSelectionModel().isEmpty())
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A PRODUCT TO DELETE!");
           

            alert.showAndWait();
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE? PRODUCT WILL BE PERMANENTLY DELETED!");
          

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() != 0)
                {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setHeaderText("NOT DELETED.DELETE PARTS ASSOCIATED FIRST!");
                  

                    alert3.showAndWait();
                }
                else
                {
                    Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());

                    productsTable.setItems(Inventory.getAllProducts());


                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setHeaderText("PRODUCT WAS SUCCESFULLY DELETED!");
                   

                    alert2.showAndWait();
                }

            }
            else {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText("THEE SELECTED PRODUCT WAS NOT DELETED!");
               

                alert3.showAndWait();
                }

        }


    }

    /** This method searches the products table.
     *
     * @param event The event that is happening in the method (searching the products table).
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) {

        String searchString = productSearchBox.getText();

        ObservableList<Product> searchResults = Inventory.lookupProduct(searchString);

        if (searchResults.isEmpty())
        {
            try {
                Product product = Inventory.lookupProduct(Integer.parseInt(searchString));
                if (product != null) {
                    searchResults.add(product);
                }
            }
            catch (NumberFormatException ex){
                //ex.printStackTrace();
            }
        }

        if (searchResults.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product was not found!");
           
            alert.showAndWait();

            return;
        }

        productsTable.setItems(searchResults);


    }


    /** Exits the application
     *
     * @param event The event that is happening in the method (exiting the application).
     */
    @FXML
    void onActionExitApplication(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit now?");
      

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

}
package controller;

import javafx.collections.FXCollections;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/** The class to load the Add product form.
 *
 
 * @author Lois Vernon Pua.
 */
public class AddProductFormController implements Initializable {

    Stage stage;
    Parent scene;

    /** List for containing the associated parts of the product.*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField addProductIDText;

    @FXML
    private TextField addProductNameText;

    @FXML
    private TextField addProductInventoryText;

    @FXML
    private TextField addProductPriceText;

    @FXML
    private TextField addProductMaxText;

    @FXML
    private TextField addProductMinText;

    @FXML
    private TextField partSearchBox;

    @FXML
    private TableView<Part> addProductPartsTable;

    @FXML
    private TableColumn<Part, Integer> addProductPartsTablePartID;

    @FXML
    private TableColumn<Part, String> addProductPartsTablePartName;

    @FXML
    private TableColumn<Part, Integer> addProductPartsTablePartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> addProductPartsTablePartPrice;

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartsTablePartID;

    @FXML
    private TableColumn<Part, String> associatedPartsTablePartName;

    @FXML
    private TableColumn<Part, Integer> associatedPartsTablePartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> associatedPartsTablePartPrice;
    
    /** Pre-Populates the table 
     *
     * @param url The location.
     * @param resourceBundle The resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        addProductPartsTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartsTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartsTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductIDText.setText(String.valueOf(Inventory.productID));

        associatedPartsTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductPartsTable.setItems(Inventory.getAllParts());
    }

    /** Will display the confirmation dialog and exits to main window.
     *
     * @param event The cancel button for the event action.
     * @throws IOException to throw the error exception from fxml loader.
     */
    @FXML
    void onActionCancelAdd(ActionEvent event) throws IOException
    {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit? (Changes may not be saved)?");
        

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }

    }


    /** Adds the product after validating fields are valid.
     *
     * @param event Add button event action.
     * @throws IOException throws the error exception from fxml loader.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException
    {



        

            try {
            String name = addProductNameText.getText();
            Double price = Double.parseDouble( addProductPriceText.getText());
            int id = 0;
            int stock = Integer.parseInt( addProductInventoryText.getText());
            int min = Integer.parseInt(addProductMinText.getText());
            int max = Integer.parseInt(addProductMaxText.getText());
          
      

            if (name.equals("")) {
               JOptionPane.showMessageDialog(null,"Error, fields must contain a value!"); 
            } else {
                if (minMaxValidation(min, max) && inventoryValidation(min, max, stock)) {
                    
                      Product product = new Product(id, name, price, stock, min, max);
                        product.getAllAssociatedParts().addAll(associatedParts);
                        Inventory.addProduct(product);

                        Inventory.productID += 1;

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    
                  
                }
                
            }
         

            catch(NumberFormatException ex) {
             JOptionPane.showMessageDialog(null, "Fields are blank or No strings allowed in Inventory!");
        }
    }
   
        
     
        
        

    /** This will search the part method.
     *
     * @param event The event for searching the part table.
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
                ex.printStackTrace();
            }
        }

        if (searchResults.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PART NOT FOUND!");
            alert.setContentText("Please enter a valid part.");

            alert.showAndWait();

            return;
        }

        addProductPartsTable.setItems(searchResults);


    }

    /** This will search the product method.
     *
     * @param event The event for searching the product table.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) throws IOException
    {

        if (addProductPartsTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PART NOT FOUND!.");
            alert.setContentText("Please select a valid part to add.");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else
        {

            associatedParts.add(addProductPartsTable.getSelectionModel().getSelectedItem());

            associatedPartsTable.setItems(associatedParts);

        }

    }

    /** The method that removes the part from the product.
     *
     * @param event The event that is happening in the method (disassociates a part from a product).
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            JOptionPane.showMessageDialog(null, "Error! Please select Part!");}
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to remove part?");
            Optional<ButtonType> result = alert.showAndWait();
            
          if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
            associatedPartsTable.setItems(associatedParts);
            
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setHeaderText("Part Removed!");
            alert2.setContentText("Part Removed Succesfully");
            alert2.showAndWait();
          }
           
            
        }

    }
    
    /**
     *
     * @param min min value to validate
     * @param max max value to validate
     * @return Boolean value to return if true
     */
    public static boolean minMaxValidation(int min, int max) {
        
        boolean retVal = true;
        
        if (min >=max || min < 0) {
        
          JOptionPane.showMessageDialog(null,"Error, Invalid Min Value!");
          retVal = false;
        
        }
        return retVal;
    }
    
    /**
     * Checks and validates inventory level associated with min and max values.
     *
     * @param min The minimum value to check.
     * @param max The maximum value to check.
     * @param stock The inventory value to check for the part.
     * @return To check if values of min is valid as a Boolean with inventory level.
     */
    public static boolean inventoryValidation(int min, int max, int stock) {
       
      boolean retVal = true;  
      
      if (min > stock || stock > max) {
       JOptionPane.showMessageDialog(null,"Error, Invalid Min/Max value!");
          retVal = false;   
        
      }
      return retVal;
    }
   
    /**
     * Loads main window after every field is validated/
     * @param event from parent method
     * @throws IOException  loads exception from fxml loader.
     */
      private void returnMainWindow(ActionEvent event) throws IOException {
      stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }
      
}
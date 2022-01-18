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

/** This class controls the Modify Product form.
 *
 * @author Lois Vernon Pua
 */
public class ModifyProductFormController implements Initializable {

    Parent scene;
    Stage stage;

    /** List for containing the associated parts of the product.*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    
    /**
     * The product object selected in the MainScreenController.
     */
    Product selectedProduct;


    
    @FXML
    private TextField modifyProductIDField;

  
    @FXML
    private TextField modifyProductNameField;

 
    @FXML
    private TextField modifyProductInventoryField;

  
    @FXML
    private TextField modifyProductPriceField;

    
    @FXML
    private TextField modifyProductMaxField;

   
    @FXML
    private TextField modifyProductMinField;


    @FXML
    private TextField partSearchBox;

    @FXML
    private TableView<Part> modifyProductPartsTable;

   
    @FXML
    private TableColumn<Part, Integer> modifyProductPartsTablePartID;

   
    @FXML
    private TableColumn<Part, String> modifyProductPartsTablePartName;


    @FXML
    private TableColumn<Part, Integer> modifyProductPartsTablePartInventoryLevel;

    
    @FXML
    private TableColumn<Part, Double> modifyProductPartsTablePartPrice;

  
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

    /** Pre populates the table based on the products being sent
     ** 
     *
     * 
     *
     * <p><b>
     *   Rubric section B.1:
     * </b></p>
     *
     * I was getting a "javafx.fxml." error when modifying the product and adding and saving the parts associated. I also realized when I add a part to a product, the part does not pre populate the table on the lower right hand when modifying the product again.
     * <p>
     *I had to add selectdProduct variable and add the last part of the paragraph on the initialize table. This way, it pre populates with the assocaited parts to product when modifying.
     * </p>
     * @param url The location.
     * @param resourceBundle The resources.
     **/
     
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        
         selectedProduct = MainFormController.getProductToModify();
        associatedParts = selectedProduct.getAllAssociatedParts();

        modifyProductPartsTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartsTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartsTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductPartsTable.setItems(Inventory.getAllParts());

        associatedPartsTablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsTablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsTablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsTablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedParts);
        
        
        modifyProductIDField.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameField.setText(selectedProduct.getName());
        modifyProductInventoryField.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxField.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinField.setText(String.valueOf(selectedProduct.getMin()));
    }

    /** If cancel is selected, goes back to Main Window.
     *
     * @param event The event that is happening in the method (cancelling the modify product form).
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionCancelModify(ActionEvent event) throws IOException
    {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?(Changes may not be saved)");
        

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }

    }

    

    /** Validates the field.
     *
     * @param event The event that is happening in the method (modifying a product).
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException
    {

      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to add product?");
        

        Optional<ButtonType> result = alert.showAndWait();



        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
            String name = modifyProductNameField.getText();
            Double price = Double.parseDouble(modifyProductPriceField.getText());
            int id =  selectedProduct.getId();
            int stock = Integer.parseInt(modifyProductInventoryField.getText());
            int min = Integer.parseInt(modifyProductMinField.getText());
            int max = Integer.parseInt(modifyProductMaxField.getText());
            
             
          
      

            if (name.equals("")) {
               JOptionPane.showMessageDialog(null,"Error, field must contain a value!"); 
           } else {
                if (min <= max) {
    if (minMaxValidation(min, max) && inventoryValidation(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnMainWindow(event);
                }
         }
            }

            }  catch(NumberFormatException ex) {
             JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Values!", 0);
        }
    

        
        
    }
    
    }
            




    

    /** This method searches the parts table.
     *
     * @param event The event that is happening in the method (searching the part table).
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
            alert.setHeaderText("Part was not found.");
          

            alert.showAndWait();

            return;
        }

        modifyProductPartsTable.setItems(searchResults);


    }

    /** Associates a part with the product.
     *
     * @param event The event that is happening in the method (associating a part with a product.).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) throws IOException
    {

         Part selectedPart = modifyProductPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(null,"Error, Please Select A part");
        } else {
            associatedParts.add(selectedPart);
            associatedPartsTable.setItems(associatedParts);
        }
    }

    /** This method disassociates a part from a product.
     *
     * @param event The event that is happening in the method (disassociates a part from a product).
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event)
    {

     Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
             
          if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartsTable.setItems(associatedParts);
            } else {
            JOptionPane.showMessageDialog(null,"Error, Please Select A part");
                
            }
          } 

    }
     /**
     * Checks and validates values of minimum and maximum fields.
     *
     * @param min The minimum value to check.
     * @param max The maximum value to check.
     * @return To check if values of min is valid as a Boolean with inventory level.
     */

  public static boolean minMaxValidation(int min, int max) {
        
        boolean retVal = true;
        
        if (min >=max || min < 0) {
        
          JOptionPane.showMessageDialog(null,"Error, Invalid value for min!");
        
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
       JOptionPane.showMessageDialog(null,"Error, Inventory can't exceed max value! or Min value is invalid!");
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

    Product product;

   
/** This method is used by the 'Main Form' to pre-populate the text fields based on the product information that is being sent.
     *
     * @param product The product that is being sent.
     *
     * <p><b>
     *   Rubric section B.1:
     * </b></p>
     *
     * I was getting a "javafx.fxml.LoadException" when I was attempting to go to the Modify Product form from the main screen.
     * It turned out to be a few things: I was not populating the 'associatedParts' ObservableList in this method, I was not initializing the
     * associatedPartsTable in this class like I should have been, and I was also not properly addressing the handling of the save button once
     * I was in the Modify Product form.
     *
     * <p>
     * I added the associatedParts.addAll(product.getAllAssociatedParts()) in this method, added the associatedPartsTable.setItems(associatedParts)
     * in the initialize method, and fixed the save button handling in the 'onActionModifyProduct' method. Once I addressed these things, the functionality
     * of the forms was working properly.
     * </p>
     */
    public void sendProduct(Product product)
    {
        this.product = product;

        modifyProductIDField.setText(Integer.toString(product.getId()));
        modifyProductNameField.setText(product.getName());
        modifyProductInventoryField.setText(Integer.toString(product.getStock()));
        modifyProductPriceField.setText(Double.toString(product.getPrice()));
        modifyProductMaxField.setText(Integer.toString(product.getMax()));
        modifyProductMinField.setText(Integer.toString(product.getMin()));

        associatedParts.addAll(product.getAllAssociatedParts());

    }
}

      



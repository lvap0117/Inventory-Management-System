package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class for Add part screen of  the application.
 *
 * @author Lois Vernon Pua
 */

public class AddPartFormController implements Initializable {
 Parent scene;
 Stage stage;
 
    /** Text field for part name.*/
    @FXML
    private TextField addPartNameText;
    /** Text field for part ID.*/
    @FXML
    private TextField addPartIDText;
    /** Text field for part quantity.*/
    @FXML
    private TextField addPartInventoryText;
     /** Text field for part price.*/
    @FXML
    private TextField addPartPriceText;
    /** Text field for part maximum value allowed.*/
    @FXML
    private TextField addPartMaxText;

    @FXML
    private TextField switchLabelTextField;
    /** Text field for part maximum allowed on hand.*/
    @FXML
    private TextField addPartMinText;
    
    @FXML
    private RadioButton inHouseRadioButton;
    
    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private Label switchLabel;
  
    @FXML
    private ToggleGroup sourceTG;
   

    /** Pre-Populates the table when In house radio button is selected
     *
     * @param url The location.
     * @param resourceBundle The resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inHouseRadioButton.setSelected(true);
        addPartIDText.setText(String.valueOf(Inventory.partID));

    }


   
    /** Will display the confirmation dialog and exits to main window.
     *
     * @param event The cancel button for the event action.
     * @throws IOException to throw the error exception from fxml loader.
     */
    @FXML
    void onActionCancelAdd(ActionEvent event) throws IOException { 


     Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? (Changes may not be saved)");
     Optional<ButtonType> result = alert.showAndWait();
     

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }

    }

    /** Verify the fields if it is valid before further action can be done.
     *
     * @param event The add button for the event action.
     * @throws IOException throws the error exception from fxml loader.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {


        try {
           String name = addPartNameText.getText();
           String companyName;
           Double price = Double.parseDouble(addPartPriceText.getText());
            int id = 0;
            int stock = Integer.parseInt(addPartInventoryText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
            int machineID;
            boolean partExists = false;

            if (name.equals("")) {
               JOptionPane.showMessageDialog(null,"Error, field must contain a value!"); 
            } else {
                if (minMaxValidation(min, max) && inventoryValidation(min, max, stock)) {

                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineID = Integer.parseInt(switchLabelTextField.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            partExists = true;
                        } catch (NumberFormatException ex) {
                           JOptionPane.showMessageDialog(null,"Error, Invalid value for machine Id!");
                           
                        }
                    }

                    if (outsourcedRadioButton.isSelected()) {
                        companyName = switchLabelTextField.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partExists = true;
                    }

                    if (partExists) {
                        returnMainWindow(event);

                    }
                }
            }
        } catch(NumberFormatException ex) {
             JOptionPane.showMessageDialog(null, "Fields are blank or No strings allowed in Inventory!");
          
        }
       
        
    }

    
 /** This method detects the source for the part, and changes the label accordingly.
     *
     * @param event The event that is happening in the method (getting part source).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionGetSource(ActionEvent event) throws IOException{

        if (this.sourceTG.getSelectedToggle().equals(this.inHouseRadioButton))
            switchLabel.setText("The Machine ID:");
        else
            switchLabel.setText("The Company Name:");

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

  
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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/** This class controls the Modify Part form
 *
 * @author Lois Vernon Pua
 */
public class ModifyPartFormController implements Initializable {

    Parent scene;
    Stage stage;

    /**
     * The part object selected in the MainScreenController.
     */
    private Part partModify;


    @FXML
    private RadioButton inHouseRadioButton;
  
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField modifyPartIDField;

    @FXML
    private TextField modifyPartNameField;

    @FXML
    private TextField modifyPartInventoryField;

    @FXML
    private TextField modifyPartPriceField;

    @FXML
    private TextField modifyPartMaxField;

    @FXML
    private TextField modifyPartMinField;

    @FXML
    private TextField modifyPartSwitchField;

    @FXML
    private Label switchLabel;

    @FXML
    private ToggleGroup sourceTG;

    
    /** This method initializes the Modify Part form.
     *
     * @param url The location.
     * @param resourceBundle The resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      partModify = MainFormController.getPartToModify();

       if (partModify instanceof InHouse) {
           inHouseRadioButton.setSelected(true);
           switchLabel.setText("The Machine ID");
         modifyPartSwitchField.setText(String.valueOf(((InHouse) partModify).getMachineId()));
       } else if (partModify instanceof Outsourced) {
           outsourcedRadioButton.setSelected(true);
           switchLabel.setText("The Company Name");
          modifyPartSwitchField.setText(String.valueOf(((InHouse) partModify).getMachineId()));
       } else {
          JOptionPane.showMessageDialog(null,"Error, field must contain a value!"); 
       }
       modifyPartInventoryField.setText(String.valueOf(partModify.getStock()));
       modifyPartIDField.setText(String.valueOf(partModify.getId()));
      modifyPartMinField.setText(String.valueOf(partModify.getMin())); 
       modifyPartMaxField.setText(String.valueOf(partModify.getMax()));
       modifyPartNameField.setText(partModify.getName());
       modifyPartPriceField.setText(String.valueOf(partModify.getPrice()));


    }

    /** Detects whether In house or outsource is selected
     *
     * @param event The event that is happening in the method (
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */

    @FXML
    void onActionGetSource(ActionEvent event) throws IOException
    {

        if (this.sourceTG.getSelectedToggle().equals(this.inHouseRadioButton))
            switchLabel.setText("Machine ID:");
        else
            switchLabel.setText("Company Name:");

    }

    /** Directs back to main screen if cancel is pressed
     *
     * @param event The event that is happening in the method (cancelling the modify part form).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionCancelModify(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?(Latest Changes may not be saved)");
       
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }

    }


    /** Validates the field before saving and returning to main screen.
     *
     * @param event The event that is happening in the method (modifying a part).
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException
    {

 try {
                int id = partModify.getId();;
                String name = modifyPartNameField.getText();
                double price = Double.parseDouble(modifyPartPriceField.getText());
                int stock = Integer.parseInt(modifyPartInventoryField.getText());
                int min = Integer.parseInt(modifyPartMinField.getText());
                int max = Integer.parseInt(modifyPartMaxField.getText());
                int machineId;

                boolean partExists = false;
                
                if(minMaxValidation(min, max) && inventoryValidation(min, max, stock)) {

                        if (inHouseRadioButton.isSelected()) {
                            try {
                        

                            machineId = Integer.parseInt(modifyPartSwitchField.getText());
                    InHouse newInHousePart = new InHouse(id, name, price, stock, min, max,  machineId);
                        Inventory.addPart(newInHousePart);
                     
                        partExists = true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error, Invalid Machine ID value");
                }
                
        }   if(outsourcedRadioButton.isSelected()) {
             String companyName = modifyPartSwitchField.getText();
             Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                 Inventory.addPart(newOutsourcedPart);
               
                            partExists = true;
        }  if(partExists) {
            Inventory.deletePart(partModify);
             returnMainWindow(event);
        } 
                }
 }  catch(Exception e) {
           
          JOptionPane.showMessageDialog(null,"Error, Some Fields are blank!");
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
       Part part;

    /**Populates the  ModifyPart and removes the error when selecting the modify.
     *
     * @param part The part that is being sent.
     */
    public void sendPart(Part part)
    {
        this.part = part;

        modifyPartIDField.setText(Integer.toString(part.getId()));
        modifyPartNameField.setText(part.getName());
        modifyPartInventoryField.setText(Integer.toString(part.getStock()));
        modifyPartPriceField.setText(Double.toString(part.getPrice()));
        modifyPartMaxField.setText(Integer.toString(part.getMax()));
        modifyPartMinField.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse)
        {
            inHouseRadioButton.setSelected(true);
            modifyPartSwitchField.setText(Integer.toString(((InHouse) part).getMachineId()));
            switchLabel.setText("Machine ID:");
        }
        else
        {
            outsourcedRadioButton.setSelected(true);
            modifyPartSwitchField.setText(((Outsourced) part).getCompanyName());
            switchLabel.setText("Company Name:");
        }


    }
}

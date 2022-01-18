package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FUTURE ENHANCEMENT: If I was given the chance to continue working on this 
 * project, one possible enhancement that comes to mind and I would do is
 * the customizable aspect of parts and products. This will include the 
 * customizable attributes, which will also include the ability of the user to 
 * create new text field and add different values, and this will enhance
 * the user experience.
 * 
 * 
 * @author Lois Vernon Pua
 */
public class Main extends Application {


    /** This method sets up the initial JavaFX application stage.
     *
     * @param primaryStage The primary stage to be set.
     * @throws Exception The exception thrown.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1000, 380));
        primaryStage.show();
    }

    /** 
     * The javadocs folder is located inside the Project primary folder.
     *
     * @param args The arguments.
     */
    public static void main(String[] args)
    {



        launch(args);
    }
}

    /**
     
     * <p><b>
     *   Rubric section B.1(Run-time ERROR):
     * </b></p>
     *
     * I was getting an error when pressing the modify for both parts and products. Also an minor error where strings are passing on inventory field values on both parts and products.
     * Another error was correct Min value was not passing and showing an error pop-up message on both parts and products screen.
     *
     * <p>
     * To Fix the errors: first, I noticed that my path on the ".getResource()" was wrong "pathing". Instead of ModifyPartForm.fxml, I was doing ModfiyPartScreen.fxml, same with the product. 
     * To fix the error on correct min values, I removed the min" <= 0" equal sign and just did min < 0. This helped fix the problem and when putting "0" as min, both parts and products was 
     * succesfully added.
     * </p>
     */
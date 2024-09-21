import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Use this template to create Apps with Graphical User Interfaces.
 *
 * @author Meetkumar Prajapati (000922812)
 * @version v100
 */
public class TicketScanningApp extends Application {

    // TODO: Instance Variables for View Components and Model
    private Table table;
    private Label label;
    private Label result;
    private Label result2;
    private TextField inputBox;
    private Button redeemButton;

    // TODO: Private Event Handlers and Helper Methods

    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception
     */


    public void start(Stage stage) throws Exception {

        table = new Table("codes.txt");
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 250); // set the size here
        stage.setTitle("FX GUI Template"); // set the window title here
        stage.setScene(scene);
        // TODO: Add your GUI-building code here
        scene.getStylesheets().add("myStyle.css");

        // 1. Create the model

        // 2. Create the GUI components
        label = new Label("Ticket Scanning App");
        redeemButton = new Button("Redeem");
        inputBox = new TextField("PNC97");
        result = new Label("Valid/Invalid");
        result2 = new Label("Name");

        // 3. Add components to the root
        root.getChildren().addAll(label,result,result2,redeemButton, inputBox);

        // 4. Configure the components (colors, fonts, size, location)
        label.relocate(30, 20);
        label.setFont(new Font(35));

        result.relocate(30, 170);
        result.setFont(new Font(6));

        result2.relocate(30, 195);
        result2.setFont(new Font(6));

        redeemButton.relocate(235, 90);
        redeemButton.setMinWidth(135);
        redeemButton.setOnAction(this::handleScanButton);

        inputBox.relocate(70, 90);
        inputBox.setFont(new Font("System", 20));
        inputBox.setId("inputBox");
        inputBox.setStyle("-fx-min-width: 55;-fx-min-height: 55;");
        inputBox.setPrefWidth(130);

        // 5. Add Event Handlers and do final setup

        // 6. Show the stage
        stage.show();

        /*
          Make no changes here.

          @param args unused
         */

    }

    private void handleScanButton(ActionEvent event) {
        String input = inputBox.getText();

        if (input.equalsIgnoreCase("reset")) {
            ResetTickets();
            result.setText("Your file has been reseted.");
            result2.setText("");
            inputBox.clear();
        }
        else if (input.equalsIgnoreCase("save")) {
            table.updateFile();
            result.setText("Your file has been saved.");
            result2.setText("");
            inputBox.clear();
        }
        else {
            doRedeem(input);
        }
    }
    private void doRedeem(String barcode) {

        if (!barcode.isEmpty()) {

            int index = table.lookup(barcode);

            if (index != -1) {
                String purchased = table.getSingleCellValue(index, 1);
                String entered = table.getSingleCellValue(index, 2);
                String ticketType = table.getSingleCellValue(index, 3);

                if (purchased.equalsIgnoreCase("Y")) {

                    if (entered.equalsIgnoreCase("N")) {
                        // Ticket is valid and not entered yet
                        result.setText("Valid ticket! Access granted.");
                        result2.setText("Ticket Type: " + ticketType);
                        table.setSingleCellValue(index,2,"Y");
                    }
                    else {
                        // Ticket is valid but already entered
                        result.setText("Duplicate ticket! This ticket has already been scanned.");
                        result2.setText("");
                    }
                }
                else {
                    // Ticket is not purchased
                    result.setText("Invalid ticket! This ticket has not been purchased.");
                    result2.setText("");
                }
            }
            else {
                // Barcode not found in the file
                result.setText("Invalid ticket! Barcode not found.");
                result2.setText("");
            }
        } else {
            // Empty barcode input
            result.setText("Please enter a ticket barcode.");
            result2.setText("");
        }

        // Clear the barcode input after processing
        inputBox.clear();
    }



    public void ResetTickets(){
        for (int i = 0; i < table.getNumRows(); i++){
            table.setSingleCellValue(i,2,"N");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

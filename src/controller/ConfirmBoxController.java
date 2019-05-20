/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ConfirmBoxController implements Initializable {
    //class attr
    private String mTitle;
    private String mMessage;
    private static boolean state = false;
    
    @FXML
    private Label title;

    @FXML
    private Label exitBarBtn;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private Label mainText;
    
    
    
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean getCurrentState(){
        return state;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        exitBarBtn.setOnMousePressed(e -> {
            ((Stage)exitBarBtn.getScene().getWindow()).close();
        });
        
        cancelBtn.setOnAction(e -> {
            state = false;
            ((Stage)cancelBtn.getScene().getWindow()).close();
        });
        
        confirmBtn.setOnAction(e -> {
           state = true;
          ((Stage)cancelBtn.getScene().getWindow()).close();
        }); 
        
        
        Platform.runLater(() -> {
            title.setText(mTitle);
            mainText.setText(mMessage);
        });
    }    
    
}

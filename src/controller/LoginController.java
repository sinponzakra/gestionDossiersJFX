/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.EffectUtilities;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtPseudo;

    @FXML
    private Hyperlink resetMdp;

    @FXML
    private Label pseudoError;

    @FXML
    private Label mdpError;

    @FXML
    private PasswordField txtMdp;

    @FXML
    private JFXButton login;

    @FXML
    private ImageView exitBarBtn;
    
    @FXML
    private ImageView minBarBtn;
    
    @FXML
    private HBox mTopBar;
    
    
    private void LoadingTransition() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/LoadingView.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(this.getClass().getResource("/images/icone.png").toString()));
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            ((Stage) login.getScene().getWindow()).close();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login.setOnAction(e -> {
            if(txtPseudo.getText().equals("admin")){
                if(txtMdp.getText().equals("admin")){
                    LoadingTransition();
                }else{
                    mdpError.setVisible(true);
                    mdpError.requestFocus();
                }
            }else{
                pseudoError.setVisible(true);
                pseudoError.requestFocus();
            }
        });
        
         Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage mStage = ((Stage) ((Node) mTopBar).getScene().getWindow());
                EffectUtilities.makeDraggable(mStage, mTopBar);
            }
        });
        
        exitBarBtn.setOnMousePressed(e -> {
            System.exit(0);
        });

        minBarBtn.setOnMousePressed(e -> {
            ((Stage) ((Node) e.getSource()).getScene().getWindow()).setIconified(true);
        });
        
        txtPseudo.textProperty().addListener(e -> {
            pseudoError.setVisible(false);
        });

        txtMdp.textProperty().addListener(e -> {
            mdpError.setVisible(false);
        });
    }

}

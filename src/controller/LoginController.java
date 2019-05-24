/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Authentification;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import service.AuthentificationService;
import util.EffectUtilities;
import util.Util;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class LoginController implements Initializable {
    AuthentificationService as = new AuthentificationService();
    Util util = new Util();
    private static Authentification at = null;
    
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
    
    @FXML
    private JFXCheckBox rememberMe;
    
    
    
    
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
    
    @FXML
    private void onEnter(ActionEvent ae) {
            String mUsername = txtPseudo.getText();
            String mPassword = txtMdp.getText();

            at = as.CheckLogin(mUsername);
            if (at == null) {
                pseudoError.setVisible(true);
                txtPseudo.requestFocus();
            } else {
                if (util.MD5(mPassword).equals(at.getPassword())) {
                    LoadingTransition();
                    saveUserConfig();
                } else {
                    mdpError.setVisible(true);
                    mdpError.requestFocus();
                }
            }

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        
        resetMdp.setOnAction(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/ResetPasswordView.fxml"));
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.getIcons().add(new Image(this.getClass().getResource("/images/icone.png").toString()));
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
     private void saveUserConfig() {
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.putInt("currentUserId", at.getId());
        
        if (rememberMe.isSelected()) {
            userPreferences.putBoolean("rememberMe", true);
        } else {
            userPreferences.putBoolean("rememberMe", false);
        }

    }

}

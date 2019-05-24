/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Authentification;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.MessagingException;
import service.AuthentificationService;
import util.EffectUtilities;
import util.Util;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ResetPasswordController implements Initializable {

    Util util = new Util();
    AuthentificationService ats = new AuthentificationService();
    Authentification authentification = null;

    @FXML
    private HBox mTopBar;

    @FXML
    private ImageView minBarBtn;

    @FXML
    private ImageView exitBarBtn;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label EmailError;

    @FXML
    private JFXButton send;

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
    
    public void verifyCode(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/ActivationCodeView.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(this.getClass().getResource("/images/icone.png").toString()));
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            ((Stage) send.getScene().getWindow()).close();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveGeneratedCode(String code, Authentification a){
        Preferences p = Preferences.userRoot();
        p.put("code", code);
        p.putInt("userID", a.getId());
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
            exitBarBtn.getScene().getWindow().hide();
        });

        minBarBtn.setOnMousePressed(e -> {
            ((Stage) ((Node) e.getSource()).getScene().getWindow()).setIconified(true);
        });

        send.setOnAction(e -> {
            authentification = ats.CheckEmail(txtEmail.getText());

            if (authentification == null) {
                EmailError.setVisible(true);
                txtEmail.requestFocus();
            } else {
                String generatedC = getRandomNumberString();      
                try {
                    util.sendMail(txtEmail.getText(), generatedC);
                    verifyCode();
                    saveGeneratedCode(generatedC, authentification);
                } catch (MessagingException ex) {
                    Logger.getLogger(ResetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}

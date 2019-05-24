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
import util.EffectUtilities;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ActivationCodeController implements Initializable {

    @FXML
    private HBox mTopBar;

    @FXML
    private ImageView minBarBtn;

    @FXML
    private ImageView exitBarBtn;

    @FXML
    private TextField txtCode;

    @FXML
    private Label pseudoError;

    @FXML
    private JFXButton verifyBtn;

    public void resetPass() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/SavepassView.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(this.getClass().getResource("/images/icone.png").toString()));
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            ((Stage) verifyBtn.getScene().getWindow()).close();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
            exitBarBtn.getScene().getWindow().hide();
        });

        minBarBtn.setOnMousePressed(e -> {
            ((Stage) ((Node) e.getSource()).getScene().getWindow()).setIconified(true);
        });

        verifyBtn.setOnAction(e -> {
            Preferences p = Preferences.userRoot();
            String code = p.get("code", "nothing");

            if (code.equalsIgnoreCase(txtCode.getText())) {
                resetPass();
            }
        });
    }

}

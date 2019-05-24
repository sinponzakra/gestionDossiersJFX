/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Authentification;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import service.AuthentificationService;
import util.EffectUtilities;
import util.Util;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class SavepassController implements Initializable {

    Util util = new Util();
    AuthentificationService ats = new AuthentificationService();

    @FXML
    private HBox mTopBar;

    @FXML
    private ImageView minBarBtn;

    @FXML
    private ImageView exitBarBtn;

    @FXML
    private PasswordField txtMdp2;

    @FXML
    private JFXButton valider;

    @FXML
    private Label mdpError;

    @FXML
    private PasswordField txtMdp1;

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

        valider.setOnAction(e -> {
            if (txtMdp1.getText().equalsIgnoreCase(txtMdp2.getText())) {
                Preferences p = Preferences.userRoot();
                Authentification a = ats.findById(p.getInt("userID", 0));
                a.setPassword(util.MD5(txtMdp1.getText()));
                ats.update(a);
                valider.getScene().getWindow().hide();
            }
        });

    }

}

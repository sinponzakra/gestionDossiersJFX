/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controller.LoginController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.HibernateUtil;

/**
 *
 * @author pc
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Preferences userPreferences = Preferences.userRoot();
        if (userPreferences.getBoolean("rememberMe", false)) {
            LoadingTransition();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image(this.getClass().getResource("images/icone.png").toString()));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        }

    }
    
    private void LoadingTransition() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("views/LoadingView.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(this.getClass().getResource("images/icone.png").toString()));
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
        launch(args);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AcquereurService;
import util.HibernateUtil;

/**
 *
 * @author pc
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(this.getClass().getResource("images/icone.png").toString()));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
        launch(args);

    }

}

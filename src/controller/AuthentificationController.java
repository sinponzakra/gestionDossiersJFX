/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Acquereur;
import Entites.Authentification;
import Entites.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AuthentificationService;
import util.Util;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AuthentificationController implements Initializable {

    AuthentificationService ats = new AuthentificationService();
    Util util = new Util();
    
    private static int index;

    ObservableList<Authentification> accounts = FXCollections.observableArrayList();
    ObservableList<String> profileItems = FXCollections.observableArrayList();

    @FXML
    private VBox mCenter;

    @FXML
    private JFXTextField nomUtilisateur;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField mdp;

    @FXML
    private JFXComboBox<String> profile;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TableView<Authentification> mTable;

    @FXML
    private TableColumn<Authentification, ?> nomUtilisateurColumn;

    @FXML
    private TableColumn<Authentification, ?> emailColumn;

    @FXML
    private TableColumn<Authentification, ?> profileColumn;

    @FXML
    void clearAction(ActionEvent event) {
        nomUtilisateur.clear();
        mdp.clear();
        email.clear();
        profile.getSelectionModel().select(null);

    }

    @FXML
    void deleteAction(ActionEvent event) {
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL.APPLICATION_MODAL);
            window.initStyle(StageStyle.DECORATED.UNDECORATED);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ConfirmBoxView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ConfirmBoxController controller = fxmlLoader.<ConfirmBoxController>getController();
            controller.setmMessage("Etes-vous sûr de vouloir supprimer cet élément?");
            controller.setmTitle("confirmation de suppression");

            Scene scene = new Scene(root);

            window.setScene(scene);
            window.showAndWait();

            if (controller.getCurrentState()) {
                ats.delete(ats.findById(index));
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(LebienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void saveAction(ActionEvent event) {
        ats.create(new Authentification(nomUtilisateur.getText(), util.MD5(mdp.getText()), profile.getSelectionModel().getSelectedItem(), email.getText()));
        init();

    }

    @FXML
    void updateAction(ActionEvent event) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL.APPLICATION_MODAL);
        window.initStyle(StageStyle.DECORATED.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ConfirmBoxView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ConfirmBoxController controller = fxmlLoader.<ConfirmBoxController>getController();
        controller.setmMessage("Etes-vous sûr de vouloir modifier cet élément?");
        controller.setmTitle("confirmation de modification");

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.showAndWait();

        if (controller.getCurrentState()) {

         Authentification a = ats.findById(index);
            a.setUser(nomUtilisateur.getText());
            a.setEmail(email.getText());
            a.setPassword(util.MD5(mdp.getText()));
            a.setProfile(profile.getSelectionModel().getSelectedItem());

            ats.update(a);
            init();
        }


    }

    /**
     * Initializes the controller class.
     */
    //init function
    private void init() {

        accounts.clear();
        profileItems.clear();

        nomUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        profileColumn.setCellValueFactory(new PropertyValueFactory<>("profile"));

        profileItems.add("admin");
        profileItems.add("user");

        profile.setItems(profileItems);

        if (ats.findAll() != null) {
            accounts.addAll(ats.findAll());
        }

        mTable.setItems(accounts);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        profile.getSelectionModel().select(1);
        
        mTable.setOnMousePressed(e -> {
            if (mTable.getSelectionModel().getSelectedCells().get(0) != null) {
                TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Authentification item = mTable.getItems().get(row);
                index = item.getId();
                
                profile.getSelectionModel().select(item.getProfile());
                nomUtilisateur.setText(item.getUser());
                email.setText(item.getEmail());
            }

        });
    }

}

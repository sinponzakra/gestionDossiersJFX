/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Acquereur;
import Entites.Authentification;
import Entites.LeBien;
import Entites.LeBienPK;
import Entites.Vendeur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import java.util.prefs.Preferences;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AcquereurService;
import service.AuthentificationService;
import service.LeBienService;
import service.VendeurService;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class LebienController implements Initializable {

    //services
    VendeurService vs = new VendeurService();
    AcquereurService as = new AcquereurService();
    LeBienService bs = new LeBienService();
    AuthentificationService ats = new AuthentificationService();

    ObservableList<Acquereur> acquereurs = FXCollections.observableArrayList();
    ObservableList<Vendeur> vendeurs = FXCollections.observableArrayList();
    ObservableList<LeBien> lesbiens = FXCollections.observableArrayList();
    ObservableList<LeBien> fetchedLeBien = FXCollections.observableArrayList();

    Date dt = new Date();
    Date dt2 = new Date();

    //inner static varriable
    private static LeBienPK index;
    private static int selectedVendeurID;
    private static int selectedAcquereurID;

    @FXML
    private VBox mCenter;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXTextField consistance;

    @FXML
    private JFXTextField charge;

    @FXML
    private JFXTextField situationLocative;

    @FXML
    private JFXTextField situationSyndic;

    @FXML
    private JFXTextField avance;

    @FXML
    private JFXTextField charges;

    @FXML
    private JFXTextField tf;

    @FXML
    private JFXTextField ri;

    @FXML
    private JFXTextField rc;

    @FXML
    private JFXTextField superficie;

    @FXML
    private JFXTextField prixCession;

    @FXML
    private JFXDatePicker delaiCompromis;

    @FXML
    private JFXDatePicker dateContract;

    @FXML
    private JFXTextField observation;

    @FXML
    private JFXComboBox<Vendeur> vendeur;

    @FXML
    private JFXComboBox<Acquereur> acquereur;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TableView<LeBien> mTable;

    @FXML
    private TableColumn<LeBien, Vendeur> vendeurColumn;

    @FXML
    private TableColumn<LeBien, Acquereur> acquereurColumn;

    @FXML
    private TableColumn<LeBien, String> adresseColumn;

    @FXML
    private TableColumn<LeBien, String> superficieColumn;

    @FXML
    private TableColumn<LeBien, String> consistanceColumn;

    @FXML
    private TableColumn<LeBien, String> chargeColumn;

    @FXML
    private TableColumn<LeBien, String> situationLocativeColumn;

    @FXML
    private TableColumn<LeBien, String> situationSyndicColumn;

    @FXML
    private TableColumn<LeBien, Double> prixCessionColumn;

    @FXML
    private TableColumn<LeBien, Double> avanceColumn;

    @FXML
    private TableColumn<LeBien, String> chargesColumn;

    @FXML
    private TableColumn<LeBien, LocalDate> delaisCompromisColumn;

    @FXML
    private TableColumn<LeBien, String> tfColumn;

    @FXML
    private TableColumn<LeBien, String> riColumn;

    @FXML
    private TableColumn<LeBien, String> rcColumn;

    @FXML
    private TableColumn<LeBien, LocalDate> dateContractColumn;
    
    @FXML
    private TextField search;

    @FXML
    private void clearAction(ActionEvent e) throws IOException {

        clearFields();

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
                bs.delete(bs.findByCustomId(index));
                init();
                clearFields();
            }
        } catch (IOException ex) {
            Logger.getLogger(LebienController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void saveAction(ActionEvent event) {
        Instant instant = Instant.from(delaiCompromis.getValue().atStartOfDay(ZoneId.systemDefault()));
        Instant instant2 = Instant.from(dateContract.getValue().atStartOfDay(ZoneId.systemDefault()));

        dt = Date.from(instant);
        dt2 = Date.from(instant2);

        bs.create(new LeBien(
                new LeBienPK(dt2, 
                        selectedVendeurID,
                        selectedAcquereurID),
                tf.getText(),
                ri.getText(),
                rc.getText(),
                adresse.getText(),
                superficie.getText(),
                consistance.getText(),
                charge.getText(),
                situationLocative.getText(),
                Double.parseDouble(prixCession.getText()),
                situationSyndic.getText(),
                charges.getText(),
                Double.parseDouble(avance.getText()),
                dt,
                vs.findById(selectedVendeurID),
                as.findById(selectedAcquereurID)
        ));

        init();
        clearFields();

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
            Instant instant = Instant.from(delaiCompromis.getValue().atStartOfDay(ZoneId.systemDefault()));
            Instant instant2 = Instant.from(dateContract.getValue().atStartOfDay(ZoneId.systemDefault()));

            dt = Date.from(instant);
            dt2 = Date.from(instant2);
            
            
            LeBien b = new LeBien();
            
            b.setAcquereur(as.findById(selectedAcquereurID));
            b.setVendeur(vs.findById(selectedVendeurID));
            b.setAdresse(adresse.getText());
            b.setSuperficie(superficie.getText());
            b.setConsistance(consistance.getText());
            b.setCharge(charge.getText());
            b.setSituationSyndic(situationSyndic.getText());
            b.setSituationLocative(situationLocative.getText());
            b.setPrixCession(Double.parseDouble(prixCession.getText()));
            b.setAvance(Double.parseDouble(avance.getText()));
            b.setChargesEtTaxes(charges.getText());
            b.setDelaiDuCompromisDeVente(dt);
            b.setTf(tf.getText());
            b.setRc(rc.getText());
            b.setRi(ri.getText());
            b.setId(new LeBienPK(dt2, selectedVendeurID, selectedAcquereurID));
            
            
            bs.delete(bs.findByCustomId(index));
            bs.create(b);
            init();
            clearFields();
        }
    }

    //init function
    private void init() {
        
        acquereurs.clear();
        vendeurs.clear();
        lesbiens.clear();

        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeur"));
        acquereurColumn.setCellValueFactory(new PropertyValueFactory<>("acquereur"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        superficieColumn.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        consistanceColumn.setCellValueFactory(new PropertyValueFactory<>("consistance"));
        situationLocativeColumn.setCellValueFactory(new PropertyValueFactory<>("situationLocative"));
        situationSyndicColumn.setCellValueFactory(new PropertyValueFactory<>("situationSyndic"));
        prixCessionColumn.setCellValueFactory(new PropertyValueFactory<>("prixCession"));
        avanceColumn.setCellValueFactory(new PropertyValueFactory<>("avance"));
        chargesColumn.setCellValueFactory(new PropertyValueFactory<>("chargesEtTaxes"));
        chargeColumn.setCellValueFactory(new PropertyValueFactory<>("charge"));
        delaisCompromisColumn.setCellValueFactory(new PropertyValueFactory<>("delaiDuCompromisDeVente"));
        tfColumn.setCellValueFactory(new PropertyValueFactory<>("tf"));
        riColumn.setCellValueFactory(new PropertyValueFactory<>("ri"));
        rcColumn.setCellValueFactory(new PropertyValueFactory<>("rc"));
        dateContractColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        if (vs.findAll() != null) {
            vendeurs.addAll(vs.findAll());
        }

        if (as.findAll() != null) {
            acquereurs.addAll(as.findAll());
        }

        if (bs.findAll() != null) {
            lesbiens.addAll(bs.findAll());
        }

        vendeur.setItems(vendeurs);
        acquereur.setItems(acquereurs);
        mTable.setItems(lesbiens);
    }

    //clear Fields function
    private void clearFields() {
        tf.clear();
        ri.clear();
        rc.clear();
        adresse.clear();
        superficie.clear();
        consistance.clear();
        charge.clear();
        situationLocative.clear();
        prixCession.clear();
        situationSyndic.clear();
        charges.clear();
        avance.clear();
        delaiCompromis.getEditor().clear();
        delaiCompromis.setValue(null);
        dateContract.getEditor().clear();
        dateContract.setValue(null);
        vendeur.getSelectionModel().select(null);
        acquereur.getSelectionModel().select(null);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            fetchedLeBien.clear();
            
            if(!newValue.equals("")){
                for(LeBien a : bs.findAll()) {
                    if(a.toString().contains(newValue)) {
                        fetchedLeBien.add(a);
                    }
                }
                mTable.setItems(fetchedLeBien);
            }else{
                init();
            }
        });
        
        vendeur.setOnAction(e -> {
            try {
                selectedVendeurID = vendeur.getSelectionModel().getSelectedItem().getId();
            } catch (Exception ex) {
            }
        });
        
        acquereur.setOnAction(e -> {
            try {
                selectedAcquereurID = acquereur.getSelectionModel().getSelectedItem().getId();
            } catch (Exception ex) {
                
            }
        });

        mTable.setOnMousePressed(e -> {
            if (mTable.getSelectionModel().getSelectedCells().get(0) != null) {
                TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                LeBien item = mTable.getItems().get(row);
                index = item.getId();

                vendeur.getSelectionModel().select(item.getVendeur());
                acquereur.getSelectionModel().select(item.getAcquereur());
                
                selectedVendeurID = item.getVendeur().getId();
                selectedAcquereurID = item.getAcquereur().getId();
                
                adresse.setText(item.getAdresse());
                superficie.setText(item.getSuperficie());
                consistance.setText(item.getConsistance());
                charge.setText(item.getCharge());
                situationLocative.setText(item.getSituationLocative());
                situationSyndic.setText(item.getSituationSyndic());
                prixCession.setText(item.getPrixCession().toString());
                avance.setText(item.getAvance().toString());
                charges.setText(item.getChargesEtTaxes());

                Date dts = item.getDelaiDuCompromisDeVente();
                Date dts2 = item.getId().getDateContrat();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(sdf.format(dts), formatter);
                LocalDate localDate2 = LocalDate.parse(sdf.format(dts2), formatter);

                delaiCompromis.setValue(localDate);
                dateContract.setValue(localDate2);

                tf.setText(item.getTf());
                ri.setText(item.getRi());
                rc.setText(item.getRc());

            }
        });
        
        Preferences userPreferences = Preferences.userRoot();
        int currentUserId = userPreferences.getInt("currentUserId", 0);

        Authentification currentAuthentification = ats.findById(currentUserId);
        
        if (!currentAuthentification.getProfile().equalsIgnoreCase("admin")){
            btnAdd.setVisible(false);
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
            btnClear.setVisible(false);
        }

    }
}

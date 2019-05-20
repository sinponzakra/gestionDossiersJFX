/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Acquereur;
import Entites.Dossier;
import Entites.LeBien;
import Entites.LeBienPK;
import Entites.Vendeur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AcquereurService;
import service.DossierService;
import service.LeBienService;
import service.VendeurService;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class DossierController implements Initializable {

    //services
    VendeurService vs = new VendeurService();
    AcquereurService as = new AcquereurService();
    LeBienService bs = new LeBienService();
    DossierService ds = new DossierService();

    ObservableList<Acquereur> acquereurs = FXCollections.observableArrayList();
    ObservableList<Vendeur> vendeurs = FXCollections.observableArrayList();
    ObservableList<LeBien> lesbiens = FXCollections.observableArrayList();
    ObservableList<Dossier> dossiers = FXCollections.observableArrayList();
    ObservableList<Dossier> fetchedDossier = FXCollections.observableArrayList();

    Date dt = new Date();

    //inner static variable
    private static int index;
    private static int selectedVendeurID;
    private static int selectedAcquereurID;
    private static LeBienPK selectedLebienID;

    @FXML
    private VBox mCenter;

    @FXML
    private JFXComboBox<Vendeur> vendeur;

    @FXML
    private JFXComboBox<Acquereur> acquereur;

    @FXML
    private JFXDatePicker dateContract;

    @FXML
    private JFXComboBox<LeBien> lebien;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<Dossier> mTable;

    @FXML
    private TableColumn<Dossier, Vendeur> vendeurColumn;

    @FXML
    private TableColumn<Dossier, Acquereur> acquereurColumn;

    @FXML
    private TableColumn<Dossier, LeBien> LebienColumn;

    @FXML
    private TableColumn<Dossier, LocalDate> dateContractColumn;

    @FXML
    private TableColumn<Dossier, String> EtatColumn;

    @FXML
    private TextField search;

    @FXML
    private JFXToggleButton etat;

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
                ds.delete(ds.findById(index));
                init();
            }
        } catch (IOException ex) {
            Logger.getLogger(LebienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void saveAction(ActionEvent event) {
        Instant instant = Instant.from(dateContract.getValue().atStartOfDay(ZoneId.systemDefault()));

        dt = Date.from(instant);

        String etat_Stat = "Inactive";
        if (etat.isSelected()) {
            etat_Stat = "Active";
        }

        ds.create(new Dossier(as.findById(selectedAcquereurID),
                bs.findByCustomId(selectedLebienID),
                vs.findById(selectedVendeurID), dt, etat_Stat));

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
            Instant instant = Instant.from(dateContract.getValue().atStartOfDay(ZoneId.systemDefault()));
            dt = Date.from(instant);

            String etat_Stat = "Inactive";
            if (etat.isSelected()) {
                etat_Stat = "Active";
            }

            Dossier d = ds.findById(index);
            d.setAcquereur(as.findById(selectedAcquereurID));
            d.setVendeur(vs.findById(selectedVendeurID));
            d.setLebien(bs.findByCustomId(selectedLebienID));
            d.setDate(dt);
            d.setEtat(etat_Stat);

            ds.update(d);
            init();
        }

    }

    //init function
    private void init() {

        acquereurs.clear();
        vendeurs.clear();
        lesbiens.clear();
        dossiers.clear();

        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeur"));
        acquereurColumn.setCellValueFactory(new PropertyValueFactory<>("acquereur"));
        LebienColumn.setCellValueFactory(new PropertyValueFactory<>("lebien"));
        dateContractColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        EtatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));

        if (vs.findAll() != null) {
            vendeurs.addAll(vs.findAll());
        }

        if (as.findAll() != null) {
            acquereurs.addAll(as.findAll());
        }

        if (bs.findAll() != null) {
            lesbiens.addAll(bs.findAll());
        }

        if (ds.findAll() != null) {
            dossiers.addAll(ds.findAll());
        }

        vendeur.setItems(vendeurs);
        acquereur.setItems(acquereurs);
        lebien.setItems(lesbiens);
        mTable.setItems(dossiers);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        mTable.setRowFactory(tv -> new TableRow<Dossier>() {
            @Override
            public void updateItem(Dossier item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getEtat().equals("Active")) {
                    setStyle("-fx-background-color: #a7ff7f;");
                } else if (item.getEtat().equals("Inactive")) {
                    setStyle("-fx-background-color: #ff7a7a;");
                } else {
                    setStyle("");
                }
            }
        });

        etat.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (etat.isSelected()) {
                    etat.setText("Active");
                } else if (!etat.isSelected()) {
                    etat.setText("Inactive");
                }
            }

        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            fetchedDossier.clear();

            if (!newValue.equals("")) {
                for (Dossier a : ds.findAll()) {
                    if (a.getVendeur().toString().contains(newValue)
                            || a.getAcquereur().toString().contains(newValue)
                            || a.getLebien().toString().contains(newValue)) {
                        fetchedDossier.add(a);
                    }
                }
                mTable.setItems(fetchedDossier);
            } else {
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

        lebien.setOnAction(e -> {
            try {
                selectedLebienID = lebien.getSelectionModel().getSelectedItem().getId();
            } catch (Exception ex) {

            }
        });

        mTable.setOnMousePressed(e -> {
            if (mTable.getSelectionModel().getSelectedCells().get(0) != null) {
                TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Dossier item = mTable.getItems().get(row);
                index = item.getId();

                vendeur.getSelectionModel().select(item.getVendeur());
                acquereur.getSelectionModel().select(item.getAcquereur());
                lebien.getSelectionModel().select(item.getLebien());

                selectedVendeurID = item.getVendeur().getId();
                selectedAcquereurID = item.getAcquereur().getId();
                selectedLebienID = item.getLebien().getId();

                Date dts = item.getDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(sdf.format(dts), formatter);

                dateContract.setValue(localDate);
                
                if(item.getEtat().equalsIgnoreCase("Active")){
                    etat.setSelected(true);
                }else if(item.getEtat().equalsIgnoreCase("Inactive")){
                    etat.setSelected(false);
                }

            }

        });

    }

}

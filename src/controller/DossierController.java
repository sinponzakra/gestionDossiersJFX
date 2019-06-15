/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Acquereur;
import Entites.Authentification;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
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
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import service.AcquereurService;
import service.AuthentificationService;
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
    AuthentificationService ats = new AuthentificationService();

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
    private TableColumn<Dossier, Integer> IdColumn;
    
    @FXML
    private TableColumn<Dossier, Void> imprimer;

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
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imprimer.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        
        
        Callback<TableColumn<Dossier, Void>, TableCell<Dossier, Void>> cellFactory = new Callback<TableColumn<Dossier, Void>, TableCell<Dossier, Void>>() {
            @Override
            public TableCell<Dossier, Void> call(final TableColumn<Dossier, Void> param) {
                final TableCell<Dossier, Void> cell = new TableCell<Dossier, Void>() {

                    private final Button btn = new Button("Imprimer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                Dossier data = getTableView().getItems().get(getIndex());
                                
                                //report aquerreur
                                JasperReport jr = JasperCompileManager.compileReport("src/reports/report.jrxml");
                                Map<String, Object> parameters = new HashMap<>();
                                parameters.put("typePersonne", data.getAcquereur().getTypePersonne());
                                parameters.put("nomPrenom", data.getAcquereur().toString());
                                parameters.put("adresse", data.getAcquereur().getAdresse());
                                parameters.put("nationalite", "Marocain");
                                parameters.put("cin", data.getAcquereur().getCin());
                                parameters.put("dateNaissance", data.getAcquereur().getDateNaissance().toString());
                                parameters.put("associe", data.getAcquereur().getAssocie());
                                parameters.put("adresseCourriel", data.getAcquereur().getAdresseCourriel());
                                parameters.put("email", data.getAcquereur().getEmail());
                                parameters.put("tel", data.getAcquereur().getTelephone());
                                parameters.put("tf", data.getLebien().getTf()+" / "+data.getLebien().getRi()+" / "+data.getLebien().getRc());
                                parameters.put("fonction", data.getAcquereur().getFonction());
                                parameters.put("adresseBien",data.getLebien().getAdresse());
                                parameters.put("superficie", data.getLebien().getSuperficie().toString()+" M²");
                                parameters.put("chargesEtTaxes", data.getLebien().getChargesEtTaxes());
                                parameters.put("sLocative", data.getLebien().getSituationLocative());
                                parameters.put("prix", data.getLebien().getPrixCession().toString()+" DH");
                                parameters.put("sSyndic",data.getLebien().getSituationSyndic());
                                parameters.put("regimeMatimonial", data.getAcquereur().getRegimeMariage());
                                parameters.put("sFamilial", data.getAcquereur().getSituationFamiliale());
                                parameters.put("consistances", data.getLebien().getConsistance());
                                
                                JRDataSource dataSource = new JREmptyDataSource();
                                JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dataSource);
                                JasperViewer.viewReport(jp, false);
                                
                                
                                
                                
                                
                                
                                //report vendeur
                                JasperReport jr2 = JasperCompileManager.compileReport("src/reports/report.jrxml");
                                Map<String, Object> parameters2 = new HashMap<>();
                                parameters2.put("typePersonne", data.getVendeur().getTypePersonne());
                                parameters2.put("nomPrenom", data.getVendeur().toString());
                                parameters2.put("adresse", data.getVendeur().getAdresse());
                                parameters2.put("nationalite", "Marocain");
                                parameters2.put("cin", data.getVendeur().getCin());
                                parameters2.put("dateNaissance", data.getVendeur().getDateNaissance().toString());
                                parameters2.put("associe", data.getVendeur().getAssocie());
                                parameters2.put("adresseCourriel", data.getVendeur().getAdresseCourriel());
                                parameters2.put("email", data.getVendeur().getEmail());
                                parameters2.put("tel", data.getVendeur().getTelephone());
                                parameters2.put("tf", data.getLebien().getTf()+" / "+data.getLebien().getRi()+" / "+data.getLebien().getRc());
                                parameters2.put("fonction", data.getVendeur().getFonction());
                                parameters2.put("adresseBien",data.getLebien().getAdresse());
                                parameters2.put("superficie", data.getLebien().getSuperficie().toString()+" M²");
                                parameters2.put("chargesEtTaxes", data.getLebien().getChargesEtTaxes());
                                parameters2.put("sLocative", data.getLebien().getSituationLocative());
                                parameters2.put("prix", data.getLebien().getPrixCession().toString()+" DH");
                                parameters2.put("sSyndic",data.getLebien().getSituationSyndic());
                                parameters2.put("regimeMatimonial", data.getVendeur().getRegimeMariage());
                                parameters2.put("sFamilial", data.getVendeur().getSituationFamiliale());
                                parameters2.put("consistances", data.getLebien().getConsistance());
                                
                                JRDataSource dataSource2 = new JREmptyDataSource();
                                JasperPrint jp2 = JasperFillManager.fillReport(jr2, parameters2, dataSource2);
                                JasperViewer.viewReport(jp2, false);
                            } catch (JRException ex) {
                                Logger.getLogger(DossierController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        imprimer.setCellFactory(cellFactory);
        
        

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
        
        LocalDate maxDate = LocalDate.now();
        dateContract.setDayCellFactory(d ->
           new DateCell() {
               @Override public void updateItem(LocalDate item, boolean empty) {
                   super.updateItem(item, empty);
                   setDisable(item.isAfter(maxDate));
               }});

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
                            || a.getLebien().toString().contains(newValue)
                            || a.getId() == Integer.parseInt(newValue)) {
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
        
        Preferences userPreferences = Preferences.userRoot();
        int currentUserId = userPreferences.getInt("currentUserId", 0);

        Authentification currentAuthentification = ats.findById(currentUserId);
        
        if (!currentAuthentification.getProfile().equalsIgnoreCase("admin")){
            btnAdd.setVisible(false);
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
        }

    }

}

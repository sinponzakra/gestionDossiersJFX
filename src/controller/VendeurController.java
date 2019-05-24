/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Acquereur;
import Entites.Authentification;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import Entites.Vendeur;
import service.VendeurService;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AuthentificationService;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class VendeurController implements Initializable {

    //Service
    VendeurService vs = new VendeurService();
    AuthentificationService ats = new AuthentificationService();

    //init Lists
    ObservableList<Vendeur> vendeurs = FXCollections.observableArrayList();
    ObservableList<Vendeur> fetchedVendeur = FXCollections.observableArrayList();

    Date dt = new Date();

    //inner static varriable
    private static int index;

    //Fields   
    @FXML
    private VBox mCenter;

    @FXML
    private JFXTextField cin;

    @FXML
    private JFXTextField tel;

    @FXML
    private JFXTextField prenom;

    @FXML
    private JFXTextField ben;

    @FXML
    private JFXTextField regimeMariage;

    @FXML
    private JFXTextField lieuMariage;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXDatePicker dateNaissance;

    @FXML
    private JFXTextField fonction;

    @FXML
    private JFXTextField adresse;

    @FXML
    private RadioButton situationF_veuf;

    @FXML
    private ToggleGroup mGroup;

    @FXML
    private RadioButton situationF_celibataire;

    @FXML
    private RadioButton situationF_marie;

    @FXML
    private JFXTextField conjoint;

    @FXML
    private JFXTextField adresseCourriel;

    @FXML
    private JFXTextField email;

    @FXML
    private RadioButton typeP_physique;

    @FXML
    private ToggleGroup ms;

    @FXML
    private RadioButton typeP_morale;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;
    
    @FXML
    private JFXButton btnClear;

    @FXML
    private TableView<Vendeur> mTable;

    @FXML
    private TableColumn<Vendeur, String> nomColumn;

    @FXML
    private TableColumn<Vendeur, String> prenomColumn;

    @FXML
    private TableColumn<Vendeur, String> benColumn;

    @FXML
    private TableColumn<Vendeur, LocalDate> dateNaissanceColumn;

    @FXML
    private TableColumn<Vendeur, String> cinColumn;

    @FXML
    private TableColumn<Vendeur, String> telColumn;

    @FXML
    private TableColumn<Vendeur, String> fonctionColumn;

    @FXML
    private TableColumn<Vendeur, String> adresseColumn;

    @FXML
    private TableColumn<Vendeur, String> situationFColumn;

    @FXML
    private TableColumn<Vendeur, String> regimeMColumn;

    @FXML
    private TableColumn<Vendeur, String> lieuMColumn;

    @FXML
    private TableColumn<Vendeur, String> conjointColumn;

    @FXML
    private TableColumn<Vendeur, String> adresseCourrielColumn;

    @FXML
    private TableColumn<Vendeur, String> emailColumn;

    @FXML
    private TableColumn<Vendeur, String> typePersonneColumn;
    
    @FXML
    private TextField search;

    //FXL Methods
    @FXML
    private void saveAction(ActionEvent e) {
        Instant instant = Instant.from(dateNaissance.getValue().atStartOfDay(ZoneId.systemDefault()));

        dt = Date.from(instant);

        //get Text from radioButton
        String situationFamiliale = "--------";
        if (situationF_celibataire.isSelected()) {
            situationFamiliale = situationF_celibataire.getText();
        } else if (situationF_marie.isSelected()) {
            situationFamiliale = situationF_marie.getText();
        } else if (situationF_veuf.isSelected()) {
            situationFamiliale = situationF_veuf.getText();
        }

        String typePersonne = "--------";
        if (typeP_morale.isSelected()) {
            typePersonne = typeP_morale.getText();
        } else if (typeP_physique.isSelected()) {
            typePersonne = typeP_physique.getText();
        }

        vs.create(new Vendeur(
                nom.getText(),
                prenom.getText(),
                ben.getText(),
                dt,
                cin.getText(),
                tel.getText(),
                fonction.getText(),
                adresse.getText(),
                situationFamiliale,
                regimeMariage.getText(),
                lieuMariage.getText(),
                conjoint.getText(),
                adresseCourriel.getText(),
                email.getText(),
                typePersonne));
        init();
        clearFields();
    }

    @FXML
    private void deleteAction(ActionEvent e) throws IOException {

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
            vs.delete(vs.findById(index));
            init();
            clearFields();
        }
    }

    @FXML
    private void updateAction(ActionEvent e) throws IOException {
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
            Instant instant = Instant.from(dateNaissance.getValue().atStartOfDay(ZoneId.systemDefault()));
            dt = Date.from(instant);

            //get Text from radioButton
            String situationFamiliale = "--------";
            if (situationF_celibataire.isSelected()) {
                situationFamiliale = situationF_celibataire.getText();
            } else if (situationF_marie.isSelected()) {
                situationFamiliale = situationF_marie.getText();
            } else if (situationF_veuf.isSelected()) {
                situationFamiliale = situationF_veuf.getText();
            }

            String typePersonne = "--------";
            if (typeP_morale.isSelected()) {
                typePersonne = typeP_morale.getText();
            } else if (typeP_physique.isSelected()) {
                typePersonne = typeP_physique.getText();
            }

            Vendeur v = vs.findById(index);
            v.setNom(nom.getText());
            v.setPrenom(prenom.getText());
            v.setBen(ben.getText());
            v.setDateNaissance(dt);
            v.setCin(cin.getText());
            v.setTelephone(tel.getText());
            v.setFonction(fonction.getText());
            v.setAdresse(adresse.getText());
            v.setSituationFamiliale(situationFamiliale);
            v.setRegimeMariage(regimeMariage.getText());
            v.setLieuMariage(lieuMariage.getText());
            v.setAssocie(conjoint.getText());
            v.setAdresseCourriel(adresseCourriel.getText());
            v.setEmail(email.getText());
            v.setTypePersonne(typePersonne);

            vs.update(v);
            init();
            clearFields();
        }
    }

    @FXML
    private void clearAction(ActionEvent e) throws IOException {

        clearFields();

    }

    /**
     * Initializes the controller class.
     */
    private void init() {
        vendeurs.clear();

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        benColumn.setCellValueFactory(new PropertyValueFactory<>("ben"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        fonctionColumn.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        situationFColumn.setCellValueFactory(new PropertyValueFactory<>("situationFamiliale"));
        regimeMColumn.setCellValueFactory(new PropertyValueFactory<>("regimeMariage"));
        lieuMColumn.setCellValueFactory(new PropertyValueFactory<>("lieuMariage"));
        conjointColumn.setCellValueFactory(new PropertyValueFactory<>("associe"));
        adresseCourrielColumn.setCellValueFactory(new PropertyValueFactory<>("adresseCourriel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typePersonneColumn.setCellValueFactory(new PropertyValueFactory<>("typePersonne"));

        if (vs.findAll() != null) {
            vendeurs.addAll(vs.findAll());
        }

        mTable.setItems(vendeurs);

    }

    //clear Fields function
    private void clearFields() {
        nom.clear();
        prenom.clear();
        ben.clear();
        dateNaissance.getEditor().clear();
        dateNaissance.setValue(null);
        cin.clear();
        tel.clear();
        fonction.clear();
        adresse.clear();
        situationF_celibataire.setSelected(false);
        situationF_marie.setSelected(false);
        situationF_veuf.setSelected(false);
        regimeMariage.clear();
        lieuMariage.clear();
        typeP_morale.setSelected(false);
        typeP_physique.setSelected(false);
        conjoint.clear();
        adresseCourriel.clear();
        email.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            fetchedVendeur.clear();
            
            if(!newValue.equals("")){
                for(Vendeur a : vs.findAll()) {
                    if(a.toString().contains(newValue)) {
                        fetchedVendeur.add(a);
                    }
                }
                mTable.setItems(fetchedVendeur);
            }else{
                init();
            }
        });

        mTable.setOnMousePressed(e -> {
            if (mTable.getSelectionModel().getSelectedCells().get(0) != null) {
                TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Vendeur item = mTable.getItems().get(row);
                index = item.getId();

                nom.setText(item.getNom());
                prenom.setText(item.getPrenom());
                ben.setText(item.getBen());

                Date dts = item.getDateNaissance();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(sdf.format(dts), formatter);
                dateNaissance.setValue(localDate);

                cin.setText(item.getCin());
                tel.setText(item.getTelephone());
                fonction.setText(item.getFonction());
                adresse.setText(item.getAdresse());

                if (situationF_celibataire.getText().equalsIgnoreCase(item.getSituationFamiliale())) {
                    situationF_celibataire.setSelected(true);
                } else if (situationF_marie.getText().equalsIgnoreCase(item.getSituationFamiliale())) {
                    situationF_marie.setSelected(true);
                } else if (situationF_veuf.getText().equalsIgnoreCase(item.getSituationFamiliale())) {
                    situationF_veuf.setSelected(true);
                }

                regimeMariage.setText(item.getRegimeMariage());
                lieuMariage.setText(item.getLieuMariage());

                if (typeP_morale.getText().equalsIgnoreCase(item.getTypePersonne())) {
                    typeP_morale.setSelected(true);
                } else if (typeP_physique.getText().equalsIgnoreCase(item.getTypePersonne())) {
                    typeP_physique.setSelected(true);
                }

                conjoint.setText(item.getAssocie());
                adresseCourriel.setText(item.getAdresseCourriel());
                email.setText(item.getEmail());
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

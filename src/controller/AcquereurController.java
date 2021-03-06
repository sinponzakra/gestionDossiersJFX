/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Acquereur;
import Entites.Acquereur;
import Entites.Authentification;
import com.jfoenix.controls.JFXButton;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AcquereurService;
import service.AuthentificationService;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class AcquereurController implements Initializable {

    //Service
    AcquereurService as = new AcquereurService();
    AuthentificationService ats = new AuthentificationService();

    //init Lists
    ObservableList<Acquereur> acquereurs = FXCollections.observableArrayList();
    ObservableList<Acquereur> fetchedAcquerreur = FXCollections.observableArrayList();

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
    private TableView<Acquereur> mTable;

    @FXML
    private TableColumn<Acquereur, String> nomColumn;

    @FXML
    private TableColumn<Acquereur, String> prenomColumn;

    @FXML
    private TableColumn<Acquereur, String> benColumn;

    @FXML
    private TableColumn<Acquereur, LocalDate> dateNaissanceColumn;

    @FXML
    private TableColumn<Acquereur, String> cinColumn;

    @FXML
    private TableColumn<Acquereur, String> telColumn;

    @FXML
    private TableColumn<Acquereur, String> fonctionColumn;

    @FXML
    private TableColumn<Acquereur, String> adresseColumn;

    @FXML
    private TableColumn<Acquereur, String> situationFColumn;

    @FXML
    private TableColumn<Acquereur, String> regimeMColumn;

    @FXML
    private TableColumn<Acquereur, String> lieuMColumn;

    @FXML
    private TableColumn<Acquereur, String> conjointColumn;

    @FXML
    private TableColumn<Acquereur, String> adresseCourrielColumn;

    @FXML
    private TableColumn<Acquereur, String> emailColumn;

    @FXML
    private TableColumn<Acquereur, String> typePersonneColumn;
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
        if (validateEmail()) {
            as.create(new Acquereur(
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
            as.delete(as.findById(index));
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

            Acquereur v = as.findById(index);
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
            if (validateEmail()) {
                as.update(v);
                init();
                clearFields();
            }
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
        acquereurs.clear();

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

        if (as.findAll() != null) {
            acquereurs.addAll(as.findAll());
        }

        mTable.setItems(acquereurs);

    }

    private void initBySearch(List<Acquereur> list) {
        acquereurs.clear();

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

        if (list != null) {
            acquereurs.addAll(list);
        }

        mTable.setItems(acquereurs);

    }

    //email validation
    private boolean validateEmail() {
        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation email");
            alert.setHeaderText(null);
            alert.setContentText("veuillez entrer un email valide");
            alert.showAndWait();
            return false;
        }
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

        tel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tel.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });

        nom.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    nom.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });

        prenom.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    prenom.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });

        ben.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    ben.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });

        LocalDate maxDate = LocalDate.now();
        dateNaissance.setDayCellFactory(d
                -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate));
            }
        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            fetchedAcquerreur.clear();

            if (!newValue.equals("")) {
                for (Acquereur a : as.findAll()) {
                    if (a.toString().contains(newValue)) {
                        fetchedAcquerreur.add(a);
                    }
                }
                mTable.setItems(fetchedAcquerreur);
            } else {
                init();
            }
        });

        mTable.setOnMousePressed(e -> {
            if (mTable.getSelectionModel().getSelectedCells().get(0) != null) {
                TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Acquereur item = mTable.getItems().get(row);
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

        if (!currentAuthentification.getProfile().equalsIgnoreCase("admin")) {
            btnAdd.setVisible(false);
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
            btnClear.setVisible(false);
        }
    }
}

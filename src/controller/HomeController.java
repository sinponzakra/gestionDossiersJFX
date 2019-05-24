/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entites.Authentification;
import Entites.Dossier;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.notification.TrayNotification;
import service.AcquereurService;
import service.AuthentificationService;
import service.DossierService;
import service.LeBienService;
import service.VendeurService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import util.EffectUtilities;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class HomeController implements Initializable {

    AcquereurService as = new AcquereurService();
    VendeurService vs = new VendeurService();
    LeBienService bs = new LeBienService();
    DossierService ds = new DossierService();
    AuthentificationService ats = new AuthentificationService();

    @FXML
    private JFXButton btnAcceuil;

    @FXML
    private JFXButton btnDossiers;

    @FXML
    private JFXButton btnAcquereurs;

    @FXML
    private JFXButton btnVendeur;

    @FXML
    private JFXButton btnBiens;

    @FXML
    private VBox dossiersTicket;

    @FXML
    private VBox AcquereursTicket;

    @FXML
    private Text DossiersCount;

    @FXML
    private Text AcquereursCount;

    @FXML
    private VBox vendeursTicket;

    @FXML
    private Text VendeursCount;

    @FXML
    private VBox BiensTicket;

    @FXML
    private Text biensCount;

    @FXML
    private BorderPane mBorder;

    @FXML
    private VBox mCenter;

    @FXML
    private Text headerText;
    @FXML
    private ImageView exitBarBtn;
    @FXML
    private ImageView maxBarBtn;
    @FXML
    private ImageView minBarBtn;
    @FXML
    private HBox mTopBar;
    @FXML
    private JFXButton logOut;
    @FXML
    private JFXButton btnComptes;
    @FXML
    private Separator sep1;
    @FXML
    private Separator sep2;
    @FXML
    private Separator sep3;
    @FXML
    private Separator sep4;

    @FXML
    private PieChart mChart;

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    public VBox fadeAnimate(VBox v) throws IOException {
        FadeTransition ft = new FadeTransition(Duration.millis(1200));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return v;
    }

    private void setChart() {
        mChart.getData().clear();
        pieChartData.clear();

        ds.getChartData().forEach((o) -> {
            pieChartData.add(new PieChart.Data(o[0].toString(), Integer.parseInt(o[1].toString())));
        });

        mChart.getData().addAll(pieChartData);

        mChart.getData().forEach(d -> {
            Optional<Node> opTextNode = mChart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
            if (opTextNode.isPresent()) {
                Double res = ((d.getPieValue() / ds.getDossierCount()) * 100);
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(0);
                ((Text) opTextNode.get()).setText(d.getName() + " " + df.format(res) + " %");
            }
        });

    }

    private void setCountsHome() {
        AcquereursCount.setText(as.getAcquereurCount() + "");
        VendeursCount.setText(vs.getVendeurCount() + "");
        biensCount.setText(bs.getLebienCount() + "");
        DossiersCount.setText(ds.getDossierCount() + "");

    }

    private void notificationPopup() {
        String idDossier = "";

        for (Dossier d : ds.findAll()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateBeforeString = d.getDate().toString();
            String dateAfterString = sdf.format(new Date());

            //Parsing the date
            LocalDate dateBefore = LocalDate.parse(dateBeforeString);
            LocalDate dateAfter = LocalDate.parse(dateAfterString);

            System.out.println("compare date ==>" + ChronoUnit.DAYS.between(dateBefore, dateAfter) + " date pc =" + dateBefore + " | date dossier =" + dateAfter);
            Long comparable = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            if (comparable >= 20 && comparable < 30) {
                idDossier += "[" + d.getId() + "] ,";
            }
        }

        if (!idDossier.equalsIgnoreCase("")) {
            TrayNotification tray = new TrayNotification();
            tray.setNotificationType(NotificationType.CUSTOM);
            tray.setTitle("RAPPEL !");
            tray.setMessage("Plus de 20 jours se sont écoulés \n depuis l'ouverture des dossiers : " + idDossier.substring(0, idDossier.length() - 1));
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.millis(10000));
            tray.setRectangleFill(Color.RED);
            tray.setImage(new Image("/images/warning.png"));
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChart();
        setCountsHome();
        notificationPopup();

        btnAcceuil.setOnMousePressed(e -> {
            try {
                headerText.setText("Acceuil");
                mBorder.setCenter(fadeAnimate(mCenter));
                btnAcceuil.setStyle("-fx-background-color:#ff8c70");
                btnAcquereurs.setStyle(null);
                btnBiens.setStyle(null);
                btnDossiers.setStyle(null);
                btnVendeur.setStyle(null);
                btnComptes.setStyle(null);
                setCountsHome();
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnBiens.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion Des Biens");
                VBox v = FXMLLoader.load(getClass().getResource("/views/LebienView.fxml"));
                mBorder.setCenter(fadeAnimate(v));
                btnAcceuil.setStyle(null);
                btnAcquereurs.setStyle(null);
                btnBiens.setStyle("-fx-background-color:#ff8c70");
                btnDossiers.setStyle(null);
                btnVendeur.setStyle(null);
                btnComptes.setStyle(null);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnVendeur.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion Des Vendeurs");
                VBox v = FXMLLoader.load(getClass().getResource("/views/VendeurView.fxml"));
                mBorder.setCenter(fadeAnimate(v));
                btnAcceuil.setStyle(null);
                btnAcquereurs.setStyle(null);
                btnBiens.setStyle(null);
                btnDossiers.setStyle(null);
                btnVendeur.setStyle("-fx-background-color:#ff8c70");
                btnComptes.setStyle(null);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnAcquereurs.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion Des Acquereurs");
                VBox v = FXMLLoader.load(getClass().getResource("/views/AcquereurView.fxml"));
                mBorder.setCenter(fadeAnimate(v));
                btnAcceuil.setStyle(null);
                btnAcquereurs.setStyle("-fx-background-color:#ff8c70");
                btnBiens.setStyle(null);
                btnDossiers.setStyle(null);
                btnVendeur.setStyle(null);
                btnComptes.setStyle(null);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnDossiers.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion Des Dossiers");
                VBox v = FXMLLoader.load(getClass().getResource("/views/DossierView.fxml"));
                mBorder.setCenter(fadeAnimate(v));
                btnAcceuil.setStyle(null);
                btnAcquereurs.setStyle(null);
                btnBiens.setStyle(null);
                btnDossiers.setStyle("-fx-background-color:#ff8c70");
                btnVendeur.setStyle(null);
                btnComptes.setStyle(null);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnComptes.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion Des Comptes");
                VBox v = FXMLLoader.load(getClass().getResource("/views/AuthentificationView.fxml"));
                mBorder.setCenter(fadeAnimate(v));
                btnAcceuil.setStyle(null);
                btnAcquereurs.setStyle(null);
                btnBiens.setStyle(null);
                btnDossiers.setStyle(null);
                btnVendeur.setStyle(null);
                btnComptes.setStyle("-fx-background-color:#ff8c70");
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        AcquereursTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion des acquereurs");
                VBox v = FXMLLoader.load(getClass().getResource("/views/AcquereurView.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        vendeursTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion des vendeurs");
                VBox v = FXMLLoader.load(getClass().getResource("/views/VendeurView.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        BiensTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion des biens");
                VBox v = FXMLLoader.load(getClass().getResource("/views/LebienView.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        dossiersTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("Gestion des Dossiers");
                VBox v = FXMLLoader.load(getClass().getResource("/views/DossierView.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage mStage = ((Stage) ((Node) mTopBar).getScene().getWindow());
                EffectUtilities.makeDraggable(mStage, mTopBar);
            }
        });

        exitBarBtn.setOnMousePressed(e -> {
            System.exit(0);
        });

        maxBarBtn.setOnMousePressed(e -> {
            if (((Stage) ((Node) e.getSource()).getScene().getWindow()).isMaximized()) {
                ((Stage) ((Node) e.getSource()).getScene().getWindow()).setMaximized(false);
            } else {
                ((Stage) ((Node) e.getSource()).getScene().getWindow()).setMaximized(true);

            }
        });

        minBarBtn.setOnMousePressed(e -> {
            ((Stage) ((Node) e.getSource()).getScene().getWindow()).setIconified(true);
        });

        Preferences userPreferences = Preferences.userRoot();
        int currentUserId = userPreferences.getInt("currentUserId", 0);

        Authentification currentAuthentification = ats.findById(currentUserId);
        logOut.setText(currentAuthentification.getUser());

        if (!currentAuthentification.getProfile().equalsIgnoreCase("admin")) {
            sep4.setVisible(false);
            btnComptes.setVisible(false);
        }

        logOut.setOnAction(e -> {
            try {

                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.initStyle(StageStyle.UNDECORATED);
                window.getIcons().add(new Image(this.getClass().getResource("/images/icone.png").toString()));

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ConfirmBoxView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                ConfirmBoxController controller = fxmlLoader.<ConfirmBoxController>getController();
                controller.setmMessage("Voulez-vous vous déconnecter?");
                controller.setmTitle("confirmation");

                Scene scene = new Scene(root);

                window.setScene(scene);
                window.showAndWait();

                if (controller.getCurrentState()) {
                    userPreferences.remove("currentUserId");
                    userPreferences.remove("rememberMe");

                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.getIcons().add(new Image(this.getClass().getResource("/images/icone.png").toString()));
                    Parent root1 = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));

                    Scene scene1 = new Scene(root1);
                    scene1.setFill(Color.TRANSPARENT);
                    stage.setScene(scene1);
                    stage.show();

                    ((Stage) logOut.getScene().getWindow()).close();
                }

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}

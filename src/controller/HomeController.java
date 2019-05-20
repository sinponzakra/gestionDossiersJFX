/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.AcquereurService;
import service.DossierService;
import service.LeBienService;
import service.VendeurService;
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
           Double res = ((d.getPieValue() / ds.getDossierCount())*100);
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
        DossiersCount.setText(ds.getDossierCount() +"");

    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChart();
        setCountsHome();
        
        btnAcceuil.setOnMousePressed(e -> {
            try {
                headerText.setText("Acceuil");
                mBorder.setCenter(fadeAnimate(mCenter));
                btnAcceuil.setStyle("-fx-background-color:#ff8c70");
                btnAcquereurs.setStyle(null);
                btnBiens.setStyle(null);
                btnDossiers.setStyle(null);
                btnVendeur.setStyle(null);
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
        
        dossiersTicket.setOnMousePressed(e ->{
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
    }    
    
}
    
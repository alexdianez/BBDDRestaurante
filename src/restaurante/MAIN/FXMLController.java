/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.MAIN;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class FXMLController implements Initializable {
    public EntityManager entityManager;
    
    @FXML
    private Button botonEstablecimiento;
    @FXML
    private Button botonEmpleado;
    @FXML
    private AnchorPane rootPrincipal;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @FXML
    private void clickbotonEMP(MouseEvent event) {
         
        try {
        // Cargar la vista de detalle
        MainPrincipal main = new MainPrincipal();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pagEMP.fxml"));
        Parent rootEmpleado = fxmlLoader.load();  
        PagEMPController empVIEW = (PagEMPController) fxmlLoader.getController();
        
            System.out.println(empVIEW);
        
        rootPrincipal.setVisible(false);
        
         StackPane rootMain = (StackPane)rootPrincipal.getScene().getRoot();
         rootMain.getChildren().add(rootEmpleado);
         empVIEW.setEntityManager(entityManager);
         empVIEW.cargarTodasPersonas();
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    @FXML
    private void clickbotonEST(MouseEvent event) {
        
         try {
        // Cargar la vista de detalle
        MainPrincipal main = new MainPrincipal();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pagEST.fxml"));
        Parent rootEst = fxmlLoader.load(); 
        PagESTController estVIEW = (PagESTController) fxmlLoader.getController();
        
        rootPrincipal.setVisible(false);
         StackPane rootMain = (StackPane)rootPrincipal.getScene().getRoot();
         rootMain.getChildren().add(rootEst);
         estVIEW.setEntityManager(entityManager);
         estVIEW.cargarTodosLocales();
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

  

}
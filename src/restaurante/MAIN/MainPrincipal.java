/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.MAIN;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alex
 */
public class MainPrincipal extends Application {
     private EntityManagerFactory emf;
     private EntityManager em;
    @Override
    public void start(Stage primaryStage) throws IOException {
         emf = Persistence.createEntityManagerFactory("RestaurantePU");
         em = emf.createEntityManager();
        
        PagEMPController añadir = new PagEMPController();
        PagESTController añadir2= new PagESTController();
        
        StackPane rootMain = new StackPane();
       
        FXMLLoader fxmlMAIN = new FXMLLoader(getClass().getResource("FXML.fxml"));
        
        
        Pane rootPrincipal = fxmlMAIN.load();   
        rootMain.getChildren().add(rootPrincipal);
        
        FXMLController restauranteVIEW = (FXMLController) fxmlMAIN.getController(); 
        restauranteVIEW.setEntityManager(em);
        
        añadir.setEntityManager(em);
//        añadir2.setEntityManager(em);

        
         
        
        Scene scene = new Scene(rootMain, 800, 600);
        
        primaryStage.setTitle("BBDD del Restaurante");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        AñadirEstablecimiento establecimiento = new AñadirEstablecimiento();
        establecimiento.añadirEstablecimiento(em);
        
        AñadirPersonas empleados = new AñadirPersonas();
        empleados.añadirPersonas(em);
        

    }
    @Override
    public void stop() throws Exception {
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BBDDREST;shutdown=true"); 
        } catch (SQLException ex) { 
        }        
}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

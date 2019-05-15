/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.MAIN;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import restaurante.Empleado;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class PagEMPController implements Initializable {
     private Empleado personaSeleccionada;
     public EntityManager entityManager;
    @FXML
    private Button botonAtras;
     @FXML
    public AnchorPane rootEMP;
    @FXML
    private TableView<Empleado> tablaEMP;
    @FXML
    private TableColumn<Empleado, String> columNOM;
    @FXML
    private TableColumn<Empleado, String> columAPELL;
    @FXML
    private TableColumn<Empleado, BigDecimal> columSAL;
    @FXML
    private TableColumn<Empleado, String> columFUNC;
    @FXML
    private TableColumn<Empleado, String> columNAC;
    @FXML
    private TableColumn<Empleado, String> columEST;
    @FXML
    private TextField fieldNOM;
    @FXML
    private TextField fieldAPELL;
   
    @FXML
    private TextField fieldFUN;
    @FXML
    private Button botonNuevo;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateFormat  sdf = new SimpleDateFormat("dd-M-yyyy");
        
        columNOM.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columAPELL.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columSAL.setCellValueFactory(new PropertyValueFactory<>("salario"));
        columFUNC.setCellValueFactory(new PropertyValueFactory<>("funcion"));
        columNAC.setCellValueFactory(
        cellData -> {
                SimpleStringProperty property = new SimpleStringProperty();
                if (cellData.getValue().getFechaNacimiento() != null) {
                    Date date= cellData.getValue().getFechaNacimiento();
                    String strDate = sdf.format(date);  
                    property.setValue(strDate);
                }
                return property;
            });	
        columEST.setCellValueFactory (
        cellData -> {
                SimpleStringProperty property = new SimpleStringProperty();
                if (cellData.getValue().getEstablecimiento() != null) {
                    property.setValue(cellData.getValue().getEstablecimiento().getNombre());
                }
                return property;
            });	
         tablaEMP.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    personaSeleccionada = newValue;
                    if (personaSeleccionada != null) {
                        fieldNOM.setText(personaSeleccionada.getNombre());
                        fieldAPELL.setText(personaSeleccionada.getApellidos());
                        fieldFUN.setText(personaSeleccionada.getFuncion());
                    } else {
                        fieldNOM.setText("");
                        fieldAPELL.setText("");
                        fieldFUN.setText("");
                    }
});
         
    }
    
     public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
         System.out.println("Entrando...");
    }
     public void cargarTodasPersonas() {
         System.out.println(entityManager);
    Query queryPersonaFindAll = entityManager.createNamedQuery("Empleado.findAll");
    List<Empleado> listPersona = queryPersonaFindAll.getResultList();
        // System.out.println(tablaEMP);
    tablaEMP.setItems(FXCollections.observableArrayList(listPersona));
} 

    @FXML
    private void clickAtras(MouseEvent event) {
        PagEMPController añadir = new PagEMPController();
        añadir.setEntityManager(entityManager);
        try {   
        // Cargar la vista de detalle
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML.fxml"));
        Parent rootPrincipal = fxmlLoader.load(); 
        FXMLController mainVIEW = (FXMLController) fxmlLoader.getController();
        rootEMP.setVisible(false);
        
        
         StackPane rootMain = (StackPane)rootEMP.getScene().getRoot();
         rootMain.getChildren().add(rootPrincipal);
         mainVIEW.setEntityManager(entityManager);
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }     

    @FXML
    private void botonSave(ActionEvent event) {
        if (personaSeleccionada != null) {
            personaSeleccionada.setNombre(fieldNOM.getText());
            personaSeleccionada.setApellidos(fieldAPELL.getText());
            personaSeleccionada.setFuncion(fieldFUN.getText());
            entityManager.getTransaction().begin();
            entityManager.merge(personaSeleccionada);
            entityManager.getTransaction().commit();

            int numFilaSeleccionada = tablaEMP.getSelectionModel().getSelectedIndex();
            tablaEMP.getItems().set(numFilaSeleccionada, personaSeleccionada);
            TablePosition pos = new TablePosition(tablaEMP, numFilaSeleccionada, null);
            tablaEMP.getFocusModel().focus(pos);
            tablaEMP.requestFocus();
        }
}

    @FXML
    private void botonEdit(ActionEvent event) {
        if(personaSeleccionada != null) {
         try {
        // Cargar la vista de detalle
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetallePersona.fxml"));
        Parent rootPers = fxmlLoader.load();
        
        rootEMP.setVisible(false);
        
         StackPane rootMain = (StackPane)rootEMP.getScene().getRoot();
         rootMain.getChildren().add(rootPers);
         
         DetallePersonaController personaDetalleViewController = (DetallePersonaController) fxmlLoader.getController();  
        personaDetalleViewController.setRootContactosView(rootEMP);
        personaDetalleViewController.setTableViewPrevio(tablaEMP);
        
        personaDetalleViewController.setPersona(entityManager, personaSeleccionada, false);
        personaDetalleViewController.mostrarDatos();
         
       
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
            }
    }

    @FXML
    private void botonDelete(ActionEvent event) {
        if(personaSeleccionada != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Desea suprimir el siguiente registro?");
            alert.setContentText(personaSeleccionada.getNombre() + " "
                    + personaSeleccionada.getApellidos());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                entityManager.getTransaction().begin();
                entityManager.remove(personaSeleccionada);
                entityManager.getTransaction().commit();
                tablaEMP.getItems().remove(personaSeleccionada);
                tablaEMP.getFocusModel().focus(null);
                tablaEMP.requestFocus();
            } else {
                int numFilaSeleccionada = tablaEMP.getSelectionModel().getSelectedIndex();
                tablaEMP.getItems().set(numFilaSeleccionada, personaSeleccionada);
                TablePosition pos = new TablePosition(tablaEMP, numFilaSeleccionada, null);
                tablaEMP.getFocusModel().focus(pos);
                tablaEMP.requestFocus();            
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
}

    @FXML
    private void botonNuevo(ActionEvent event) {
         try {
        // Cargar la vista de detalle
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetallePersona.fxml"));
        Parent rootPers = fxmlLoader.load();
        rootEMP.setVisible(false);
        
         StackPane rootMain = (StackPane)rootEMP.getScene().getRoot();
         rootMain.getChildren().add(rootPers);
         
         DetallePersonaController personaDetalleViewController = (DetallePersonaController) fxmlLoader.getController();  
            personaDetalleViewController.setRootContactosView(rootEMP);
            personaDetalleViewController.setTableViewPrevio(tablaEMP);
            //personaDetalleViewController.setEntityManager(entityManager);
            personaSeleccionada = new Empleado();
            personaDetalleViewController.setPersona(entityManager, personaSeleccionada, true);
//            personaDetalleViewController.setNuevaPersona(true);
            personaDetalleViewController.mostrarDatos();
         
       
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    }
    



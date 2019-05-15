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
import restaurante.Establecimiento;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class PagESTController implements Initializable {
    private Establecimiento lugarSeleccionado;
    public EntityManager entityManager;
    @FXML
    private AnchorPane rootEST;
    @FXML
    private TableView<Establecimiento> tablaEST;
    @FXML
    private TableColumn<Establecimiento, String> columNOM;
    @FXML
    private TableColumn<Establecimiento, String> columDUEÑO;
    @FXML
    private TableColumn<Establecimiento, String> columFUN;
    @FXML
    private TableColumn<Establecimiento, BigDecimal> columESP;
    @FXML
    private Button botonAtras;
    @FXML
    private TextField fieldNOM;
    @FXML
    private Button botonNuevo;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonEliminar;
    @FXML
    private TextField fieldESP;
    @FXML
    private TextField fieldDueño;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DateFormat  sdf = new SimpleDateFormat("dd-M-yyyy");
         columNOM.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columDUEÑO.setCellValueFactory(new PropertyValueFactory<>("dueño"));
        columFUN.setCellValueFactory(
                 cellData -> {
                SimpleStringProperty property = new SimpleStringProperty();
                if (cellData.getValue().getFechaCreacion() != null) {
                    Date date= cellData.getValue().getFechaCreacion();
                    String strDate = sdf.format(date);  
                    property.setValue(strDate);
                }
                return property;
            });	
        
        columESP.setCellValueFactory(new PropertyValueFactory<>("espacio"));
        
         tablaEST.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    lugarSeleccionado = newValue;
                    if (lugarSeleccionado != null) {
                        fieldNOM.setText(lugarSeleccionado.getNombre());
                        fieldDueño.setText(lugarSeleccionado.getDueño());
                        fieldESP.setText(lugarSeleccionado.getEspacio().toString());
                    } else {
                        fieldNOM.setText("");
                        fieldDueño.setText("");
                        fieldESP.setText("");
                    }
});
    }    
     public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
         System.out.println("Entrando...");
    }
     public void cargarTodosLocales() {
    Query queryLocalFindAll = entityManager.createNamedQuery("Establecimiento.findAll");
    List<Establecimiento> listLocal = queryLocalFindAll.getResultList();
        
    tablaEST.setItems(FXCollections.observableArrayList(listLocal));
} 
    @FXML
    private void clickAtras(MouseEvent event) {
         PagESTController añadir2= new PagESTController();
         añadir2.setEntityManager(entityManager);
         try {
        // Cargar la vista de detalle
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML.fxml"));
        Parent rootPrincipal = fxmlLoader.load();  
        FXMLController mainVIEW = (FXMLController) fxmlLoader.getController();
        rootEST.setVisible(false);
        
         StackPane rootMain = (StackPane)rootEST.getScene().getRoot();
         rootMain.getChildren().add(rootPrincipal);
         mainVIEW.setEntityManager(entityManager);
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }     

    @FXML
    private void botonSave(ActionEvent event) {
        if (lugarSeleccionado != null) {
            lugarSeleccionado.setNombre(fieldNOM.getText());
            lugarSeleccionado.setDueño(fieldDueño.getText());
            lugarSeleccionado.setEspacio(BigDecimal.valueOf(Double.valueOf(fieldESP.getText())));
            entityManager.getTransaction().begin();
            entityManager.merge(lugarSeleccionado);
            entityManager.getTransaction().commit();

            int numFilaSeleccionada = tablaEST.getSelectionModel().getSelectedIndex();
            tablaEST.getItems().set(numFilaSeleccionada, lugarSeleccionado);
            TablePosition pos = new TablePosition(tablaEST, numFilaSeleccionada, null);
            tablaEST.getFocusModel().focus(pos);
            tablaEST.requestFocus();
        }
    }

    @FXML
    private void botonNuevo(ActionEvent event) {
         try {
        // Cargar la vista de detalle
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetalleEstablecimiento.fxml"));
        Parent rootPers = fxmlLoader.load();
        rootEST.setVisible(false);
        
         StackPane rootMain = (StackPane)rootEST.getScene().getRoot();
         rootMain.getChildren().add(rootPers);
         
         DetalleEstablecimientoController lugarDetalleViewController = (DetalleEstablecimientoController) fxmlLoader.getController();  
            lugarDetalleViewController.setRootContactosView(rootEST);
            lugarDetalleViewController.setTableViewPrevio(tablaEST);
            //personaDetalleViewController.setEntityManager(entityManager);
            lugarSeleccionado = new Establecimiento();
            lugarDetalleViewController.setEst(entityManager, lugarSeleccionado, true);
//            personaDetalleViewController.setNuevaPersona(true);
            lugarDetalleViewController.mostrarDatos();
         
       
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    

    @FXML
    private void botonEdit(ActionEvent event) {
        if(lugarSeleccionado != null) {
         try {
        // Cargar la vista de detalle
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetalleEstablecimiento.fxml"));
        Parent rootPers = fxmlLoader.load();
        
        rootEST.setVisible(false);
        
         StackPane rootMain = (StackPane)rootEST.getScene().getRoot();
         rootMain.getChildren().add(rootPers);
         
         DetalleEstablecimientoController lugarDetalleViewController = (DetalleEstablecimientoController) fxmlLoader.getController();  
        lugarDetalleViewController.setRootContactosView(rootEST);
        lugarDetalleViewController.setTableViewPrevio(tablaEST);
        
        lugarDetalleViewController.setEst(entityManager, lugarSeleccionado, false);
        lugarDetalleViewController.mostrarDatos();
         
       
        
    } catch (IOException ex) {
        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
            }
    }

    @FXML
    private void botonDelete(ActionEvent event) {
        if(lugarSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText("Debe de borrar al empleado"+"  ' "+lugarSeleccionado.getDueño()+" '  "+"que tiene asignado.");
            alert.setContentText(lugarSeleccionado.getNombre() + "---> "
                    + lugarSeleccionado.getDueño());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                entityManager.getTransaction().begin();
                entityManager.remove(lugarSeleccionado);
                entityManager.getTransaction().commit();
                tablaEST.getItems().remove(lugarSeleccionado);
                tablaEST.getFocusModel().focus(null);
                tablaEST.requestFocus();
            } else {
                int numFilaSeleccionada = tablaEST.getSelectionModel().getSelectedIndex();
                tablaEST.getItems().set(numFilaSeleccionada, lugarSeleccionado);
                TablePosition pos = new TablePosition(tablaEST, numFilaSeleccionada, null);
                tablaEST.getFocusModel().focus(pos);
                tablaEST.requestFocus();            
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
    }

 

    }
    


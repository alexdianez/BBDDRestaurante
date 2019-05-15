/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.MAIN;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import restaurante.Establecimiento;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class DetalleEstablecimientoController implements Initializable {
    private Pane rootEstView;
    private TableView tableViewPrevio;
    private Establecimiento lugar;
    private EntityManager entityManager;
    private boolean nuevoLugar;
    @FXML
    private TextField textFieldDueño;
    @FXML
    private TextField textFieldEspacio;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private DatePicker textFieldFechNac;
    @FXML
    private AnchorPane rootEstablecimientoView;
    private TextField textFieldID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void setRootContactosView(Pane rootEstView) {
    this.rootEstView = rootEstView;
    }
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
    this.tableViewPrevio = tableViewPrevio;
    }
    
    public void setEst(EntityManager entityManager, Establecimiento lugar, boolean nuevoLugar) {
    this.entityManager = entityManager;
    entityManager.getTransaction().begin();
    if(!nuevoLugar) {
        this.lugar = entityManager.find(Establecimiento.class, lugar.getIdEstab());
    } else {
        this.lugar = lugar;
    }
    this.nuevoLugar = nuevoLugar;
}
    
     public void mostrarDatos() {
        
        textFieldNombre.setText(lugar.getNombre());
        textFieldDueño.setText(lugar.getDueño());
  
        if (lugar.getEspacio() != null) {
            textFieldEspacio.setText(lugar.getEspacio().toString());
        }
        if (lugar.getNombre() != null) {
            textFieldNombre.setText(lugar.getNombre());
        }
        if (lugar.getDueño() != null) {
            textFieldDueño.setText(lugar.getDueño());
        }
        
        
        if (lugar.getFechaCreacion() != null) {
            Date date = lugar.getFechaCreacion();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            textFieldFechNac.setValue(localDate);
        }     
       
}

    @FXML
    private void botonGuardar(ActionEvent event) {
         int numFilaSeleccionada;
        boolean errorFormato = false;

        lugar.setNombre(textFieldNombre.getText());
        lugar.setDueño(textFieldDueño.getText());
//        persona.setEstablecimiento

        if (!textFieldEspacio.getText().isEmpty()) {
            try {
                lugar.setEspacio(BigDecimal.valueOf(Double.valueOf(textFieldEspacio.getText())));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Espacio no válido");
                alert.showAndWait();
                textFieldEspacio.requestFocus();
            }

        if (!textFieldNombre.getText().isEmpty()) {
            try {
                lugar.setNombre((textFieldNombre.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nombre no válido");
                alert.showAndWait();
                textFieldNombre.requestFocus();
            }
        }
         if (!textFieldDueño.getText().isEmpty()) {
            try {
                lugar.setDueño((textFieldDueño.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dueño no válido");
                alert.showAndWait();
                textFieldDueño.requestFocus();
            }
        }
          if (textFieldFechNac.getValue() != null) {
            LocalDate localDate = textFieldFechNac.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            lugar.setFechaCreacion(date);
        } else {
            lugar.setFechaCreacion(null);
        }
           if (!errorFormato) {
            try {
                if (nuevoLugar) {
                    entityManager.persist(lugar);
                } else {
                    entityManager.merge(lugar);
                }
                entityManager.getTransaction().commit();

                StackPane rootMain = (StackPane) rootEstablecimientoView.getScene().getRoot();
                rootMain.getChildren().remove(rootEstablecimientoView);
                rootEstView.setVisible(true);

                if (nuevoLugar) {
                    tableViewPrevio.getItems().add(lugar);
                    numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
                    tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
                    tableViewPrevio.scrollTo(numFilaSeleccionada);
                } else {
                    numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
                    tableViewPrevio.getItems().set(numFilaSeleccionada, lugar);
                }
                TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
                tableViewPrevio.getFocusModel().focus(pos);
                tableViewPrevio.requestFocus();
            } catch (RollbackException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. "
                        + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
}
    }
    }

    @FXML
    private void botonCancelar(ActionEvent event) {
        entityManager.getTransaction().rollback();

        StackPane rootMain = (StackPane) rootEstablecimientoView.getScene().getRoot();
        rootMain.getChildren().remove(rootEstablecimientoView);

        rootEstView.setVisible(true);

        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
}
    }
    


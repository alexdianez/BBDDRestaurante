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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import restaurante.Empleado;
import restaurante.Establecimiento;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class DetallePersonaController implements Initializable {
    private Pane rootContactosView;
    private TableView tableViewPrevio;
    private Empleado persona;
    private EntityManager entityManager;
    private boolean nuevaPersona;
    @FXML
    private TextField textFieldFuncion;
    @FXML
    private TextField textFieldApellidos;
    @FXML
    private ComboBox<Establecimiento> textFieldEstab;
    
    @FXML
    private TextField textFieldSalario;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private DatePicker textFieldFechNac;
    @FXML
    private AnchorPane rootPersonaView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void setRootContactosView(Pane rootContactosView) {
    this.rootContactosView = rootContactosView;
    }
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
    this.tableViewPrevio = tableViewPrevio;
}
    
    public void setPersona(EntityManager entityManager, Empleado persona, boolean nuevaPersona) {
    this.entityManager = entityManager;
    entityManager.getTransaction().begin();
    if(!nuevaPersona) {
        this.persona = entityManager.find(Empleado.class, persona.getIdEmpleado());
    } else {
        this.persona = persona;
    }
    this.nuevaPersona = nuevaPersona;
}
    public void mostrarDatos() {
        Query queryEstablecimientoFindAll = entityManager.createNamedQuery("Establecimiento.findAll");
        List<Establecimiento> listEstab = queryEstablecimientoFindAll.getResultList();
        
        textFieldNombre.setText(persona.getNombre());
        textFieldApellidos.setText(persona.getApellidos());
        textFieldFuncion.setText(persona.getFuncion());
  
        
        if (persona.getSalario() != null) {
            textFieldSalario.setText(persona.getSalario().toString());
        }
        if (persona.getNombre() != null) {
            textFieldNombre.setText(persona.getNombre());
        }
        textFieldEstab.setItems(FXCollections.observableList(listEstab));
        
        if (persona.getEstablecimiento() != null) {
            
            textFieldEstab.setValue(persona.getEstablecimiento());
        }
        
        textFieldEstab.setCellFactory((ListView<Establecimiento> l) -> new ListCell<Establecimiento>() {
            @Override
            protected void updateItem(Establecimiento establecimiento, boolean empty) {
                super.updateItem(establecimiento, empty);
                if (establecimiento == null || empty) {
                    setText("");
                } else {
                    setText(establecimiento.getNombre());
                }
            
             }
                });
        
            textFieldEstab.setConverter(new StringConverter<Establecimiento>() {
            @Override
            public String toString(Establecimiento establecimiento) {
                if (establecimiento == null) {
                    return null;
                } else {
                    return establecimiento.getNombre();
                }
            }
            @Override
            public Establecimiento fromString(String userId) {
                return null;
            }
        });
        
        

        if (persona.getFechaNacimiento() != null) {
            Date date = persona.getFechaNacimiento();
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

        persona.setNombre(textFieldNombre.getText());
        persona.setApellidos(textFieldApellidos.getText());
        persona.setFuncion(textFieldFuncion.getText());
//        persona.setEstablecimiento

        if (!textFieldNombre.getText().isEmpty()) {
            try {
                persona.setNombre((textFieldNombre.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Nombre no válido");
                alert.showAndWait();
                textFieldNombre.requestFocus();
            }
        }
         if (!textFieldApellidos.getText().isEmpty()) {
            try {
                persona.setApellidos((textFieldApellidos.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Apellido no válido");
                alert.showAndWait();
                textFieldApellidos.requestFocus();
            }
        }
         if (!textFieldSalario.getText().isEmpty()) {
            try {
                persona.setSalario(BigDecimal.valueOf(Double.valueOf(textFieldSalario.getText())));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Salario no válido");
                alert.showAndWait();
                textFieldSalario.requestFocus();
            }
            if (textFieldEstab.getValue() != null) {
            persona.setEstablecimiento(textFieldEstab.getValue());
        } else {
            Alert alert = new Alert(AlertType.INFORMATION, "Debe indicar un establecimiento");
            alert.showAndWait();
            errorFormato = true;
}
}
         if (!textFieldFuncion.getText().isEmpty()) {
            try {
                persona.setFuncion((textFieldFuncion.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Funcion no válida");
                alert.showAndWait();
                textFieldFuncion.requestFocus();
            }
        }
         if (textFieldFechNac.getValue() != null) {
            LocalDate localDate = textFieldFechNac.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            persona.setFechaNacimiento(date);
        } else {
            persona.setFechaNacimiento(null);
        }
       

        if (!errorFormato) {
            try {
                if (nuevaPersona) {
                    entityManager.persist(persona);
                } else {
                    entityManager.merge(persona);
                }
                entityManager.getTransaction().commit();

                StackPane rootMain = (StackPane) rootPersonaView.getScene().getRoot();
                rootMain.getChildren().remove(rootPersonaView);
                rootContactosView.setVisible(true);

                if (nuevaPersona) {
                    tableViewPrevio.getItems().add(persona);
                    numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
                    tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
                    tableViewPrevio.scrollTo(numFilaSeleccionada);
                } else {
                    numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
                    tableViewPrevio.getItems().set(numFilaSeleccionada, persona);
                }
                TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
                tableViewPrevio.getFocusModel().focus(pos);
                tableViewPrevio.requestFocus();
            } catch (RollbackException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. "
                        + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
}
    
    }
    

    @FXML
    private void botonCancelar(ActionEvent event) {
        entityManager.getTransaction().rollback();

        StackPane rootMain = (StackPane) rootPersonaView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaView);

        rootContactosView.setVisible(true);

        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
}
}

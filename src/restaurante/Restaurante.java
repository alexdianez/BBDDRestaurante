package restaurante;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Alex
 */
public class Restaurante {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        
        //Damos formato a la fecha y las creamos
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        
	String dateInString = "31-08-1982";
	Date dJuan = sdf.parse(dateInString);
        
        String dateInString2 = "04-08-1980";
	Date dPepe = sdf.parse(dateInString2);
        
        String dateInString3 = "21-12-2000";
	Date dFatima = sdf.parse(dateInString3);
        
        String dateInString4 = "27-11-1999";
	Date dAlex = sdf.parse(dateInString4);
        
        String dateInString5 = "01-02-1990";
	Date dJose = sdf.parse(dateInString5);
        
        String dateInString6 = "14-06-1997";
	Date dCurro = sdf.parse(dateInString6);
        
        String dateInString7= "29-07-2016";
        Date dlocal= sdf.parse(dateInString7);
        
        String dateInString8= "19-03-2013";
        Date dlocal2= sdf.parse(dateInString8);
        
        String dateInString9= "10-01-2010";
        Date dlocal3= sdf.parse(dateInString9);
        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantePU");
        EntityManager EM = emf.createEntityManager();
        
        //Establecimiento
        
        Establecimiento local = new Establecimiento(0,"La tazita","Alejandro Dianez");
        local.setFechaCreacion(dlocal);
        local.setEspacio(BigDecimal.valueOf(150));
        
        Establecimiento local2 = new Establecimiento(1,"Domino'sCopas","Alejandro Dianez");
        local2.setFechaCreacion(dlocal2);
        local2.setEspacio(BigDecimal.valueOf(125));
        
        Establecimiento local3 = new Establecimiento(2,"TrickyBurguer","Alejandro Dianez");
        local3.setFechaCreacion(dlocal3);
        local3.setEspacio(BigDecimal.valueOf(100));
        
        
        // Empleados
        
        Empleado juan = new Empleado(0,"Juan","Perez Rodirguez");
        juan.setSalario(BigDecimal.valueOf(1050));
        juan.setFuncion("Cocinero");
        juan.setFechaNacimiento(dJuan);
        
        Empleado pepe = new Empleado(0,"Pepe","Toro Poley");
        pepe.setSalario(BigDecimal.valueOf(1200));
        pepe.setFuncion("Camarero");
        pepe.setFechaNacimiento(dPepe);
        
        Empleado fatima = new Empleado(0,"Fatima","Medina Sanchez");
        fatima.setSalario(BigDecimal.valueOf(1500));
        fatima.setFuncion("Chef");
        fatima.setFechaNacimiento(dFatima);
        
        Empleado alejandro = new Empleado(0,"Alejandro","Dianez Toro");
        alejandro.setSalario(BigDecimal.valueOf(1800));
        alejandro.setFuncion("Dueño");
        alejandro.setFechaNacimiento(dAlex);
        
        Empleado jose = new Empleado(0,"Jose","Dianez Gil");
        jose.setSalario(BigDecimal.valueOf(1200));
        jose.setFuncion("Cocinero");
        jose.setFechaNacimiento(dJose);
        
        Empleado curro = new Empleado(0,"Curro","Diaz Martinez");
        curro.setSalario(BigDecimal.valueOf(1100));
        curro.setFuncion("Camarero");
        curro.setFechaNacimiento(dCurro);
        
        EM.getTransaction().begin();
              
        EM.persist(juan);
        EM.persist(pepe);
        EM.persist(fatima);
        EM.persist(alejandro);
        EM.persist(jose);
        EM.persist(curro);    
        EM.persist(local);
        EM.persist(local2);
        EM.persist(local3);
        
        //Modificacion de objeto
        
//        SetModif fix = new SetModif();
//        fix.SetModfif(EM);
        
        //Eliminamos un objeto
        
//        SetElimin borrar = new SetElimin();
//        borrar.SetElimin(EM);
        
        EM.getTransaction().commit();
        
        //Mostramos la lista de empleados por linea de comandos
        
        MostrarLista lista = new MostrarLista();
        lista.ListaEMP(EM);
        
        //Mostramos un objeto especifico por linea de comandos
        
        GetObjeto objeto = new GetObjeto();
        objeto.GetObjeto(EM);
        
    }
}

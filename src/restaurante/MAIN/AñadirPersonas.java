/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.MAIN;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import restaurante.Empleado;
import restaurante.Establecimiento;

/**
 *
 * @author Alex
 */
public class AñadirPersonas {
       
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        public void añadirPersonas(EntityManager EM)   {
            
             Query Establecimiento = EM.createNamedQuery("Establecimiento.findAll");
             List<Establecimiento> listLoc = Establecimiento.getResultList();
	try { 
                
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
        
      
            
            Establecimiento local1 = listLoc.get(0);
            Establecimiento local2 = listLoc.get(1);
            Establecimiento local3 = listLoc.get(2);
            
            juan.setEstablecimiento(local1);
            pepe.setEstablecimiento(local1);
            fatima.setEstablecimiento(local2);
            alejandro.setEstablecimiento(local2);
            jose.setEstablecimiento(local3);
            curro.setEstablecimiento(local3);
            
        
        
        EM.getTransaction().begin();
              
        EM.persist(juan);
        EM.persist(pepe);
        EM.persist(fatima);
        EM.persist(alejandro);
        EM.persist(jose);
        EM.persist(curro);    
        
        EM.getTransaction().commit();
        }catch(ParseException e){
        
        }
}
}

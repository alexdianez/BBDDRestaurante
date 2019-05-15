/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante.MAIN;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import restaurante.Establecimiento;

/**
 *
 * @author Alex
 */
public class AñadirEstablecimiento {
        Establecimiento local = new Establecimiento(0,"La tazita","Alejandro Dianez");
        Establecimiento local2 = new Establecimiento(1,"Domino'sCopas","Alejandro Dianez");
        Establecimiento local3 = new Establecimiento(2,"TrickyBurguer","Alejandro Dianez"); 
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        public void añadirEstablecimiento(EntityManager EM)   {
           
	try { 
        
         String dateInString7= "29-07-2016";
        Date dlocal= sdf.parse(dateInString7);
        
        String dateInString8= "19-03-2013";
        Date dlocal2= sdf.parse(dateInString8);
        
        String dateInString9= "10-01-2010";
        Date dlocal3= sdf.parse(dateInString9);
        
        //Establecimiento
        
        
        local.setFechaCreacion(dlocal);
        local.setEspacio(BigDecimal.valueOf(150));

        
        local2.setFechaCreacion(dlocal2);
        local2.setEspacio(BigDecimal.valueOf(125));
        
        
        local3.setFechaCreacion(dlocal3);
        local3.setEspacio(BigDecimal.valueOf(100));
        
        EM.getTransaction().begin();
              
        EM.persist(local);
        EM.persist(local2);
        EM.persist(local3);
          
        
        EM.getTransaction().commit();
        }catch(ParseException e){
        
        }
}
}

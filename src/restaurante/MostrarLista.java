/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Alex
 */
public class MostrarLista{ 
    public void ListaEMP(EntityManager EM){
        Query queryEM = EM.createNamedQuery("queryEM.findAll");
        List<Establecimiento> listLoc = queryEM.getResultList();
        List<Empleado> listEM = queryEM.getResultList();
        for(int i=0; i<listEM.size(); i++) {
            Empleado empleado = listEM.get(i);
            System.out.println(empleado.getNombre());
            System.out.println(empleado.getApellidos());
            System.out.println(empleado.getFechaNacimiento());
            System.out.println(empleado.getFuncion());
            System.out.println(empleado.getIdEmpleado());
            System.out.println(empleado.getSalario());
        }
        for(int i=0; i<listLoc.size(); i++) {
            Establecimiento local = listLoc.get(i);
            System.out.println(local.getDueño());
            System.out.println(local.getEspacio());
            System.out.println(local.getFechaCreacion());
            System.out.println(local.getIdEstab());
            System.out.println(local.getNombre());
            
        }
    }
    
}

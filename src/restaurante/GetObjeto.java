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
public class GetObjeto {
    public void GetObjeto(EntityManager EM){
        Query queryObj = EM.createNamedQuery("Empleado.findByNombre");
        queryObj.setParameter("nombre", "Cádiz");
        List<Empleado> listEmp = queryObj.getResultList();
        for(Empleado empleado : listEmp) {
            System.out.print(empleado.getIdEmpleado()+ ": ");
            System.out.println(empleado.getNombre()+ ": ");
}
    }
}

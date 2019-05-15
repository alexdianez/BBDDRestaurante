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
public class SetModif {
    public void SetModfif(EntityManager EM){
    Query querylocal = EM.createNamedQuery("Provincia.findByNombre");
        querylocal.setParameter("nombre", "Cádiz");
        List<Establecimiento> listLocal = querylocal.getResultList();
        EM.getTransaction().begin();
        for(Establecimiento local : listLocal) {
            local.setIdEstab(11);
            EM.merge(local);
}
}
    
}

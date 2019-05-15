/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurante;

import javax.persistence.EntityManager;

/**
 *
 * @author Alex
 */
public class SetElimin {
    public void SetElimin(EntityManager EM){
        Establecimiento localID2 = EM.find(Establecimiento.class, 2);
        if(localID2 != null) {
            EM.remove(localID2);
        } else {
            System.out.println("No hay ningun local con ID=2");
        }
            }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Claseprincipal;

import Controlador.Controlador_Principal;
import Vista.Principal;

/**
 *
 * @author lorena
 */
public class Ejecutar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Principal vista = new Principal();
        Controlador_Principal controller = new Controlador_Principal(vista);
        controller.iniciaControl();
    }
    
}

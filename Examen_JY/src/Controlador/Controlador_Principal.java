/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo_Cliente;
import Vista.Principal;
import Vista.Vista_clie;

/**
 *
 * @author lorena
 */
public class Controlador_Principal {
     Principal vista_menu;
    
    public Controlador_Principal(Principal vista_menu){
        this.vista_menu=vista_menu;
        vista_menu.setVisible(true);
        vista_menu.setLocationRelativeTo(null);
    }
    
      public void iniciaControl(){
        vista_menu.getBtnCliente().addActionListener(l->Crud_cliente());

    }
      
      
      private void Crud_cliente(){
        Vista_clie vista_cli = new Vista_clie();
        Modelo_Cliente modelo_cli = new Modelo_Cliente();
        vista_menu.getdPrincipal().add(vista_cli);
        Controlador_Cliente controlador = new Controlador_Cliente(modelo_cli, vista_cli);
        controlador.ControlPrincipal();
        
    }
}
//
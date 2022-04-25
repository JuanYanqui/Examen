/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo_Cliente;
import Vista.Vista_clie;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author lorena
 */
public class Controlador_Cliente {
    private Modelo_Cliente modelcli;
    private Vista_clie vistacli;
    private JFileChooser jfc;


    //Constructor de la clase Controlador de persona.
    public Controlador_Cliente(Modelo_Cliente modelcli,Vista_clie vistacli){
    this.modelcli = modelcli;
    this.vistacli = vistacli;
    }

    //Método de control de todos los botones iniciales.
    public void ControlPrincipal() {
        vistacli.getBtnCrear().addActionListener(l->abrirDialogo(1));
        vistacli.getBtnEditar().addActionListener(l->abrirDialogo(2));
        
    }
   
    private void abrirDialogo(int ce){
        String title;
        if(ce==1){
            title="Crear nueva persona";
      //      vistaMenuPrincipal.getDtpPrincipal().add(vistaPersona);
            vistacli.getClientes().setName("crear");
        }else{
            title="Editar persona";
            vistacli.getClientes().setName("editar");
        }
        vistacli.getClientes().setLocationRelativeTo(vistacli);
        vistacli.getClientes().setSize(600,500);
        vistacli.getClientes().setTitle(title);
        vistacli.getClientes().setVisible(true);
    }
    
    private void crearEditarCliente(){
        if(vistacli.getClientes().getName()=="crear"){
            //Insertar
                    String cedula = vistacli.getTxtCedula().getText();
                    String nombre = vistacli.getTxtNombre().getText();
                    String apellido= vistacli.getTxtApellidos().getText();
                    String direccion = vistacli.getTxtdireccion().getText();
                    String telefono = vistacli.getTxtTelefono().getText();
                    String correo = vistacli.getTxtgmail().getText();
                    String fechanacimiento = ((JTextField)vistacli.getFechaini().getDateEditor().getUiComponent()).getText();
                    Date fechana = java.sql.Date.valueOf(fechanacimiento);
            
                    Modelo_Cliente cliente = new Modelo_Cliente ();
                    cliente.setCedula(cedula);
                    cliente.setNombres(nombre);
                    cliente.setApellidos(apellido);
                    cliente.setDireccion(direccion);
                    cliente.setTelefono(telefono);
                    cliente.setCorreo(correo);
                    cliente.setFecha_naci((java.sql.Date)fechana);
                    try {
                    //Foto
                    FileInputStream img=new FileInputStream(jfc.getSelectedFile());
                    int largo=(int)jfc.getSelectedFile().length();
                    cliente.setImagen(img);
                    cliente.setLargo(largo);
                         } catch (FileNotFoundException ex) {
                           Logger.getLogger(Controlador_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                         }

                    if(cliente.CrearCliente()){
                        JOptionPane.showMessageDialog(vistacli, "Persona creada satisfactoriamente");
                        vistacli.getClientes().setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(vistacli, "No se pudo crear la persona");
                    }
                
           
        }else if (vistacli.getClientes().getName()=="editar"){    
                String fecha = vistacli.getFechaini().getDate().toString();
               String cedula = vistacli.getTxtCedula().getText();
                    String nombre = vistacli.getTxtNombre().getText();
                    String apellido= vistacli.getTxtApellidos().getText();
                    String direccion = vistacli.getTxtdireccion().getText();
                    String telefono = vistacli.getTxtTelefono().getText();
                    String correo = vistacli.getTxtgmail().getText();
                    String fechanacimiento = ((JTextField)vistacli.getFechaini().getDateEditor().getUiComponent()).getText();
                    Date fechana = java.sql.Date.valueOf(fechanacimiento);
            
                    Modelo_Cliente cliente = new Modelo_Cliente ();
                    cliente.setCedula(cedula);
                    cliente.setNombres(nombre);
                    cliente.setApellidos(apellido);
                    cliente.setDireccion(direccion);
                    cliente.setTelefono(telefono);
                    cliente.setCorreo(correo);
                    cliente.setFecha_naci((java.sql.Date)fechana);
                    try {
                    //Foto
                    FileInputStream img=new FileInputStream(jfc.getSelectedFile());
                    int largo=(int)jfc.getSelectedFile().length();
                    cliente.setImagen(img);
                    cliente.setLargo(largo);
                         } catch (FileNotFoundException ex) {
                           Logger.getLogger(Controlador_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                         }

                    if(cliente.ModificarClienteBDA()){
                        JOptionPane.showMessageDialog(vistacli, "Persona creada satisfactoriamente");
                        vistacli.getClientes().setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(vistacli, "No se pudo crear la persona");
                    }
        }
        
    
    }
    
    


    
    

}

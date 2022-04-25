/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Clases.Cliente;
import Modelo.Modelo_Cliente;
import Vista.Vista_clie;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;

/**
 *
 * @author lorena
 */
public class Controlador_Cliente {

    private Modelo_Cliente modelcli;
    private Vista_clie vistacli;
    private JFileChooser jfc;

    //Constructor de la clase Controlador de persona.
    public Controlador_Cliente(Modelo_Cliente modelcli, Vista_clie vistacli) {
        this.modelcli = modelcli;
        this.vistacli = vistacli;
        vistacli.setVisible(true);
        CargarCliengte();
    }

    //Método de control de todos los botones iniciales.
    public void ControlPrincipal() {
        vistacli.getBtnCrear().addActionListener(l -> abrirDialogo(1));
        vistacli.getBtnEditar().addActionListener(l -> abrirDialogo(2));
        vistacli.getBtnAceptar().addActionListener(l -> crearEditarCliente());
        vistacli.getBtnCancelar().addActionListener(l -> cancelar());
        vistacli.getBtnfoto().addActionListener(l -> examinaFoto());
        vistacli.getBtnActualizar().addActionListener(l -> CargarCliengte());
        vistacli.getBtnRemover().addActionListener(l -> EliminarCliente());
        

    }

    private void abrirDialogo(int ce) {
        String title;
        if (ce == 1) {
            title = "Crear nueva persona";
            //      vistaMenuPrincipal.getDtpPrincipal().add(vistaPersona);
            vistacli.getClientes().setName("crear");
        } else {
            title = "Editar persona";
            vistacli.getClientes().setName("editar");
            modificar();
        }
        vistacli.getClientes().setLocationRelativeTo(vistacli);
        vistacli.getClientes().setSize(600, 500);
        vistacli.getClientes().setTitle(title);
        vistacli.getClientes().setVisible(true);
    }

    private void crearEditarCliente() {
        if (vistacli.getClientes().getName() == "crear") {
            //Insertar
            String cedula = vistacli.getTxtCedula().getText();
            String nombre = vistacli.getTxtNombre().getText();
            String apellido = vistacli.getTxtApellidos().getText();
            String direccion = vistacli.getTxtdireccion().getText();
            String telefono = vistacli.getTxtTelefono().getText();
            String correo = vistacli.getTxtgmail().getText();
            String fechanacimiento = ((JTextField) vistacli.getFechaini().getDateEditor().getUiComponent()).getText();
            Date fechana = java.sql.Date.valueOf(fechanacimiento);

            Modelo_Cliente cliente = new Modelo_Cliente();
            cliente.setCedula(cedula);
            cliente.setNombres(nombre);
            cliente.setApellidos(apellido);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setFecha_naci((java.sql.Date) fechana);
            try {
                //Foto
                FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                int largo = (int) jfc.getSelectedFile().length();
                cliente.setImagen(img);
                cliente.setLargo(largo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controlador_Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (cliente.CrearCliente()) {
                JOptionPane.showMessageDialog(vistacli, "Cliente creada satisfactoriamente");
                CargarCliengte();
                vistacli.getClientes().setVisible(false);
            } else {
                JOptionPane.showMessageDialog(vistacli, "No se pudo crear el cliente");
            }

        } else if (vistacli.getClientes().getName() == "editar") {
            String cedula = vistacli.getTxtCedula().getText();
            String nombre = vistacli.getTxtNombre().getText();
            String apellido = vistacli.getTxtApellidos().getText();
            String direccion = vistacli.getTxtdireccion().getText();
            String telefono = vistacli.getTxtTelefono().getText();
            String correo = vistacli.getTxtgmail().getText();
            String fechanacimiento = ((JTextField) vistacli.getFechaini().getDateEditor().getUiComponent()).getText();
            Date fechana = java.sql.Date.valueOf(fechanacimiento);

            Modelo_Cliente cliente = new Modelo_Cliente();
            cliente.setCedula(cedula);
            cliente.setNombres(nombre);
            cliente.setApellidos(apellido);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setFecha_naci((java.sql.Date) fechana);
            try {
                //Foto
                FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                int largo = (int) jfc.getSelectedFile().length();
                cliente.setImagen(img);
                cliente.setLargo(largo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controlador_Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (cliente.ModificarClienteBDA()) {
                JOptionPane.showMessageDialog(vistacli, "Cliente modificado satisfactoriamente");
                vistacli.getClientes().setVisible(false);
                CargarCliengte();
            } else {
                JOptionPane.showMessageDialog(vistacli, "No se pudo modificar el cliente");
            }
        }

    }

    public void EliminarCliente() {
        int seleccionado = vistacli.getTblCliente().getSelectedRow();
        int respuesta = 0;
        Component rootPane = null;
        Modelo_Cliente clieeli = new Modelo_Cliente();
        if (seleccionado != -1) {
            String idperson = vistacli.getTblCliente().getValueAt(seleccionado, 0).toString();

            respuesta = JOptionPane.showConfirmDialog(rootPane, "¿Estas seguro que deseas eliminar este registro?");
            if (respuesta == 0) {
                if (clieeli.removerPersona(idperson)) {
                    JOptionPane.showMessageDialog(rootPane, "Registro de cliente a sido eliminado");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error al eliminar");
                }

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay datos a eliminar");
        }
    }

    public void modificar() {
        int seleccionado = vistacli.getTblCliente().getSelectedRow();
        if (seleccionado != -1) {
            String ver = vistacli.getTblCliente().getValueAt(seleccionado, 0).toString();
            List<Cliente> tablaP = modelcli.listaClie();
            for (int j = 0; j < tablaP.size(); j++) {
                if (tablaP.get(j).getCedula().equals(ver)) {
                    vistacli.getTxtCedula().setText(tablaP.get(j).getCedula());
                    vistacli.getTxtNombre().setText(tablaP.get(j).getNombres());
                    vistacli.getTxtApellidos().setText(tablaP.get(j).getApellidos());
                    vistacli.getTxtdireccion().setText(tablaP.get(j).getDireccion());
                    vistacli.getTxtTelefono().setText(tablaP.get(j).getTelefono());
                    vistacli.getTxtgmail().setText(tablaP.get(j).getCorreo());
                    Date fec = tablaP.get(j).getFecha_naci();
                    vistacli.getFechaini().setDate(fec);
                    if (tablaP.get(j).getFoto() == null) {
                        vistacli.getLblfoto().setIcon(null);
                    } else {
                        Image in = tablaP.get(j).getFoto();
//                        Image ja= in.getScaledInstance(vistap.getLblFoto().getWidth() ,vistap.getLblFoto().getWidth(), Image.SCALE_SMOOTH);
                        Image img = in.getScaledInstance(133, 147, Image.SCALE_SMOOTH);
                        Icon icono = new ImageIcon(img);
                        vistacli.getLblfoto().setIcon(icono);
                    }
//              vista.getLblfoto().setText(tablaP.get(j).getImagen().read(b));

                } else {

                }
            }
        } else {
            JOptionPane.showMessageDialog(vistacli, "No a seleccionado a nigun cliente");
        }

    }
    
    private void CargarCliengte(){
        
        vistacli.getTblCliente().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
        vistacli.getTblCliente().setRowHeight(100);
        
        //Enlazar el modelo de tabla con mi controlador.
        DefaultTableModel tblModel;
        tblModel=(DefaultTableModel)vistacli.getTblCliente().getModel();
        tblModel.setNumRows(0);//limpio filas de la tabla.

        List<Cliente> listap=modelcli.listaClie();//Enlazo al Modelo y obtengo los datos
        Holder<Integer> i = new Holder<>(0);//contador para el no. fila
        listap.stream().forEach(pe->{
            
            tblModel.addRow(new Object[8]);//Creo una fila vacia/
            vistacli.getTblCliente().setValueAt(pe.getCedula(), i.value, 0);
            vistacli.getTblCliente().setValueAt(pe.getNombres(), i.value, 1);
            vistacli.getTblCliente().setValueAt(pe.getApellidos(), i.value, 2);
            vistacli.getTblCliente().setValueAt(pe.getDireccion(), i.value, 3);
            vistacli.getTblCliente().setValueAt(pe.getTelefono(), i.value, 4);
            vistacli.getTblCliente().setValueAt(pe.getCorreo(), i.value, 5);
            LocalDateTime fechaactual= LocalDateTime.now();
            Period periodo=Period.between(pe.getFecha_naci().toLocalDate(), fechaactual.toLocalDate());
            vistacli.getTblCliente().setValueAt(periodo.getYears(), i.value, 6);
            Image foto=pe.getFoto();
            if(foto!=null){
            
                Image nimg= foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon icono=new ImageIcon(nimg);
                DefaultTableCellRenderer renderer= new DefaultTableCellRenderer();
                renderer.setIcon(icono);
                vistacli.getTblCliente().setValueAt(new JLabel(icono), i.value, 7);
                
            }else{
                 vistacli.getTblCliente().setValueAt(null, i.value, 7);
            }
            
            i.value++;
        });
        
    }
    
    public void cancelar(){
        int respuesta = 0;
        respuesta = JOptionPane.showConfirmDialog(null,"¿Estas seguro que deseas cancelar?");
        if(respuesta==0){
            vistacli.setVisible(true);
            vistacli.getClientes().setVisible(false);
            }else{
                
            }
    }
    
        private void examinaFoto(){
        jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado=jfc.showOpenDialog(vistacli);
        if(estado==JFileChooser.APPROVE_OPTION){
            try {
                Image imagen=ImageIO.read(jfc.getSelectedFile()).getScaledInstance(
                        vistacli.getLblfoto().getWidth(),
                        vistacli.getLblfoto().getHeight(),
                        Image.SCALE_DEFAULT);
                
                Icon icono=new ImageIcon(imagen);
                vistacli.getLblfoto().setIcon(icono);
                vistacli.getLblfoto().updateUI();
            } catch(IOException ex) {
                Logger.getLogger(Controlador_Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

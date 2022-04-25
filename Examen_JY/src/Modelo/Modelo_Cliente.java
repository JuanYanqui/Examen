/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Clases.Cliente;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author lorena
 */
public class Modelo_Cliente extends Cliente {

    Conexion_BD cp = new Conexion_BD();

    public Modelo_Cliente() {
    }

    public Modelo_Cliente(String cedula, String nombres, String apellidos, String direccion, String telefono, String correo, Date fecha_naci, Image foto, FileInputStream imagen, int largo) {
        super(cedula, nombres, apellidos, direccion, telefono, correo, fecha_naci, foto, imagen, largo);
    }
        
       public List<Cliente> listaClie() {
        List<Cliente> lp = new ArrayList<Cliente>();
        try {
            String sql = "select * from cliente";
            ResultSet rs = cp.colsulta(sql);
            byte[] bytea;
            while (rs.next()) {
                Cliente client = new Cliente();
                client.setCedula(rs.getString("cedula"));
                client.setNombres(rs.getString("nombres"));
                client.setApellidos(rs.getString("apellidos"));
                client.setDireccion(rs.getString("direccion"));
                client.setTelefono(rs.getString("telefono"));
                client.setCorreo(rs.getString("correo"));
                client.setFecha_naci(rs.getDate("fecha_naci"));
                bytea = rs.getBytes("foto");
                if (bytea != null) {
                    try {
                        client.setFoto(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(Modelo_Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                lp.add(client);
            }
            rs.close();
            return lp;
        } catch (SQLException ex) {
            Logger.getLogger(Modelo_Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
       
   private Image obtenerImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator it = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) it.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceSubsampling(1, 1, 0, 0);
        return reader.read(0, param);
    }

}

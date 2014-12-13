/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dentegra.manager.domain;

/**
 *
 * @author aminRX
 */
import java.sql.*;
import java.util.HashMap;
import com.dentegra.manager.datasource.Gateway;

public class DentegraService {

    private Gateway db = Gateway.GetInstance();
    private HashMap<String, String> map;

    public boolean login(String user, String password) {
        try {
            ResultSet rs = db.findUserFor(user, password);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    ;
	
	public boolean signUp(String user, String password, String email, String question, String answer) {
        try {
            db.singIn(user, password, email, question, answer);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;

    }

    public void changePassword(String email, String password) {
        try {
            db.updatePassword(email, password);
        } catch (SQLException e) {
            System.out.println(e);
        };
    }

    public HashMap<String, String> restore(String email) {
        map = new HashMap<String, String>();
        try {
            ResultSet rs = db.findEmail(email);
            while (rs.next()) {
                map.put("username", rs.getString("username"));
                map.put("email", rs.getString("email"));
                map.put("password", rs.getString("password"));
                map.put("pregunta", rs.getString("pregunta"));
                map.put("respuesta", rs.getString("respuesta"));
                map.put("admin", rs.getString("admin"));
            }
            return map;
        } catch (SQLException e) {
            System.out.println(e);
        };
        return map;
    }

    public HashMap<String, String> paciente(String id) {
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        try {
            ResultSet rs = db.findPaciente(id);
            while (rs.next()) {
                System.out.println(rs.getString("idPaciente"));
                map.put("idPaciente", rs.getString("idPaciente"));
                map.put("apellidoPaterno", rs.getString("apellido_paterno"));
                map.put("apellidoMaterno", rs.getString("apellido_materno"));
                map.put("nombres", rs.getString("nombres"));
                map.put("profesion", rs.getString("profesion"));
                map.put("direccion", rs.getString("direccion"));
                map.put("codigoPostal", rs.getString("codigo_postal"));
                map.put("estado", rs.getString("estado"));
                map.put("ciudad", rs.getString("cuidad"));
                map.put("pais", rs.getString("pais"));
                map.put("fechaNacimiento", rs.getString("fecha_nacimiento"));
                map.put("telefono", rs.getString("telefono"));
                map.put("email", rs.getString("email"));
            }
            return map;
        } catch (SQLException e) {
            System.out.println(e);
        };
        return map;
    }

    public Boolean insertOdontograma(String index, String[][] table) {
        try {
            for (int i = 0; i < table.length; i++) {
                    db.insetarOdontograma(table[i][0], table[i][1], table[i][2], table[i][3],index);
            }

            return true;
        } catch (SQLException e) {
            System.out.println(e);
        };
        return false;
    }

    public Boolean insertPaciente(String apellidoPaterno, String apellidoMaterno, String nombre, String profesion, String direccion, String codPostal, String estado, String ciudad, String pais, String fechaDeNacimiento, String telefono, String email) {
        try {
            db.insetarPaciente(apellidoPaterno, apellidoMaterno, nombre, profesion, direccion, codPostal, estado, ciudad, pais, fechaDeNacimiento, telefono, email);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public String[][] pacientesExpedienteYNombre() {
        int count = 0;
        String pacientes[][] = null;
        try {
            ResultSet rs = db.allPacientesGrepExpedientesNombre();
            while (rs.next()) {
                count += 1;
            }
            pacientes = new String[count][2];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                System.out.println(rs.getString("idPaciente"));
                pacientes[i][0] = rs.getString("idPaciente");
                pacientes[i][1] = rs.getString("apellido_paterno") + " " + rs.getString("apellido_materno") + " " + rs.getString("nombres");
                i++;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return pacientes;
    }

    public String[] findPacienteModelo(String expediente) {
        String paciente[] = new String[2];
        try {
            ResultSet rs = db.findPaciente(expediente);
            while (rs.next()) {
                paciente[0] = rs.getString("idPaciente");
                paciente[1] = rs.getString("apellido_paterno") + " " + rs.getString("apellido_materno") + " " + rs.getString("nombres");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return paciente;
    }
    
    public String[][] pacienteOdontograma(String expediente) {
        int count = 0;
        String odontograma[][] = null;
        try {
            ResultSet rs = db.allPacienteOdontograma(expediente);
            while (rs.next()) {
                count += 1;
            }
            odontograma = new String[count][4];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                odontograma[i][0] = rs.getString("numero_diente");
                odontograma[i][1] = rs.getString("problema");
                odontograma[i][2] = rs.getString("lado");
                odontograma[i][3] = rs.getString("comentario");
                i++;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return odontograma;
    }
    
    public void deletePaciente(String idPaciente) {
        try {
            db.deletePaciente(idPaciente);
        } catch(SQLException e){
            System.out.println(e);
        }
    }

    public boolean editPaciente(String idPaciente, String apellidoPaterno, String apellidoMaterno, String nombre, String profesion, String direccion, String codPostal, String estado, String ciudad, String pais, String fechaDeNacimiento, String telefono, String email) {
        try {
            db.editPaciente(idPaciente, apellidoPaterno, apellidoMaterno, nombre, profesion, direccion, codPostal, estado, ciudad, pais, fechaDeNacimiento, telefono, email);
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
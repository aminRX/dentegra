/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dentegra.manager.datasource;

import java.sql.*;

public class Gateway {
    
    private Connection con;
    private static Gateway gateway;
    private static final String createDatabase =
            "CREATE DATABASE IF NOT EXISTS revenue";
    private static final String useDatabase =
            "USE revenue";
    private static final String createTableUser =
            "CREATE TABLE IF NOT EXISTS user ("
            + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "username VARCHAR(50) NOT NULL, "
            + "email VARCHAR(50) NOT NULL, "
            + "password VARCHAR(50) NOT NULL, "
            + "pregunta VARCHAR(50) NOT NULL, "
            + "respuesta VARCHAR(50) NOT NULL) ENGINE=InnoDB;";
    private static final String createTablePaciente =
            "create table IF NOT EXISTS paciente ("
            + "idPaciente INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
            + "apellido_paterno VARCHAR(50) NOT NULL, "
            + "apellido_materno VARCHAR(50) NOT NULL, "
            + "nombres VARCHAR(50) NOT NULL, "
            + "profesion VARCHAR(50) NOT NULL, "
            + "direccion VARCHAR(50) NOT NULL, "
            + "codigo_postal VARCHAR(50) NOT NULL, "
            + "estado VARCHAR(50) NOT NULL, "
            + "cuidad VARCHAR(50) NOT NULL, "
            + "pais VARCHAR(50) NOT NULL, "
            + "fecha_nacimiento VARCHAR(50) NOT NULL, "
            + "telefono VARCHAR(50) NOT NULL, "
            + "email VARCHAR(50) NOT NULL) ENGINE=InnoDB;";
    private static final String createTableOdontograma =
            "create table IF NOT EXISTS odontograma ("
            + "idOdontograma INT NOT NULL auto_increment PRIMARY KEY, "
            + "numero_diente VARCHAR(50) NOT NULL, "
            + "problema VARCHAR(50) NOT NULL, "
            + "lado VARCHAR(50) NOT NULL, "
            + "comentario VARCHAR(50) NOT NULL, "
            + "idPaciente INT not null, "
            + "FOREIGN KEY(idPaciente) references paciente(idPaciente)) "
            + "ENGINE=InnoDB;";
    
    private Gateway() {
        try {
            // Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
            // con = DriverManager.getConnection("jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine");
            Statement s = con.createStatement();
            s.executeUpdate(createDatabase);
            
            PreparedStatement stmt2 = con.prepareStatement(useDatabase);
            PreparedStatement stmt3 = con.prepareStatement(createTableUser);
            PreparedStatement stmt4 = con.prepareStatement(createTablePaciente);
            PreparedStatement stmt = con.prepareStatement(createTableOdontograma);
            stmt2.executeUpdate();
            stmt3.executeUpdate();
            stmt4.executeUpdate();
            stmt.executeUpdate();
            
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static Gateway GetInstance() {
        if (gateway == null) {
            gateway = new Gateway();
        }
        return gateway;
    }
    private static final String findUser =
            "SELECT * "
            + "FROM user "
            + "WHERE username = ? AND password = ?";
    
    public ResultSet findUserFor(String usuario, String contrasena) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(findUser);
        stmt.setString(1, usuario);
        stmt.setString(2, contrasena);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String insertUser =
            "INSERT INTO revenue.user(username,email,password, pregunta, respuesta) "
            + "VALUES (?, ?, ?, ?, ?)";
    
    public void singIn(String user, String password, String email,
            String question, String answer) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(insertUser);
        stmt.setString(1, user);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, question);
        stmt.setString(5, answer);
        stmt.executeUpdate();
    }
    private static final String findEmail =
            "SELECT * "
            + "FROM user "
            + "WHERE email = ?";
    
    public ResultSet findEmail(String email) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(findEmail);
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String updatePassword =
            "UPDATE `revenue`.`user` "
            + "SET "
            + "password = ?"
            + "WHERE `email` = ?";
    
    public void updatePassword(String email, String password) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(updatePassword);
        stmt.setString(1, password);
        stmt.setString(2, email);
        stmt.executeUpdate();
    }
    private static final String findPaciente =
            "SELECT * "
            + "FROM paciente "
            + "WHERE idPaciente = ?";
    
    public ResultSet findPaciente(String id) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(findPaciente);
        stmt.setString(1, id);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String insertPaciente =
            "INSERT INTO revenue.paciente(apellido_paterno, apellido_materno, nombres, profesion, direccion, codigo_postal, estado, cuidad, pais, fecha_nacimiento, telefono, email) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    public void insetarPaciente(String apellidoPaterno, String apellidoMaterno,
            String nombre, String profesion, String direccion, String codPostal,
            String estado, String ciudad, String pais, String fechaDeNacimiento,
            String telefono, String email) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(insertPaciente);
        stmt.setString(1, apellidoPaterno);
        stmt.setString(2, apellidoMaterno);
        stmt.setString(3, nombre);
        stmt.setString(4, profesion);
        stmt.setString(5, direccion);
        stmt.setString(6, codPostal);
        stmt.setString(7, estado);
        stmt.setString(8, ciudad);
        stmt.setString(9, pais);
        stmt.setString(10, fechaDeNacimiento);
        stmt.setString(11, telefono);
        stmt.setString(12, email);
        stmt.executeUpdate();
    }
    private static final String insertOdontograma =
            "INSERT INTO revenue.odontograma(numero_diente,problema,lado, comentario, idPaciente) "
            + "VALUES (?, ?, ?, ?, ?)";
    
    public void insetarOdontograma(String numero_diente, String problema,
            String lado, String comentario,
            String idPaciente) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(insertOdontograma);
        System.out.println(numero_diente);
        stmt.setString(1, numero_diente);
        stmt.setString(2, problema);
        stmt.setString(3, lado);
        stmt.setString(4, comentario);
        stmt.setString(5, idPaciente);
        stmt.executeUpdate();
    }
    private static final String relation =
            "SELECT * "
            + "FROM odontograma "
            + "WHERE idPaciente = ?";
    
    public ResultSet relationship(String idPaciente) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(insertOdontograma);
        stmt.setString(1, idPaciente);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String allPacientes =
            "SELECT * "
            + "FROM paciente ";
    
    public ResultSet allPacientes() throws SQLException {
        PreparedStatement stmt = con.prepareStatement(allPacientes);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String allPacientesGrepExpedientesNombre =
            "SELECT idPaciente, apellido_paterno, apellido_materno, nombres "
            + "FROM paciente ";
    
    public ResultSet allPacientesGrepExpedientesNombre() throws SQLException {
        PreparedStatement stmt = con.prepareStatement(allPacientesGrepExpedientesNombre);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String allPacienteOdontograma =
            "SELECT * "
            + "FROM odontograma "
            + "WHERE idPaciente = ?";
    
    public ResultSet allPacienteOdontograma(
            String expediente) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(allPacienteOdontograma);
        stmt.setString(1, expediente);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    private static final String deletePaciente =
            "DELETE FROM paciente WHERE idPaciente = ?";
    private static final String deleteRelationWithPaciente =
            "DELETE FROM odontograma WHERE idPaciente = ?";
    
    public void deletePaciente(String idPaciente) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(deletePaciente);
        stmt.setString(1, idPaciente);
        stmt.execute();
        PreparedStatement stmt2 = con.prepareStatement(deleteRelationWithPaciente);
        stmt2.setString(1, idPaciente);
        stmt2.execute();
    }
    public static final String editPaciente =
            "UPDATE paciente "
            + "SET apellido_paterno = ?, apellido_materno = ?, "
            + "nombres = ?, profesion = ?, direccion = ?, "
            + "codigo_postal = ?, estado = ?, cuidad = ?, pais = ?, "
            + "fecha_nacimiento = ?, telefono = ?, email = ? "
            + "WHERE idPaciente = ?";
    
    public void editPaciente(String idPaciente, String apellidoPaterno,
            String apellidoMaterno, String nombre, String profesion,
            String direccion, String codPostal, String estado,
            String ciudad, String pais, String fechaDeNacimiento,
            String telefono, String email) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(editPaciente);
        stmt.setString(1, apellidoPaterno);
        stmt.setString(2, apellidoMaterno);
        stmt.setString(3, nombre);
        stmt.setString(4, profesion);
        stmt.setString(5, direccion);
        stmt.setString(6, codPostal);
        stmt.setString(7, estado);
        stmt.setString(8, ciudad);
        stmt.setString(9, pais);
        stmt.setString(10, fechaDeNacimiento);
        stmt.setString(11, telefono);
        stmt.setString(12, email);
        stmt.setString(13, idPaciente);
        stmt.executeUpdate();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dentegra.manager;
import com.dentegra.manager.presentation.Login;
import com.dentegra.manager.datasource.Gateway;
/**
 *
 * @author aminRX
 */
public class Dentegra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Login login = new Login();
        login.setVisible(true);
    }
}

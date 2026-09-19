/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.miapp;

import com.miapp.vista.EstudianteView;
import com.miapp.controlador.EstudianteController;

/**
 *
 * @author taidy
 */
public class EstudianteMVC {
    public static void main(String[] args) {
  
        EstudianteView vista = new EstudianteView();
    EstudianteController controlador = new EstudianteController(vista);
    
    controlador.mostrarTodos(); 
    
    vista.setVisible(true);
    }
    
    
}

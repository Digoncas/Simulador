/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;
/**
 *
 * @author b24882
 */
public class Administrador {
    int hilos;      //Total de hilos que se van a correr
    Contexto tablaDeRegistros[];
    
    Administrador(){
        tablaDeRegistros = new Contexto[3];
    }
}

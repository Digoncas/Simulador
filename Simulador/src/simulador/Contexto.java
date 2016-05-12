/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daniel Orozco
 * @author Diego González
 */
package simulador;

public class Contexto {

    int Registros[][];    //Los 32 registros de cada uno de los 4 contexto
    boolean estaCorriendo[];

    Contexto() {
        Registros = new int[4][33];
        estaCorriendo = new boolean[4];
        for (int i = 0; i < estaCorriendo.length; i++) {
            estaCorriendo[i] = false;
        }
    }

    public void setPC(int proceso, int direccion) {
        Registros[proceso][0] = direccion;
    }
    
    public int getPC(int proceso) {
        return Registros[proceso][0];
    }
    
    public void setRegistro(int proceso, int registro, int valor){
        Registros[proceso][registro+1] = valor;
    }
    
    public int getRegistro(int proceso, int registro){
        return Registros[proceso][registro+1];
    }

    public boolean hayProcesosCorriendo() {
        boolean retorno = false;
        for (int i = 0; i < estaCorriendo.length; i++) {
            if (estaCorriendo[i] == true) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void terminarProceso(int proceso) {
        estaCorriendo[proceso] = false;
    }

    public boolean estaCorriendo(int proceso) {
        return estaCorriendo[proceso];
    }
    
    public void setEstaCorriendo(int proceso) {
        estaCorriendo[proceso] = true;
    }

}

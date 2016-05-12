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
public class Palabra {
    private int[] contenedor = new int[4];
    
    Palabra(){
        contenedor[0] = 0;
        contenedor[1] = 0;
        contenedor[2] = 0;
        contenedor[3] = 0;
    }
    
    public void setPalabra(int a, int b, int c, int d){
        contenedor[0] = a;
        contenedor[1] = b;
        contenedor[2] = c;
        contenedor[3] = d;
    }
    
    public int[] getPalabra(){
        return contenedor;
    }
}

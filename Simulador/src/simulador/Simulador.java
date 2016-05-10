/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

/**
 *
 * @author Diego
 */
public class Simulador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Procesador procesador1 = new Procesador(4);
        procesador1.memCache.etiqueta[0] = 0;
        procesador1.memCache.etiqueta[1] = -1;
        procesador1.memCache.etiqueta[2] = -1;
        procesador1.memCache.etiqueta[3] = -1;
        procesador1.memCache.memoria[0][0] = 14;
        procesador1.memCache.memoria[0][1] = 1;
        procesador1.memCache.memoria[0][2] = 3;
        procesador1.memCache.memoria[0][3] = 4;
        procesador1.Registro[0] = 0;
        procesador1.Registro[3] = 8;
        procesador1.Registro[4] = 4;
        procesador1.correrInstruccion(0);
    }
    
}

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

public class MemCache {

    int etiqueta[];
    int memoria[][];

    MemCache() {
        etiqueta = new int[4];
        for (int i = 0; i < etiqueta.length; i++) {
            etiqueta[i] = -1;
        }
        memoria = new int[4][4];
    }

    /**
     *
     * @param direccion
     * @return Retorna la posición de la cache donde se encuentra la
     * instruccion, y -1 si no se encuentra
     */
    public int estaInstruccion(int direccion) {
        int retorno = -1;
        for (int i = 0; i < etiqueta.length; i++) {
            if (direccion == etiqueta[i]) {
                retorno = i;
            }
        }
        return retorno;
    }

    public int getMemoria(int i, int j) {
        return memoria[i][j];
    }

    public void setMemoria(int i, int j, int valor) {
        memoria[i][j] = valor;
    }

    public void setEtiqueta(int pos, int direccion) {
        etiqueta[pos] = direccion;
    }
}

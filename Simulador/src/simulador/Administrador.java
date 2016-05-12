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

import java.io.*;

public class Administrador {

    int hilos;      //Total de hilos que se van a correr
    Contexto tablaDeRegistros[];

    Administrador() {

        tablaDeRegistros = new Contexto[3];
    }

    public void correrProceso() {
        Procesador p1 = new Procesador(20);
        int contador = 0;
        contador = leerArchivo("src/simulador/1.txt", p1, contador);
        p1.imprimirMemoria();
        p1.ejecutarProgramas();

    }

    public int leerArchivo(String nombreDelArchivo, Procesador p, int contador) {
        // Nombre del archivo
        String fileName = nombreDelArchivo;

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader
                    = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

            p.guardarPC(contador + 128);
            p.setEstaCorriendo(0);
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                guardarInstruccion(line, p, contador);
                contador += 4;
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return contador;
    }

    public void guardarInstruccion(String linea, Procesador p, int direccion) {
        int inicio = 0;
        int fin = 1;
        for (int i = 0; i < linea.length(); i++) {
            char caracter = linea.charAt(i);
            if (caracter == ' ') {
                p.setMemoria(Integer.parseInt(linea.substring(inicio, i)), direccion);
                direccion++;
                inicio = i + 1;
            }
        }
        p.setMemoria(Integer.parseInt(linea.substring(inicio, linea.length())), direccion);
    }
}

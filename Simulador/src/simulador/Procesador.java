/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 * Daniel Orozco
 * Diego González        
 */
public class Procesador {
    int quantum;
    int PC;
    int Registro[];
    MemCache memCache;
    int memPrincipal[] = new int[256];
    Contexto contexto;
    boolean termino;
    
    Procesador(int quantum){
        this.quantum = quantum;
        Registro = new int[32];
        memCache = new MemCache();
        memPrincipal = new int[256];
        contexto = new Contexto();
        
    }
    
    public void agregarInstrucciones(){
        
    }
    
    /**
     * Este metodo se encarga de comprobar que instrucción se va a correr, y 
     * llamar al método correspondiente
     * 
     * @param instruccion La dirección de memoria donde se encuentra la instrucción
     */
    public void correrInstruccion(int instruccion){
        int v1 = instruccion - 128;
        int v2 = instruccion - 127;
        int v3 = instruccion - 126;        
        int v4 = instruccion - 125;
        
        switch(v1){
            case 8:
                DADDI(v2, v3, v4);
                break;
            case 32:
                DADD(v2, v3, v4);
                break;
            case 34:
                DSUB(v2, v3, v4);
                break;
            case 12:
                DMUL(v2, v3, v4);
                break;
            case 14:
                DDIV(v2, v3, v4);
                break;
            case 4:
                BEQZ(v2, v3, v4);
                break;
            case 5:
                BENZ(v2, v3, v4);
                break;
            case 3:
                JAL(v2, v3, v4);
                break;
            case 2:
                JR(v2, v3, v4);
                break;
            case 63:
                FIN(v2, v3, v4);
                break;
                
        }
    }
    
    /**Se encarga de sumar el valor del registro RY con un inmediato n, y 
     * guardarlo en el registro RX
     * 
     * @param RX Registro de destino
     * @param RY Registro sumando
     * @param n immediato sumando 
     */
    public void DADDI(int RX, int RY, int n){
        //Si el registro RX es el destino, o si uno de los registros no es
        //valido hay error
        if(RX == 0 && esValido(RX) && esValido(RY)){
            //Aqui va el error
        }
        else{
            Registro[RX] = Registro[RY] + n;    //Realiza el DADDI
            PC +=4;                             //Suma 4 al PC para pasar a la siguiente instruccion
        }
    }
    
    public void DADD(int v2, int v3, int v4){
    
    }
    
    public void DSUB(int v2, int v3, int v4){
    
    }
    
    public void DMUL(int v2, int v3, int v4){
    
    }
    
    public void DDIV(int v2, int v3, int v4){
    
    }
    
    public void BEQZ(int v2, int v3, int v4){
    
    }
    
    public void BENZ(int v2, int v3, int v4){
    
    }
    
    public void JAL(int v2, int v3, int v4){
    
    }
    
    public void JR(int v2, int v3, int v4){
    
    }
    
    public void FIN(int v2, int v3, int v4){
    
    }
    
    public boolean esValido(int RX){
        if(RX >= 0 && RX <= 32)
            return true;
        else
            return false;
    }
    
}

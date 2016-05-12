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

public class Procesador {

    int quantum;
    int PC;
    int Registro[];
    MemCache memCache;
    int memPrincipal[] = new int[256];
    Contexto contexto;
    boolean termino;
    int contadorPrograma;   //Lleva el control del número de hilillos corriendo, no confundir con el PC

    Procesador(int quantum) {
        this.quantum = quantum;
        Registro = new int[32];
        memCache = new MemCache();
        memPrincipal = new int[256];
        contexto = new Contexto();
        contadorPrograma = 0;
    }

    public void guardarPC(int PC) {
        contexto.setPC(contadorPrograma, PC);
    }

    public void ejecutarProgramas() {
        while (contexto.hayProcecesosCorriendo()) {
            if(contexto.estaCorriendo(contadorPrograma)){
                cargarContexto(contadorPrograma);
                correrPrograma();
            }
            if (contadorPrograma < 3) {
                contadorPrograma++;
            } else {
                contadorPrograma = 0;
            }
        }
    }

    public void correrPrograma() {
        for (int i = 0; i < quantum && contexto.estaCorriendo(contadorPrograma); i++) {
            int posicionCache = memCache.estaInstruccion(PC);
            if (posicionCache == -1) {
                errorDeCache(PC);
                posicionCache = memCache.estaInstruccion(PC);
                correrInstruccion(PC, posicionCache);
            }
            correrInstruccion(PC, posicionCache);
        }
        guardarContexto();
    }

    public void cargarContexto(int proceso) {
        setPC(contexto.getPC(proceso));
        for(int i = 0; i < 32; i++){
            setRegistro(i, contexto.getRegistro(proceso, i));
        }
    }
    
    public void guardarContexto() {
         contexto.setPC(contadorPrograma, PC);
         for(int i = 0; i < 32; i++){
             contexto.setRegistro(PC, i, Registro[i]);
         }
    }

    public void errorDeCache(int instruccion) {
        int posicion = instruccion - 128;
        for (int i = 0; i < 4; i++) {
            memCache.setEtiqueta(i, instruccion);
            for (int j = 0; j < 4; j++, instruccion++, posicion++) {
                memCache.setMemoria(i, j, getMemoria(posicion));
            }
        }
    }

    /**
     * Este metodo se encarga de comprobar que instrucción se va a correr, y
     * llamar al método correspondiente
     *
     * @param instruccion La dirección de memoria donde se encuentra la
     * instrucción
     */
    public void correrInstruccion(int instruccion, int posicionCache) {
        int v1 = memCache.getMemoria(posicionCache, 0);
        int v2 = memCache.getMemoria(posicionCache, 1);
        int v3 = memCache.getMemoria(posicionCache, 2);
        int v4 = memCache.getMemoria(posicionCache, 3);

        switch (v1) {
            case 8:
                System.out.println("DADDI");
                DADDI(v2, v3, v4);
                break;
            case 32:
                System.out.println("DADD");
                DADD(v2, v3, v4);
                break;
            case 34:
                System.out.println("DSUB");
                DSUB(v2, v3, v4);
                break;
            case 12:
                System.out.println("DMUL");
                DMUL(v2, v3, v4);
                break;
            case 14:
                System.out.println("DDIV");
                DDIV(v2, v3, v4);
                break;
            case 4:
                System.out.println("BEQZ");
                BEQZ(v2, v3, v4);
                break;
            case 5:
                System.out.println("BENZ");
                BENZ(v2, v3, v4);
                break;
            case 3:
                System.out.println("JAL");
                JAL(v2, v3, v4);
                break;
            case 2:
                System.out.println("JR");
                JR(v2, v3, v4);
                break;
            case 63:
                System.out.println("FIN");
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
     * Estado del metodo: Verificado
     */
    public void DADDI(int RY, int RX, int n){
        //Si el registro RX es el destino, o si uno de los registros no es
        //valido hay error
        if(!esDestinoValido(RX) || !esRegistroValido(RX) || !esRegistroValido(RY)){
            System.out.println("Error: registro invalido");
        }
        else{
            Registro[RX] = Registro[RY] + n;    //Realiza el DADDI
            PC +=4;                             //Suma 4 al PC para pasar a la siguiente instruccion
            System.out.println("Registro["+RX+"] = "+Registro[RX]);
        }
    }
    
    /**Se encarga de sumar el valor del registro RY con el valor el registro
     * RZ, y guardarlo en el registro RX
     * 
     * @param RX Registro de destino
     * @param RY Registro sumando
     * @param RZ Registro sumando 
     * Estado del metodo: Verificado
     */
    public void DADD(int RX, int RY, int RZ){
        //Si el registro RX es el destino, o si uno de los registros no es
        //valido hay error
        if(!esDestinoValido(RX) || !(esRegistroValido(RX) || !esRegistroValido(RY))){
            System.out.println("Error: registro invalido");
        }
        else{
            Registro[RX] = Registro[RY] + Registro[RZ];    //Realiza el DADDI
            PC +=4;                             //Suma 4 al PC para pasar a la siguiente instruccion
            System.out.println("Registro["+RX+"] = "+Registro[RX]);
        }
    }
    
    /**Se encarga de restarle al registro RY el valor del registro RZ y 
     * almacenarlo en RX
     * 
     * @param RX Registro donde se guarda la resta
     * @param RY Registro donde esta el minuendo 
     * @param RZ Registro donde esta el sustraendo 
     * Estado del metodo: Verificado
     */
    public void DSUB(int RX, int RY, int RZ){
        //Si el registro RX es el destino, o si uno de los registros no es
        //valido hay error
        if(!esDestinoValido(RX) || !(esRegistroValido(RX) || !esRegistroValido(RY))){
            System.out.println("Error: registro invalido");
        }
        else{
            Registro[RX] = Registro[RY] - Registro[RZ];    //Realiza el DADDI
            PC +=4;                             //Suma 4 al PC para pasar a la siguiente instruccion
            System.out.println("Registro["+RX+"] = "+Registro[RX]);
        }
    }
    
    /**Se encarga de multiplicar los registros RY y RZ y guardar el producto en
     * RZ
     * 
     * @param RX Registro donde se guarda el producto
     * @param RY Registro donde esta el factor 
     * @param RZ Registro donde esta el factor 
     * Estado del metodo: Verificado
     */
    public void DMUL(int RX, int RY, int RZ){
        //Si el registro RX es el destino, o si uno de los registros no es
        //valido hay error
        if(!esDestinoValido(RX) || !(esRegistroValido(RX) || !esRegistroValido(RY))){
            System.out.println("Error: registro invalido");
        }
        else{
            Registro[RX] = Registro[RY] * Registro[RZ];    //Realiza el DADDI
            PC +=4;                             //Suma 4 al PC para pasar a la siguiente instruccion
            System.out.println("Registro["+RX+"] = "+Registro[RX]);
        }
    }
    
    /**Se encarga de dividir los registros RY y RZ y guardar el producto en
     * RX
     * 
     * @param RX Registro donde se guarda el producto
     * @param RY Registro donde esta el factor 
     * @param RZ Registro donde esta el factor 
     * Estado del metodo: Verificado
     */
    public void DDIV(int RX, int RY, int RZ){
        //Si el registro RX es el destino, o si uno de los registros no es
        //valido hay error
        if(!esDestinoValido(RX) || !(esRegistroValido(RX) || !esRegistroValido(RY))){
            System.out.println("Error: registro invalido");
        }
        else{
            Registro[RX] = Registro[RY] / Registro[RZ];    //Realiza el DADDI
            PC +=4;                             //Suma 4 al PC para pasar a la siguiente instruccion
            System.out.println("Registro["+RX+"] = "+Registro[RX]);
        }
    }

    public void BEQZ(int v2, int v3, int v4) {
        PC += 4;
    }

    public void BENZ(int v2, int v3, int v4) {
        PC += 4;
    }

    public void JAL(int v2, int v3, int v4) {
        PC += 4;
    }

    public void JR(int v2, int v3, int v4) {
        PC += 4;
    }

    public void FIN(int v2, int v3, int v4) {
        PC += 4;
        contexto.terminarProceso(contadorPrograma);
    }

    public boolean esValido(int RX) {
        if (RX >= 0 && RX <= 32) {
            return true;
        } else {
            return false;
        }
    }

    public void setMemoria(int valor, int pocision) {
        memPrincipal[pocision] = valor;
    }

    public int getMemoria(int pocision) {
        return memPrincipal[pocision];
    }

    public void imprimirMemoria() {
        System.out.println("| DIR\t| CODIFICADO");
        for (int i = 0; i < memPrincipal.length; i += 4) {
            System.out.print("| " + (i + 128) + "\t| " + memPrincipal[i] + " " + memPrincipal[i + 1] + " "
                    + memPrincipal[i + 2] + " " + memPrincipal[i + 3] + "\n");
        }
    }
    
    public void setPC(int PC){
        this.PC = PC;
    }
    
    public void setRegistro(int registro, int valor){
        Registro[registro] = valor;
    }

        public boolean esRegistroValido(int RX){
        if(RX >= 0 && RX <= 32){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean esDestinoValido(int RX){
        if(RX != 0){
            return true;
        }
        else{
            return false;
        }
    }
    
}

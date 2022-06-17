package com.juego.j2048.core;
//TODO HECHO

import java.util.Random;

//Clase que tiene el funcionamiento del juego
public class ImplementModelo implements CoreModelo{
    private final int filSum;
    private final int colSum;
    private DataListener dataListener;
    private final int[][] data;
    private int puntActual;
    private GameEstado gameEstado = GameEstado.juego;
    private final Random random;

    //Constructor de la clase
    public ImplementModelo(int filSum, int colSum, DataListener dataListener) {
        this.filSum = filSum;
        this.colSum = colSum;
        this.dataListener = dataListener;
        data = new int[filSum][colSum];
        random = new Random();
    }

    //metodo que genera un numero para el juego
    //80% para un 2
    //20% para un 4
    private void generarNumero(){
        //Bucle que cuenta cuantos numeros vacios hay
        int numerosVaciosCont = 0;
        for(int fil = 0; fil < filSum; fil++){
            for(int col = 0; col < colSum; col++){
                if(data[fil][col] == 0){
                    numerosVaciosCont++;
                }
            }
        }

        //Si no quedan numeros vacios, el juego termina
        if(numerosVaciosCont == 0){
            gameEstado = GameEstado.loose;
            if(dataListener != null){
                dataListener.onGameOver(false);
            }
        }

        //Metodo que elige la posicion para generar un numero
        // donde no hay ninguno -> numerosVaciosCont-1
        int nuevoNumPos = random.nextInt(numerosVaciosCont);

        //Obtenemos la probabilidad de sacar un 2 o un 4 (80/20)
        float probNum2 = 0.8f;
        int nuevoNum = random.nextFloat() < probNum2 ? 2 : 4;
        //Encontramos el espacio vacio
        int numVacioPos = 0;
        for(int fil = 0; fil < filSum; fil++){
            for(int col = 0; col < colSum; col++){
                //Nos saltamos donde hay ya numeros
                if(data[fil][col] != 0){
                    continue;
                }
                //Al encontrar un sitio vacio, añadimos el nuevo numero
                if(numVacioPos == nuevoNumPos){
                    data[fil][col] = nuevoNum;
                    if(dataListener != null){
                        dataListener.onGenerarNum(fil, col, nuevoNum);
                    }
                }
                numVacioPos++;
            }
        }
    }

    @Override
    public void initData(){
        //todas las variables se inician a 0
        for(int fil = 0; fil < filSum; fil++){
            for(int col = 0; col < colSum; col++){
                data[fil][col] = 0;
            }
        }

        //Reinicia la puntuación
        puntActual = 0;
        gameEstado = GameEstado.juego;

        //Genera aleatoriamente 2 numeros
        generarNumero();
        generarNumero();
    }

    @Override
    public int[][] getData(){
        return data;
    }

    @Override
    public int getPuntActual(){
        return puntActual;
    }

    //Metodo que mueve hacia arriba
    @Override
    public void paArriba(){
        //Si el game state no es = a que se este jugando, el metodo se ignora
        if(gameEstado != GameEstado.juego){
            return;
        }

        //Booleano que comprueba si se ha movido.
        boolean saMovio = false;

        //Mueve tod.o verticalmente, hace una columna a la vez
        for(int col = 0; col < colSum; col++){
            //Inicia desde el valor 0 de col y va subiendo
            for(int fil = 0; fil < filSum; fil++){
                //encuentra el primer num debajo de la fil
                // y la mueve a la posición de la fila actual
                for(int tmpFil = fil + 1; tmpFil < filSum; tmpFil++){
                    // determina la posición , si es 0,
                    // no se necesita ninguna operación de movimiento o fusión,
                    // continua con el siguiente bucle tmpFil que itera a través de la sig pos
                    if(data[tmpFil][col] == 0){
                        continue;
                    }
                    // Si la siguiente fila no esta vacia, se determina la posición
                    if(data[fil][col] == 0){
                        // Si la fila está vacía, mueve el num directamente
                        data[fil][col] = data[tmpFil][col];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpFil,fil,col,true);
                        }
                        saMovio = true;
                        // Pos=0dpues demover el numero
                        data[tmpFil][col] = 0;
                        // Después de que el num vacio actual se ha movido a una nueva pos,
                        // break.El bucle va a la siguiente fil y necesita hacer
                        // determinar la posicion correcta para el siguiente numero
                        fil--;
                    }else if(data[fil][col] == data[tmpFil][col]){
                        // Si la posición actual de la fila es la misma que el número
                        // de la primera tarjeta no vacía debajo de la fila encontrada,
                        // entonces fusiona los números
                        data[fil][col] += data[tmpFil][col];
                        saMovio = true;
                        //Si se ha realizado un movimiento se aumenta la puntuación
                        puntActual += data[fil][col];
                        //Se llama al listener
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpFil,fil,col,true);
                            dataListener.onFusionNum(fil, col, data[fil][col], puntActual);
                        }
                        //Pos=0 dpues de fusionar nums
                        data[tmpFil][col] = 0;
                    }
                    // Si los números no son iguales, el bucle salta
                    break;
                }
            }
        }

        //Dpues de mover-fusionar se ejecutan los siguientes metodos para saber si ha terminado o no
        if (saMovio) {
            //Comprueba si ha ganado
            checkGameState();
            //Si no, se genera un numero
            generarNumero();
            //Si se ha llenado el tablero y no hay mov, se termina el juego
            checkGameState();
        }
    }

    //Metodo que mueve hacia abajo
    @Override
    public void paAbajo(){
        if(gameEstado != GameEstado.juego){
            return;
        }

        boolean saMovio = false;

        //de izq a der y de abj a arriba
        for(int col = 0; col < colSum; col++){
            for(int fil = filSum - 1; fil > 0; fil--){
                for(int tmpFil = fil - 1; tmpFil >= 0; tmpFil--){
                    if(data[tmpFil][col] == 0){
                        continue;
                    }
                    if(data[fil][col] == 0){
                        data[fil][col] = data[tmpFil][col];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpFil,fil,col,true);
                        }
                        saMovio = true;
                        data[tmpFil][col] = 0;
                        fil++;
                    }else if(data[tmpFil][col] == data[fil][col]){
                        data[fil][col] += data[tmpFil][col];
                        saMovio = true;
                        puntActual += data[fil][col];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpFil,fil,col,true);
                            dataListener.onFusionNum(fil, col, data[fil][col], puntActual);
                        }
                        data[tmpFil][col] = 0;
                    }
                    break;
                }
            }
        }

        if(saMovio){
            checkGameState();
            generarNumero();
            checkGameState();
        }
    }

    //Metodo que mueve hacia izq
    @Override
    public void paIzquierda(){
        if(gameEstado != GameEstado.juego){
            return;
        }

        boolean saMovio = false;

        //de arriba a abajo, de izquierda a derecha
        for(int fil = 0; fil < filSum; fil++){
            for(int col = 0; col < colSum; col++){
                for(int tmpCol = col + 1; tmpCol < colSum; tmpCol++){
                    if(data[fil][tmpCol] == 0){
                        continue;
                    }
                    if(data[fil][col] == 0){
                        data[fil][col] = data[fil][tmpCol];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpCol,col,fil,false);
                        }
                        saMovio = true;
                        data[fil][tmpCol] = 0;
                        col--;
                    }else if(data[fil][col] == data[fil][tmpCol]){
                        data[fil][col] += data[fil][col];
                        saMovio = true;
                        puntActual += data[fil][col];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpCol,col,fil,false);
                            dataListener.onFusionNum(fil, col, data[fil][col], puntActual);
                        }
                        data[fil][tmpCol] = 0;
                    }
                    break;
                }
            }
        }

        if(saMovio){
            checkGameState();
            generarNumero();
            checkGameState();
        }
    }

    //Metodo que mueve hacia der
    @Override
    public void paDerecha(){
        if (gameEstado != GameEstado.juego) {
            return;
        }

        boolean saMovio = false;

        // de arriba a abajo, de derecha a izquierda
        for(int fil = 0; fil < filSum; fil++){
            for(int col = colSum - 1; col >= 0; col--){
                for(int tmpCol = col - 1; tmpCol >= 0; tmpCol--){
                    if(data[fil][tmpCol] == 0){
                        continue;
                    }
                    if(data[fil][col] == 0){
                        data[fil][col] = data[fil][tmpCol];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpCol,col,fil,false);
                        }
                        saMovio = true;
                        data[fil][tmpCol] = 0;
                        col++;
                    }else if(data[fil][col] == data[fil][tmpCol]){
                        data[fil][col] += data[fil][col];
                        saMovio = true;
                        puntActual += data[fil][col];
                        if(dataListener != null){
                            dataListener.onMoverNum(tmpCol,col,fil,false);
                            dataListener.onFusionNum(fil, col, data[fil][col], puntActual);
                        }
                        data[fil][tmpCol] = 0;
                    }
                    break;
                }
            }
        }

        if(saMovio){
            checkGameState();
            generarNumero();
            checkGameState();
        }
    }

   //Metodo que determina si el juego ha terminado
    private void checkGameState() {
        for(int fil = 0; fil < fil; fil++){
            for(int col = 0; col < colSum; col++){
                //Si un numero es 2048 es win
                if(data[fil][col] == 2048){
                    gameEstado = GameEstado.win;
                    if(dataListener != null){
                        dataListener.onGameOver(true);
                    }
                }
            }
        }
        //si no hay un num=2048 se comprueba si hay movimiento posible
        if(!isNumMovible()) {
            //Si no es win y no se puede mover, es game over
            gameEstado = GameEstado.loose;
            if(dataListener != null){
                dataListener.onGameOver(false);
            }
        }
    }

    //Metodo que comprueba si se puede hace un movimiento
    private boolean isNumMovible() {
        //Comprueba si se puede mover horizontalmente
        for(int fil = 0; fil < filSum; fil++){
            for(int col = 0; col < colSum-1; col++){
                if(
                    data[fil][col] == 0 ||
                    data[fil][col + 1] == 0 ||
                    data[fil][col] == data[fil][col + 1]
                ){
                    return true;
                }
            }
        }

        //Comprueba si se puede mover verticalmente
        for(int col = 0; col < colSum; col++){
            for(int fil = 0; fil < filSum - 1; fil++){
                if(
                    data[fil][col] == 0 ||
                    data[fil + 1][col] == 0 ||
                    data[fil][col] == data[fil + 1][col]
                ){
                    return true;
                }
            }
        }
        return false;
    }
}
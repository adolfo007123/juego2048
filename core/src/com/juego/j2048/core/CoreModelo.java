package com.juego.j2048.core;
//TODO HECHO

//Interfaz que se utiliza para el funcionamiento del juego
public interface CoreModelo {
    //enumeracion de estado del juego
    enum GameEstado {
        juego,
        win,
        loose;
    }
    //inicialización de datos
    // crea una instancia del modelo de datos y llama a este método al iniciar el juego
    void initData();

    //Obtiene los datos
    int[][] getData();

    //Consigue la puntuación actual
    int getPuntActual();

    //Movimientos
    void paArriba();

    void paAbajo();

    void paIzquierda();

    void paDerecha();

    //Listener que hace que obtiene los datos necesarios al ejecutar esos metodos y hace la animacion correspondiente
    interface DataListener{
        // Metodo que se llama al generar los numeros
        void onGenerarNum(int fila, int col, int num);

        //Metodo que se llama al mover un numero
        void onMoverNum(int ini, int fin, int col, boolean isUpOrDown);

        //Metodo que se llama al juntarse numeros
        void onFusionNum(int filaAfterMerge, int colAfterMerge, int numAfterMerge, int puntuacionAfterMerger);

        //Metodo que se llama cuando se gana (2048)
        void onGameOver(boolean isWin);
    }

    class Builder{
        public static CoreModelo crearDataModel(int filSum, int colSum, DataListener dataListener) {
            return new ImplementModelo(filSum, colSum, dataListener);
        }
    }
}
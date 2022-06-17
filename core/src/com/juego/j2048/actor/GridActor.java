package com.juego.j2048.actor;
//TODO HECHO

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.juego.j2048.J2048;
import com.juego.j2048.actor.base.BaseActor;
import com.juego.j2048.core.CoreModelo;
import com.juego.j2048.recursos.Recursos;

//Clase que maneja el pos y mov de las cajas y se usa como ref para la pos de los demas actores
public class GridActor extends BaseActor{

    //num de filas de cajas
    private static final int BOX_FILAS = 4;
    private static final int BOX_COLS = 4;

    //Matriz donde van el bg de las cajas
    private final BoxActor[][] bgBox = new BoxActor[BOX_FILAS][BOX_COLS];


    //matriz donde van las cajas
    private final BoxActor[][] boxs = new BoxActor[BOX_FILAS][BOX_COLS];

    //Sonido al mover y fusionar
    private Sound moverSound;
    private Sound fusionSound;

    //llamamos a la interface
    private CoreModelo coreModelo;

    public GridActor(J2048 mainJuego){
        super(mainJuego);
        init();
    }

    private void init(){
        //background img
        Image bgImage = new Image(getJ2048().getAtlas().findRegion(Recursos.Assets.GRID_BACKGROUND));

        addActor(bgImage);

        // Establecer la anchura y la altura
        setSize(bgImage.getWidth(), bgImage.getHeight());

        for(int fil = 0; fil < BOX_FILAS; fil++){
            for(int col = 0; col < BOX_COLS; col++){
                bgBox[fil][col] = new BoxActor(getJ2048());
                addActor(bgBox[fil][col]);
                boxs[fil][col] = new BoxActor(getJ2048());
                boxs[fil][col].setOrigin(Align.center);
                addActor(boxs[fil][col]);
            }
        }

        // Obtener la anchura y la altura de la caja
        float boxWidth = bgBox[0][0].getWidth();
        float boxHeight = bgBox[0][0].getHeight();

        // Calcular el tamaño de los huecos horizontales y verticales en el GridGroup
        // después de haber alineado todas las cajas
        float horizontalInterval = (getWidth() - BOX_COLS * boxWidth) / (BOX_COLS + 1);
        float verticalInterval = (getHeight() - BOX_FILAS * boxHeight) / (BOX_FILAS + 1);

        //Coloca todas las cajas y los num en la screen
        float boxY;
        for(int fil = 0; fil < BOX_FILAS; fil++){
            //Cordenadas del eje y
            boxY = getHeight() - (verticalInterval + boxHeight) * (fil + 1);
            for(int col = 0; col < BOX_COLS; col++){
                bgBox[fil][col].setPosition(
                        horizontalInterval + (boxWidth + horizontalInterval) * col,
                        boxY
                );
                boxs[fil][col].setPosition(
                        horizontalInterval + (boxWidth + horizontalInterval) * col,
                        boxY
                );
            }
        }

        //llamamos el listener
        addListener(new InputListenerImpl());

        //cargamos los datos
        coreModelo = CoreModelo.Builder.crearDataModel(BOX_FILAS, BOX_COLS, new DataListenerImpl());
        coreModelo.initData();
        addDataToBoxGroup();

        //Cargamos el sonido
        moverSound =getJ2048().getAssetManager().get(Recursos.Audio.MOVER_AUDIO);
        fusionSound =getJ2048().getAssetManager().get(Recursos.Audio.FUSION_AUDIO);
    }


    public void paArriba(){
        coreModelo.paArriba();
        moverSound.play();
    }

    public void paAbajo(){
        coreModelo.paAbajo();
        moverSound.play();
    }

    public void paIzquierda(){
        coreModelo.paIzquierda();
        moverSound.play();
    }

    public void paDerecha(){
        coreModelo.paDerecha();
        moverSound.play();
    }

    //Reiniciamos juego
    public void restartGame() {
        coreModelo.initData();
        addDataToBoxGroup();
    }

    //Obtenenemos los datos a traves de CoreModel y
    // añadimos la matriz numérica generada a BoxGroups
    private void addDataToBoxGroup(){
        int[][] data = coreModelo.getData();
        for(int fil = 0; fil < BOX_FILAS; fil++){
            for(int col = 0; col < BOX_COLS; col++){
                boxs[fil][col].setNum(data[fil][col]);
            }
        }
    }


    //listener que implementa los metodos de deslizamiento de las cajas
    private class InputListenerImpl extends InputListener{
        private float touchDownX;
        private float touchDownY;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            touchDownX = x;
            touchDownY = y;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            float moveX = x - touchDownX;
            float moveY = y - touchDownY;
            //Comprueba la direccion del movimiento en los ejes x e y para saber que metodo de movimiento utilizar
            if (Math.abs(moveX) >= 20 && Math.abs(moveX) > Math.abs(moveY)){
                if (moveX > 0){
                    paDerecha();
                }else{
                    paIzquierda();
                }
            } else if (Math.abs(moveY) >= 20 && Math.abs(moveY) > Math.abs(moveX)){
                if (moveY > 0){
                    paArriba();
                }else{
                    paAbajo();
                }
            }
        }
    }

    //listener que implementa las animaciones a las acciones realizadas
    private class DataListenerImpl implements CoreModelo.DataListener{
        @Override
        public void onGenerarNum(int fil, int col, int num){
            boxs[fil][col].setScale(0.2f);
            ScaleToAction scaleTo = Actions.scaleTo(1.0f, 1.0f, 0.2f);
            boxs[fil][col].addAction(scaleTo);
        }

        @Override
        public void onMoverNum(final int begin, final int end, final int fixed, boolean isUpOrDown){

            //variables que almacenan la pos inicial de las cajas
            final int beginFil;
            final int beginCol;
            
            //Variables que almacenan las pos iniciales y finales en x e y
            final float beginX;
            final float beginY;
            float endX;
            float endY;

            if(isUpOrDown){
                // Si se mueve arriba-abajo, el num de columna no se mueve a fixed,
                // begin es el num de fila inicial y end es el num de fila final
                beginFil=begin;
                beginCol=fixed;
                beginX = boxs[begin][fixed].getX();
                beginY = boxs[begin][fixed].getY();
                endX = bgBox[end][fixed].getX();
                endY = bgBox[end][fixed].getY();
            }else{
                // Si se mueve izq-der, el num de fila no se mueve a fixed,
                // begin es el num de columna inicial y end es el num de columna final
                beginFil=fixed;
                beginCol=begin;
                beginX=boxs[fixed][begin].getX();
                beginY=boxs[fixed][begin].getY();
                endX = bgBox[fixed][end].getX();
                endY = bgBox[fixed][end].getY();
            }

            //Metodo del movimiento
            SequenceAction moveToAction = Actions.sequence(
                Actions.moveTo(endX, endY, 0.1f),
                Actions.run(
                    new Runnable(){
                        @Override
                        public void run(){
                            // Restablecer la caja movida a su posición original,
                            // una vez completado el movimiento
                            boxs[beginFil][beginCol].setPosition(beginX, beginY);
                            addDataToBoxGroup();
                        }
                    }
                )
            );
            // Coloca la caja que muestra la acción en la parte superior
            // para que la animación no quede tapada
            boxs[beginFil][beginCol].toFront();
            boxs[beginFil][beginCol].addAction(moveToAction);
            moverSound.play();
        }

        //metodo que se ejecuta cuando 2 numeros se fusionan
        @Override
        public void onFusionNum(int filAfterMerge, int colAfterMerge, int numAfterMerge, int currentScoreAfterMerger){
            boxs[filAfterMerge][colAfterMerge].setScale(0.8f);
            SequenceAction sequenceAction = Actions.sequence(
                    Actions.scaleTo(1.2f, 1.2f, 0.1f),
                    Actions.scaleTo(1.0f, 1.0f, 0.1f)
            );
            boxs[filAfterMerge][colAfterMerge].addAction(sequenceAction);
            fusionSound.play();
            //aumenta puntuación
            getJ2048().getGameScreen().getGameEstado().addPuntActual(numAfterMerge);
        }
        //Llamar al método showGameOverStage() en JuegoScreen para mostrar el escenario del juego y pasar la puntuación actual del modelo de datos
        @Override
        public void onGameOver(boolean isWin){
            getJ2048().getGameScreen().showGameOverStage(isWin, coreModelo.getPuntActual());
        }
    }
}
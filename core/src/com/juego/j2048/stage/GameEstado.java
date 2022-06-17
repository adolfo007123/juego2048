package com.juego.j2048.stage;
//TODO HECHO

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.juego.j2048.J2048;
import com.juego.j2048.actor.GridActor;
import com.juego.j2048.actor.InferiorActor;
import com.juego.j2048.actor.SuperiorActor;
import com.juego.j2048.stage.base.BaseEstado;

//Escenario principal del juego, añade a los actores
//Añade los sonidos
//Guardar la mejor puntuación cuando se destruye.
//Añadir animaciones al generar números y fusionar números.
public class GameEstado extends BaseEstado{
    private GridActor gridActor;
    private SuperiorActor topActor;
    private InferiorActor inferiorActor;

    public GameEstado(J2048 mainJuego, Viewport viewport){
        super(mainJuego, viewport);
        init();
    }

    private void init(){
        //gridActor centrado al escenario
        // los demás actores utilizan este gridActor como referencia para su posición

        gridActor = new GridActor(getJ2048());
        gridActor.setPosition(
                getWidth() / 2 - gridActor.getWidth() / 2,
                getHeight() / 2 - gridActor.getHeight() / 2
        );
        addActor(gridActor);

        //Creamos superiorActor
        topActor = new SuperiorActor(getJ2048());
        //Pos en ref a grid actor
        float gridGroupTopY = gridActor.getY() + gridActor.getHeight();
        topActor.setPosition(
                getWidth() / 2 - gridActor.getWidth() / 2,
                gridGroupTopY + 20
        );
        addActor(topActor);

        //Inicializamos la puntuacion
        topActor.getPuntActualBG().setPunt(0);

        //get mejor puntuacion
        Preferences prefs = Gdx.app.getPreferences("game_score_pref");
        int bestScore = prefs.getInteger("best_score", 0);
        // set mejor puntuacion
        topActor.getMejorPuntBG().setPunt(bestScore);

        //Creamos inferiorActor y sus 2 botones
        inferiorActor=new InferiorActor(getJ2048());
        //pos en ref de gridActor
        inferiorActor.setX(getWidth() / 2 - inferiorActor.getWidth() / 2);
        inferiorActor.setY(gridActor.getY()-inferiorActor.getHeight()-20);
        addActor(inferiorActor);


    }

    public void addPuntActual(int scoreStep){
        //aumentar la puntuacion
        topActor.getPuntActualBG().addPunt(scoreStep);
        int currentSore = topActor.getPuntActualBG().getPunt();
        int bestSore = topActor.getMejorPuntBG().getPunt();
        //si se cumple la condicion se actualiza la mejorpunt
        if (currentSore > bestSore) {
            topActor.getMejorPuntBG().setPunt(currentSore);
        }
    }

    //Reiniciar juego
    public void restartGame(){
        gridActor.restartGame();
        //ponemos puntuacion a 0
        topActor.getPuntActualBG().setPunt(0);
    }

    @Override
    public void dispose(){
        super.dispose();
        Preferences prefs = Gdx.app.getPreferences("game_score_pref");
        prefs.putInteger("best_score", topActor.getMejorPuntBG().getPunt());
        prefs.flush();
    }
}
package com.juego.j2048.screens;
//TODO HECHO
//Clase que contiene la pantalla del juego y llama a los metodos necesarios para que funcione

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.juego.j2048.J2048;
import com.juego.j2048.stage.GameOverEstado;
import com.juego.j2048.stage.GameEstado;

//Clase que tiene la pantalla del juego
public class JuegoScreen extends ScreenAdapter{

    private J2048 mainJuego;
    private GameEstado gameEstado;
    Color bgColor = new Color(0xF8F8F0FF);
    private GameOverEstado gameOverStage;

    //Constructor clase
    public JuegoScreen(J2048 mainJuego){
        this.mainJuego = mainJuego;
        init();
    }

    //inicializacion
    private void init(){
        gameEstado = new GameEstado(
                mainJuego,
                new StretchViewport(
                        mainJuego.getWorldWidth(),
                        mainJuego.getWorldHeight()
                )
        );

        //Estado fin juego, no visible default
        gameOverStage=new GameOverEstado(
                mainJuego,
                new StretchViewport(
                        mainJuego.getWorldWidth(),
                        mainJuego.getWorldHeight()
                )
        );
        gameOverStage.setVisible(false);

        //Llama a los procesos del inicio del juego
        Gdx.input.setInputProcessor(gameEstado);
    }

    @Override
    public void render(float f){
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //llama al escenario principal del juego y lo "pinta"
        gameEstado.act();
        gameEstado.draw();

        //Estado fin juego
        if (gameOverStage.isVisible()){
            gameOverStage.act();
            gameOverStage.draw();
        }
    }

    @Override
    public void dispose(){
        // Cuando se destruye una escena, todos los escenarios se destruyen al mismo tiempo
        gameEstado.dispose();
        if (gameOverStage != null){
            gameOverStage.dispose();
        }
    }

    public GameEstado getGameEstado(){
        return gameEstado;
    }

    // Mostrar la estado final y muestra la visualización del texto
    // y la puntuación en la etapa final
    public void showGameOverStage(boolean isWin, int score){
        gameOverStage.setGameOverEstado(isWin, score);
        gameOverStage.setVisible(true);
        Gdx.input.setInputProcessor(gameOverStage);
    }

    //Oculta texto final y reinicia el juego
    public void restartGame(){
        gameOverStage.setVisible(false);
        Gdx.input.setInputProcessor(gameEstado);
        gameEstado.restartGame();
    }
}
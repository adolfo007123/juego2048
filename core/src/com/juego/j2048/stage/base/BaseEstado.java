package com.juego.j2048.stage.base;
//TODO HECHO

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.juego.j2048.J2048;

//clase base de los escenarios que heredan los demas
public class BaseEstado extends Stage{
    private J2048 mainJuego;
    private boolean visible = true;

    public BaseEstado(J2048 mainJuego, Viewport viewport){
        super(viewport);
        this.mainJuego = mainJuego;
    }

    public J2048 getJ2048(){
        return mainJuego;
    }

    public void setJ2048(J2048 mainJuego){
        this.mainJuego = mainJuego;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
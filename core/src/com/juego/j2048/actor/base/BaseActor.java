package com.juego.j2048.actor.base;
//TODO HECHO

import com.badlogic.gdx.scenes.scene2d.Group;
import com.juego.j2048.J2048;

//Clase base de los actores de la cual heredan metodos necesarios
public class BaseActor extends Group{
    private J2048 mainJuego;
    public BaseActor(J2048 mainJuego){
        this.mainJuego =mainJuego;
    }

    public J2048 getJ2048(){
        return mainJuego;
    }

    public void setJ2048(J2048 mainJuego){
        this.mainJuego = mainJuego;
    }
}

package com.juego.j2048.actor;
//TODO HECHO

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.juego.j2048.J2048;
import com.juego.j2048.actor.base.BaseActor;
import com.juego.j2048.recursos.Recursos;

//clase que contiene el logotipo del juego y la img de fondo del puntuaje
public class SuperiorActor extends BaseActor{
    private Image logoImagen;
    //Puntuacion actual
    private PuntuacionActor puntActualBG;
    //mejor puntuacion
    private PuntuacionActor mejorPuntBG;

    public SuperiorActor(J2048 mainJuego){
        super(mainJuego);
        init();
    }
    private void init(){
        // primero añadimos la puntuación actual como ref para la pos
        puntActualBG = new PuntuacionActor(getJ2048(), getJ2048().getAtlas().findRegion(Recursos.Assets.PUNT_ACTUAL));
        //Establecemos la altura y anchura
        setSize(getJ2048().getWorldWidth(), puntActualBG.getHeight());

        //Añadimos el logo de 2048
        logoImagen = new Image(getJ2048().getAtlas().findRegion(Recursos.Assets.LOGO));
        logoImagen.setX(20);
        //ajustamos pos del logo
        logoImagen.setY(getY() + (getHeight() / 2 - logoImagen.getHeight() / 2));
        addActor(logoImagen);

        //Colocamos la puntuacion a la derecha del logo
        puntActualBG.setX(logoImagen.getX() + logoImagen.getWidth() + 20);
        puntActualBG.setY(getHeight() - puntActualBG.getHeight());
        addActor(puntActualBG);

        //Mejor puntuacion
        mejorPuntBG = new PuntuacionActor(getJ2048(), getJ2048().getAtlas().findRegion(Recursos.Assets.PUNT_MEJOR));
        mejorPuntBG.setX(puntActualBG.getX() + puntActualBG.getWidth() + 10);
        mejorPuntBG.setY(getHeight() - puntActualBG.getHeight());
        addActor(mejorPuntBG);
    }
    public PuntuacionActor getPuntActualBG(){
        return puntActualBG;
    }

    public PuntuacionActor getMejorPuntBG(){
        return mejorPuntBG;
    }
}
package com.juego.j2048.actor;
//TODO HECHO

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.juego.j2048.J2048;
import com.juego.j2048.actor.base.BaseActor;

public class PuntuacionActor extends BaseActor{
    private Image bgImage;
    private Label puntActual;
    private int punt = 99999;

    public PuntuacionActor(J2048 mainJuego, TextureRegion bgRegion){
        super(mainJuego);
        init(bgRegion);
    }

    private void init(TextureRegion bgRegion){
        //Ajustamos anchura y altura
        setSize(bgRegion.getRegionWidth(), bgRegion.getRegionHeight());
        bgImage = new Image(bgRegion);
        addActor(bgImage);

        //creamos estilo y cogemos la fuente
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getJ2048().getBitmapFont();

        //creamos el txt de la puntuacion
        puntActual = new Label(String.valueOf(punt), style);

        //Ponemos el tamaño de las letras
        puntActual.setFontScale(0.4F);

        //ajustamos la altura y anchura del scoreLabel
        puntActual.setSize(puntActual.getPrefWidth(), puntActual.getPrefHeight());

        //Centramos el texto
        puntActual.setX(getWidth() / 2 - puntActual.getWidth() / 2);
        puntActual.setY(18);

        addActor(puntActual);
    }

    public int getPunt(){
        return punt;
    }

    public void setPunt(int punt){
        this.punt = punt;
        puntActual.setText(String.valueOf(punt));

        //establecemos el txt y lo ajustamos
        puntActual.setWidth(puntActual.getPrefWidth());
        puntActual.setX(getWidth() / 2 - puntActual.getWidth() / 2);
    }

    public void addPunt(int scoreStep) {
        setPunt(this.punt + scoreStep);
    }
}
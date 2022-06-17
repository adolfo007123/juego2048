package com.juego.j2048.actor;
//TODO HECHO

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.juego.j2048.J2048;
import com.juego.j2048.actor.base.BaseActor;
import com.juego.j2048.recursos.Recursos;

//Clase que maneja las cajas que contienen los numeros
public class BoxActor extends BaseActor{

    //fondo de la caja
    private Image bgImage;
    private Label numLabel;
    private int num;

    public BoxActor(J2048 mainJuego){
        super(mainJuego);
        init();
    }

    private void init(){
        bgImage = new Image(getJ2048().getAtlas().findRegion(Recursos.Assets.BOX_BACKGROUND));
        addActor(bgImage);

        // Establecer la anchura y la altura,
        // cada imagen de fondo es un grupo de caja, utilizando la anchura y la altura del fondo
        // de la caja como la anchura y la altura del grupo
        setSize(bgImage.getWidth(), bgImage.getHeight());

        //Configurar la etiqueta de la caja para que muestre los números
        // Establecer el estilo de la caja
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getJ2048().getBitmapFont();
        style.fontColor = new Color(0x5E5B51FF);

        numLabel = new Label("0", style);
        numLabel.setFontScale(0.6f);

        //ajustamos el ancho del texto
        numLabel.setSize(numLabel.getPrefWidth(), numLabel.getPrefHeight());

        //centramos la etiqueta del texto
        numLabel.setX(getWidth() / 2 - numLabel.getWidth() / 2);
        numLabel.setY(getHeight() / 2 - numLabel.getHeight() / 2);

        addActor(numLabel);
        setNum(num);
    }

    //metodo que establece los numeros a las cajas
    public void setNum(int num){
        this.num = num;
        if (this.num == 0){
            //Si es 0, no se muestra ningún texto
            numLabel.setText("");
        }else{
            numLabel.setText(String.valueOf(this.num));
        }

        //Al cambiar num, la anchura del texto cambia,
        // es necesario restablecer la anchura de la etiqueta y
        // volver a centrarla horizontalmente
        numLabel.setWidth(numLabel.getPrefWidth());
        numLabel.setX(getWidth() / 2 - numLabel.getWidth() / 2);

        // Establecer el color del fondo de la tarjeta según los diferentes números
        switch(this.num){
            case 2:{
                bgImage.setColor(Recursos.BoxColors.RGBA_2);
                break;
            }
            case 4:{
                bgImage.setColor(Recursos.BoxColors.RGBA_4);
                break;
            }
            case 8:{
                bgImage.setColor(Recursos.BoxColors.RGBA_8);
                break;
            }
            case 16:{
                bgImage.setColor(Recursos.BoxColors.RGBA_16);
                break;
            }
            case 32:{
                bgImage.setColor(Recursos.BoxColors.RGBA_32);
                break;
            }
            case 64:{
                bgImage.setColor(Recursos.BoxColors.RGBA_64);
                break;
            }
            case 128:{
                bgImage.setColor(Recursos.BoxColors.RGBA_128);
                break;
            }
            case 256: {
                bgImage.setColor(Recursos.BoxColors.RGBA_256);
                break;
            }
            case 512: {
                bgImage.setColor(Recursos.BoxColors.RGBA_512);
                break;
            }
            case 1024: {
                bgImage.setColor(Recursos.BoxColors.RGBA_1024);
                break;
            }
            case 2048: {
                bgImage.setColor(Recursos.BoxColors.RGBA_2048);
                break;
            }
            default: {
                bgImage.setColor(Recursos.BoxColors.RGBA_0);
                break;
            }
        }
    }
}

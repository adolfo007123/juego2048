package com.juego.j2048.actor;
//TODO HECHO

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.juego.j2048.J2048;
import com.juego.j2048.actor.base.BaseActor;
import com.juego.j2048.recursos.Recursos;

public class InferiorActor extends BaseActor{
    private Button restartButton;
    private Button exitButton;

    public InferiorActor(J2048 mainJuego){
        super(mainJuego);
        init();
    }
    private void init(){
        //Boton de reinicio
        Button.ButtonStyle restartStyle=new Button.ButtonStyle();
        //cargamos estilo de los botones
        restartStyle.up=new TextureRegionDrawable(
                getJ2048().getAtlas().findRegion(Recursos.Assets.RESTART_BUTTON,1)
        );
        restartStyle.down=new TextureRegionDrawable(
                getJ2048().getAtlas().findRegion(Recursos.Assets.RESTART_BUTTON,2)
        );
        restartButton=new Button(restartStyle);
        // El botón está centrado en la mitad izquierda de la pantalla
        restartButton.setX(getJ2048().getWorldWidth()/4-restartButton.getPrefWidth()/2);
        //Listener del botón
        restartButton.addListener(
            new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    //reinicio del juegoi
                    getJ2048().getGameScreen().restartGame();
                }
            }
        );
        addActor(restartButton);

        setSize(getJ2048().getWorldWidth(), restartButton.getHeight());

        //Boton para salir del juego
        Button.ButtonStyle exitStyle=new Button.ButtonStyle();
        exitStyle.up=new TextureRegionDrawable(
                getJ2048().getAtlas().findRegion(Recursos.Assets.EXIT_BUTTON,1)
        );
        exitStyle.down=new TextureRegionDrawable(
                getJ2048().getAtlas().findRegion(Recursos.Assets.EXIT_BUTTON,2)
        );
        exitButton=new Button(exitStyle);
        exitButton.setX(restartButton.getX()+restartButton.getPrefWidth()+50);

        //Listener del boton
        exitButton.addListener(
            new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    //Salir de la app
                    Gdx.app.exit();
                }
            }
        );
        addActor(exitButton);
    }

    @Override
    public J2048 getJ2048(){
        return super.getJ2048();
    }

    @Override
    public void setJ2048(J2048 mainJuego){
        super.setJ2048(mainJuego);
    }
}
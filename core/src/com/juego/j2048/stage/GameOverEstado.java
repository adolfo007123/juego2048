package com.juego.j2048.stage;
//TODO HECHO

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.juego.j2048.J2048;
import com.juego.j2048.recursos.Recursos;
import com.juego.j2048.stage.base.BaseEstado;

//Clase del estado cuando se pierde el juego
public class GameOverEstado extends BaseEstado{

    //panel de puntuacion y label
    private Image puntImage;
    private Label puntLabel;
    private Label mejorPuntLabel;

    //img game over y win
    private Image gameoverImage;
    private Image winText;

    //Restart buttom
    private Button restartButton;

    public GameOverEstado(J2048 mainJuego, Viewport viewport){
        super(mainJuego, viewport);
        init();
    }

    private void init(){
        //Img fin de partida
        puntImage = new Image(getJ2048().getAtlas().findRegion(Recursos.Assets.SCORE_BOARD));
        //set pos
        puntImage.setPosition(
                getWidth() / 2 - puntImage.getWidth() / 2,
                getHeight() / 2 - puntImage.getHeight() / 2
        );
        addActor(puntImage);

        //imagenes por win y loose
        winText = new Image(getJ2048().getAtlas().findRegion(Recursos.Assets.VICTORY_TEXT));
        gameoverImage = new Image(getJ2048().getAtlas().findRegion(Recursos.Assets.OVER_TEXT));

        //Txt punt
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getJ2048().getBitmapFont();
        puntLabel = new Label("", style);
        puntLabel.setColor(Color.BLACK);
        addActor(puntLabel);

        //txt mej punt
        mejorPuntLabel = new Label("", style);
        mejorPuntLabel.setColor(new Color(0x808080FF));
        mejorPuntLabel.setFontScale(0.4f);
        addActor(mejorPuntLabel);

        //boton restart
        Button.ButtonStyle restartStyle = new Button.ButtonStyle();
        //img para el boton antes de pulsar
        restartStyle.up = new TextureRegionDrawable(getJ2048().getAtlas().findRegion(Recursos.Assets.OVER_RESTART, 1));
        //img para el boton pulsado
        restartStyle.down = new TextureRegionDrawable(getJ2048().getAtlas().findRegion(Recursos.Assets.OVER_RESTART, 2));
        restartButton = new Button(restartStyle);
        //set pos
        restartButton.setPosition(
                getWidth() / 2 - restartButton.getWidth() / 2,
                puntImage.getY() - restartButton.getHeight() - 10
        );
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //ocultar gameoverestado y se vuelve al principal
                getJ2048().getGameScreen().restartGame();
            }
        });
        addActor(restartButton);
    }

    public void setGameOverEstado(boolean isWin, int score){
        //Se muestra imgwin si se gana y imggameover si se pierda
        if (isWin){
            //set pos
            winText.setPosition(
                    getWidth() / 2 - winText.getWidth() / 2,
                    puntImage.getY() + puntImage.getHeight() - 100
            );
            addActor(winText);
        }else{
            gameoverImage.setPosition(
                    getWidth() / 2 - gameoverImage.getWidth() / 2,
                    puntImage.getY() + puntImage.getHeight() - 100
            );
            addActor(gameoverImage);
        }

        puntLabel.setText(score);
        puntLabel.setAlignment(Align.center);
        puntLabel.setPosition(
                puntImage.getX() + puntImage.getPrefWidth() / 2,
                puntImage.getY() + puntImage.getPrefHeight() / 2 - 30
        );

        //get mejpunt y se muestra
        Preferences prefs = Gdx.app.getPreferences("game_score_pref");
        mejorPuntLabel.setText(prefs.getInteger("best_score", 0));
        mejorPuntLabel.setPosition(
                puntImage.getX() + puntImage.getWidth() / 2 + 10,
                puntImage.getY() + 123
        );
    }
}
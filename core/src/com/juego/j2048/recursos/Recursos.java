package com.juego.j2048.recursos;

import com.badlogic.gdx.graphics.Color;

//Interfaz para cargar los recursos al llamarla.
public interface Recursos {
    interface BoxColors {
        Color RGBA_0 = new Color(0xCCC0B3FF);
        Color RGBA_2 = new Color(0xEEE4DAFF);
        Color RGBA_4 = new Color(0xEDE0C8FF);
        Color RGBA_8 = new Color(0xF2B179FF);
        Color RGBA_16 = new Color(0xF49563FF);
        Color RGBA_32 = new Color(0xF5794DFF);
        Color RGBA_64 = new Color(0xF55D37FF);
        Color RGBA_128 = new Color(0xEEE863FF);
        Color RGBA_256 = new Color(0xEDB04DFF);
        Color RGBA_512 = new Color(0xECB04DFF);
        Color RGBA_1024 = new Color(0xEB9437FF);
        Color RGBA_2048 = new Color(0xEA7821FF);
    }

    interface Audio{
        String MOVER_AUDIO ="audio/move.mp3";
        String FUSION_AUDIO ="audio/merge.wav";
    }

    interface Assets{
        String GRID_BACKGROUND="grid_bg";
        String PUNT_ACTUAL ="current_score";
        String PUNT_MEJOR ="best_score";
        String BOX_BACKGROUND ="card_bg";
        String LOGO="logo";
        String VICTORY_TEXT="victory_text";
        String OVER_TEXT="over_text";
        String SCORE_BOARD="score_board";
        String OVER_RESTART="over_restart";
        String RESTART_BUTTON="restart_btn";
        String EXIT_BUTTON="exit_btn";
    }
}
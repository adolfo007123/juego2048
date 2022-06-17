package com.juego.j2048;
//TODO HECHO
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.juego.j2048.screens.JuegoScreen;

//Clase principal del juego donde se declaran elementos necesarios y
// se inicia el juego a traves de los metodos pertinentes.

public class J2048 extends Game{
	//altura y anchura del mundo
	private float worldWidth;
	private float worldHeight;

	AssetManager assetManager;
	private TextureAtlas atlas;
	private BitmapFont bitmapFont;
	private JuegoScreen juegoScreen;

	@Override
	public void create (){
		//declaramos la anchura y altura del mundo
		worldWidth = 720;
		worldHeight = Gdx.graphics.getHeight() * worldWidth / Gdx.graphics.getWidth();

		//Inicializamos el assetmanager
		assetManager = new AssetManager();

		//Cargamos elementos de asset
		assetManager.load("atlas/game.atlas", TextureAtlas.class);
		assetManager.load("font/bitmap_font.fnt", BitmapFont.class);
		assetManager.load("audio/move.mp3", Sound.class);
		assetManager.load("audio/merge.wav", Sound.class);

		//notificamos que ya hemos cargado lo necesario
		assetManager.finishLoading();

		//Declaramos el atlas y el bitmapfont
		atlas = assetManager.get("atlas/game.atlas", TextureAtlas.class);
		bitmapFont = assetManager.get("font/bitmap_font.fnt", BitmapFont.class);

		//declaramos la screen y la seteamos
		juegoScreen = new JuegoScreen(this);
		setScreen(juegoScreen);
	}

	@Override
	public void dispose(){
		super.dispose();
		//Cerramos la screen
		if (juegoScreen != null) {
			juegoScreen.dispose();
		}
		//Y descargamos los datos
		if (assetManager != null) {
			assetManager.dispose();
		}
	}

	public JuegoScreen getGameScreen(){
		return juegoScreen;
	}

	public AssetManager getAssetManager(){
		return assetManager;
	}

	public float getWorldWidth(){
		return worldWidth;
	}

	public float getWorldHeight(){
		return worldHeight;
	}

	public TextureAtlas getAtlas(){
		return atlas;
	}

	public BitmapFont getBitmapFont(){
		return bitmapFont;
	}
}
package br.com.forca.beta.services.utils;

import android.content.Context;
import android.media.MediaPlayer;

import br.com.forca.R;


public class SoundManager{

    private MediaPlayer mp;

    private static SoundManager soundManager;

  
    private SoundManager(Context ctx) {
	mp = MediaPlayer.create(ctx, R.raw.som_fundo);
	mp.setLooping(true);
	
    }

    public void play() {
	mp.start();
    }

    public void stop() {
	mp.stop();
    }
    public void pause() {
   	mp.pause();
       }

    public static SoundManager getInstaced(Context ctx) {
	if(soundManager == null){
	    soundManager = new SoundManager(ctx);
	}
	return soundManager;
    }
}

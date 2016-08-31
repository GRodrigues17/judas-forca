package br.com.forca.beta.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;

import br.com.forca.R;
import br.com.forca.beta.services.facade.ConfiguracaoFacade;
import br.com.forca.beta.services.facade.ConfiguracaoFacadeImp;

/**
 *
 * @author G Rodrigues
 * @date 10/06/2013
 *
 * This class is responsable about sound avaible
 */


public class SplashActivity extends Activity implements Runnable {

    private ConfiguracaoFacade configuracaoFacade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        configuracaoFacade = ConfiguracaoFacadeImp
                .getInstacia(SplashActivity.this);
        String somStatus = new String();
        somStatus = configuracaoFacade.getPreferenceString("audioAtivo");
        if (somStatus.length() == 0) {
            configuracaoFacade.setPreference("audioAtivo", "true");
            somStatus = "true";
        }
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
            if (somStatus.equals("true")) {
                configuracaoFacade.play();
            } else {
                configuracaoFacade.pause();
            }
        } else {
            configuracaoFacade.pause();
        }

        Handler handler = new Handler();
        handler.postDelayed(this, 5000);

    }


    @Override
    protected void onPause() {
        super.onPause();
        configuracaoFacade.pause();
    }

    @Override
    public void run() {
        Intent intent = new Intent(this, ConfiguracaoActivity.class);
        startActivity(intent);
        finish();

    }

}
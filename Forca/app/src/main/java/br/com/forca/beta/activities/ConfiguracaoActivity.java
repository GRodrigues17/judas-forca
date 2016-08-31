package br.com.forca.beta.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ToggleButton;

import java.util.List;

import br.com.forca.R;
import br.com.forca.beta.services.entity.Tema;
import br.com.forca.beta.services.facade.ConfiguracaoFacade;
import br.com.forca.beta.services.facade.ConfiguracaoFacadeImp;
import br.com.forca.beta.services.utils.ImageAdapter;

/**
 *
 * @author G Rodrigues
 * @date 10/06/2013
 */


public class ConfiguracaoActivity extends Activity {
    ConfiguracaoFacade configuracaoFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        configuracaoFacade = ConfiguracaoFacadeImp.getInstacia(ConfiguracaoActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ToggleButton btnSom = (ToggleButton) findViewById(R.id.btnSom);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        String somStatus = configuracaoFacade.getPreferenceString("audioAtivo");

        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
            if (somStatus.equals("true")) {
                btnSom.setChecked(true);
                configuracaoFacade.play();
            } else {
                btnSom.setChecked(false);
                configuracaoFacade.pause();
            }
        } else {
            btnSom.setChecked(false);
            configuracaoFacade.pause();
        }

        final List<Tema> listTemas = configuracaoFacade.getListTemas();
        Tema tema = new Tema();
        tema.setNome("todos");
        listTemas.add(0, tema);
        GridView gridTemas = (GridView) findViewById(R.id.gridTemas);
        gridTemas.setAdapter(new ImageAdapter(this, listTemas));
        gridTemas.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(ConfiguracaoActivity.this,JogoActivity.class);
                intent.putExtra("tema", listTemas.get(position).getNome());
                intent.putExtra("imagem", position);
                startActivity(intent);
            }
        });

    }

    protected void onPause() {
        super.onPause();
        configuracaoFacade.pause();
    };

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            configuracaoFacade.play();
            configuracaoFacade.setPreference("audioAtivo", "true");
        } else {
            configuracaoFacade.setPreference("audioAtivo", "false");
            configuracaoFacade.pause();
        }
    }

}

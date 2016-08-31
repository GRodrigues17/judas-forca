package br.com.forca.beta.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.forca.R;
import br.com.forca.beta.services.entity.Palavra;
import br.com.forca.beta.services.facade.ConfiguracaoFacade;
import br.com.forca.beta.services.facade.ConfiguracaoFacadeImp;
import br.com.forca.beta.services.facade.GeraPalavraAleatoria;
import br.com.forca.beta.services.facade.JogoFacade;
import br.com.forca.beta.services.facade.JogoFacadeImp;
import br.com.forca.beta.services.utils.ImageAdapter;

/**
 *
 * @author G Rodrigues
 * @date 10/06/2013
 */

public class JogoActivity extends Activity {

    private JogoFacade jogoFacade;
    private ConfiguracaoFacade configuracaoFacade;
    private Palavra palavra;
    private Integer totalAcerto;
    private Integer totalErros;
    private Integer qtdLetras;

    @Override
    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {

        configuracaoFacade  = ConfiguracaoFacadeImp.getInstacia(JogoActivity.this);
        Bundle extras = getIntent().getExtras();
        String nomeTema = extras.getString("tema");
        GeraPalavraAleatoria palavraAletoria = new GeraPalavraAleatoria();
        totalAcerto = 0;
        totalErros=0;
        qtdLetras=0;

        palavra = palavraAletoria.geraPalavraAleatoria(this, nomeTema);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ImageView imageView = (ImageView) findViewById(R.id.imgTema);
        imageView.setImageBitmap( BitmapFactory.decodeResource(this.getResources(), this.getResources().getIdentifier("@drawable/"+ ImageAdapter.IMAGEM_TEMA+getIntent().getExtras().getInt("imagem"),null,this.getPackageName())));
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutPalavra);
        TextView tema = (TextView) findViewById(R.id.txtTema);
        tema.setText(nomeTema.toUpperCase());
        jogoFacade = JogoFacadeImp.getInstancia(JogoActivity.this);
        jogoFacade.setPalavra(palavra.getNome());

        int tam = palavra.getNome().length();

        for (int i = 0; i < tam; i++) {

            EditText obj = new EditText(JogoActivity.this);
            obj.setId(i);
            obj.setEnabled(false);
            obj.setFocusable(false);

            qtdLetras++;

            if(palavra.getNome().charAt(i)=='-'){
                qtdLetras--;
                obj.append("-");

            }
            if(palavra.getNome().charAt(i)==' '){
                qtdLetras--;
                obj.setVisibility(-1);
            }
            if(palavra.getNome().charAt(i)=='\''){
                qtdLetras--;
                obj.append("'");
            }

            layout.addView(obj);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        configuracaoFacade.pause();
    };

    @Override
    protected void onResume() {
        super.onResume();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        String somStatus = configuracaoFacade.getPreferenceString("audioAtivo");

        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
            if (somStatus.equals("true")) {
                configuracaoFacade.play();
            } else {
                configuracaoFacade.pause();
            }
        } else {
            configuracaoFacade.pause();
        }
    }


    @SuppressLint("ResourceAsColor")
    public void click(View v) {

        Integer qtdAcerto;

        qtdAcerto = jogoFacade.comparaCaracter(((Button) v).getText().toString(), JogoActivity.this);
        ((Button) v).setEnabled(false);
        ((Button) v).setBackgroundResource(R.drawable.shape_desativar);
        ((Button) v).setTextColor(R.color.texts_views_unselected);

        if (qtdAcerto==0) {
            totalErros = totalErros + 1;
            /**
             * Atualizar imagem da forca
             */
            ImageView imageView = (ImageView) findViewById(R.id.imgCorpo_Completo);
            imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),this.getResources().getIdentifier("@drawable/img"+(totalErros+1),null,this.getPackageName())));
            if(totalErros >=5){
                /**
                 * Chamar a tela de Derrota
                 */
                Intent intent = new Intent(this,ResultadoActivity.class);
                intent.putExtra("vitoria", false);
                intent.putExtra("palavra",palavra.getNome());
                startActivity(intent);
                finish();
            }
        } else {
            totalAcerto = totalAcerto + qtdAcerto;
            if(totalAcerto==qtdLetras){
                /**
                 * Chamar a tela de Vitoria
                 */
                Intent intent = new Intent(this,ResultadoActivity.class);
                intent.putExtra("vitoria", true);
                intent.putExtra("palavra",palavra.getNome());
                startActivity(intent);
                finish();
            }

        }

    }

}

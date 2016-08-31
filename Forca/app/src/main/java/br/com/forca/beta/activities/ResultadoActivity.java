package br.com.forca.beta.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.forca.R;

/**
 *
 * @author G Rodrigues
 * @date 10/06/2013
 */

public class ResultadoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        boolean vitoria = getIntent().getExtras().getBoolean("vitoria");
        ImageView imageView = (ImageView)findViewById(R.id.imgRosto);
        if(vitoria){
            imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.rostoacertou));
        }else{
            if(!vitoria){
                imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.errou));
                TextView textView = (TextView) findViewById(R.id.txtMsg_Acerto);
                textView.setText(R.string.title_loser);
                TextView textViewPalavra = (TextView)  findViewById(R.id.txtPalavra_Correta);
                textViewPalavra.setText(textViewPalavra.getText()+": "+getIntent().getExtras().getString("palavra"));
            }
        }

    }

    public void onClick(View v){
        Intent intent = new Intent(this, ConfiguracaoActivity.class);
        startActivity(intent);
        finish();
    }
}

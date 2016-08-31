package br.com.forca.beta.services.utils;

import java.text.Collator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;

import br.com.forca.beta.activities.JogoActivity;

public class VerificarPalavra {

    private String palavra_recebida;

    @SuppressLint("DefaultLocale")
    public Integer comparaCaracter(String letra_dig, Context ctx) {

	int tam = getPalavra_recebida().length();
	Integer letrasCertas=0;
	for (int i = 0; i < tam; i++) {
	    Collator c = Collator.getInstance();
	    c.setStrength(Collator.PRIMARY);
	    String tmpLetra = String.valueOf(getPalavra_recebida().charAt(i));
	    
	    if (c.compare(tmpLetra, letra_dig)==0) {
		EditText textView = (EditText) ((JogoActivity) ctx)
			.findViewById(i);
		textView.append(tmpLetra.toUpperCase());

		letrasCertas ++;
	    }

	}

	return letrasCertas;
    }

    public String getPalavra_recebida() {
	return palavra_recebida;
    }

    public void setPalavra_recebida(String palavra_recebida) {
	this.palavra_recebida = palavra_recebida;
    }

}

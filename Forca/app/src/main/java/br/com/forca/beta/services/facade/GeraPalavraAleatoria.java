package br.com.forca.beta.services.facade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.forca.beta.services.entity.Palavra;
import br.com.forca.beta.services.entity.Tema;
import br.com.forca.beta.services.repository.PalavraRepository;
import br.com.forca.beta.services.repository.TemaRepository;
import br.com.forca.beta.services.utils.DataBaseHelper;


/**
 *
 * @author G Rodrigues
 * @date 10/06/2013
 */

public class GeraPalavraAleatoria {
    
    private SQLiteDatabase database;

    private int geraAleatorio(final int top) {
	int randNumero;

	final Random gerador = new Random();

	randNumero = gerador.nextInt(top);

	return randNumero;
    }
    
    public Palavra geraPalavraAleatoria(final Context contexto, final String nomeTema){
	Palavra palavra;
	Tema tema;
	int palavraPosicao,numPalavras;

	final DataBaseHelper dbHelper = new DataBaseHelper(contexto);
	setDatabase(dbHelper.getReadableDatabase());
	
	List<Palavra> listPalavras = new ArrayList<Palavra>();
	final PalavraRepository repPalavra =  new PalavraRepository(contexto);
	
	List<Tema> listTemas = new ArrayList<Tema>();
	final TemaRepository repTema =  new TemaRepository(contexto);
	
	listTemas = repTema.getListTemas();

	if(nomeTema.equals("todos")){

	    	int numTemas,temaRandom;
		String temaEscolhido;
	
		numTemas = listTemas.size();
		
		temaRandom = geraAleatorio(numTemas);
		
		tema = listTemas.get(temaRandom);
		temaEscolhido = tema.getNome();

		listPalavras = repPalavra.getListPalavras(temaEscolhido);
		numPalavras = listPalavras.size();
		palavraPosicao = geraAleatorio(numPalavras);
		palavra = listPalavras.get(palavraPosicao);
		
        	palavra.setTema(tema);
        	dbHelper.close();

        	return palavra;
        	
	}else{
       		listPalavras = repPalavra.getListPalavras(nomeTema);
       		numPalavras = listPalavras.size();
        	palavraPosicao = geraAleatorio(numPalavras);
        
        	palavra = listPalavras.get(palavraPosicao);
        	
        	for(int i=0; i<listTemas.size();i++){
        	    if (listTemas.get(i).getNome().equals(nomeTema)){
        		palavra.setTema(listTemas.get(i));
        		break;
        	    }
        	}
        	
        	dbHelper.close();

        	return palavra;
	}
	
    }

    public SQLiteDatabase getDatabase() {
	return database;
    }

    public void setDatabase(SQLiteDatabase database) {
	this.database = database;
    }
    
}

package br.com.forca.beta.services.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.forca.beta.services.entity.Palavra;
import br.com.forca.beta.services.entity.Tema;
import br.com.forca.beta.services.repository.PalavraRepository;
import br.com.forca.beta.services.repository.TemaRepository;

public class DataBaseHelper extends SQLiteOpenHelper{
    
    private static final String NOME_ARQUIVO="tema";
    
    public DataBaseHelper(final Context context) {
	super(context,BASE_NOME, null,VERSAO);
	this.context = context;
    }  
    private Context context;
    
    public static final String TABELA_TEMA="tema";
    public static final  String COLUNA_ID="_id";
    public static final String COLUNA_NOME="nome";
    public static final String TABELA_PALAVRAS="palavras";
    public static final String COLUNA_ID_TEMA_PALAVRA="id_tema";
    public static final String BASE_NOME="dicionario";
    public static final int VERSAO=1;
    private static final int QTD_ARQUIVOS=11;
    
    private static final String CREATE_TEMA = " create table "
	    + TABELA_TEMA + " ( " + COLUNA_ID
	    + " integer primary key autoincrement , " + COLUNA_NOME
	    + " text not null ) ; " ;
   
    
 
    private static final String CREATE_PALAVRAS = " create table "
	    + TABELA_PALAVRAS + " ( " + COLUNA_ID
	    + " integer primary key autoincrement , " + COLUNA_NOME
	    + " text not null," 
	    + COLUNA_ID_TEMA_PALAVRA+" integer not null, " 
	    + " foreign key("+COLUNA_ID_TEMA_PALAVRA +") references "+TABELA_TEMA+"("+COLUNA_ID+")" +
	    "on delete restrict on update cascade) ; " ;
    
    @Override
    public void onCreate(final SQLiteDatabase dataBase) {
	dataBase.execSQL(CREATE_TEMA);
	dataBase.execSQL(CREATE_PALAVRAS);
	inserirTemasPlavras(dataBase);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void inserirTemasPlavras(final SQLiteDatabase dataBase){
	final TemaRepository temaRepository = new TemaRepository(context);
	temaRepository.setDatabase(dataBase);
	final PalavraRepository palavraRepository = new PalavraRepository(context);
	palavraRepository.setDatabase(dataBase);
	for(int i=1;i<=QTD_ARQUIVOS;i++){
	  final  InputStream inputStream = context.getResources().openRawResource(context.getResources().getIdentifier(NOME_ARQUIVO+i,"raw",context.getPackageName()));
	  final  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	    try {
		String str = bufferedReader.readLine();
		if(str!=null){
		    Tema tema = new Tema();
		    tema.setNome(str);
		    temaRepository.create(tema);
		    while (str != null) {
			str = bufferedReader.readLine();
			if(str!=null){
				Palavra palavra = new Palavra();
				palavra.setTema(tema);
				palavra.setNome(str);
				palavraRepository.create(palavra);
			}
		    }
		}
	    } catch (Exception e) { 
		e.printStackTrace();
	    }

	}
    }
    public Context getContext() {
        return this.context;
    }
    public void setContext(final Context context) {
        this.context = context;
    }
}

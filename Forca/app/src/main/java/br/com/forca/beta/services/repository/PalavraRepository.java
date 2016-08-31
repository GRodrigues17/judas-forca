package br.com.forca.beta.services.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.forca.beta.services.entity.Palavra;
import br.com.forca.beta.services.utils.DataBaseHelper;


public class PalavraRepository {

    private SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;
    
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    
    public PalavraRepository(Context context) {
	dataBaseHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException{
     if(database==null || !database.isOpen()){
	 database = dataBaseHelper.getWritableDatabase();
     }
	
    }
    public void close() throws SQLException{
   	 dataBaseHelper.close();
    }
    
    public long create(Palavra palavra){
	open();
	ContentValues values = new ContentValues();
  	values.put(DataBaseHelper.COLUNA_NOME, palavra.getNome());
  	values.put(DataBaseHelper.COLUNA_ID_TEMA_PALAVRA, palavra.getTema().getId());
  	long insertId= database.insert(DataBaseHelper.TABELA_PALAVRAS, null, values);
  	palavra.setId(insertId);
  	close();
  	return insertId;
      }
    
    public List<Palavra> getListPalavras(String nomeTema){
	open();
	List<Palavra> listPalavras = new ArrayList<Palavra>();
	String query = "select palavra._id, palavra.nome from "+DataBaseHelper.TABELA_PALAVRAS+" palavra inner join "+DataBaseHelper.TABELA_TEMA+" tema " +
			"on  palavra."+DataBaseHelper.COLUNA_ID_TEMA_PALAVRA+" = tema._id where tema.nome=?";
	Cursor cursor = database.rawQuery(query, new String[]{nomeTema});
        cursor.moveToFirst();
	while(!cursor.isAfterLast()){
	    Palavra palavra = new Palavra();
	    palavra.setId(cursor.getLong(0));
	    palavra.setNome(cursor.getString(1));
	    listPalavras.add(palavra);
	    cursor . moveToNext () ;
	 
	}
	cursor.close();
	close();
	return listPalavras;  
    }
    
}

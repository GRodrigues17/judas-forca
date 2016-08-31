package br.com.forca.beta.services.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.forca.beta.services.entity.Tema;
import br.com.forca.beta.services.utils.DataBaseHelper;


public class TemaRepository {


    private SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;
    private String [] columns = { DataBaseHelper.COLUNA_ID ,
	    DataBaseHelper.COLUNA_NOME};
    
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    
    public TemaRepository(Context context) {
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
    
    public long create(Tema tema){
	open();
	ContentValues values = new ContentValues();
	values.put(DataBaseHelper.COLUNA_NOME, tema.getNome());
	long insertId= database.insert(DataBaseHelper.TABELA_TEMA, null, values);
	tema.setId(insertId);
	close();
	return insertId;
    }
    
    
    public List<Tema> getListTemas(){
	open();
  	List<Tema> listTemas = new ArrayList<Tema>();
  	Cursor cursor = database.query(DataBaseHelper.TABELA_TEMA, columns, null, null, null, null, null);
          cursor.moveToFirst();
  	while(!cursor.isAfterLast()){
  	    Tema tema = new Tema();
  	    tema.setId(cursor.getLong(0));
  	    tema.setNome(cursor.getString(1));
  	    listTemas.add(tema);
  	    cursor . moveToNext () ;
  	}
  	cursor.close();
  	close();
  	return listTemas;  
      }
}

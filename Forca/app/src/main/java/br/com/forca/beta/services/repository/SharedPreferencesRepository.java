package br.com.forca.beta.services.repository;

import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreferencesRepository {

    private static final String PREFERENCES= "CONFIGURACAO";
    private final Editor editor;
    private static final int DEFAULT_INT = 0;
    private static final String EMPTY_STRING = "";
    private final SharedPreferences preferences;
    
    
    public SharedPreferencesRepository(Context context) {
	preferences =  context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE); 
	editor = preferences.edit();
    }
    
    
    public boolean save() { 
	return editor.commit();
    }

    public Editor putInt(final String key, final int value) {
	return editor.putInt(key, value);
    }

    public Editor putBoolean(final String key, final boolean value) {
	return editor.putBoolean(key, value);
    }

    public Editor putString(final String key, final String value) {
	return editor.putString(key, value);
    }


    public int getInt(final String key) {
	return preferences.getInt(key, DEFAULT_INT);
    }

    public boolean getBoolean(final String key) {
	return preferences.getBoolean(key, false);
    }
    public String getString(final String key) {
	return preferences.getString(key, EMPTY_STRING);
    }


    public Map<String, ?> getAll(final String key) {
	return preferences.getAll();
    }

    public SharedPreferences getPreferences() {
	return preferences;
    }

}

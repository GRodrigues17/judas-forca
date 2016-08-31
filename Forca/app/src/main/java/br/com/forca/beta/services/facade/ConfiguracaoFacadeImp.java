package br.com.forca.beta.services.facade;

import java.util.List;

import android.content.Context;

import br.com.forca.beta.services.entity.Tema;
import br.com.forca.beta.services.repository.SharedPreferencesRepository;
import br.com.forca.beta.services.repository.TemaRepository;
import br.com.forca.beta.services.utils.SoundManager;


public class ConfiguracaoFacadeImp implements ConfiguracaoFacade {

    private static SharedPreferencesRepository sharedPreferencesRepository;
    private static ConfiguracaoFacadeImp configuracaoFacadeImp;
    private static SoundManager soundManager;
    private static TemaRepository temaRepository;

    @Override
    public void setPreference(String key, String value) {
    	sharedPreferencesRepository.putString(key, value);
    	sharedPreferencesRepository.save();
    }

    @Override
    public String getPreferenceString(String key) {
    	return sharedPreferencesRepository.getString(key);
    }

    public static SharedPreferencesRepository getIstanciaSharedPrerences(
    		Context ctx) {
    	if (sharedPreferencesRepository == null) {
    		sharedPreferencesRepository = new SharedPreferencesRepository(ctx);
    	}
    	return sharedPreferencesRepository;
    }

    private ConfiguracaoFacadeImp() {
    }

    @Override
    public void play() {
    	soundManager.play();
    }

    @Override
    public void stop() {
    	soundManager.stop();
    }

    @Override
    public void pause() {
    	soundManager.pause();

    }

    public static ConfiguracaoFacadeImp getInstacia(Context ctx) {
    	if (configuracaoFacadeImp == null) {
    		configuracaoFacadeImp = new ConfiguracaoFacadeImp();
    		getIstanciaSharedPrerences(ctx);
    		soundManager = SoundManager.getInstaced(ctx);
    	}
    	if(temaRepository == null){
    		temaRepository = new TemaRepository(ctx);
    	}
    	return configuracaoFacadeImp;
    }

	@Override
	public List<Tema> getListTemas() {
		return temaRepository.getListTemas();
	}
}

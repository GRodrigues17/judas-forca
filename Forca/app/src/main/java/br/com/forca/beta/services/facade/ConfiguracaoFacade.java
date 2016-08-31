package br.com.forca.beta.services.facade;

import java.util.List;

import br.com.forca.beta.services.entity.Tema;


public interface ConfiguracaoFacade {

    public void setPreference(String key, String value);

    public String getPreferenceString(String key);

    public void play();
    
    public void stop();
    
    public void pause();
    
    public List<Tema> getListTemas();
}

package br.com.forca.beta.services.facade;

import android.content.Context;

import br.com.forca.beta.services.utils.VerificarPalavra;


public class JogoFacadeImp implements JogoFacade {

    private static VerificarPalavra verificarPalavra;
    private static JogoFacadeImp facadeImp;
    
    @Override
    public Integer comparaCaracter(String letra, Context ctx){
	return verificarPalavra.comparaCaracter(letra, ctx);
    }
    
    @Override 
    public void setPalavra(String palavra){
	verificarPalavra.setPalavra_recebida(palavra);
    }
    private JogoFacadeImp(){
	
    }

    public static JogoFacadeImp getInstancia(Context ctx){
	if(facadeImp == null){
	    facadeImp = new JogoFacadeImp();
	    verificarPalavra = new VerificarPalavra();
	}
	return facadeImp;
    }
    
   
  
   }

package br.com.forca.beta.services.entity;

public class Palavra {
    
    
    private long id;
    private String nome;
    private Tema tema;
      
      
      
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Tema getTema() {
        return this.tema;
    }
    public void setTema(Tema tema) {
        this.tema = tema;
    }
   
      
      
}

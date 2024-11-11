package Models;
import java.time.LocalDate;

public abstract class Transacao {
    private int id;
    private String descricao;
    private double valor;
    private LocalDate data;
    private Categoria categoria;
    private Usuario usuario;
    
    // Construtores
    public Transacao(int id, String descricao, double valor, LocalDate data, Categoria categoria, Usuario usuario) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.usuario = usuario;
    }
    // Getters e Setters
    public int getId(){
         return id; 
    }
    public String getDescricao(){ 
        return descricao; 
    }
    public double getValor(){
         return valor; 
    }
    public LocalDate getData(){
         return data;
    }
    public Categoria getCategoria(){
         return categoria; 
    }
    public Usuario getUsuario(){
         return usuario;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setDescricao(String descricao){
         this.descricao = descricao; 
    }
    public void setValor(double valor){
         this.valor = valor;
    }
    public void setData(LocalDate data){
         this.data = data;
    }
    public void setCategoria(Categoria categoria){
         this.categoria = categoria;
    }
    public void setUsuario(Usuario usuario){
         this.usuario = usuario;
    }
}
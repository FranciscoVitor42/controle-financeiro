package Models;

import java.time.LocalDate;

public class Receita extends Transacao {
    public Receita(int id, String descricao, double valor, LocalDate data, Categoria categoria, Usuario usuario) {
        super(id, descricao, valor, data, categoria, usuario);
    }
}
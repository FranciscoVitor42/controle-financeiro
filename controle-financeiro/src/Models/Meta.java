package Models;

import java.time.LocalDate;

public class Meta {
    private String descricao;
    private double valor;
    private LocalDate data;

    // Construtor que aceita a data
    public Meta(String descricao, double valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    // Getters
    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    // toString para exibir a meta formatada
    @Override
    public String toString() {
        return "Meta: " + descricao + ", Valor: R$" + valor + ", Data: " + data;
    }
}
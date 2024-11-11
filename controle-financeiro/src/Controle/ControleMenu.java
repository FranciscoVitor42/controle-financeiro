package Controle;
import Models.*;

import javax.swing.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControleMenu {

    private List<Usuario> usuarios = new ArrayList<>();
    private Map<Usuario, List<Transacao>> transacoes = new HashMap<>();
     private Map<Usuario, List<Meta>> metas = new HashMap<>();
    private Usuario usuarioAtual; // usuário que esta logado
    private double saldoTotal = 0.0;
    private static int idTransacao = 1;
    private Map<Usuario, Integer> contadoresDeIdTransacao = new HashMap<>();
    public ControleMenu() {
        adicionarUsuarioPredefinidos();
        adicionarTransacaoPredefinida(usuarios.get(0));
        adicionarTransacaoPredefinida(usuarios.get(1));
        adicionarTransacaoPredefinida(usuarios.get(2));
        for (Usuario usuario : usuarios) {
            metas.put(usuario, new ArrayList<>());
        }
    }
    
    private void adicionarUsuarioPredefinidos() {
        Registrar_Usuario("João Pedro", "joao@gmail.com", "123");
        Registrar_Usuario("Francisco Vitor", "fran@gmail.com", "123");
        Registrar_Usuario("Jones de Oliveira", "joao@gmail.com", "123");        
    }


    private void adicionarTransacaoPredefinida(Usuario usuario){
        transacoes.put(usuario, new ArrayList<>());

        //istanciando todos para adicionar direto
        Receita receita1 = new Receita(1, "Venda Produto A", 150, LocalDate.now(), Categoria.VENDA_PRODUTO, usuario);
        Despesas despesa1 = new Despesas(2, "Compra de Material", 50, LocalDate.of(2024, 11, 01), Categoria.CARTAO, usuario);

        transacoes.get(usuario).add(receita1);
        transacoes.get(usuario).add(despesa1);
    }

    //login do usuário
    public void loginUsuario() {
        String email = JOptionPane.showInputDialog(null,"","Digite o Email",-1);
    
        // Criação do campo de senha
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "Digite a senha:",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }
    
        // Obtém a senha como char[] e converte para String
        String senha = new String(passwordField.getPassword());
    
        // Verifica se o usuário existe e se a senha está correta
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                usuarioAtual = usuario; // Se as condições forem aceitas o usuário é logado
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!\n" + "Bem-vindo " + usuario.getNome() + "!");
                calcularSaldo();
                Menu.menuUsuario(); // Puxa o menu de usuário que está no Menu.java
                return;
            }
        }
    
        JOptionPane.showMessageDialog(null, "Email ou senha incorretos."); // Se as condições não forem aceitas retorna esta mensagem
    }

    public void cadastrarUsuario() {
        String nome = JOptionPane.showInputDialog("Digite o seu nome:");
        String email = JOptionPane.showInputDialog("Digite o seu email:");
        String senha = JOptionPane.showInputDialog("Digite a sua senha:");

        if(usuarioJaCadastrado(email)){
            JOptionPane.showMessageDialog(null, "Já existe um usuário cadastrado no sistema com esse e-mail.");
            return;
        }

        Registrar_Usuario(nome, email, senha);

        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso.");
    }

    private boolean usuarioJaCadastrado(String email){
        for (Usuario usuario : usuarios){
            if (usuario.getEmail().equals(email.toLowerCase())){
                return true;
            }
        }

        return false;
    }
    
    // aqui calcula o valor total de lucros que o usuario tem diminuindo com as despesas
    private void calcularSaldo() {
        saldoTotal = 0.0;
        
        List<Transacao> transacaoList = transacoes.get(usuarioAtual);

        if (transacaoList == null) return;

        for (Transacao transacao : transacaoList) {
            if (transacao instanceof Receita) {
                saldoTotal += transacao.getValor();
            } else if (transacao instanceof Despesas) {
                saldoTotal -= transacao.getValor();
            }
        }
    }

    // aqui puxa todos os usuario que foram cadastrados
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
            return;
        }

        StringBuilder lista = new StringBuilder("Usuários cadastrados:\n");
        for (Usuario usuario : usuarios) {
            lista.append("Nome: ").append(usuario.getNome())
                 .append("| Email: ").append(usuario.getEmail())
                 .append("\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    // metodo de adicionar Rceita
    public void adicionarReceita() {
        String descricao = JOptionPane.showInputDialog("Nome da Receita\n(prestação de serviços,\n aluguéis, juros sobre investimentos,\n etc.):");
        double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor da Receita:"));
        
        // Solicitar a categoria
        String categoriaStr = JOptionPane.showInputDialog("Categoria \n1-DINHEIRO\n2-TRANSFERENCIA(PIX)\n3-VENDA PRODUTO\n4-CARTÃO:");
        int categoriaOpcao = Integer.parseInt(categoriaStr);
    
        // Valida a entrada e mapeia para o enum Categoria
        Categoria categoria = Categoria.values()[categoriaOpcao - 1];
    
        Receita receita = new Receita(idTransacao++, descricao, valor, LocalDate.now(), categoria, usuarioAtual);
        transacoes.putIfAbsent(usuarioAtual, new ArrayList<>());
        transacoes.get(usuarioAtual).add(receita);
        saldoTotal += valor;
    
        JOptionPane.showMessageDialog(null, "Receita adicionada com sucesso!");
    }

    // metodo de adicionar despesas
    //praticamente igual o de lucros so que em vez de adicionar ele diminui o lucro ganho
    public void adicionarDespesa() {
        String descricao = JOptionPane.showInputDialog("Descrição da Despesa:");
        double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor da Despesa:"));
    
        // Solicitar a categoria
        String categoriaStr = JOptionPane.showInputDialog("Categoria \n1-DINHEIRO\n2-TRANSFERENCIA(PIX)\n3-VENDA PRODUTO\n4-CARTÃO:");
        int categoriaOpcao = Integer.parseInt(categoriaStr);
    
        // Valida a entrada e mapeia para o enum Categoria
        Categoria categoria = Categoria.values()[categoriaOpcao - 1];
    
        Despesas despesa = new Despesas(idTransacao++, descricao, valor, LocalDate.now(), categoria, usuarioAtual);
        transacoes.putIfAbsent(usuarioAtual, new ArrayList<>());
        transacoes.get(usuarioAtual).add(despesa);
        saldoTotal -= valor;
    
        JOptionPane.showMessageDialog(null, "Despesa adicionada com sucesso!");
    }
    // Revisão do método adicionarMeta no ControleMenu:
    public void adicionarMeta() {
        if (!metas.containsKey(usuarioAtual)) {
            metas.put(usuarioAtual, new ArrayList<>()); // Inicializa a lista de metas, caso não exista
        }
    
        String descricao = JOptionPane.showInputDialog("Digite o que será sua meta:");
        if (descricao == null || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descrição não pode ser vazia.");
            return;
        }
    
        String valorStr = JOptionPane.showInputDialog("Digite o valor da meta (em R$):");
        try {
            double valor = Double.parseDouble(valorStr); // Converte o valor numérico
    
            // Solicita a data
            String dataStr = JOptionPane.showInputDialog("Digite a data da meta (formato: ANO-MES-DIA):");
            LocalDate data = LocalDate.parse(dataStr); // Converte a string para LocalDate
    
            // Cria a nova meta com a data
            Meta novaMeta = new Meta(descricao, valor, data);
            metas.get(usuarioAtual).add(novaMeta); // Adiciona a meta na lista do usuário atual
    
            JOptionPane.showMessageDialog(null, "Meta adicionada com sucesso:\n" + novaMeta);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira apenas números para o valor.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar os dados: " + e.getMessage());
        }
    }
    // aqui mostra todas as transações feitas pelo usuario, tanto lucro quanto despesas
    public void listarTransacoes() {
        List<Transacao> transacaoList = transacoes.get(usuarioAtual);
        if(transacaoList == null){
            JOptionPane.showMessageDialog(null, "Nenhuma transação registrada.");
            return;
        }

        StringBuilder transacoesStr = new StringBuilder("Lista de Transações:\n");
    for (Transacao transacao : transacaoList) {
        String tipoTransacao = (transacao instanceof Receita) ? "Receita" : "Despesa";
        transacoesStr
                .append("ID: ").append(transacao.getId())
                .append(" \nTipo: ").append(tipoTransacao)
                .append(" \nValor: R$").append(transacao.getValor())
                .append(" \nDescrição: ").append(transacao.getDescricao())
                .append(" \nCategoria: ").append(transacao.getCategoria())
                .append(" \nData: ").append(transacao.getData())
                .append("\n");
    }

        JOptionPane.showMessageDialog(null, transacoesStr.toString());
    }

    // aqui apenas exibe o total do lucro do usuario
    public void exibirTotalReceitas() {
        if (transacoes.get(usuarioAtual) == null || transacoes.get(usuarioAtual).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma Receita registrada!");
            return; // Sai do método, pois não há receitas para calcular
        }
        double totalReceitas = transacoes.get(usuarioAtual).stream()
                .filter(t -> t instanceof Receita)
                .mapToDouble(Transacao::getValor)
                .sum();
        JOptionPane.showMessageDialog(null, "Total das Receitas: R$" + totalReceitas);
    }

    // aqui apenas exibe o total de despesas do usuario
    public void exibirTotalDespesas() {
        if (transacoes.get(usuarioAtual) == null || transacoes.get(usuarioAtual).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma Despesa registrada!");
            return; // Sai do método, pois não há Despesa para calcular
        }
        double totalDespesas = transacoes.get(usuarioAtual).stream()
                .filter(t -> t instanceof Despesas)
                .mapToDouble(Transacao::getValor)
                .sum();
        JOptionPane.showMessageDialog(null, "Total de Despesas: R$" + totalDespesas);
    }
    public void listarMetas() {
    List<Meta> listaMetas = metas.get(usuarioAtual);

    if (listaMetas == null || listaMetas.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhuma meta registrada.");
        return;
    }

    // Define um formatador para exibir a data de forma legível
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    StringBuilder mensagem = new StringBuilder("Metas Registradas:\n");
    for (Meta meta : listaMetas) {
        mensagem.append("Descrição: ").append(meta.getDescricao())
                .append(" | Valor: R$").append(String.format("%.2f", meta.getValor())) // Formata o valor para 2 casas decimais
                .append(" | Data: ").append(meta.getData().format(formatter)) // Formata a data para o formato dd/MM/yyyy
                .append("\n");
    }

    JOptionPane.showMessageDialog(null, mensagem.toString());
}
    //getters e setters adicionais
    public double getSaldoTotal() {
        return saldoTotal;
    }
    
    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }


    private void Registrar_Usuario(String nome, String email, String senha){
        int ultimoID = usuarios.size();

        Usuario usuario = new Usuario(ultimoID + 1, nome, email, senha);

        usuarios.add(usuario);
        contadoresDeIdTransacao.put(usuario, 1);
    }
    
    public void Alterar_Senha(){
        String senha = JOptionPane.showInputDialog("Digite a nova senha:");
        usuarioAtual.setSenha(senha);
        JOptionPane.showMessageDialog(null, "Senha alterada !!");
    }
    public void menuConclusao() {
        // Obter a lista de metas do usuário atual
        List<Meta> listaMetas = metas.get(usuarioAtual);
    
        // Verificar se há metas registradas
        if (listaMetas == null || listaMetas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma meta registrada.");
            return;
        }
    
        // Selecionar a meta mais recente (última adicionada)
        Meta metaAtual = listaMetas.get(listaMetas.size() - 1);
    
        // Calcular o total de receitas
        double totalReceitas = transacoes.get(usuarioAtual).stream()
                .filter(t -> t instanceof Receita)
                .mapToDouble(Transacao::getValor)
                .sum();
    
        // Calcular o total de despesas
        double totalDespesas = transacoes.get(usuarioAtual).stream()
                .filter(t -> t instanceof Despesas)
                .mapToDouble(Transacao::getValor)
                .sum();
    
        // Calcular o saldo atual e o quanto falta para alcançar a meta
        double saldoAtual = totalReceitas - totalDespesas;
        double restanteParaMeta = metaAtual.getValor() - saldoAtual;
    
        // Definir o formatador para exibir a data de forma legível
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        // Criar a mensagem para exibir no menu de conclusão
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Meta Atual:\n")
                .append("Descrição: ").append(metaAtual.getDescricao()).append("\n")
                .append("Valor da Meta: R$").append(String.format("%.2f", metaAtual.getValor())).append("\n")
                .append("Data da Meta: ").append(metaAtual.getData().format(formatter)).append("\n\n");
    
        mensagem.append("Receitas Totais: R$").append(String.format("%.2f", totalReceitas)).append("\n")
                .append("Despesas Totais: R$").append(String.format("%.2f", totalDespesas)).append("\n")
                .append("Saldo Atual: R$").append(String.format("%.2f", saldoAtual)).append("\n\n");
    
        if (restanteParaMeta > 0) {
            mensagem.append("Falta para alcançar a meta: R$").append(String.format("%.2f", restanteParaMeta)).append("\n");
        } else {
            mensagem.append("Parabéns! Você atingiu sua meta!\n");
        }
    
        // Exibir a mensagem no JOptionPane
        JOptionPane.showMessageDialog(null, mensagem.toString(), "Resumo da Meta", JOptionPane.INFORMATION_MESSAGE);
    }
}
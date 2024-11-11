package Controle;
import javax.swing.*;

public class Menu {
    private static ControleMenu controleMenu = new ControleMenu(); // instancia da classe controlemenu
    
    public static void main(String[] args) {
        while (true) {
            // menu inicial dei o nome de menu1
            String menu1 = "Controle Financeiro\n1. Login\n2. Cadastro de Usuario\n3. Listar Usuarios\n4. Sair";
            String opcaoMenu1 = JOptionPane.showInputDialog(null,menu1,"KAKEBO",3);

            switch (opcaoMenu1) {
                case "1":
                controleMenu.loginUsuario(); // chama o metodo de login no controlemenu
                    break;
                case "2":
                  controleMenu.cadastrarUsuario();
                    break;
                case "3":
                controleMenu.listarUsuarios(); // exibe os usuarios padroes por enquanto que foi cadastrado
                    break;
                case "4":
                    JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                    return; // sai do programa
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                    break;
            }
        }
    }

    // Menu para quando o usuário loga
    public static void menuUsuario() {
        while (true) {
            String menu3 = "INTERFACE DO USUÁRIO\n"  +   "(Usuário: " + controleMenu.getUsuarioAtual().getNome() + ")\n"
                    + "1. Perfil\n"
                    + "2. Transações\n"                    
                    + "3. Voltar ao Menu Inicial";

            String opcaoMenu3 = JOptionPane.showInputDialog(menu3);

            switch (opcaoMenu3) {
                case "1":
                    menuPerfil();
                    break;
                case "2":
                    menuTransacoes(); // chama o metodo para adicionar despesa
                    break;
                case "3":
                    return; // volta ao menu inicial
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                    break;
            }
        }
    }
    public static void menuPerfil() {
        while (true) {
            String menu3 = "INTERFACE DE PERFIL\n"  +   "(Usuário: " + controleMenu.getUsuarioAtual().getNome() + ")" + "\nTotal R$: "+ controleMenu.getSaldoTotal() + '\n' 
                    + "1. Listar Transações\n"
                    + "2. Listar Metas\n"
                    + "3. Exibir Só de Receitas\n"
                    + "4. Exibir Só de Despesas\n"
                    + "5. Conclusão\n"
                    + "6. Alterar Senha\n" 
                    + "7. Voltar ao Menu Inicial";

            String opcaoMenu3 = JOptionPane.showInputDialog(menu3);

            switch (opcaoMenu3) {
              
                case "1":
                     controleMenu.listarTransacoes(); // lista das transações do usuário
                    break;
                case "2":
                    controleMenu.listarMetas();
                    break;
                case "3":
                    controleMenu.exibirTotalReceitas(); // exibe total de receitas
                    break;
                case "4":
                    controleMenu.exibirTotalDespesas(); // exibe total de receitas
                    break;
                case "5":
                    controleMenu.menuConclusao();
                    break;
                case "6":
                    controleMenu.Alterar_Senha();
                    break;
                case "7":
                    return; // volta ao menu inicial
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                    break;
            }
        }
    }

    public static void menuTransacoes() {
        while (true) {
            String menu3 = "CONTROLE FINANCEIRO\n"  +   "(Usuário: " + controleMenu.getUsuarioAtual().getNome() + ") " + "Total R$: "+ controleMenu.getSaldoTotal() + '\n'
                    + "1. Adicionar Receita\n"
                    + "2. Adicionar Despesas\n"
                    + "3. Adicionar Metas\n"
                    + "4. Voltar ao menu anterior";

            String opcaoMenu3 = JOptionPane.showInputDialog(menu3);

            switch (opcaoMenu3) {
                case "1":
                    controleMenu.adicionarReceita(); // chama o metodo para adicionar receita
                    break;
                case "2":
                    controleMenu.adicionarDespesa(); // chama o metodo para adicionar despesa
                    break;
                case "3":
                    controleMenu.adicionarMeta();
                    break;
                case "4":
                    return; // volta ao menu inicial
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
                    break;
            }
        }
    }
}
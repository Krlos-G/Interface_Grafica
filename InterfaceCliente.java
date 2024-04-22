package devops;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InterfaceCliente extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new InterfaceCliente();
    }

    private CadastroClientes cadastroClientes;
    private JTextField nomeTextField;
    private JTextField emailTextField;
    private JTextField telefoneTextField;
    private JButton adicionarButton;
    private JButton visualizarButton;
    private JButton atualizarButton;
    private JButton excluirButton;
    private JButton salvarButton;
    private JTextArea clientesTextArea;

    public InterfaceCliente() {
        super("Cadastro de Clientes");
        cadastroClientes = new CadastroClientes();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        JPanel formularioJPanel = new JPanel(new GridLayout(7, 4));
        formularioJPanel.setBorder(new TitledBorder("Dados do Cliente"));
        formularioJPanel.add(new JLabel("Nome: "));
        formularioJPanel.add(nomeTextField = new JTextField());
        formularioJPanel.add(new JLabel("Email: "));
        formularioJPanel.add(emailTextField = new JTextField());
        formularioJPanel.add(new JLabel("Telefone: "));
        formularioJPanel.add(telefoneTextField = new JTextField());

        JPanel botaoJPanel = new JPanel(new FlowLayout());
        adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(this);
        botaoJPanel.add(adicionarButton);
        visualizarButton = new JButton("Visualizar");
        visualizarButton.addActionListener(this);
        botaoJPanel.add(visualizarButton);
        atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(this);
        botaoJPanel.add(atualizarButton);
        excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(this);
        botaoJPanel.add(excluirButton);
        salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(this);
        botaoJPanel.add(salvarButton);

        clientesTextArea = new JTextArea();
        clientesTextArea.setEditable(false);
        clientesTextArea.setBorder(new TitledBorder("Lista de Clientes"));

        add(formularioJPanel, BorderLayout.CENTER);
        add(botaoJPanel, BorderLayout.SOUTH);
        add(clientesTextArea, BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comand = e.getActionCommand();
        if (comand.equals("Adicionar")) {
            String nome = nomeTextField.getText();
            String email = emailTextField.getText();
            String telefone = telefoneTextField.getText();
            Cliente cliente = new Cliente(nome, email, telefone);

            cadastroClientes.addCliente(cliente);

            nomeTextField.setText("");
            emailTextField.setText("");
            telefoneTextField.setText("");

            JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
        } else if (comand.equals("Visualizar")) {
            clientesTextArea.setText("");
            String strCli = cadastroClientes.viewClientes();
            clientesTextArea.setText(strCli);
        } else if (comand.equals("Atualizar")) {
            String nome = nomeTextField.getText();
            String email = emailTextField.getText();
            String telefone = telefoneTextField.getText();

            Cliente cliente = new Cliente(nome, email, telefone);
            int selectedIndex = -1;
            try {
                selectedIndex = Integer
                        .parseInt(JOptionPane.showInputDialog(this, "Digite o nome do cliente a ser atualizado: "));
                if (selectedIndex >= 0 && selectedIndex < cadastroClientes.getClientes().size()) {
                    cadastroClientes.attCliente(selectedIndex, cliente);
                    JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice invalido. Digite um numero inteiro");
            }
        } else if (comand.equals("Salvar")) {
            Path caminho = Paths.get("C:\\Users\\T33162\\Documents\\clientes");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(caminho.toString(), true));
                for (Cliente cliente : cadastroClientes.getClientes()) {
                    byte textByte[] = cliente.toString().getBytes();
                    writer.write(new String(textByte));
                    writer.newLine();
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Clientes salvos no arquivo clientes.txt!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possivel salvar os dados no arquivo!");
            }
        } else if (comand.equals("Excluir")) {
            int selectedIndex = -1;
            try {
                selectedIndex = Integer
                        .parseInt(JOptionPane.showInputDialog(this, "Digite o índice a ser excluido: "));
                if (selectedIndex >= 0 && selectedIndex < cadastroClientes.getClientes().size()) {
                    cadastroClientes.excluirCliente(selectedIndex);
                    JOptionPane.showMessageDialog(this, "Cliente excluido com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice invalido. Digite um numero inteiro");
            }
        }
    }
}
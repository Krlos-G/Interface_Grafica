package devops;

import java.util.ArrayList;
import java.util.List;

public class CadastroClientes {
    private List<Cliente> clientes;

    public CadastroClientes() {
        this.clientes = new ArrayList<Cliente>();
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public String viewClientes() {
        StringBuilder sb = new StringBuilder();

        for (Cliente cliente : clientes) {
            sb.append(cliente.toString()).append("\n");
        }

        return sb.toString();
    }

    public void attCliente(int index, Cliente cliente) {
        if (index >= 0 && index < clientes.size()) {
            clientes.set(index, cliente);
            System.out.println("Cliente atualizado com sucesso.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void excluirCliente(int index) {
        if (index >= 0 && index < clientes.size()) {
            clientes.remove(index);
            System.out.println("Cliente excluído com sucesso.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
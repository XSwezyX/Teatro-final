import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Teatro {

    private Vetor<Espetaculo> espetaculos;
    private Vetor<Reserva> reservas;
    private Scanner entrada;
    private boolean dadosCarregados;

    public Teatro() {
        espetaculos = new Vetor<Espetaculo>(20);
        reservas = new Vetor<Reserva>(800);
        entrada = new Scanner(System.in);
        dadosCarregados = false;
    }

    public static void main(String[] args) {
        Teatro teatro = new Teatro();
        teatro.executar();
    }

    public void executar() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Opcao: ");

            if (opcao == 1) {
                carregarEspetaculos();
            } else if (opcao == 2) {
                exibirEspetaculos();
            } else if (opcao == 3) {
                fazerReserva();
            } else if (opcao == 4) {
                exibirMapa();
            } else if (opcao == 5) {
                exibirReserva();
            } else if (opcao == 6) {
                exibirEstatisticas();
            } else if (opcao != 7) {
                System.out.println("Opcao invalida.");
            }
        } while (opcao != 7);

        exibirIntegrantes();
        System.out.println("Encerrando o programa.");
    }

    public void exibirMenu() {
        System.out.println("\n1. Carregar espetaculos");
        System.out.println("2. Exibir espetaculos");
        System.out.println("3. Fazer reserva");
        System.out.println("4. Consultar mapa de assentos");
        System.out.println("5. Consultar reserva");
        System.out.println("6. Estatisticas");
        System.out.println("7. Sair");
    }

    private void exibirIntegrantes() {
        System.out.println("\nIntegrantes do grupo:");
        System.out.println("- Henrique Haramaki Mataveli - 10752924");
        System.out.println("-Lukas Barone Sussa -10753291");
        System.out.println("- Ryan Silva de Sousa - 10757255");
    }

    public void carregarEspetaculos() {
        if (dadosCarregados) {
            System.out.println("Os espetaculos ja foram carregados.");
            return;
        }

        try {
            FileReader data = new FileReader("espetaculos.txt");
            BufferedReader linha = new BufferedReader(data);
            String aux = linha.readLine();

            while (aux != null) {
                String[] partes = aux.split(";");

                if (partes.length == 5) {
                    if (espetaculos.isFull()) {
                        System.out.println("Limite de 20 espetaculos atingido. As demais linhas foram ignoradas.");
                        break;
                    }

                    int codigo = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String dataEspetaculo = partes[2];
                    String horario = partes[3];
                    double preco = Double.parseDouble(partes[4]);
                    Espetaculo espetaculo = new Espetaculo(codigo, nome, dataEspetaculo, horario, preco);
                    espetaculos.add(espetaculos.size(), espetaculo);
                }

                aux = linha.readLine();
            }

            data.close();
            dadosCarregados = true;
            System.out.println("Espetaculos carregados com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados nao encontrado.");
        } catch (IOException e) {
            espetaculos = new Vetor<Espetaculo>(20);
            System.out.println("Erro na leitura do arquivo.");
        } catch (Exception e) {
            espetaculos = new Vetor<Espetaculo>(20);
            System.out.println("Erro ao carregar os espetaculos. Verifique o formato das linhas do arquivo.");
        }
    }

    public void exibirEspetaculos() {
        if (!verificarCarga()) {
            return;
        }
       

        System.out.println("\nEspetaculos disponiveis:");

        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);
                System.out.printf("%d - %s - %s - %s - R$ %.2f%n", espetaculo.getCodigo(),
                        espetaculo.getNome(), espetaculo.getData(), espetaculo.getHorario(),
                        espetaculo.getPreco());
            } catch (Exception e) {
                System.out.println("Erro ao exibir os espetaculos.");
                return;
            }
        }
    }

    public void fazerReserva() {
        if (!verificarCarga()) {
            return;
        }
        Espetaculo espetaculo = null;
        while (espetaculo == null) {
            int codigo = lerInteiro("Codigo do espetaculo: ");
            espetaculo = buscarEspetaculo(codigo);

            if (espetaculo == null) {
                System.out.println("Espetaculo nao encontrado. Digite o codigo novamente.");
            }
        }

        if (reservas.isFull()) {
            System.out.println("Limite de reservas atingido.");
            return;
        }

        exibirDadosEspetaculo(espetaculo);
        String cpf = lerCpf();
        String nome = lerTexto("Nome: ");
        int quantidade = lerQuantidade();

        if (espetaculo.quantidadeAssentosLivres() < quantidade) {
            System.out.println("Nao ha assentos livres suficientes para esta reserva.");
            return;
        }

        System.out.println("\nMapa de assentos:");
        espetaculo.exibirMapa();
        String[] assentosReservados = new String[quantidade];

        for (int indice = 0; indice < quantidade; indice++) {
            assentosReservados[indice] = selecionarAssento(espetaculo, indice + 1);
            espetaculo.exibirMapa();
        }

        try {
            Reserva reserva = new Reserva(cpf, nome, espetaculo, quantidade, assentosReservados);
            reservas.add(reservas.size(), reserva);
            System.out.println("\nReserva realizada!\n");
            exibirResumoReserva(reserva, false);
        } catch (Exception e) {
            System.out.println("Erro ao registrar a reserva.");
        }
    }

    public void exibirMapa() {
        if (!verificarCarga()) {
            return;
        }

        int codigo = lerInteiro("Codigo do espetaculo: ");
        Espetaculo espetaculo = buscarEspetaculo(codigo);

        if (espetaculo == null) {
            System.out.println("Espetaculo nao encontrado.");
            return;
        }

        exibirDadosEspetaculo(espetaculo);
        System.out.println("\nMapa de assentos:");
        espetaculo.exibirMapa();
    }

  public void exibirReserva() {
    if (!verificarCarga()) {
        return;
    }

    try {
        String cpf = lerCpf();
        boolean encontrou = false; 

        for (int indice = 0; indice < reservas.size(); indice++) {
            Reserva reserva = reservas.get(indice);

            if (reserva.getCpf().equals(cpf)) {
                System.out.println();
                exibirResumoReserva(reserva, true);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Reserva nao encontrada para o CPF informado.");
        }
    } catch (Exception e) {
        System.out.println("Erro ao buscar a reserva.");
    }
}

    private boolean verificarCarga() {
        if (!dadosCarregados) {
            System.out.println("Carregue os espetaculos antes de usar esta opcao.");
            return false;
        }

        return true;
    }

    private Espetaculo buscarEspetaculo(int codigo) {
        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);

                if (espetaculo.getCodigo() == codigo) {
                    return espetaculo;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    private Reserva buscarReserva(String cpf) throws Exception {
        for (int indice = 0; indice < reservas.size(); indice++) {
            Reserva reserva = reservas.get(indice);

            if (reserva.getCpf().equals(cpf)) {
                return reserva;
            }
        }

        return null;
    }

    private String selecionarAssento(Espetaculo espetaculo, int numeroAssento) {
        while (true) {
            String assento = lerTexto("Assento " + numeroAssento + " (ex: A8): ").toUpperCase();

            if (assento.length() != 2) {
                System.out.println("Assento invalido. Use o formato A1 ate E8.");
            } else {
                int linha = assento.charAt(0) - 'A';
                int coluna = assento.charAt(1) - '1';

                if (!espetaculo.posicaoValida(linha, coluna)) {
                    System.out.println("Assento inexistente.");
                } else if (!espetaculo.estaLivre(linha, coluna)) {
                    System.out.println("Assento ocupado.");
                } else {
                    espetaculo.ocuparAssento(linha, coluna);
                    return assento;
                }
            }
        }
    }

    private void exibirDadosEspetaculo(Espetaculo espetaculo) {
        System.out.printf("%n%d - %s - %s - %s - R$ %.2f%n", espetaculo.getCodigo(),
                espetaculo.getNome(), espetaculo.getData(), espetaculo.getHorario(),
                espetaculo.getPreco());
    }

    private void exibirResumoReserva(Reserva reserva, boolean consulta) {
        if (consulta) {
            System.out.println("Cliente: " + reserva.getNome());
        }

        System.out.println("Espetaculo: " + reserva.getEspetaculo().getNome());

        String lista = "";
        for (int indice = 0; indice < reserva.getQtdeAssentos(); indice++) {
            lista += reserva.getAssentos()[indice];
            if (indice < reserva.getQtdeAssentos() - 1) {
                lista += " ";
            }
        }
        System.out.println("Assentos: " + lista);

        if (!consulta) {
            System.out.println("Quantidade: " + reserva.getQtdeAssentos());
        }

        System.out.printf("Valor total: R$ %.2f%n",
                reserva.getEspetaculo().getPreco() * reserva.getQtdeAssentos());
    }

    private void exibirEstatisticas() {
        if (!verificarCarga()) {
            return;
        }

        System.out.println("\n=== ESTATISTICAS ===");
        exibirEspetaculoMaisPovo();
        exibirEspetaculoMaisArrecadou();
        exibirMedia();
    }

    private void exibirMedia() {
        double totalVendas = 0;

        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);
                totalVendas += espetaculo.quantidadeAssentosOcupados() * espetaculo.getPreco();
            } catch (Exception e) {
                System.out.println("Erro ao calcular media de vendas.");
                return;
            }
        }

        double mediaVendas = totalVendas / espetaculos.size();
        System.out.printf("Media de vendas por espetaculo: R$ %.2f%n", mediaVendas);
    }

    private void exibirEspetaculoMaisPovo() {
        Espetaculo maisPovo = null;
        int maiorOcupacao = 0;

        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);
                int ocupados = espetaculo.quantidadeAssentosOcupados();

                if (ocupados > maiorOcupacao) {
                    maiorOcupacao = ocupados;
                    maisPovo = espetaculo;
                }
            } catch (Exception e) {
                System.out.println("Erro ao calcular estatisticas.");
                return;
            }
        }

        if (maisPovo != null) {
            System.out.println("\nEspetaculo com mais publico: " + maisPovo.getNome());
            System.out.println("Assentos ocupados: " + maiorOcupacao + " de 40");
        } else {
            System.out.println("\nNenhuma reserva realizada ainda.");
        }
        System.out.println("---------------------");
    }

    private void exibirEspetaculoMaisArrecadou() {
        Espetaculo maisArrecadou = null;
        double maiorArrecadacao = 0;

        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);
                double arrecadacao = espetaculo.quantidadeAssentosOcupados() * espetaculo.getPreco();

                if (arrecadacao > maiorArrecadacao) {
                    maiorArrecadacao = arrecadacao;
                    maisArrecadou = espetaculo;
                }
            } catch (Exception e) {
                System.out.println("Erro ao calcular estatisticas.");
                return;
            }
        }

        if (maisArrecadou != null) {
            System.out.println("Espetaculo que mais arrecadou: " + maisArrecadou.getNome());
            System.out.printf("Arrecadacao: R$ %.2f%n", maiorArrecadacao);
        } else {
            System.out.println("Nenhuma arrecadacao registrada ainda.");
        }
        System.out.println("---------------------");
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem);

            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private int lerQuantidade() {
        while (true) {
            int quantidade = lerInteiro("Quantidade de ingressos (1 a 4): ");

            if (quantidade >= 1 && quantidade <= 4) {
                return quantidade;
            }

            System.out.println("A quantidade deve estar entre 1 e 4.");
        }
    }

    private String lerCpf() {
        while (true) {
            String cpf = lerTexto("CPF (11 digitos, somente numeros): ");
            boolean valido = cpf.length() == 11;

            for (int indice = 0; valido && indice < cpf.length(); indice++) {
                if (cpf.charAt(indice) < '0' || cpf.charAt(indice) > '9') {
                    valido = false;
                }
            }

            if (valido) {
                return cpf;
            }

            System.out.println("CPF invalido. Digite exatamente 11 digitos numericos.");
        }
    }

    private String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Digite uma informacao valida.");
        }
    }
}

public class Espetaculo {
    private int codigo;
    private String nome;
    private String data;
    private String horario;
    private double preco;
    private char[][] assentos;

    public Espetaculo(int codigo, String nome, String data, String horario, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.data = data;
        this.horario = horario;
        this.preco = preco;
        assentos = new char[5][8];

        for (int linha = 0; linha < assentos.length; linha++) {
            for (int coluna = 0; coluna < assentos[linha].length; coluna++) {
                assentos[linha][coluna] = 'L';
            }
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public double getPreco() {
        return preco;
    }

    public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < assentos.length && coluna >= 0 && coluna < assentos[linha].length;
    }

    public boolean estaLivre(int linha, int coluna) {
        return posicaoValida(linha, coluna) && assentos[linha][coluna] == 'L';
    }

    public void ocuparAssento(int linha, int coluna) {
        if (estaLivre(linha, coluna)) {
            assentos[linha][coluna] = 'X';
        }
    }

    public int quantidadeAssentosLivres() {
        int livres = 0;

        for (int linha = 0; linha < assentos.length; linha++) {
            for (int coluna = 0; coluna < assentos[linha].length; coluna++) {
                if (assentos[linha][coluna] == 'L') {
                    livres++;
                }
            }
        }

        return livres;
    }

    public int quantidadeAssentosOcupados(){
         
        int ocupados = 0;

        for (int linha = 0; linha < assentos.length; linha++) {
            for (int coluna = 0; coluna < assentos[linha].length; coluna++) {
                if (assentos[linha][coluna] == 'X') {
                    ocupados++;
                }
            }
        }

        return ocupados;
    }

    public void exibirMapa() {
        System.out.println("\n    1 2 3 4 5 6 7 8");

        for (int linha = 0; linha < assentos.length; linha++) {
            System.out.print((char) ('A' + linha) + "   ");

            for (int coluna = 0; coluna < assentos[linha].length; coluna++) {
                System.out.print(assentos[linha][coluna] + " ");
            }

            System.out.println();
        }
    }
}

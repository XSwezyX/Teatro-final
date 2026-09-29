
public class Reserva {
    private String cpf;
    private String nome;
    private Espetaculo espetaculo;
    private int qtdeAssentos;
    private String[] assentos;

    public Reserva(String cpf, String nome, Espetaculo espetaculo, int qtdeAssentos, String[] assentos) {
        this.cpf = cpf;
        this.nome = nome;
        this.espetaculo = espetaculo;
        this.qtdeAssentos = qtdeAssentos;
        this.assentos = assentos;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Espetaculo getEspetaculo() {
        return espetaculo;
    }

    public int getQtdeAssentos() {
        return qtdeAssentos;
    }

    public String[] getAssentos() {
        return assentos;
    }
}

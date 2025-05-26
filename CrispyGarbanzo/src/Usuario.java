import java.util.ArrayList;

public abstract class Usuario {
    protected String nome;
    protected String email;
    protected String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public abstract void imprimirDados();

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

class Proprietario extends Usuario {
    private ArrayList<Propriedade> propriedades;

    public Proprietario(String nome, String email, String senha) {
        super(nome, email, senha);
        this.propriedades = new ArrayList<>();
    }

    public void cadastrarPropriedade(Propriedade p) {
        propriedades.add(p);
    }

    public void listarPropriedades() {
        for (Propriedade p : propriedades) {
            p.imprimirDados();
        }
    }

    @Override
    public void imprimirDados() {
        System.out.println("Proprietário: " + nome + " - " + email);
    }
    // Getters e Setters...
}

class Cliente extends Usuario {
    private ArrayList<Reserva> reservasRealizadas;

    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha);
        this.reservasRealizadas = new ArrayList<>();
    }

    public void realizarReserva(Propriedade p, LocalDate checkIn, LocalDate checkOut) {
        // lógica de reserva
    }

    public void listarReservas() {
        for (Reserva r : reservasRealizadas) {
            r.imprimirReserva();
        }
    }

    @Override
    public void imprimirDados() {
        System.out.println("Cliente: " + nome + " - " + email);
    }
    // Getters e Setters...
}

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Usuario implements Serializable {
    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public abstract void imprimirDados();

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

// Proprietario
class Proprietario extends Usuario {
    private ArrayList<Propriedade> propriedades = new ArrayList<>();

    public Proprietario(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public ArrayList<Propriedade> getPropriedades() { return propriedades; }

    public void cadastrarPropriedade(Propriedade p) { propriedades.add(p); }

    public void listarPropriedades() {
        System.out.println("Propriedades do proprietário " + getNome() + ":");
        for (Propriedade p : propriedades) {
            p.imprimirDados();
            System.out.println();
        }
    }

    public void listarPropriedadesAlugadas(ArrayList<Reserva> reservas) {
        System.out.println("Propriedades alugadas do proprietário " + getNome() + ":");
        for (Reserva r : reservas) {
            if (propriedades.contains(r.getPropriedade())) {
                r.getPropriedade().imprimirDados();
                System.out.println("Alugada por: " + r.getCliente().getNome());
                System.out.println();
            }
        }
    }

    @Override
    public void imprimirDados() {
        System.out.println("Proprietário: " + getNome() + " - " + getEmail());
    }
}

// Cliente
class Cliente extends Usuario {
    private ArrayList<Reserva> reservasRealizadas = new ArrayList<>();

    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public ArrayList<Reserva> getReservasRealizadas() { return reservasRealizadas; }

    public void realizarReserva(Propriedade p, LocalDate checkIn, LocalDate checkOut) {
        Reserva novaReserva = new Reserva(p, this, checkIn, checkOut);
        reservasRealizadas.add(novaReserva);
    }

    public void listarReservas() {
        System.out.println("Reservas realizadas por " + getNome() + ":");
        for (Reserva r : reservasRealizadas) {
            r.imprimirReserva();
            System.out.println();
        }
    }

    @Override
    public void imprimirDados() {
        System.out.println("Cliente: " + getNome() + " - " + getEmail());
    }
}

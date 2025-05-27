import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private Propriedade propriedade;
    private Cliente cliente;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double custoTotal;

    public Reserva(Propriedade propriedade, Cliente cliente, LocalDate checkIn, LocalDate checkOut) {
        this.propriedade = propriedade;
        this.cliente = cliente;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.custoTotal = calcularCustoTotal();
    }

    public double calcularCustoTotal() {
        long dias = ChronoUnit.DAYS.between(checkIn, checkOut);
        return propriedade.calcularPrecoTotal((int) dias);
    }

    public void imprimirReserva() {
        System.out.println("=== Detalhes da Reserva ===");
        System.out.println("Propriedade: " + propriedade.getTitulo());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Custo Total: R$" + custoTotal);
    }

    // Getters e Setters
    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(double custoTotal) {
        this.custoTotal = custoTotal;
    }
}

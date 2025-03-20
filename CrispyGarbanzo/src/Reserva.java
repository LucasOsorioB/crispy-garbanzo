import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Reserva {
    private Propriedade propriedade;
    private Locatario locatario;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double custoTotal;

    public void imprimirReserva() {
        System.out.println("=== Detalhes da Reserva ===");
        System.out.println("Propriedade: " + propriedade.getTitulo());
        System.out.println("Locatário: " + locatario.nome);
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Custo Total: R$" + custoTotal);
    }
    
    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Locatario getLocatario() {
        return locatario;
    }

    public void setLocatario(Locatario locatario) {
        this.locatario = locatario;
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

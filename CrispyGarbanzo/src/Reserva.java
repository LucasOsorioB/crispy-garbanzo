import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Reserva {
    private Propriedade propriedade;
    private Locatario locatario;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double custoTotal;

    public Reserva(Propriedade propriedade, Locatario locatario, LocalDate checkIn, LocalDate checkOut) {
        this.propriedade = propriedade;
        this.locatario = locatario;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.custoTotal = calcularCustoTotal();
    }

    public double calcularCustoTotal() {
        long noites = ChronoUnit.DAYS.between(checkIn, checkOut);
        return noites * propriedade.getPrecoPorNoite();
    }

    public void imprimirReserva() {
        System.out.println("=== Detalhes da Reserva ===");
        System.out.println("Propriedade: " + propriedade.getTitulo());
        System.out.println("Locatário: " + locatario.nome);
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Custo Total: R$" + custoTotal);
    }

    public static Reserva fazerReserva(Propriedade propriedade, Locatario locatario, LocalDate checkIn, LocalDate checkOut) {
        if (!propriedade.verificarDisponibilidade()) {
            System.out.println("A propriedade não está disponível para reserva.");
            return null;
        }

        Reserva reserva = new Reserva(propriedade, locatario, checkIn, checkOut);
        propriedade.setDisponibilidade(false);
        System.out.println("Reserva realizada com sucesso!");
        return reserva;
    }
}
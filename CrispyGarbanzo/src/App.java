import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<Proprietario> proprietarios;
    private List<Locatario> locatarios;
    private List<Propriedade> propriedades;
    private List<Reserva> reservas;

    public App() {
        this.proprietarios = new ArrayList<>();
        this.locatarios = new ArrayList<>();
        this.propriedades = new ArrayList<>();
        this.reservas = new ArrayList<>();

        // Adicionando proprietários locatários
        Proprietario proprietario1 = new Proprietario("Carlos", "carlos@email.com", "senha123", 5);
        Locatario locatario1 = new Locatario("Ana", "ana@email.com", "segredo", "123.456.789-00");

        this.proprietarios.add(proprietario1);
        this.locatarios.add(locatario1);

        // Adicionandopropriedades
        Propriedade propriedade1 = new Propriedade(true, "Apartamento no Centro",
            "Apartamento aconchegante com vista para a cidade.",
            "São Paulo, SP", 4, 250.00, proprietario1);

        this.propriedades.add(propriedade1);
    }

    public void listarUsuarios() {
        System.out.println("=== Lista de Usuários ===");
        for (Proprietario proprietario : proprietarios) {
            proprietario.imprimirDados();
            System.out.println();
        }
        for (Locatario locatario : locatarios) {
            locatario.imprimirDados();
            System.out.println();
        }
    }

    public void listarPropriedades() {
        System.out.println("=== Lista de Propriedades ===");
        for (Propriedade propriedade : propriedades) {
            propriedade.imprimirDados();
            System.out.println();
        }
    }

    public void cadastrarPropriedade() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Cadastro de Propriedade ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Localização: ");
        String localizacao = scanner.nextLine();
        System.out.print("Capacidade: ");
        int capacidade = scanner.nextInt();
        System.out.print("Preço por noite: ");
        double precoPorNoite = scanner.nextDouble();
        scanner.nextLine();

        // Supondo que o primeiro proprietário seja o dono da nova propriedade
        Proprietario proprietario = proprietarios.get(0);

        Propriedade novaPropriedade = new Propriedade(true, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        propriedades.add(novaPropriedade);
        System.out.println("Propriedade cadastrada com sucesso!");
    }

    public void menuProprietario() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== Menu Proprietário ===");
            System.out.println("1. Cadastrar Propriedade");
            System.out.println("2. Exibir Detalhes das Propriedades");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarPropriedade();
                    break;
                case 2:
                    listarPropriedades();
                    break;
                case 3:
                    System.out.println("Saindo do menu proprietário...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    public void menuUsuario() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== Menu Usuário ===");
            System.out.println("1. Consultar Propriedades Disponíveis");
            System.out.println("2. Alugar Propriedade");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    consultarPropriedadesDisponiveis();
                    break;
                case 2:
                    alugarPropriedade();
                    break;
                case 3:
                    System.out.println("Saindo do menu usuário...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    public void consultarPropriedadesDisponiveis() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Data de check-in (AAAA-MM-DD): ");
        LocalDate checkIn = LocalDate.parse(scanner.nextLine());
        System.out.print("Data de check-out (AAAA-MM-DD): ");
        LocalDate checkOut = LocalDate.parse(scanner.nextLine());

        System.out.println("=== Propriedades Disponíveis ===");
        for (Propriedade propriedade : propriedades) {
            boolean disponivel = true;
            for (Reserva reserva : reservas) {
                if (reserva.getPropriedade().equals(propriedade) &&
                    (checkIn.isBefore(reserva.getCheckOut()) && checkOut.isAfter(reserva.getCheckIn()))) {
                    disponivel = false;
                    break;
                }
            }
            if (disponivel) {
                propriedade.imprimirDados();
                System.out.println();
            }
        }
    }

    public void alugarPropriedade() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Data de check-in (AAAA-MM-DD): ");
        LocalDate checkIn = LocalDate.parse(scanner.nextLine());
        System.out.print("Data de check-out (AAAA-MM-DD): ");
        LocalDate checkOut = LocalDate.parse(scanner.nextLine());

        System.out.println("=== Propriedades Disponíveis ===");
        List<Propriedade> disponiveis = new ArrayList<>();
        for (Propriedade propriedade : propriedades) {
            boolean disponivel = true;
            for (Reserva reserva : reservas) {
                if (reserva.getPropriedade().equals(propriedade) &&
                    (checkIn.isBefore(reserva.getCheckOut()) && checkOut.isAfter(reserva.getCheckIn()))) {
                    disponivel = false;
                    break;
                }
            }
            if (disponivel) {
                disponiveis.add(propriedade);
                propriedade.imprimirDados();
                System.out.println();
            }
        }

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhuma propriedade disponível para as datas selecionadas.");
            return;
        }

        System.out.print("Escolha a propriedade pelo título: ");
        String titulo = scanner.nextLine();
        Propriedade propriedadeEscolhida = null;
        for (Propriedade propriedade : disponiveis) {
            if (propriedade.getTitulo().equalsIgnoreCase(titulo)) {
                propriedadeEscolhida = propriedade;
                break;
            }
        }

        if (propriedadeEscolhida == null) {
            System.out.println("Propriedade não encontrada.");
            return;
        }

        // Supondo que o primeiro locatário seja o que está alugando a propriedade
        Locatario locatario = locatarios.get(0);

        Reserva novaReserva = new Reserva(propriedadeEscolhida, locatario, checkIn, checkOut);
        reservas.add(novaReserva);

        long dias = ChronoUnit.DAYS.between(checkIn, checkOut);
        double custoTotal = dias * propriedadeEscolhida.getPrecoPorNoite();

        System.out.println("Reserva realizada com sucesso!");
        System.out.println("Custo total da reserva: R$ " + custoTotal);
    }

    public static void main(String[] args) {
        App app = new App();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== Menu Inicial ===");
            System.out.println("1. Proprietário");
            System.out.println("2. Usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    app.menuProprietario();
                    break;
                case 2:
                    app.menuUsuario();
                    break;
                case 3:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
    }
}

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    private Proprietario proprietario;
    private Locatario locatario;
    private Propriedade propriedade;

    public App() {
        this.proprietario = new Proprietario("Carlos", "carlos@email.com", "senha123", 5);
        this.locatario = new Locatario("Ana", "ana@email.com", "segredo", "123.456.789-00");
        this.propriedade = new Propriedade(true, "Apartamento no Centro",
            "Apartamento aconchegante com vista para a cidade.",
            "São Paulo, SP", 4, 250.00, proprietario);
    }

    public void listarUsuarios() {
        System.out.println("=== Lista de Usuários ===");
        proprietario.imprimirDados();
        System.out.println();
        locatario.imprimirDados();
        System.out.println();
    }

    public void listarPropriedades() {
        System.out.println("=== Lista de Propriedades ===");
        propriedade.imprimirDados();
        System.out.println();
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
        scanner.nextLine(); // Consumir a nova linha

        Propriedade novaPropriedade = new Propriedade(true, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
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
            scanner.nextLine(); // Consumir a nova linha

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
                    listarPropriedades();
                    break;
                case 2:
                    // Simulando uma reserva
                    LocalDate checkIn = LocalDate.of(2025, 4, 10);
                    LocalDate checkOut = LocalDate.of(2025, 4, 15);
                    Reserva reserva = Reserva.fazerReserva(propriedade, locatario, checkIn, checkOut);

                    if (reserva != null) {
                        reserva.imprimirReserva();
                    }
                    break;
                case 3:
                    System.out.println("Saindo do menu usuário...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
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
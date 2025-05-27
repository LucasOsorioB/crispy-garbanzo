import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    
    private UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
    private PropriedadeDAO propriedadeDAO = DAOFactory.getPropriedadeDAO();
    private ReservaDAO reservaDAO = DAOFactory.getReservaDAO();

    private List<Usuario> usuarios;
    private List<Propriedade> propriedades;
    private List<Reserva> reservas;

    public App() {
        this.usuarios = new ArrayList<>();
        this.propriedades = new ArrayList<>();
        this.reservas = new ArrayList<>();

        // Dados iniciais (exemplo)
        Proprietario proprietario1 = new Proprietario("Carlos", "carlos@email.com", "senha123");
        Cliente cliente1 = new Cliente("Ana", "ana@email.com", "segredo");
        usuarios.add(proprietario1);
        usuarios.add(cliente1);

        // Propriedades iniciais
        propriedades.add(new Casa(true, "Casa de Praia", "Casa com piscina", "Guarujá", 8, 500.0, proprietario1, true, 1.2));
        propriedades.add(new Apartamento(true, "Apto Centro", "Apto moderno", "São Paulo", 4, 300.0, proprietario1, 10, 50.0));
        propriedades.add(new Sitio(true, "Sítio Verde", "Sítio amplo", "Interior", 15, 800.0, proprietario1, 5.0));
    }

    public void listarUsuarios() {
        System.out.println("=== Lista de Usuários ===");
        for (Usuario usuario : usuarios) {
            usuario.imprimirDados();
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

    public void cadastrarUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Cadastro de Usuário ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Tipo (1-Proprietário, 2-Cliente): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            usuarios.add(new Proprietario(nome, email, senha));
            System.out.println("Proprietário cadastrado com sucesso!");
        } else {
            usuarios.add(new Cliente(nome, email, senha));
            System.out.println("Cliente cadastrado com sucesso!");
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

        System.out.println("Tipo (1-Casa, 2-Apartamento, 3-Sítio): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        // Seleciona o proprietário (simples: primeiro proprietário da lista)
        Proprietario proprietario = null;
        for (Usuario u : usuarios) {
            if (u instanceof Proprietario) {
                proprietario = (Proprietario) u;
                break;
            }
        }
        if (proprietario == null) {
            System.out.println("Nenhum proprietário cadastrado.");
            return;
        }

        Propriedade novaPropriedade = null;
        if (tipo == 1) {
            System.out.print("Possui piscina (true/false): ");
            boolean piscina = scanner.nextBoolean();
            System.out.print("Preço por pessoa: ");
            double precoPorPessoa = scanner.nextDouble();
            novaPropriedade = new Casa(true, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, piscina, precoPorPessoa);
        } else if (tipo == 2) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            System.out.print("Taxa: ");
            double taxa = scanner.nextDouble();
            novaPropriedade = new Apartamento(true, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, andar, taxa);
        } else if (tipo == 3) {
            System.out.print("Área total (hectares): ");
            double area = scanner.nextDouble();
            novaPropriedade = new Sitio(true, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, area);
        }

        if (novaPropriedade != null) {
            propriedades.add(novaPropriedade);
            proprietario.cadastrarPropriedade(novaPropriedade);
            System.out.println("Propriedade cadastrada com sucesso!");
        }
    }

    public void menuProprietario() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== Menu Proprietário ===");
            System.out.println("1. Cadastrar Propriedade");
            System.out.println("2. Exibir Detalhes das Propriedades");
            System.out.println("3. Listar Propriedades Alugadas");
            System.out.println("4. Sair");
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
                    // Exemplo: listar propriedades alugadas do primeiro proprietário
                    for (Usuario u : usuarios) {
                        if (u instanceof Proprietario) {
                            ((Proprietario) u).listarPropriedadesAlugadas(new ArrayList<>(reservas));
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saindo do menu proprietário...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
    }

    public void menuCliente() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== Menu Cliente ===");
            System.out.println("1. Consultar Propriedades Disponíveis");
            System.out.println("2. Alugar Propriedade");
            System.out.println("3. Listar Reservas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    consultarPropriedadesDisponiveis();
                    break;
                case 2:
                    alugarPropriedade();
                    break;
                case 3:
                    // Exemplo: listar reservas do primeiro cliente
                    for (Usuario u : usuarios) {
                        if (u instanceof Cliente) {
                            ((Cliente) u).listarReservas();
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saindo do menu cliente...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
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

        // Seleciona o cliente (simples: primeiro cliente da lista)
        Cliente cliente = null;
        for (Usuario u : usuarios) {
            if (u instanceof Cliente) {
                cliente = (Cliente) u;
                break;
            }
        }
        if (cliente == null) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        Reserva novaReserva = new Reserva(propriedadeEscolhida, cliente, checkIn, checkOut);
        reservas.add(novaReserva);
        cliente.realizarReserva(propriedadeEscolhida, checkIn, checkOut);

        System.out.println("Reserva realizada com sucesso!");
        System.out.println("Custo total da reserva: R$ " + novaReserva.getCustoTotal());
    }

    public static void criarBancoETabelas() {
        String url = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:6543/postgres?user=postgres.moojvwojczvwtajvxteg&password=BANCODEDADOS1";
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            // Tabela de usuários
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT,
                    email TEXT UNIQUE,
                    senha TEXT,
                    tipo TEXT
                );
            """);
            // Tabela de propriedades
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS propriedades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT,
                    descricao TEXT,
                    localizacao TEXT,
                    capacidade INTEGER,
                    precoPorNoite REAL,
                    disponivel BOOLEAN,
                    proprietario_email TEXT
                );
            """);
            // Tabela de reservas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    propriedade_id INTEGER,
                    cliente_email TEXT,
                    checkIn TEXT,
                    checkOut TEXT,
                    custoTotal REAL
                );
            """);
            System.out.println("Banco e tabelas criados/verificados com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar banco/tabelas: " + e.getMessage());
        }
    }

    public static void main(String[] args) { // Cria o banco e as tabelas antes de iniciar o app
        App app = new App();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("=== Menu Inicial ===");
            System.out.println("1. Proprietário");
            System.out.println("2. Cliente");
            System.out.println("3. Cadastrar Usuário");
            System.out.println("4. Listar Usuários");
            System.out.println("5. Listar Propriedades");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    app.menuProprietario();
                    break;
                case 2:
                    app.menuCliente();
                    break;
                case 3:
                    app.cadastrarUsuario();
                    break;
                case 4:
                    app.listarUsuarios();
                    break;
                case 5:
                    app.listarPropriedades();
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
    }
}

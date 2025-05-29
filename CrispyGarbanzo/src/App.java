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
        // Carrega dados dos arquivos
        this.usuarios = usuarioDAO.listar();
        this.propriedades = propriedadeDAO.listar();
        this.reservas = reservaDAO.listar();

        // Se não houver dados, cria alguns exemplos
        if (usuarios.isEmpty() && propriedades.isEmpty()) {
            Proprietario proprietario1 = new Proprietario("Carlos", "carlos@email.com", "senha123");
            Cliente cliente1 = new Cliente("Ana", "ana@email.com", "segredo");
            usuarios.add(proprietario1);
            usuarios.add(cliente1);

            Casa casa = new Casa(true, "Casa de Praia", "Casa com piscina", "Guarujá", 8, 500.0, proprietario1, true, 1.2);
            Apartamento apto = new Apartamento(true, "Apto Centro", "Apto moderno", "São Paulo", 4, 300.0, proprietario1, 10, 50.0);
            Sitio sitio = new Sitio(true, "Sítio Verde", "Sítio amplo", "Interior", 15, 800.0, proprietario1, 5.0);

            propriedades.add(casa);
            propriedades.add(apto);
            propriedades.add(sitio);

            proprietario1.cadastrarPropriedade(casa);
            proprietario1.cadastrarPropriedade(apto);
            proprietario1.cadastrarPropriedade(sitio);

            usuarioDAO.salvar(proprietario1);
            usuarioDAO.salvar(cliente1);
            propriedadeDAO.salvar(casa);
            propriedadeDAO.salvar(apto);
            propriedadeDAO.salvar(sitio);
        }
    }

    private void salvarTudo() {
        // Salva usuários
        List<String> linhasUsuarios = new ArrayList<>();
        for (Usuario u : usuarios) {
            String tipo = u instanceof Proprietario ? "Proprietario" : "Cliente";
            StringBuilder sb = new StringBuilder();
            sb.append(tipo).append(";");
            sb.append(u.getNome()).append(";");
            sb.append(u.getEmail()).append(";");
            sb.append(u.getSenha());
            linhasUsuarios.add(sb.toString());
        }
        ArquivoUtil.salvarLinhas(linhasUsuarios, "usuarios.csv");

        // Salva propriedades (já está implementado na PropriedadeArquivoDAO)
        ((PropriedadeArquivoDAO) propriedadeDAO).salvarTodas(propriedades);

        // Salva reservas
        List<String> linhasReservas = new ArrayList<>();
        for (Reserva r : reservas) {
            linhasReservas.add(
                r.getPropriedade().getTitulo() + ";" +
                r.getCliente().getEmail() + ";" +
                r.getCheckIn() + ";" +
                r.getCheckOut() + ";" +
                r.getCustoTotal()
            );
        }
        ArquivoUtil.salvarLinhas(linhasReservas, "reservas.csv");
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

        Usuario novoUsuario;
        if (tipo == 1) {
            novoUsuario = new Proprietario(nome, email, senha);
            System.out.println("Proprietário cadastrado com sucesso!");
        } else {
            novoUsuario = new Cliente(nome, email, senha);
            System.out.println("Cliente cadastrado com sucesso!");
        }
        usuarios.add(novoUsuario);
        usuarioDAO.salvar(novoUsuario);
        salvarTudo();
    }

    public void cadastrarPropriedade(Proprietario proprietario) {
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
            propriedadeDAO.salvar(novaPropriedade);
            usuarioDAO.salvar(proprietario);
            salvarTudo();
            System.out.println("Propriedade cadastrada com sucesso!");
        }
    }

    private Usuario autenticarUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        System.out.println("Usuário ou senha inválidos.");
        return null;
    }

    public void menuProprietario(Proprietario proprietario) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;
        do {
            System.out.println("=== Menu Proprietário ===");
            System.out.println("1. Cadastrar Propriedade");
            System.out.println("2. Exibir Detalhes das Propriedades");
            System.out.println("3. Listar Propriedades Alugadas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Digite um número.");
                scanner.next(); // descarta entrada inválida
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPropriedade(proprietario);
                    break;
                case 2:
                    proprietario.listarPropriedades();
                    break;
                case 3:
                    proprietario.listarPropriedadesAlugadas(new ArrayList<>(reservas));
                    break;
                case 4:
                    System.out.println("Saindo do menu proprietário...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
    }

    public void menuCliente(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;
        do {
            System.out.println("=== Menu Cliente ===");
            System.out.println("1. Consultar Propriedades Disponíveis");
            System.out.println("2. Alugar Propriedade");
            System.out.println("3. Listar Reservas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Digite um número.");
                scanner.next(); // descarta entrada inválida
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    consultarPropriedadesDisponiveis();
                    break;
                case 2:
                    alugarPropriedade(cliente);
                    break;
                case 3:
                    cliente.listarReservas();
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

    public void alugarPropriedade(Cliente cliente) {
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

        // Checa se já existe reserva para essa propriedade e datas
        for (Reserva reserva : reservas) {
            if (reserva.getPropriedade().equals(propriedadeEscolhida) &&
                (checkIn.isBefore(reserva.getCheckOut()) && checkOut.isAfter(reserva.getCheckIn()))) {
                System.out.println("Já existe uma reserva para essa propriedade nesse período!");
                return;
            }
        }

        Reserva novaReserva = new Reserva(propriedadeEscolhida, cliente, checkIn, checkOut);
        reservas.add(novaReserva);
        reservaDAO.salvar(novaReserva);
        cliente.realizarReserva(propriedadeEscolhida, checkIn, checkOut);
        usuarioDAO.salvar(cliente);
        salvarTudo();

        System.out.println("Reserva realizada com sucesso!");
        System.out.println("Custo total da reserva: R$ " + novaReserva.getCustoTotal());
    }

    public static void main(String[] args) {
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
                case 1: {
                    Usuario usuario = app.autenticarUsuario();
                    if (usuario instanceof Proprietario) {
                        app.menuProprietario((Proprietario) usuario);
                    } else {
                        System.out.println("Acesso negado. Não é um proprietário.");
                    }
                    break;
                }
                case 2: {
                    Usuario usuario = app.autenticarUsuario();
                    if (usuario instanceof Cliente) {
                        app.menuCliente((Cliente) usuario);
                    } else {
                        System.out.println("Acesso negado. Não é um cliente.");
                    }
                    break;
                }
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
                    app.salvarTudo();
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
    }
}

class Usuario {
    protected String nome;
    protected String email;
    protected String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void imprimirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
    }
}

class Proprietario extends Usuario {
    private int numeroDeImoveis;

    public Proprietario(String nome, String email, String senha, int numeroDeImoveis) {
        super(nome, email, senha);
        this.numeroDeImoveis = numeroDeImoveis;
    }

    @Override
    public void imprimirDados() {
        super.imprimirDados();
        System.out.println("Número de imóveis: " + numeroDeImoveis);
    }
}

class Locatario extends Usuario {
    private String documento;

    public Locatario(String nome, String email, String senha, String documento) {
        super(nome, email, senha);
        this.documento = documento;
    }

    @Override
    public void imprimirDados() {
        super.imprimirDados();
        System.out.println("Documento: " + documento);
    }
}

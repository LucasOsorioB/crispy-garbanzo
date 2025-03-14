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

    // Getters e Setters
    public int getNumeroDeImoveis() {
        return numeroDeImoveis;
    }

    public void setNumeroDeImoveis(int numeroDeImoveis) {
        this.numeroDeImoveis = numeroDeImoveis;
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

    // Getters e Setters
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}

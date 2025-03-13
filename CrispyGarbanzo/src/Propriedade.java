class Propriedade {
    private boolean disponivel;
    private String titulo;
    private String descricao;
    private String localizacao;
    private int capacidade;
    private double precoPorNoite;
    private Proprietario proprietario;

    public Propriedade(boolean disponivel, String titulo, String descricao, 
                       String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario) {
        this.disponivel = disponivel;
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.capacidade = capacidade;
        this.precoPorNoite = precoPorNoite;
        this.proprietario = proprietario;
    }

    public boolean verificarDisponibilidade() {
        return disponivel;
    }

    public void imprimirDados() {
        System.out.println("Título: " + titulo);
        System.out.println("Descrição: " + descricao);
        System.out.println("Localização: " + localizacao);
        System.out.println("Capacidade: " + capacidade + " pessoas");
        System.out.println("Preço por noite: R$" + precoPorNoite);
        System.out.println("Proprietário: " + proprietario.nome);
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
    }

    public void setDisponibilidade(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public double getPrecoPorNoite() {
        return precoPorNoite;
    }

    public String getTitulo() {
        return titulo;
    }
}
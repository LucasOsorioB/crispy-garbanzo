public abstract class Propriedade {
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

    public abstract void imprimirDados();
    public abstract double calcularPrecoTotal(int dias);

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

public class Casa extends Propriedade {
    private boolean possuiPiscina;
    private double precoPorPessoa;

    public Casa(boolean disponivel, String titulo, String descricao, 
                String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario,
                boolean possuiPiscina, double precoPorPessoa) {
        super(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        this.possuiPiscina = possuiPiscina;
        this.precoPorPessoa = precoPorPessoa;
    }

    @Override
    public void imprimirDados() {
        super.imprimirDados();
        System.out.println("Possui piscina: " + (possuiPiscina ? "Sim" : "Não"));
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return precoPorPessoa * dias;
    }
}

public class Apartamento extends Propriedade {
    private int andar;
    private double taxa;

    public Apartamento(boolean disponivel, String titulo, String descricao, 
                       String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario,
                       int andar, double taxa) {
        super(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        this.andar = andar;
        this.taxa = taxa;
    }

    @Override
    public void imprimirDados() {
        super.imprimirDados();
        System.out.println("Andar: " + andar);
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return taxa + (getPrecoPorNoite() * dias);
    }
}

public class Sitio extends Propriedade {
    private double areaTotal;

    public Sitio(boolean disponivel, String titulo, String descricao, 
                 String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario,
                 double areaTotal) {
        super(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        this.areaTotal = areaTotal;
    }

    @Override
    public void imprimirDados() {
        super.imprimirDados();
        System.out.println("Área total: " + areaTotal + " hectares");
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return getPrecoPorNoite() * dias;
    }
}
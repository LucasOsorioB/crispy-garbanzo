import java.io.Serializable;

public abstract class Propriedade implements Serializable {
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

    public boolean verificarDisponibilidade() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
    public double getPrecoPorNoite() { return precoPorNoite; }
    public void setPrecoPorNoite(double precoPorNoite) { this.precoPorNoite = precoPorNoite; }
    public Proprietario getProprietario() { return proprietario; }
    public void setProprietario(Proprietario proprietario) { this.proprietario = proprietario; }

    public abstract void imprimirDados();
    public abstract double calcularPrecoTotal(int dias);
}

// =================== CASA ===================
class Casa extends Propriedade {
    private boolean possuiPiscina;
    private double precoPorPessoa;

    public Casa(boolean disponivel, String titulo, String descricao,
                String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario,
                boolean possuiPiscina, double precoPorPessoa) {
        super(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        this.possuiPiscina = possuiPiscina;
        this.precoPorPessoa = precoPorPessoa;
    }

    public boolean isPossuiPiscina() {
        return possuiPiscina;
    }

    public void setPossuiPiscina(boolean possuiPiscina) {
        this.possuiPiscina = possuiPiscina;
    }

    public double getPrecoPorPessoa() {
        return precoPorPessoa;
    }

    public void setPrecoPorPessoa(double precoPorPessoa) {
        this.precoPorPessoa = precoPorPessoa;
    }

    @Override
    public void imprimirDados() {
        System.out.println("Tipo: Casa");
        System.out.println("Título: " + getTitulo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Localização: " + getLocalizacao());
        System.out.println("Capacidade: " + getCapacidade());
        System.out.println("Preço por noite: R$" + getPrecoPorNoite());
        System.out.println("Proprietário: " + (getProprietario() != null ? getProprietario().getNome() : "N/A"));
        System.out.println("Disponível: " + (verificarDisponibilidade() ? "Sim" : "Não"));
        System.out.println("Possui piscina: " + (possuiPiscina ? "Sim" : "Não"));
        System.out.println("Preço por pessoa: R$" + precoPorPessoa);
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return getPrecoPorNoite() * dias * precoPorPessoa;
    }
}

// =================== APARTAMENTO ===================
class Apartamento extends Propriedade {
    private int andar;
    private double taxa;

    public Apartamento(boolean disponivel, String titulo, String descricao,
                       String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario,
                       int andar, double taxa) {
        super(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        this.andar = andar;
        this.taxa = taxa;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    @Override
    public void imprimirDados() {
        System.out.println("Tipo: Apartamento");
        System.out.println("Título: " + getTitulo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Localização: " + getLocalizacao());
        System.out.println("Capacidade: " + getCapacidade());
        System.out.println("Preço por noite: R$" + getPrecoPorNoite());
        System.out.println("Proprietário: " + (getProprietario() != null ? getProprietario().getNome() : "N/A"));
        System.out.println("Disponível: " + (verificarDisponibilidade() ? "Sim" : "Não"));
        System.out.println("Andar: " + andar);
        System.out.println("Taxa: R$" + taxa);
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return taxa + (getPrecoPorNoite() * dias);
    }
}

// =================== SITIO ===================
class Sitio extends Propriedade {
    private double areaTotal;

    public Sitio(boolean disponivel, String titulo, String descricao,
                 String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario,
                 double areaTotal) {
        super(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario);
        this.areaTotal = areaTotal;
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        this.areaTotal = areaTotal;
    }

    @Override
    public void imprimirDados() {
        System.out.println("Tipo: Sítio");
        System.out.println("Título: " + getTitulo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Localização: " + getLocalizacao());
        System.out.println("Capacidade: " + getCapacidade());
        System.out.println("Preço por noite: R$" + getPrecoPorNoite());
        System.out.println("Proprietário: " + (getProprietario() != null ? getProprietario().getNome() : "N/A"));
        System.out.println("Disponível: " + (verificarDisponibilidade() ? "Sim" : "Não"));
        System.out.println("Área total: " + areaTotal + " hectares");
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return getPrecoPorNoite() * dias;
    }
}
import java.util.*;

public class PropriedadeArquivoDAO implements PropriedadeDAO {
    private static final String ARQUIVO = "propriedades.csv";

    @Override
    public void salvar(Propriedade propriedade) {
        List<Propriedade> propriedades = listar();
        propriedades.add(propriedade);
        salvarTodas(propriedades);
    }

    // Salva todas as propriedades em arquivo
    public void salvarTodas(List<Propriedade> propriedades) {
        List<String> linhas = new ArrayList<>();
        for (Propriedade p : propriedades) {
            String tipo = p instanceof Casa ? "Casa" : p instanceof Apartamento ? "Apartamento" : "Sitio";
            StringBuilder sb = new StringBuilder();
            sb.append(tipo).append(";");
            sb.append(p.verificarDisponibilidade()).append(";");
            sb.append(p.getTitulo()).append(";");
            sb.append(p.getDescricao()).append(";");
            sb.append(p.getLocalizacao()).append(";");
            sb.append(p.getCapacidade()).append(";");
            sb.append(p.getPrecoPorNoite()).append(";");
            sb.append(p.getProprietario().getEmail()).append(";"); // Salva referência pelo email

            if (p instanceof Casa) {
                Casa c = (Casa) p;
                sb.append(c.isPossuiPiscina()).append(";").append(c.getPrecoPorPessoa());
            } else if (p instanceof Apartamento) {
                Apartamento a = (Apartamento) p;
                sb.append(a.getAndar()).append(";").append(a.getTaxa());
            } else if (p instanceof Sitio) {
                Sitio s = (Sitio) p;
                sb.append(s.getAreaTotal());
            }
            linhas.add(sb.toString());
        }
        ArquivoUtil.salvarLinhas(linhas, ARQUIVO);
    }

    @Override
    public List<Propriedade> listar() {
        List<Propriedade> propriedades = new ArrayList<>();
        List<String> linhas = ArquivoUtil.carregarLinhas(ARQUIVO);
        for (String linha : linhas) {
            String[] campos = linha.split(";");
            String tipo = campos[0];
            boolean disponivel = Boolean.parseBoolean(campos[1]);
            String titulo = campos[2];
            String descricao = campos[3];
            String localizacao = campos[4];
            int capacidade = Integer.parseInt(campos[5]);
            double precoPorNoite = Double.parseDouble(campos[6]);
            String emailProprietario = campos[7];
            Proprietario proprietario = buscarProprietarioPorEmail(emailProprietario);

            if (tipo.equals("Casa")) {
                boolean piscina = Boolean.parseBoolean(campos[8]);
                double precoPorPessoa = Double.parseDouble(campos[9]);
                propriedades.add(new Casa(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, piscina, precoPorPessoa));
            } else if (tipo.equals("Apartamento")) {
                int andar = Integer.parseInt(campos[8]);
                double taxa = Double.parseDouble(campos[9]);
                propriedades.add(new Apartamento(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, andar, taxa));
            } else if (tipo.equals("Sitio")) {
                double area = Double.parseDouble(campos[8]);
                propriedades.add(new Sitio(disponivel, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, area));
            }
        }
        return propriedades;
    }

    @Override
    public Propriedade buscarPorTitulo(String titulo) {
        for (Propriedade p : listar()) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) return p;
        }
        return null;
    }

    // Busca o proprietário pelo email (ajuste conforme sua estrutura)
    private Proprietario buscarProprietarioPorEmail(String email) {
        UsuarioArquivoDAO usuarioDAO = new UsuarioArquivoDAO();
        Usuario u = usuarioDAO.buscarPorEmail(email);
        if (u instanceof Proprietario) return (Proprietario) u;
        return null;
    }
}
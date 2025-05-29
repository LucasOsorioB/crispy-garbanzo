import java.util.*;

public class UsuarioArquivoDAO implements UsuarioDAO {
    private static final String ARQUIVO = "usuarios.csv";

    @Override
    public void salvar(Usuario usuario) {
        List<Usuario> usuarios = listar();
        usuarios.add(usuario);
        salvarTodas(usuarios);
    }

    public void salvarTodas(List<Usuario> usuarios) {
        List<String> linhas = new ArrayList<>();
        for (Usuario u : usuarios) {
            String tipo = u instanceof Proprietario ? "Proprietario" : "Cliente";
            linhas.add(tipo + ";" + u.getNome() + ";" + u.getEmail() + ";" + u.getSenha());
        }
        ArquivoUtil.salvarLinhas(linhas, ARQUIVO);
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        List<String> linhas = ArquivoUtil.carregarLinhas(ARQUIVO);
        for (String linha : linhas) {
            String[] campos = linha.split(";");
            if (campos.length < 4) continue; // Pula linhas inválidas
            String tipo = campos[0];
            String nome = campos[1];
            String email = campos[2];
            String senha = campos[3];
            if (tipo.equals("Proprietario")) {
                usuarios.add(new Proprietario(nome, email, senha));
            } else {
                usuarios.add(new Cliente(nome, email, senha));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        for (Usuario u : listar()) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }
}
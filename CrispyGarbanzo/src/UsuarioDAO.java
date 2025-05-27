import java.util.List;

public interface UsuarioDAO {
    void salvar(Usuario usuario);
    List<Usuario> listar();
    Usuario buscarPorEmail(String email);
}
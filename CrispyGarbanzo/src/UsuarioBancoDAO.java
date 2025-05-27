import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioBancoDAO implements UsuarioDAO {
    @Override
    public void salvar(Usuario usuario) {
        try (Connection conn = ConexaoBanco.conectar()) {
            String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario instanceof Proprietario ? "proprietario" : "cliente");
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = ConexaoBanco.conectar()) {
            String sql = "SELECT * FROM usuarios";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("proprietario".equals(tipo)) {
                    usuarios.add(new Proprietario(rs.getString("nome"), rs.getString("email"), rs.getString("senha")));
                } else {
                    usuarios.add(new Cliente(rs.getString("nome"), rs.getString("email"), rs.getString("senha")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        try (Connection conn = ConexaoBanco.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("proprietario".equals(tipo)) {
                    return new Proprietario(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
                } else {
                    return new Cliente(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }
}
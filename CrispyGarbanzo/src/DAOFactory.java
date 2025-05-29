public class DAOFactory {
    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioArquivoDAO();
    }
    public static PropriedadeDAO getPropriedadeDAO() {
        return new PropriedadeArquivoDAO();
    }
    public static ReservaDAO getReservaDAO() {
        return new ReservaArquivoDAO();
    }
}
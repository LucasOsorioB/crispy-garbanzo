public class DAOFactory {
    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioBancoDAO();
    }
    public static PropriedadeDAO getPropriedadeDAO() {
        return new PropriedadeBancoDAO();
    }
    public static ReservaDAO getReservaDAO() {
        // Implemente ReservaBancoDAO semelhante ao UsuarioBancoDAO
        return new ReservaBancoDAO();
    }
}

// Remove PropriedadeBancoDAO from this file. It will be moved to its own file.
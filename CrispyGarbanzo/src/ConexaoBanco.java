import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:6543/postgres?user=postgres.moojvwojczvwtajvxteg&password=BANCODEDADOS1";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
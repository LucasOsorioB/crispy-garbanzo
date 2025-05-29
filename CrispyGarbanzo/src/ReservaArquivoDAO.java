import java.util.*;
import java.time.LocalDate;

public class ReservaArquivoDAO implements ReservaDAO {
    private static final String ARQUIVO = "reservas.csv";

    @Override
    public void salvar(Reserva reserva) {
        List<Reserva> reservas = listar();
        reservas.add(reserva);
        salvarTodas(reservas);
    }

    public void salvarTodas(List<Reserva> reservas) {
        List<String> linhas = new ArrayList<>();
        for (Reserva r : reservas) {
            linhas.add(
                r.getPropriedade().getTitulo() + ";" +
                r.getCliente().getEmail() + ";" +
                r.getCheckIn() + ";" +
                r.getCheckOut() + ";" +
                r.getCustoTotal()
            );
        }
        ArquivoUtil.salvarLinhas(linhas, ARQUIVO);
    }

    @Override
    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();
        List<String> linhas = ArquivoUtil.carregarLinhas(ARQUIVO);
        PropriedadeArquivoDAO propDAO = new PropriedadeArquivoDAO();
        UsuarioArquivoDAO usuarioDAO = new UsuarioArquivoDAO();

        for (String linha : linhas) {
            String[] campos = linha.split(";");
            if (campos.length < 5) continue; // ignora linhas inválidas
            String tituloPropriedade = campos[0];
            String emailCliente = campos[1];
            LocalDate checkIn = LocalDate.parse(campos[2]);
            LocalDate checkOut = LocalDate.parse(campos[3]);
            double custoTotal = Double.parseDouble(campos[4]);

            Propriedade propriedade = propDAO.buscarPorTitulo(tituloPropriedade);
            Usuario usuario = usuarioDAO.buscarPorEmail(emailCliente);
            if (propriedade != null && usuario instanceof Cliente) {
                Reserva reserva = new Reserva(propriedade, (Cliente) usuario, checkIn, checkOut);
                reserva.setCustoTotal(custoTotal); // garante o valor salvo
                reservas.add(reserva);
            }
        }
        return reservas;
    }
}
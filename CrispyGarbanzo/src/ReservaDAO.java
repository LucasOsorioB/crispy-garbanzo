import java.util.List;

public interface ReservaDAO {
    void salvar(Reserva reserva);
    List<Reserva> listar();
}
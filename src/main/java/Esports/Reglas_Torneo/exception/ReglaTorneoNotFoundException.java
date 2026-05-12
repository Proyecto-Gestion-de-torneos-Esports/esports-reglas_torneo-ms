package Esports.Reglas_Torneo.exception;

public class ReglaTorneoNotFoundException extends RuntimeException {
    public ReglaTorneoNotFoundException(String mensaje){
        super(mensaje);
    }
}

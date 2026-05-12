package Esports.Reglas_Torneo.repository;

import Esports.Reglas_Torneo.model.ReglaTorneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReglaTorneoRepository extends JpaRepository <ReglaTorneo,Long>{
    List<ReglaTorneo> findByActivoTrue(); // listar solo reglas vigentes

}

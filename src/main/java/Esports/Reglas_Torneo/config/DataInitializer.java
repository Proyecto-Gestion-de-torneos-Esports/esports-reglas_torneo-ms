package Esports.Reglas_Torneo.config;

import Esports.Reglas_Torneo.model.ReglaTorneo;
import Esports.Reglas_Torneo.repository.ReglaTorneoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ReglaTorneoRepository reglaTorneoRepository;

    @Override
    public void run(String... args) {
        if (reglaTorneoRepository.count() > 0 ){

            ReglaTorneo regla1 = new ReglaTorneo(null, 5, "Torneo estricto 5v5. Se requiere cámara encendida y anti-cheat activo.", 100L, 1L, true);
            ReglaTorneo regla2 = new ReglaTorneo(null, 1, "Torneo 1v1 modalidad al mejor de 3. Prohibido usar macros.", 200L, 2L, true);

            reglaTorneoRepository.saveAll(List.of(regla1,regla2));
        } else {
            log.info("La base de dato ya tiene reglas de torneo");

        }

    }


}

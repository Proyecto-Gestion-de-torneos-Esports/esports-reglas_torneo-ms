package Esports.Reglas_Torneo;


import Esports.Reglas_Torneo.model.ReglaTorneo;
import Esports.Reglas_Torneo.repository.ReglaTorneoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {
    private final ReglaTorneoRepository repository;

    public DataLoader(ReglaTorneoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));
            List<ReglaTorneo> reglas = new ArrayList<>();


            Integer[] formatosMejorDe = {1, 3, 5, 7};
            Integer[] cuposEquipos = {8, 16, 32, 64, 128};
            Integer[] tamañoEquipo = {1, 2, 3, 4, 5, 6};

            for (int i = 0; i < 15; i++) {
                ReglaTorneo regla = new ReglaTorneo();

                regla.setTorneoId(faker.number().numberBetween(1L, 20L));
                regla.setJuegoId(faker.number().numberBetween(1L, 10L));

                regla.setJugadoresRequeridos(tamañoEquipo[faker.random().nextInt(tamañoEquipo.length)]);

                long diasParaInscripcion = faker.number().numberBetween(5, 60);
                regla.setFechaLimiteInscripcion(LocalDate.now().plusDays(diasParaInscripcion));

                regla.setCupoMaximoEquipos(cuposEquipos[faker.random().nextInt(cuposEquipos.length)]);
                regla.setFormatoMejorDe(formatosMejorDe[faker.random().nextInt(formatosMejorDe.length)]);
                regla.setDescripcion("Torneo de " + faker.esports().game());

                regla.setActivo(true);

                reglas.add(regla);
            }

            repository.saveAll(reglas);
            System.out.println("15 reglas generadas exitosamente");
        }
    }
}

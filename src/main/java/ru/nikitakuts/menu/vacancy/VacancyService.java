package ru.nikitakuts.menu.vacancy;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@EnableMapRepositories
public class VacancyService {
    private final CrudRepository<Vacancy, Long> repository;

    public VacancyService(CrudRepository<Vacancy, Long> repository) {
        this.repository = repository;
        this.repository.saveAll(defaultVacancies());
    }

    private static List<Vacancy> defaultVacancies() {
        return List.of(
                new Vacancy(0L, 70000L, 1, "Moscow", new Date()),
                new Vacancy(1L, 80000L, 1, "Saint-Petersburg", new Date()),
                new Vacancy(2L, 90000L, 1, "Novosibirsk", new Date())
        );
    }

    public List<Vacancy> findAll() {
        List<Vacancy> list = new ArrayList<>();
        Iterable<Vacancy> vacancies = repository.findAll();
        vacancies.forEach(list::add);
        return list;
    }

    public Optional<Vacancy> find(Long id) {
        return repository.findById(id);
    }

    public Vacancy create(Vacancy vacancy) {
        // To ensure the vacancy ID remains unique,
        // use the current timestamp.
        Vacancy copy = new Vacancy(
                new Date().getTime(),
                vacancy.getSalary(),
                vacancy.getWorkExperience(),
                vacancy.getCity(),
                vacancy.getDate()
        );
        return repository.save(copy);
    }

    public Optional<Vacancy> update( Long id, Vacancy newVacancy) {
        // Only update an vacancy if it can be found first.
        return repository.findById(id)
                .map(oldVacancy -> {
                    Vacancy updated = oldVacancy.updateWith(newVacancy);
                    return repository.save(updated);
                });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
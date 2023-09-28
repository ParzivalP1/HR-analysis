package ru.nikitakuts.menu.vacancy;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InMemoryVacancyRepository extends CrudRepository<Vacancy, Long> {}

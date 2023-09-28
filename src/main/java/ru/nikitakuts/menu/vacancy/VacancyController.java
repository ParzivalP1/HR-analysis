package ru.nikitakuts.menu.vacancy;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import jakarta.validation.Valid;


@RestController
@RequestMapping("api/menu/vacancies")
public class VacancyController {
    private final VacancyService service;

    public VacancyController(VacancyService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<Vacancy>> findAll() {
        List<Vacancy> vacancies = service.findAll();
        return ResponseEntity.ok().body(vacancies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> find(@PathVariable("id") Long id) {
        Optional<Vacancy> vacancy = service.find(id);
        return ResponseEntity.of(vacancy);
    }


    @PostMapping
    public ResponseEntity<Vacancy> create(@Valid @RequestBody Vacancy vacancy) {
        Vacancy created = service.create(vacancy);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody Vacancy updatedVacancy) {

        Optional<Vacancy> updated = service.update(id, updatedVacancy);

        return updated
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> {
                    Vacancy created = service.create(updatedVacancy);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(created.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(created);
                });
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return ResponseEntity.badRequest().body(map);
    }
}

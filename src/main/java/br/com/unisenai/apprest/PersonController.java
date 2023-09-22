package br.com.unisenai.apprest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return ResponseEntity.ok(repository.insert(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable int id, @RequestBody Person person) {

        if (!person.getId().equals(id) || person.getId() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(repository.update(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable int id) {
        boolean result = repository.delete(id);
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}

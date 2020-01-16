package com.ar.centurylink.rest;

import com.ar.centurylink.adapter.PersonaAdapter;
import com.ar.centurylink.exception.entity.EntityNotFoundException;
import com.ar.centurylink.service.PersonaService;
import com.ar.centurylink.view.PersonaView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/personas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class PersonaRest {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<PersonaView> findAll() {
        return PersonaAdapter.adaptToView(personaService.findAll());
    }

    @GetMapping("/{id}")
    public PersonaView findById(@Valid @PathVariable("id") Long id) throws EntityNotFoundException {
        return PersonaAdapter.adaptToView(personaService.findById(id));
    }

    @PostMapping
    public void add(@Valid @RequestBody PersonaView personaView) {
        personaService.save(PersonaAdapter.adaptToDomain(personaView));
    }

    @PutMapping
    public void edit(@Valid @RequestBody PersonaView personaView) {
        personaService.update(PersonaAdapter.adaptToDomain(personaView));
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable("id") Long id) throws EntityNotFoundException {
        personaService.deleteById(id);
    }
}

package com.ar.centurylink.service.impl;

import com.ar.centurylink.domain.Persona;
import com.ar.centurylink.exception.entity.EntityNotFoundException;
import com.ar.centurylink.repository.PersonaRepository;
import com.ar.centurylink.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personaService")
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    public Persona findById(Long id) {
        return personaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Persona.class, "id", id.toString()));
    }

    @Override
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public void update(Persona persona) {
        if (!personaRepository.existsById(persona.getId())) {
            new EntityNotFoundException(Persona.class, "id", persona.getId().toString());
        }
        personaRepository.save(persona);
    }

    @Override
    public void deleteById(Long id) {
        if (!personaRepository.existsById(id)) {
            new EntityNotFoundException(Persona.class, "id", id.toString());
        }
        personaRepository.deleteById(id);
    }
}
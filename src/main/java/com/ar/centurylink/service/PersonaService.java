package com.ar.centurylink.service;

import com.ar.centurylink.domain.Persona;

import java.util.List;

public interface PersonaService {

    List<Persona> findAll();

    Persona findById(Long id);

    Persona save(Persona persona);

    void update(Persona persona);

    void deleteById(Long id);
}
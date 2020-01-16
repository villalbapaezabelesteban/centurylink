package com.ar.centurylink.adapter;

import com.ar.centurylink.domain.Persona;
import com.ar.centurylink.view.PersonaView;

import java.util.ArrayList;
import java.util.List;

public class PersonaAdapter {

    public static PersonaView adaptToView(Persona persona) {
        PersonaView personaView = new PersonaView();
        personaView.setId(persona.getId());
        personaView.setName(persona.getName());
        personaView.setLastName(persona.getLastName());
        personaView.setAge(persona.getAge());
        personaView.setGender(persona.getGender());
        return personaView;
    }

    public static Persona adaptToDomain(PersonaView personaView) {
        Persona persona = new Persona();
        persona.setId(personaView.getId());
        persona.setName(personaView.getName());
        persona.setLastName(personaView.getLastName());
        persona.setAge(personaView.getAge());
        persona.setGender(personaView.getGender());
        return persona;
    }

    public static List<PersonaView> adaptToView(List<Persona> personas) {
        List<PersonaView> views = new ArrayList<>();
        personas.stream().forEach((p) ->
        {
            views.add(PersonaAdapter.adaptToView(p));
        });
        return views;
    }
}

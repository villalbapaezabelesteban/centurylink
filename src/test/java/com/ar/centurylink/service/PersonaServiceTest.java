package com.ar.centurylink.service;

import com.ar.centurylink.domain.Persona;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PersonaServiceTest {

    @Autowired
    private PersonaService personaService;

    private Persona persona;

    @Before
    public void before() {
        this.persona = this.givenAPerson();
    }

    @After
    public void after() {
        this.deletePersonById();
        this.persona = null;
    }

    @Test
    public void add() {
        this.savePerson();
        this.whenPersonIsSavedCorrectly();
    }

    @Test
    public void edit() {
        this.savePerson();
        this.editPersonNameToEsteban();
        this.whenThePersonNameIsEditedCorrectly();
    }

    @Test
    public void delete() {
        this.savePerson();
        this.deletePersonById();
        this.whenThePersonIsSuccessfullyRemoved();
    }

    @Test
    public void list() {
        this.savePerson();
        this.whenPersonListIsOne();
    }

    private Persona givenAPerson() {
        Persona persona = new Persona();
        persona.setId(-1L);
        persona.setName("Abel");
        persona.setLastName("Villalba");
        persona.setAge(30);
        persona.setGender("Masculino");
        return persona;
    }

    private void savePerson() {
        this.persona = this.personaService.save(this.persona);
    }

    private void deletePersonById() {
        this.personaService.deleteById(this.persona.getId());
    }

    private void whenPersonIsSavedCorrectly() {
        assertNotNull(this.personaService.findById(this.persona.getId()));
    }

    private void editPersonNameToEsteban() {
        this.persona.setName("Esteban");
        this.savePerson();
    }

    private void whenThePersonNameIsEditedCorrectly() {
        assertEquals("Esteban", this.persona.getName());
    }

    private void whenThePersonIsSuccessfullyRemoved() {
        assertEquals(0, this.personaService.findAll().size());
    }

    private void whenPersonListIsOne() {
        assertEquals(1, this.personaService.findAll().size());
    }
}

package com.ar.centurylink.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PersonaView {

    private Long id;

    @NotEmpty(message = "El valor del campo 'Nombre' es requerido. Por favor, verificar request body (attribute name and value)")
    private String name;

    @NotEmpty(message = "El valor del campo 'Apellido' es requerido. Por favor, verificar request body (attribute name and value)")
    private String lastName;

    @NotNull(message = "El valor del campo 'Edad' es requerido. Por favor, verificar request body (attribute name and value)")
    @Min(value = 18, message = "El campo 'Edad' no puede ser menor a 18")
    @Max(value = 150, message = "El campo 'Edad' no puede ser mayor a 150")
    private Integer age;

    @NotEmpty(message = "El valor del campo 'Género' es requerido. Por favor, verificar request body (attribute name and value)")
    private String gender;

    public PersonaView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

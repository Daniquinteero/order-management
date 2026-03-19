package com.daniel.ordermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la creación y actualización de clientes.
 */
public class CustomerRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
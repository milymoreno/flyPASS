package com.flypass.financiera.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}

package com.example.Q6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor

@NoArgsConstructor
@Table("produtos")
public class Produto {

    @Id
    private Long id;
    private String nome;
    private Double preco;
}

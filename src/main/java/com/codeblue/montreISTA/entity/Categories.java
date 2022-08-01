package com.codeblue.montreISTA.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Categories extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoriesId;

    @NotBlank
    private String name;
}

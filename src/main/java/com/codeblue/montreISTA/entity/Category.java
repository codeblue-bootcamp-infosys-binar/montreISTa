package com.codeblue.montreISTA.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "categories")
@Builder
public class Category extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriesId;

    @NotBlank
    private String name;
}

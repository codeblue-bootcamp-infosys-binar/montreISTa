package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "photos")

public class Photo extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @NotBlank
    private String photoName;

    @NotBlank
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotBlank
    private Product product;


}

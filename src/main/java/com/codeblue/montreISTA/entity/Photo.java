package com.codeblue.montreISTA.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photos")

public class Photo extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @NotBlank
    private String photoName;

    @NotBlank
    @Lob
    @Column(columnDefinition = "TEXT")
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotBlank
    private Product product;
}


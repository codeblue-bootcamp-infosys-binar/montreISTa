package com.codeblue.montreISTA.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productId")
public class Product extends AuditEntity{

    //product id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    //seller id
    @ManyToOne
    @JoinColumn(name="seller_id")
    @NotEmpty
    private Seller seller;

    //product name
    @Column(name="product_name")
    @NotEmpty
    private String productName;

    //description
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", columnDefinition = "TEXT")
    @NotEmpty
    private String description;

    //price
    @Column(name = "price")
    @NotEmpty
    private Integer price;

    //list of photos
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product",
            fetch = FetchType.LAZY)
    private List<Photo> photos;
}
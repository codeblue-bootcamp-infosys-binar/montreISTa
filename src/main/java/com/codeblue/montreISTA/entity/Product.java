package com.codeblue.montreISTA.entity;

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
public class Product extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Seller seller;

    @Column(name="product_name")
    private String productName;


    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private Integer price;

//    @OneToMany(cascade = CascadeType.ALL,
//            mappedBy = "product",
//            fetch = FetchType.LAZY)
//    private List<Photo> ListPhoto;
}


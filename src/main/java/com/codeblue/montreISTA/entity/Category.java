package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.CategoryResponseDTO;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriesId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "category",
            fetch = FetchType.LAZY)
    private List<ProductCategory> products;


    public CategoryResponseDTO convertToResponse(){
        return CategoryResponseDTO.builder()
                .categoryId(this.categoriesId)
                .name(this.name)
                .build();
    }
}

package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryRequestDTO {

    private Long categoryId;
    private String name;

    public Category convertToEntity(){
        return Category.builder()
                .categoriesId(this.categoryId)
                .name(this.name)
                .build();
    }
}

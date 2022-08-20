package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryRequestDTO {

    private String name;
    public Category convertToEntity(){
        return Category.builder()
                .name(this.name)
                .build();
    }
}

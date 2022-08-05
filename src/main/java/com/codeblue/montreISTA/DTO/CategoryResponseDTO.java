package com.codeblue.montreISTA.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryResponseDTO {

    private Long categoryId;

    private String name;
}

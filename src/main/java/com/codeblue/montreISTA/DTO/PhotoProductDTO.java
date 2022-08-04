package com.codeblue.montreISTA.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoProductDTO {
    private Long photo_id;
    private String photo_name;
    private String photo_url;
}

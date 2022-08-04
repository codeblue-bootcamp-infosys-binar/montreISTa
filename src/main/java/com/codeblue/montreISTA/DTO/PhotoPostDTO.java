package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Photo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoPostDTO {
    private Long photo_id;
    private String photo_name;
    private String photo_url;
    private Long product_id;

    @Override
    public String toString() {
        return "PhotoPostDTO{" +
                "photo_id=" + photo_id +
                ", photo_name='" + photo_name + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", product_id=" + product_id +
                '}';
    }
}


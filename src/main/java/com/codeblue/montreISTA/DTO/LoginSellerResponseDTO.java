package com.codeblue.montreISTA.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginSellerResponseDTO {
    private SellerResponseDTO my_store;
    private List<ProductResponseDTO> my_product;
}

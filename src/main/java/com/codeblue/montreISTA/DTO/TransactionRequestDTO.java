package com.codeblue.montreISTA.DTO;

import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Transaction;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequestDTO {
    private Long order_id;
    private String name_shipping;
    private String addreess_shipping;
    private String zip_code;
    private String phone_shipping;
    public Transaction convertToEntity(Order orderDTO){
        return Transaction.builder()
          .order(orderDTO)
                .addressShipping(this.addreess_shipping)
                .nameShipping(this.name_shipping)
                .zipCode(this.zip_code)
                .phoneShipping(this.phone_shipping)
                .build();
    }
}

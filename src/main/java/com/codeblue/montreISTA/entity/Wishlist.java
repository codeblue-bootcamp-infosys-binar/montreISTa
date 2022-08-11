package com.codeblue.montreISTA.entity;


import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @NotNull
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;
    @NotNull
    private Integer quantity;

    public WishlistResponseDTO convertToResponse(ProductResponseDTO productDTO){
        return WishlistResponseDTO.builder()
                .wishlist_id(this.getWishlistId())
                .product(productDTO)
                .buyer_id(this.getBuyer().getBuyerId())
                .quantity(this.getQuantity())
                .created_at(this.getCreatedAt())
                .modified_at(this.getModifiedAt())
                .build();
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", buyer=" + buyer +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
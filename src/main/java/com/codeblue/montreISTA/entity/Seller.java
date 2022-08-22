package com.codeblue.montreISTA.entity;

import com.codeblue.montreISTA.DTO.LoginSellerResponseDTO;
import com.codeblue.montreISTA.DTO.ProductResponseDTO;
import com.codeblue.montreISTA.DTO.SellerResponseDTO;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

   @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String storeName;

    @NotNull
    private String storePhoto;

    @NotNull
    private String storeAddress;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "seller",
            fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "seller",
            fetch = FetchType.LAZY)
    private List<HistoryTransaction> transactions;

    public SellerResponseDTO convertToResponse(){
        return SellerResponseDTO.builder()
                .sellerId(this.getSellerId())
                .user_id(this.getUser().getUserId())
                .name(this.getUser().getName())
                .username(this.getUser().getUsername())
                .email(this.getUser().getEmail())
                .store_address(this.getStoreAddress())
                .store_name(this.getStoreName())
                .store_photo(this.getStorePhoto())
                .createdAt(this.getCreatedAt())
                .modifiedAt(this.getModifiedAt())
                .build();
    }
    public LoginSellerResponseDTO convertToLoginSeller(SellerResponseDTO my_store,List<ProductResponseDTO> my_product){
        return LoginSellerResponseDTO.builder()
                .my_store(my_store)
                .my_product(my_product)
                .build();
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", user=" + user +
                ", storeName='" + storeName + '\'' +
                ", storePhoto='" + storePhoto + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                '}';
    }
}
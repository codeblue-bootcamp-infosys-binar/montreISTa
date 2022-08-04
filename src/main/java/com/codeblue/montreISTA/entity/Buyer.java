package com.codeblue.montreISTA.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buyers")
public class Buyer extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @NotBlank
    private User user;

    @Override
    public String toString() {
        return "Buyer{" +
                "buyerId=" + buyerId +
                ", user=" + user +
                '}';
    }
}

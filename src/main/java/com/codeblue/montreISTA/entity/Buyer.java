package com.codeblue.montreISTA.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "buyers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Buyer extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @NotBlank
    private User user;
}

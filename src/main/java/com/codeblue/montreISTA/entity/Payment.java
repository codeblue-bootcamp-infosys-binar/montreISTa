package com.codeblue.montreISTA.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "payments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotBlank(message = "name must not be blank")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "name must not be blank")
    private String paymentCode;
}

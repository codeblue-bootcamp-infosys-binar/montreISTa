package com.codeblue.montreISTA.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Builder
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;


    @NotEmpty(message = "name must not be blank")
    @Column(unique = true)
    private String name;


    @NotEmpty(message = "name must not be blank")
    private String paymentCode;
}

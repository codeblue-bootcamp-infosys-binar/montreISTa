package com.codeblue.montreISTA.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

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

//    @NotBlank(message = "name must not be blank")
    @NotEmpty
    @Column(unique = true)
    private String name;

//    @NotBlank(message = "name must not be blank")
    @NotEmpty
    private String paymentCode;
}

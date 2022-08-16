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

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private String paymentCode;
}

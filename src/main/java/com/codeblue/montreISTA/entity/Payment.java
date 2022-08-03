package com.codeblue.montreISTA.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "payments")
@Builder
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

package com.codeblue.montreISTA.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "photoes")

public class Photoes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long photoesId;

}

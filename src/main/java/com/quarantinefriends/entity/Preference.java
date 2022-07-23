package com.quarantinefriends.entity;

import javax.persistence.*;

@Entity
@Table
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;
}

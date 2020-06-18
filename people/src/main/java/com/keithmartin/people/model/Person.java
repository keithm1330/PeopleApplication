package com.keithmartin.people.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "ForeignKeyEntity")
@Table(name = "person_table")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @NonNull
    private String name;

    private String surname;

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="Address_ID")
    private Set<Address> addresses;
}

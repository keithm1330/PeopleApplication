package com.keithmartin.people.repo;

import com.keithmartin.people.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
}

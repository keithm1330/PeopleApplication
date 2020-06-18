package com.keithmartin.people;

import com.keithmartin.people.model.Address;
import com.keithmartin.people.model.Person;
import com.keithmartin.people.repo.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Stream;

@Component
class LoadDB implements CommandLineRunner {

    private final PersonRepository repository;

    public LoadDB(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Keith", "Dave", "Mike"
                ).forEach(name ->
                repository.save(new Person(name))
        );

        Person p1 = repository.findByName("Keith");
        Address e = Address.builder().street("Swords")
                .city("Dublin")
                .state("Leinster")
                .postalCode("555")
                .build();
        p1.setAddresses(Collections.singleton(e));
        repository.save(p1);

    }


}

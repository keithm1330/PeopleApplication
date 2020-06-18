package com.keithmartin.people.web;

import com.keithmartin.people.model.Address;
import com.keithmartin.people.repo.AddressRepository;
import com.keithmartin.people.model.Person;
import com.keithmartin.people.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
class PersonController {

    private final Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonRepository personRepository;
    private AddressRepository addressRepository;

    public PersonController(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/people")
    Collection<Person> groups() {
        return personRepository.findAll();
    }
    @GetMapping("/count")
    Integer peopleCount() {
        return personRepository.findAll().size();
    }

    @GetMapping("/person/{id}")
    ResponseEntity<?> getPerson(@PathVariable Long id) {
        Optional<Person> group = personRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/person")
    ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.info("Request to create person: {}", person);
        Person result = personRepository.save(person);
        return ResponseEntity.created(new URI("/api/person/" + result.getId()))
                .body(result);
    }

    @PutMapping("/person")
    ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.info("Request to edit person: {}", person);
        Optional<Person> group = personRepository.findById(person.getId());
        if(group.isPresent()) {
            Person existingCustomer = group.get();
            existingCustomer.setName(person.getName());
            existingCustomer.setSurname(person.getSurname());
            personRepository.save(existingCustomer);
            return ResponseEntity.created(new URI("/api/person/" + existingCustomer.getId()))
                    .body(existingCustomer);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/person/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        log.info("Request to delete person: {}", id);
        personRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }







    @GetMapping("/person/address/{id}")
    ResponseEntity<?> getAddress(@PathVariable Long id) {
        Optional<Address> group = addressRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PostMapping("/person/{personId}/address")
    ResponseEntity<Person> addAddressToPerson(@Valid @RequestBody Address address,@PathVariable Long personId) throws URISyntaxException {
        log.info("Request to create address: {}", address);
        Optional<Person> parentPerson = personRepository.findById(personId);

        if(parentPerson.isPresent()) {
            Person existingPerson = parentPerson.get();
            Address e = Address.builder().street(
                    address.getStreet())
                    .city(address.getCity())
                    .postalCode(address.getPostalCode())
                    .state(address.getState())
                    .build();
            Set<Address> addresses =existingPerson.getAddresses();
            addresses.add(e);
            existingPerson.setAddresses(addresses);
            personRepository.save(existingPerson);

            return ResponseEntity.created(new URI("/api/person/" + existingPerson.getId()))
                    .body(existingPerson);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/person/{personId}/address")
    ResponseEntity<Address> editAddress(@Valid @RequestBody Address address,@PathVariable Long personId) throws URISyntaxException {
        log.info("Request to edit address: {}", address);
        Optional<Address> group = addressRepository.findById(address.getId());
        if(group.isPresent()) {
            Address existingAddress = group.get();
            existingAddress.setCity(address.getCity());
            existingAddress.setState(address.getState());
            existingAddress.setStreet(address.getStreet());
            existingAddress.setPostalCode(address.getPostalCode());
            addressRepository.save(existingAddress);
            return ResponseEntity.created(new URI("/api/person/" + personId))
                    .body(existingAddress);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        log.info("Request to delete address: {}", id);
        addressRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

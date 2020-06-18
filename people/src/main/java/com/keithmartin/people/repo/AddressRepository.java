package com.keithmartin.people.repo;

import com.keithmartin.people.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

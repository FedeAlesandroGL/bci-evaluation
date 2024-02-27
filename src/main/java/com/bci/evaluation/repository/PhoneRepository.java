package com.bci.evaluation.repository;

import com.bci.evaluation.model.Phone;
import com.bci.evaluation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}

package com.bci.evaluation.service;

import com.bci.evaluation.exception.RepositoryFailedException;
import com.bci.evaluation.model.Phone;
import com.bci.evaluation.repository.PhoneRepository;
import com.bci.evaluation.service.impl.PhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

  PhoneRepository phoneRepository;
  PhoneService phoneService;

  @BeforeEach
  void setUp() {
    this.phoneRepository = mock(PhoneRepository.class);
    this.phoneService = new PhoneServiceImpl(this.phoneRepository);
  }

  @Test
  void whenSaveAllThenOk() {
    this.phoneService.saveAll(new ArrayList<>());

    verify(this.phoneRepository).saveAll(new ArrayList<>());
  }

  @Test
  void whenSaveAllThenThrowsRepositoryFailedException() {
    List<Phone> phones = new ArrayList<>();
    doThrow(new RuntimeException()).when(this.phoneRepository).saveAll(phones);

    assertThrows(RepositoryFailedException.class, () -> this.phoneService.saveAll(phones));
  }
}

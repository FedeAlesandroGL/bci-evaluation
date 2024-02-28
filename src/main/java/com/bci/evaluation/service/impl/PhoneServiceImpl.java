package com.bci.evaluation.service.impl;

import com.bci.evaluation.exception.RepositoryFailedException;
import com.bci.evaluation.model.Phone;
import com.bci.evaluation.repository.PhoneRepository;
import com.bci.evaluation.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

  private final PhoneRepository phoneRepository;
  private static final String REPOSITORY_FAILED = "Repository failed, please try again later";

  @Override
  public void saveAll(List<Phone> phoneList) {
    try {
      phoneRepository.saveAll(phoneList);
    } catch (Exception e) {
      throw new RepositoryFailedException(REPOSITORY_FAILED);
    }
  }
}

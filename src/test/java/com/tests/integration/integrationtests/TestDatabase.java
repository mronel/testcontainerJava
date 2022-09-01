package com.tests.integration.integrationtests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tests.integration.app1.domain.EmployeeEntity;
import com.tests.integration.app1.repository.EmployeeRepository;
import com.tests.integration.domain.MessageEntity;
import com.tests.integration.repository.MessageRepository;

@DirtiesContext
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest()
public class TestDatabase {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  void insertOnTableWithJPA() {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setMessage("test");
    messageRepository.save(messageEntity);

    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setEmployeeName("employee");
    employeeRepository.save(employeeEntity);

    var findMessage = messageRepository.findByMessage("test");
    var findEmployee = employeeRepository.findById(1);

    Assert.assertEquals("test", findMessage.get(0).getMessage());
    Assert.assertTrue("Employee save with success!", findEmployee.isPresent());
  }

}

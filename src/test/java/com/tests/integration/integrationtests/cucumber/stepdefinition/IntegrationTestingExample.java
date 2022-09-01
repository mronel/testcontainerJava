package com.tests.integration.integrationtests.cucumber.stepdefinition;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;

import com.tests.integration.app1.domain.EmployeeEntity;
import com.tests.integration.app1.repository.EmployeeRepository;
import com.tests.integration.domain.MessageEntity;
import com.tests.integration.repository.MessageRepository;
import com.zaxxer.hikari.HikariDataSource;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

@ActiveProfiles("test")
public class IntegrationTestingExample {

  @Autowired
  private HikariDataSource dataSource;

  @Qualifier("db2DataSource")
  @Autowired
  private HikariDataSource dataSource2;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private EmployeeRepository employeeRepository;


  @Dado("que tenho a base de dados instanciada")
  public void que_tenho_a_base_de_dados_instanciada() {
    Assert.assertTrue(dataSource.isRunning());
    Assert.assertTrue(dataSource2.isRunning());
  }

  @Quando("realizo a inserção da mensagem {string} na tabela Mensagem")
  public void realizo_a_insercao_da_mensagem_na_tabela_mensagem(String mensagem) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setMessage(mensagem);
    messageRepository.save(messageEntity);
  }

  @Então("deverá retornar a mensagem {string} da base de dados")
  public void devera_retornar_a_mensagem_da_base_de_dados(String mensagem) {
    var msg = messageRepository.findByMessage(mensagem);
    Assert.assertEquals(mensagem, msg.get(0).getMessage());
  }

  @Quando("realizo a inserção do empregado {string} na tabela Employee")
  public void realizo_a_insercao_do_empregado_na_tabela_employee(String employee) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setEmployeeName(employee);
    employeeRepository.save(employeeEntity);
  }

  @Então("deverá retornar os dados do empregado {string} da base de dados")
  public void devera_retornar_os_dados_do_empregado_da_base_de_dados(String employee) {
    var findEmployee = employeeRepository.findByName(employee);
    Assert.assertEquals(employee,findEmployee.get(0).getEmployeeName());
  }

}

#language: pt

  Funcionalidade: Validando os testes de integração

    Contexto: Verificando as instancias das bases de dados
      Dado que tenho a base de dados instanciada

    Esquema do Cenario: Validando integração com base de dados DB1
      Quando realizo a inserção da mensagem "<mensagem>" na tabela Mensagem
      Então deverá retornar a mensagem "<mensagem>" da base de dados

      Exemplos:
      | mensagem      |
      | mensagem teste|


    Esquema do Cenario: Validando integração com base de dados DB2
      Quando realizo a inserção do empregado "<employee>" na tabela Employee
      Então deverá retornar os dados do empregado "<employee>" da base de dados

      Exemplos:
        | employee |
        | Employee |



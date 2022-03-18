# Desafio Banco Itaú | Feito com você
Desafio Banco Itaú | Feito com você

Demanda: Construir dois serviços que serão responsáveis por deteminadas tarefas na conta bancária dos cliente de um banco
* Cadastrar novos clientes na base de dados do banco

* Realizar todas as operações básicas de um banco (saldo, extrato, depósito, saque e transferência)

* Cada cliente tem um número gratuito de saques mensal, e após isso, é cobrada uma taxa por cada saque.

  O número gratuito de saques é informado no momento do cadastro da conta do cliente, de acordo com o tipo da conta
    - Conta tipo pessoa física, terá 5 saques gratuitos por mês, após isso deve ser cobrado R$10 por cada saque
    - Conta tipo pessoa jurídica terá 50 saques gratuitos por mês, após isso deve ser cobrado R$10 por cada saque
    - Conta governamental terá 250 saques gratuitos por mês, após isso deve ser cobrado R$20 por cada saque
    - O valor adicional do saque deve ser descrescido do valor do saldo do cliente (Saque de 100 reais, ao ser cobrado 5 reais de taxa, o cliente deve ter no mínimo 105 reais de saldo)

Funcionalidades do serviço 1 (responsavel pelo controle da conta bancária do usuario):
1 - Efetuar o cadastro de novos clientes
* um cliente pode ter mais de uma conta
* cadastrar dados basicos do cliente (nome, cpf, telefone e endereço)

    2 - Efetuar cadastro da conta
        * agencia
        * numero da conta
        * tipo da conta 
        * digito verificador
        * cliente (cpf)

    2 - Efetuar operações de consulta de saldo e extrato, realizar saque e transferência 
    3 - Receber a entrada de informações para cadastrar a quantidade de saques gratuitos por mês. (operação assincrona)
    4 - Apenas saques, verificar no cache a quantidade de saques gratis, caso tenha disponibilidade, não cobrar taxa e notificar (mensageria) o uso ao servico responsável

Funcionalidades do serviço 2 (responsavel pelo controle da quantidade de saques gratuitos):
1 - criar o limite de saque gratuito por mes para o cliente
2 - controlar o uso, tanto na base de dados como no cache.

Requisitos técnicos na construção dos serviços:
- Implementar arquitetura de microserviços
- Utilizar base de dados SQL para armazenar os dados
- Utilizar REDIS para implementar o cache
- Utilizar KAFKA para serviços de mensageria
- Utilizar java 11
- Utilizar boas práticas de programação (clean code)
- -------------------------------------------------------------
# Endpoints
## Verbos HTTP

POST
- Criar um recurso no servidor.
  Envia-se o recurso a ser criado.
  Resposta com código 201(CREATED).

GET
- Obter um recurso do servidor
  Pode se enviar parâmentros com critérios de busca.
  Resposta de sucesso com código 200(OK) ou 404 (NOT FOUND)
  quando o rerurso não foi encontrado no servidor.

PUT
- Atualizar um recurso no servidor.
  Envia-se recurso com as informaçoes atualizadas a através
  da URL, envia-se o identificador do recurso que irá receber a atualização.
  Resposta com código 200(OK).

DELETE
- Deleta um recurso no servidor.
  Envia-se o identificador do recurso que deseja-se
  deletar através da URL.
  Resposta com código 204(NO CONTENT).
---------------------------------------------------------------
# Códigos de Status

### Verbo HTTP  --- Sucesso --- Erro

POST   |  --->    201 (CREATED)  |---> 400 (BAD REQUEST)           
GET    |  --->   200 (OK)   |---> 404 (NOT FOUND)                
DELETE |  --->  204 (NO CONTENT)   | ---> 400 (BAD REQUEST)/ 404 (NOT FOUND)          
PUT    |  --->   200 (OK) | --->  400/ 404 (NOT FOUND)

## Tecnologias Usadas
Java. Spring Boot. Maven. Intellij IDEA.
H2. MySQL. Workbench. DBeaver.
Git. GitHub. Postman. Redis. Kafka





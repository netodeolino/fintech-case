# Desafio Backend

## Problema
O PicPay é uma empresa de pagamentos que visa revolucionar a forma com que lidamos com dinheiro em nosso dia-a-dia. Um dos passos necessários para completarmos essa missão é implementar a criação de diferentes tipos de contas para utilização do nosso aplicativo. 
É importante lembrar que o seu sistema será integrado aos nossos painéis internos e ao aplicativo.

Todo o processo começa com a criação de um Usuário. Um usuário pode ter mais de um tipo de conta vinculada a ele. 
De um **Usuário (User)**, queremos saber seu `Nome Completo`, `CPF`, `Número de Telefone`, `e-mail` e `Senha`. 
CPFs e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail.

Os tipos de conta que existem no PicPay são **Consumidor (Consumer)** e **Lojista (Seller)**. Todo Consumidor ou Lojista deve estar vinculado a um usuário existente. 
De um Lojista queremos saber a `Razão Social`, o `Nome Fantasia`, o `CNPJ` e seu `Username`, além do `id de Usuário` que será dono dessa conta. 
De um Consumidor, queremos saber apenas seu `Username`, além do `id de Usuário` que será dono dessa conta. 
Os usernames devem ser únicos dentro do sistema, mesmo entre contas de tipos diferentes.
Devido a algumas limitações do sistema, **cada Usuário pode ter apenas uma conta de cada tipo**.

Seu sistema deve ser capaz de listar todos os usuários, além de conseguir trazer informações detalhadas de um usuário específico. 
Durante a listagem, deve ser possível filtar os resultados por `Nome` ou `Username`.
Para fins didáticos, sua busca deve considerar apenas resultados que comecem com a string especificada na busca. Como exemplo,
`GET /users?q=joao` deve retornar apenas Usuários cujos Nomes ou Usernames comecem com a string **joao**. 
Não há a necessidade de lidar com acentos.

Outra funcionalidade do sistema deve ser a possiblidade de contas poderem realizar pagamentos entre si, chamados **Transações (Transactions)**. 
Para a realização de pagamentos, seu sistema deve consumir um serviço externo (mock criado por você) para autorização das movimentações entre as contas.
Todas as transações cujo valor seja **maior ou igual a R$ 100.00** devem ser **recusadas**. Transações de **valor inferior a R$ 100.00** devem ser autorizadas.
Transações recusadas **devem** retornar código **HTTP 401**, não autorizadas. **A lógica de autorização ou recusa de transações deve estar contida no serviço externo**.

Sua tarefa é desenvolver uma API capaz de cumprir com todos os requisitos especificados. 

A especificação da API está disponível no [nosso site](https://careers-picpay.s3.amazonaws.com/desafio/users-api/api-spec.json). O arquivo disponibilizado está no formato Swagger 2.0, e pode ser visualizado
utilizando o [Swagger](https://swagger.io). Siga as [aqui as instruções](https://hub.docker.com/r/swaggerapi/swagger-editor/) de como visualizar a especificação. 

## Instruções
Para ajudar no desenvolvimento e evitar perda de tempo com código *boilerplate*, decidimos prover uma estrutura básica para o desenvolvimento da sua solução utilizando a plataforma Java (Spring Boot + MySQL/MongoDB).
A estrutura **deve** ser utilizada no desenvolvimento da sua solução. É possível fazer [download do template inicial aqui](https://careers-picpay.s3.amazonaws.com/desafio/java/java-base.zip).

O primeiro passo para o início do desenvolvimento é escolher qual tecnologia de banco de dados será utilizada no seu projeto. Dependendo da escolha, existem algumas alterações que devem ser feitas no seu projeto base.

- Alterar o arquivo `docker-compose.yml` para injetar a variável de ambiente correspondente ao seu banco. Adicionalmente, você pode comentar 
a definição do container da opção que não escolher para evitar desperdício de recursos. Exemplo de `docker-compose.yml` utilizando MySQL:
    ```yaml
    version: '3.4'
    services:
      users-api:
        container_name: users-api-java
        build:
          context: .
          dockerfile: users.Dockerfile
        depends_on:
    #      - users-db-mongo
          - users-db-mysql
        ports:
          - "8000:8000"
        environment:
          - NOOP=
    #      Escolha adequadamente qual banco de dados deseja utilizar descomentando uma das variáveis abaixo.
    #      Durante o desenvolvimento utilizando sua IDE, mantenha ambas as variáveis de ambiente comentadas.
    #      Apenas especifique no arquivo application.yml qual banco deseja utilizar. Ele está configurado para apontar para
    #      o container utilizando a rede do host local.
    
    #      - DB_URL=users-db-mongo
          - DB_URL=users-db-mysql
    
    #  users-db-mongo:
    #    container_name: users-db-mongo
    #    image: mongo:3.6
    #    ports:
    #      - "27017:27017"
    
      users-db-mysql:
        container_name: users-db-mysql
        build:
          context: .
          dockerfile: mysql.Dockerfile
        ports:
          - "3306:3306"
        environment:
          - MYSQL_DATABASE=users
          - MYSQL_ROOT_PASSWORD=root
    ```
- Alterar o arquivo `pom.xml` para incluir as dependências que correspondem à sua escolha. Existem instruções no arquivo sobre quais dependências devem ser adicionadas.
- Caso escolha utilizar MySQL, existe um terceiro arquivo localizado no diretório `support/db-init.sql` contendo instruções sobre como carregar o esquema do seu banco de dados.

Para verificar se a sua solução está funcionando, utilize o comando `docker-compose up --build` a partir do diretório raiz do projeto. A sua API estará mapeada para a porta `8000`do seu host local. 

## Avaliação
A avaliação da sua solução será constituída de duas etapas principais: **Correção objetiva** e **Correção qualitativa**. 

Caso você não se sinta à vontade com a arquitetura proposta, você pode apresentar sua solução utilizando frameworks diferentes. 
Porém, nesse caso, uma entrevista de **Code Review** será necessária para a avaliação da sua solução.

A correção objetiva será realizada através da utilização de um script de correção automatizada. A correção qualitativa levará em conta os seguintes critérios:

* Modelagem de Dados
* Domínio da Linguagem
* Legibilidade do Código
* Estrutura do Código
* Organização do Código
* Design Patterns
* Manutenibilidade do Código
* Diferenciais: Testes Unitários e Cobertura de Testes

## Como submeter
Ao finalizar envie um email para **desafio@picpay.com** com seu Nome, Telefone para contato e a solução do desafio. 
Caso já esteja em processo de avaliação, é interessante também informar o(a) seu(sua) recrutador(a) sobre a conclusão desta etapa.

**Lembre-se de não enviar arquivos compilados e configurações de IDE ao submeter a sua solução.** 

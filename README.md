## Projeto Spring Boot Essentials !

Bem-vindo à API de Gerenciamento de Animes, desenvolvida como parte do projeto Spring Boot 2 Essentials. Esta API provê um overview completo pelos principais fundamentos do Framework Spring e oferece funcionalidades completas de CRUD para gerenciar informações sobre animes. Você pode encontrar mais sobre [clicando aqui](https://www.youtube.com/watch?v=bCzsSXE4Jzg&list=PL62G310vn6nFBIxp6ZwGnm8xMcGE3VA5H&index=1). Aqui estão as principais características e funcionalidades implementadas:

## Funcionalidades Implementadas

Como parte do projeto fizemos um CRUD completo utilizando todos os protocolos HTTP com inserção dos dados no H2 e MySQL, junto com todas as validações e exceções personalizadas dos campos e IDs, abrangemos também toda a parte de documentação e monitoramento de métricas da aplicação utilizando o Grafana, Actuator e Swagger.

- **Requisição HTTP:** A API suporta todos os protocolos HTTP necessários para operações CRUD, incluindo GET, POST, DELETE e PUT.
- **Spring Data JPA e MVC:** Utilizamos o Spring Data JPA para interagir com o banco de dados MySQL e o Spring MVC para criar endpoints RESTful.
- **Framework de Mapeamento MapStruct:** Implementamos o mapeamento de objetos com o MapStruct para facilitar a transformação de dados.
- **Validação de Campos e IDs:** Todas as entradas são validadas, e exceções personalizadas são tratadas de forma apropriada com handler global.
- **Testes Unitários e Testes de Integração:** Realizamos testes unitários abrangentes para verificar a funcionalidade de componentes individuais do aplicativo, garantindo que eles funcionem conforme o esperado.
- **Documentação e Monitoramento:** Implementamos documentação usando o Swagger e monitoramento de métricas com Grafana e Spring Boot Actuator.

## Requisitos
1. Java 11
2. Docker ou MySQL rodando nativamente

## Requisições HTTP

| Método | Endpoint | Função |
|--------|----------|--------|
| GET    | /        | Retorna uma lista paginada de todos os animes cadastrados |
| GET    | /{id}    | Retorna informações sobre o anime com o ID especificado |
| GET    | /find    | Retorna informações sobre o anime com o NOME especificado |
| POST | /    | Cadastra um novo anime |
| DELTE | /admin/{id} | Exclui o anime com o ID especificado (requer autenticação de administrador) |
| PUT    | /    | Cria ou Atualiza as informações do anime. |

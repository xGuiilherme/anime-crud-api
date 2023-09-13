## Projeto Spring Boot Essentials !

Esse repositório contém todo o código desenvolvido por mim durante o curso Spring Boot 2 Essentials, desenvolvido pelo canal DevDojo, provê um overview completo pelos principais fundamentos do Framework Spring, você pode encontrar ele [clicando aqui](https://www.youtube.com/watch?v=bCzsSXE4Jzg&list=PL62G310vn6nFBIxp6ZwGnm8xMcGE3VA5H&index=1).

## Funcionalidades Implementadas

Nesse projeto de microservices fizemos um CRUD completo utilizando todos os protocolos HTTP com inserção dos dados no MySQL, junto com todas as validações e exceções personalizadas dos campos e IDs, abrangemos também toda a parte de documentação e monitoramento de métricas da aplicação utilizando o Grafana, Actuator e Swagger.

As seguintes lógicas e funcionalidades foram implementadas:

- Requisição Http.
- Crud completo com os métodos GET, POST, DELETE e PUT.
- Spring Data JPA, MVC.
- Framework de Mapeamento MapStruct.
- Request Params.
- Exceções Customizadas / Handler Global e Transações.
- Validação de Campos / Handler para Validação de Campos.
- Sobrescrevendo handler do Spring.
- Paginação / WebMVC Configurer.
- Sporting e Log SQL no Console.
- RestTemplate getForObject e getForEntity.
- RestTemplate Exchange.
- RestTemplate com métodos POST, PUT e Delete.
- Spring Data JPA - Test.
- Unit Tests e Teste de Integração.
- Maven Profile Para Teste de Integração.
- Spring Security - CSRF Token.
- Spring Security - Segurança a nível de métodos com PreAuthori.
- Spring Security - Autenticação - 'Pegar um Usuario Que Está Autenticado na Requisição'.
- Spring Security - Autenticação Com Usuário no Banco de Dados.
- Spring Security - Proteção de URL com Antmatcher.
- Spring Boot Actuator - Monitoramento da aplicação com prometheus
- Monitorando métricas com gráficos no Grafana.
- Documentação com SpringDoc OpenAPI.

## Requisitos
1. Java 11
2. Docker ou MySQL rodando nativamente

## Requisições HTTP

| Método | Endpoint | Função |
|--------|----------|--------|
| GET    | /        | Retorna uma lista de todos os animes cadastrados de forma paginada. |
| GET    | /{id}    | Retorna informações sobre o anime com o ID especificado. |
| GET    | /find    | Retorna informações sobre o anime com o NOME especificado. |
| POST | /    | Cadastra um novo anime. |
| DELTE | /admin/{id} | Exclui o anime com o ID especificado somente com autenticação de admin. |
| PUT    | /    | Cria ou Atualiza as informações do anime. |

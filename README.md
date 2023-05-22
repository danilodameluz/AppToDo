# API em Java com Spring boot

API desenvolvida em Java com Spring Boot e utilizando Maven como gerenciador de 
Dependências e H2 como Banco de Dados em memória.
Inicializada com [Spring Initialzr](https://start.spring.io/).

### `Java`
Versão 17

### `Spring Boot`
Versão 3.1.0

### `H2`
Versão 2.1.214

### `Git`
Repositório público [App-To-Do](https://github.com/danilodameluz/app-to-do.git)


## Informações Úteis

- O banco de dados é carregado em memória a partir da inicialização da aplicação.
Todas as configurações do banco de dados estão na pasta 
resources>applicattion.properties
- Foram incluídas as dependências do springfox para criação automática da documentação 
pelo Swagger. No momento existe alguma configuração incorreta que não está gerando 
corretamente a documentação. Necessário análise mais profunda.
- Autenticação com JWT ainda não foi realizada.

# 🛍️ Store Manager — Sistema de Gestão de Produtos e Clientes

API RESTful desenvolvida com Java e Spring Boot para gerenciamento de produtos, clientes e, futuramente, pedidos. Este projeto é a base para um sistema completo de gestão de vendas, focado em boas práticas de arquitetura, organização de código e modelagem próxima da realidade do mercado.



## 🚀 Tecnologias e ferramentas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Bean Validation
- PostgreSQL
- H2
- OpenAPI (Swagger)
- Maven
- Lombok



## 📦 Funcionalidades

| Status | Funcionalidade |
|--------|----------------|
| ✅     | CRUD de Produtos |
| ✅     | CRUD de Clientes |
| 🔄     | Registro de Pedidos com múltiplos itens *(em desenvolvimento)* |
| 🔄     | Cálculo automático do valor total do pedido |
| 🔄     | Redução de estoque ao efetuar pedido |
| 🔄     | Controle de status do pedido (PENDENTE, PAGO, ENVIADO, CANCELADO) |
| ⏳     | Autenticação com Spring Security e JWT |
| ⏳     | Filtros por status, cliente, data |
| ⏳     | Testes unitários com JUnit e Mockito |
| ⏳     | Deploy em ambiente cloud gratuito (Railway, Render, etc) |



## 🗃️ Modelo de dados atual

- **Produto**: nome, descrição, preço, estoque
- **Cliente**: nome, e-mail, CPF, endereço *(embutido)*
    - **Endereço** (embutido): rua, número, complemento, bairro, cidade, UF, CEP



## ⚙️ Como executar o projeto

### 1. Pré-requisitos

- Java 17 ou superior
- Maven
- PostgreSQL (ou utilize H2 para testes locais)

### 2. Clone o projeto

```bash
git clone https://github.com/periclesmatos/store_manager.git
cd store-manager
```

### 3. Configure o banco de dados

No arquivo `application.properties`:

#### PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/order_api
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

#### Ou H2 (para testes)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```



## 🔍 Acesse a documentação da API

A documentação gerada automaticamente está disponível em:

```
http://localhost:8080/swagger-ui.html
```



## 🧪 Endpoints disponíveis (até o momento)
### 🔹 Produtos

| Método   | Endpoint                  | Descrição                    |
|----------|---------------------------|------------------------------|
| `POST`   | `/produtos`               | Cadastra um novo produto     |
| `GET`    | `/produtos`               | Lista todos os produtos      |
| `GET`    | `/produtos/{id}`          | Busca produto por ID         |
| `GET`    | `/produtos/busca?nome=x`  | Filtra produtos por nome     |
| `PUT`    | `/produtos/{id}`          | Atualiza os dados do produto |
| `PATCH`  | `/produtos/{id}/preco`    | Modifica somente o preço     |
| `PATCH`  | `/produtos/{id}/estoque`  | Modifica somente o estoque   |
| `DELETE` | `/produtos/{id}`          | Remove um produto            |

### 🔹 Clientes

| Método   | Endpoint         | Descrição                    |
|----------|------------------|------------------------------|
| `POST`   | `/clientes`      | Cadastra um novo cliente     |
| `GET`    | `/clientes`      | Lista todos os clientes      |
| `GET`    | `/clientes/{id}` | Busca cliente por ID         |
| `GET`    | `/clientes/busca?termo=x`| Filtra clientes por termo no nome, e-mail ou CPF |
| `PUT`    | `/clientes/{id}` | Atualiza os dados do cliente |
| `DELETE` | `/clientes/{id}` | Remove um cliente            |



## 💡 Objetivo do projeto

Este projeto é parte de um portfólio prático com foco em:

- Modelagem de domínio próxima do mercado
- Boas práticas de arquitetura em camadas
- Tratamento de exceções e validações
- Expansibilidade para incluir pedidos, segurança, e deploy



## 📫 Contato

Desenvolvido por **Pericles Matos**

- 🔗 [LinkedIn](https://www.linkedin.com/in/periclesm/)
- 💻 [GitHub](https://github.com/periclesmatos)

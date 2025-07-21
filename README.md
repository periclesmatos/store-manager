# 🛍️ Store Manager — Sistema de Gestão de Produtos e Clientes

API RESTful desenvolvida com Java e Spring Boot para gerenciamento de produtos, clientes e, futuramente, pedidos. Este projeto é a base para um sistema completo de gestão de vendas, focado em boas práticas de arquitetura, organização de código e modelagem próxima da realidade do mercado.



## 🚀 Tecnologias e ferramentas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security com JWT
- Bean Validation
- PostgreSQL
- H2 (ambiente de testes)
- OpenAPI (Swagger)
- Maven
- Lombok

## ✅ Funcionalidades implementadas


### 🔐 Autenticação (JWT)
- Registro de novos usuários: `POST /auth/register`
- Login com geração de token: `POST /auth/login`
- Senhas criptografadas com `BCrypt`
- Proteção de endpoints com validação de token JWT
- Validação de expiração automática do token
- Filtro personalizado interceptando requisições autenticadas
- Tokens com validade de 2 horas

### 📦 Produtos
- Cadastro, atualização e exclusão lógica de produtos
- Filtro por nome (com paginação)
- Validação de estoque e preço

### 👤 Clientes
- Registro, atualização e exclusão lógica de clientes
- Listagem com filtro por nome, e-mail ou CPF

### 🧾 Pedidos
- Criação com múltiplos itens
- Cálculo automático do valor total
- Validação de estoque
- Relacionamento com cliente e produtos
- Ações no pedido:
  - Confirmação de pagamento
  - Envio
  - Entrega
  - Cancelamento (com reposição automática de estoque)
- Filtros por status, cliente e data
- Paginação completa

## 🔄 Status do pedido

| Status              | Ação permitida                           |
|---------------------|------------------------------------------|
| `AGUARDANDO_PAGAMENTO` | Confirmar pagamento ou cancelar          |
| `PROCESSANDO`        | Confirmar envio ou cancelar              |
| `ENVIADO`            | Confirmar entrega ou cancelar            |
| `CONCLUIDO`          | Finalizado, nenhuma ação permitida       |
| `CANCELADO`          | Pedido cancelado, nenhuma ação permitida |

## 🔜 Próximas implementações

- Melhor documentação Swagger
- Exceptions personalizadas por cenário
- Testes unitários e de integração
- Suporte a descontos e formas de pagamento
- Refresh Token para sessões persistentes

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
spring.datasource.url=jdbc:postgresql://localhost:5432/store_manager_db
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

###🔐 Autenticação
- `POST /auth/register` — Registrar novo usuário
- `POST /auth/login` — Efetuar login (retorna token JWT)

```
Use o token JWT retornado no login no cabeçalho das requisições protegidas:
Authorization: Bearer <token>
```

### Produtos
- `POST /produtos` - Cadastrar 
- `GET /produtos` - Listar 
- `GET /produtos?nome=...` - Filtrar por nome
- `PUT /produtos/{id}` - Atualizar
- `PATCH /produtos/{id}/preco` - Modificar preço
- `PATCH /produtos/{id}/estoque` - Modificar estoque
- `DELETE /produtos/{id}` - Excluir (lógica)

### Clientes
- `POST /clientes` - Cadastrar 
- `GET /clientes` - Listar
- `GET /clientes?termo=...` - Filtrar por termo (Nome, email ou CPF)
- `DELETE /clientes/{id}` Excluir (lógica)

### Pedidos
- `POST /pedidos` - Registrar pedido
- `GET /pedidos` - Listar (com filtros e paginação)
- `PUT /pedidos/{id}/itens` - Atualizar itens
- `POST /pedidos/{id}/confirmar-pagamento` - Confirmar pagamento
- `POST /pedidos/{id}/confirmar-envio` - Confirmar envio
- `POST /pedidos/{id}/confirmar-entrega` - Confirmar entrega
- `POST /pedidos/{id}/cancelar` - Cancelar pedido

## 🧠 Aprendizados

Este projeto tem como objetivo consolidar conhecimentos essenciais em:

- Arquitetura limpa com Spring Boot
- Boas práticas RESTful
- Autenticação JWT e segurança de endpoints
- Criptografia de senha com BCrypt
- Validação robusta com Bean Validation
- Tratamento de exceções centralizado (`@ControllerAdvice`)
- Filtros dinâmicos com Specification
- Separação clara entre controller, service e repository

## 📫 Contato

Desenvolvido por **Pericles Matos**

- 🔗 [LinkedIn](https://www.linkedin.com/in/periclesm/)
- 💻 [GitHub](https://github.com/periclesmatos)

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

## ✅ Funcionalidades implementadas

### 📦 Produtos
- Cadastro, atualização e exclusão lógica de produtos
- Filtro por nome (com paginação)
- Validação de estoque e preço

### 👤 Clientes
- Registro, atualização e exclusão lógica de clientes
- Listagem com filtro por nome, e-mail ou CPF

### 🧾 Pedidos
- Criação de pedidos com múltiplos itens
- Cálculo automático do valor total
- Validação de estoque no momento do pedido
- Relacionamento com cliente e produtos
- Paginação e filtros por status, cliente e data
- Ações no pedido:
  - Confirmação de pagamento
  - Confirmação de envio
  - Confirmação de entrega
  - Cancelamento (com reposição automática de estoque)

## 🔄 Status do pedido

| Status              | Ação permitida                           |
|---------------------|------------------------------------------|
| `AGUARDANDO_PAGAMENTO` | Confirmar pagamento ou cancelar          |
| `PROCESSANDO`        | Confirmar envio ou cancelar              |
| `ENVIADO`            | Confirmar entrega ou cancelar            |
| `CONCLUIDO`          | Finalizado, nenhuma ação permitida       |
| `CANCELADO`          | Pedido cancelado, nenhuma ação permitida |

## 🔜 Próximas implementações

- Exceptions mais específicas
- Autenticação e autorização
- Documentação com Swagger/OpenAPI
- Testes unitários e de integração
- calculo de desconto e formas de pagamento

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
- Validação robusta com Bean Validation
- Tratamento de exceções centralizado (`@ControllerAdvice`)
- Uso de Specification para filtros dinâmicos
- Controle de transações com `@Transactional` no Service
- Organização de regras de negócio e separação de camadas

## 📫 Contato

Desenvolvido por **Pericles Matos**

- 🔗 [LinkedIn](https://www.linkedin.com/in/periclesm/)
- 💻 [GitHub](https://github.com/periclesmatos)

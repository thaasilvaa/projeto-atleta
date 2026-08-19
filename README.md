# Sistema de Cadastro de Atletas

Sistema web desenvolvido para gerenciamento de atletas de um clube esportivo.  
O projeto permite cadastrar, visualizar, editar e excluir atletas, além de organizar os jogadores por modalidade esportiva.

---

## 📌 Sobre o Projeto

O **SportClub** foi desenvolvido com o objetivo de facilitar o gerenciamento de atletas de um clube esportivo.

O sistema possui uma interface moderna e organizada, permitindo que os dados dos atletas sejam cadastrados e armazenados por meio de uma API desenvolvida em **Java Spring Boot**.

As modalidades disponíveis são:

- ⚽ Futebol
- 🏀 Basquete
- 🏊 Natação
- 🏃 Atletismo

---

## 🎯 Objetivos

- Realizar o cadastro de atletas.
- Consultar atletas cadastrados.
- Editar informações dos atletas.
- Excluir atletas.
- Separar atletas por modalidade.
- Facilitar a visualização das informações.
- Criar uma interface simples, moderna e responsiva.
- Integrar o Front-end com uma API REST.

---

## 🚀 Funcionalidades

### 👤 Cadastro de atletas

O sistema permite cadastrar:

- Nome do atleta
- Modalidade
- Idade
- Salário mensal

---

### 📋 Listagem

Todos os atletas cadastrados podem ser visualizados em uma tabela contendo:

- ID
- Nome
- Modalidade
- Idade
- Salário
- Ações

---

### 🏅 Separação por modalidade

Os atletas são organizados automaticamente em categorias:

**⚽ Futebol**

Exibe somente os atletas cadastrados na modalidade Futebol.

**🏀 Basquete**

Exibe somente os atletas cadastrados na modalidade Basquete.

**🏊 Natação**

Exibe somente os atletas cadastrados na modalidade Natação.

**🏃 Atletismo**

Exibe somente os atletas cadastrados na modalidade Atletismo.

---

### ✏️ Edição

É possível selecionar um atleta e alterar suas informações.

Ao clicar em **Editar**, os dados são carregados automaticamente no formulário.

---

### 🗑️ Exclusão

O sistema permite excluir atletas cadastrados.

Antes da exclusão, uma confirmação é apresentada para evitar exclusões acidentais.

---

### 🔎 Pesquisa

A tabela possui um campo de pesquisa que permite localizar atletas pelo:

- Nome
- Modalidade

---

### 📊 Dashboard

O painel inicial apresenta a quantidade de:

- Total de atletas
- Atletas de Futebol
- Atletas de Basquete
- Atletas de Natação
- Atletas de Atletismo

---

## 🛠️ Tecnologias utilizadas

### Front-end

- HTML5
- CSS3
- JavaScript
- Fetch API
- Google Fonts

### Back-end

- Java
- Spring Boot
- Spring Web
- Spring Data JPA

### Banco de dados

- MySQL

### Ferramentas

- Visual Studio Code
- IntelliJ IDEA / Eclipse
- Postman
- Git
- GitHub

---

## 📁 Estrutura do projeto

```text
SportClub/
│
├── index.html
├── style.css
├── script.js
│
└── README.md

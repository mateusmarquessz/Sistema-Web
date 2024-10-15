# Sistema de Gerenciamento de Tarefas

Este repositório contém um sistema completo de gerenciamento de tarefas, desenvolvido com **Spring Boot** no back-end e **React** no front-end. O sistema foi criado com o objetivo de facilitar a criação, atribuição e gestão de tarefas de forma eficiente, permitindo a execução tanto localmente quanto em um ambiente de produção.

## Funcionalidades principais:
- **Gerenciamento de Usuários**: Somente usuários com a função de **Gestor** podem criar novos usuários no sistema.
- **Criação e Atribuição de Tarefas**: Apenas o Gestor pode criar e atribuir tarefas aos usuários.
- **Visualização de Tarefas**: Usuários comuns podem visualizar apenas suas próprias tarefas, enquanto o Gestor tem uma visão completa de todas as tarefas no sistema.
- **Autenticação e Controle de Acesso**: O sistema utiliza autenticação de usuário e permissões baseadas em funções (roles), garantindo que apenas o Gestor possa gerenciar usuários e tarefas.

## Usuário Padrão:
Ao iniciar o sistema pela primeira vez, um **usuário padrão** é criado automaticamente. Esse usuário tem permissões de **Gestor** e pode realizar todas as operações administrativas no sistema, como a criação e atribuição de tarefas e a gestão de outros usuários.

### Detalhes do Usuário Padrão:
- **Email**: gestorpadrao@gmail.com.br
- **Senha**: teste123

## Funções e Permissões:
- **Gestor**: Pode visualizar todas as tarefas, criar novos usuários, atribuir tarefas e realizar a gestão completa do sistema.
- **Usuário Comum**: Pode visualizar e gerenciar apenas suas próprias tarefas.

## Instruções de Uso:
1. **Login com o Gestor Padrão**:
   - Use o email `gestorpadrao@gmail.com.br` e a senha `teste123` para fazer login como o gestor.
2. **Criação de Novos Usuários**:
   - Após o login, o Gestor pode criar novos usuários e definir suas permissões (roles).
3. **Atribuição de Tarefas**:
   - O Gestor pode criar tarefas e atribuí-las aos usuários registrados.
4. **Visualização de Tarefas**:
   - O Gestor pode visualizar todas as tarefas, enquanto os usuários comuns só podem visualizar as tarefas atribuídas a eles.

## Tecnologias Utilizadas:
- **Back-end**: Spring Boot
- **Front-end**: React
- **Autenticação**: Spring Security com controle de roles
- **Banco de Dados**: Postgresql

## Como Executar o Sistema Localmente:
1. Clone este repositório.
2. Configure o banco de dados no arquivo de propriedades (`application.properties`).
3. Compile e execute o back-end com o Spring Boot.
4. Inicie o front-end com React.
5. Acesse a aplicação no navegador e faça login com as credenciais do **Gestor Padrão**.



### Online via Vercel

https://sistema-web-khaki.vercel.app/

### Download

Os arquivos do sistema estão disponíveis para download neste repositório. Para baixar, clique em `Code` e, em seguida, `Download ZIP` ou clone o repositório usando o seguinte comando:

```bash
git clone https://github.com/usuario/repo.git

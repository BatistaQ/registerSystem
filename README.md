# 📋 Register System

Este é um sistema de registro de usuários desenvolvido em **Java**, que permite cadastrar, listar, buscar e armazenar informações de usuários em arquivos de texto. Além disso, o sistema possibilita a adição e remoção de perguntas do formulário de registro.

## 🚀 Funcionalidades

- 📌 **Registrar Usuário**: O usuário responde a um formulário e seus dados são armazenados.
- 📜 **Listar Usuários Registrados**: Exibe todos os usuários cadastrados.
- ➕ **Adicionar Pergunta ao Formulário**: Permite adicionar novas perguntas ao formulário.
- ❌ **Remover Pergunta do Formulário**: Possibilita excluir perguntas personalizadas do formulário.
- 🔍 **Buscar Usuário**: Pesquisa usuários cadastrados pelo nome, email ou idade.

## 🛠️ Tecnologias Utilizadas

- **Java** 🟡 (Versão recomendada: 8+)
- **Manipulação de Arquivos** 📂 (Leitura e escrita de arquivos `.txt`)
- **Tratamento de Exceções** ⚠️ (Validações para nome, email, idade e altura)
- **Coleções Java** 📜 (`ArrayList`, `List`)
- **Scanner** ⌨️ (Entrada de dados pelo usuário)

## 📄 Como Executar o Projeto

### 1️⃣ **Configuração do Formulário**

Para que o sistema funcione corretamente, é necessário criar um arquivo `form.txt` contendo as perguntas iniciais do registro. O conteúdo do arquivo deve ser o seguinte:

- 1 - Qual seu nome completo?
- 2 - Qual seu email de contato?
- 3 - Qual sua idade?
- 4 - Qual sua altura?


📍 **OBS:** Se desejar alterar o caminho do arquivo, modifique a variável `outputPath` no código:

```java
private static String outputPath = "C:\\Users\\Kauan\\Desktop\\Projeto Java\\Register System\\form.txt";
```

## 📌 Exemplos de Uso
### ➕ Cadastrando um Usuário


``` 
Menu:
1 - Register User
2 - List all registered users
3 - Register new question in the form
4 - Delete question from form
5 - Search user by name, email or age
Choose an option: 1
```
Após selecionar a opção 1, o usuário será solicitado a preencher as seguintes informações:

``` 
1 - Qual seu nome completo?
> João da Silva
2 - Qual seu email de contato?
> joao@email.com
3 - Qual sua idade?
> 25
4 - Qual sua altura?
> 1.75

```

Se os dados forem válidos, a saída será:

```
User saved successfully!

```

Os dados serão armazenados em um arquivo nomeado como 1-JOÃODASILVA.txt.

### 🔍 Buscando um Usuário

```
Digite o termo para buscar (nome, email ou idade): João

```

Saída esperada:

```

Usuários encontrados:
Nome: João da Silva
Email: joao@email.com
Idade: 25
Altura: 1.75

```









  

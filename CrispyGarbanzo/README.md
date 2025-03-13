# BeeHive

## Descrição

BeeHive é uma plataforma de aluguel de propriedades onde proprietários podem cadastrar suas propriedades e usuários podem buscar e reservar propriedades disponíveis.

## Funcionalidades

1. **Cadastro de Propriedades**: Os proprietários podem cadastrar suas propriedades, fornecendo informações como título, descrição, localização, capacidade, preço por noite e fotos.
2. **Cadastro de Usuários**: Os usuários podem se cadastrar na plataforma, fornecendo informações pessoais como nome, email e senha. Os usuários podem ser proprietários ou locatários.
3. **Reservas**: Os usuários registrados podem buscar propriedades disponíveis em uma determinada data e localização e fazer reservas. A plataforma calcula o custo total da reserva.
4. **Menu**:
   - **Proprietário**: Pode cadastrar propriedades, exibir detalhes de propriedades e verificar quais propriedades estão alugadas.
   - **Usuário**: Pode consultar propriedades disponíveis para aluguel e alugar uma propriedade.

## Estrutura de Pastas

O workspace contém as seguintes pastas:

- `src`: Pasta que contém os arquivos fonte.
- `lib`: Pasta que contém as dependências.
- `bin`: Pasta onde os arquivos compilados são gerados.

## Configuração do Projeto

Para personalizar a estrutura de pastas, abra `.vscode/settings.json` e atualize as configurações relacionadas.

## Gerenciamento de Dependências

A visualização `JAVA PROJECTS` permite gerenciar suas dependências. Mais detalhes podem ser encontrados [aqui](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Como Executar

1. Compile o projeto:
    ```sh
    javac -d bin src/*.java
    ```

2. Execute o projeto:
    ```sh
    java -cp bin App
    ```

## Pré-requisitos

- Java Development Kit (JDK) 8 ou superior

## Descrição das Classes

1. **App.java**
   - Esta é a classe principal do projeto. Ela contém o método `main` que inicia o aplicativo e exibe o menu inicial. A classe `App` também possui métodos para listar usuários, listar propriedades, cadastrar propriedades e gerenciar os menus de proprietário e usuário.

2. **Proprietario.java**
   - Representa um proprietário de propriedade. Contém informações como nome, email, senha e uma lista de propriedades que o proprietário possui. Possui métodos para imprimir os dados do proprietário.

3. **Locatario.java**
   - Representa um locatário (usuário que aluga propriedades). Contém informações como nome, email, senha e CPF. Possui métodos para imprimir os dados do locatário.

4. **Propriedade.java**
   - Representa uma propriedade que pode ser alugada. Contém informações como título, descrição, localização, capacidade, preço por noite e o proprietário da propriedade. Possui métodos para imprimir os dados da propriedade.

5. **Reserva.java**
   - Representa uma reserva de uma propriedade. Contém informações como a propriedade reservada, o locatário que fez a reserva, data de check-in e check-out. Possui métodos para fazer uma reserva e imprimir os detalhes da reserva.

## Diagrama de Classes

Aqui está um diagrama de classes simplificado para exemplificar o esquema do aplicativo:

![Diagrama de Classes](attachment:image.png)

## Contribuição

Sinta-se à vontade para contribuir com o projeto. Para isso, siga os passos abaixo:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova feature'`).
4. Faça push para a branch (`git push origin feature/nova-feature`).
5. Crie um novo Pull Request.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

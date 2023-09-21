# Pokédex Microservice

Este é um microserviço que consome dados da PokéAPI e oferece endpoints para pesquisar e ordenar pokémons.

## Estrutura do Projeto

O projeto é dividido em três partes principais:

### Controller

O controller é responsável por definir os endpoints REST que podem ser acessados pelos clientes. Ele usa o framework Spring Boot para expor as seguintes funcionalidades:

- `GET /pokemons`: Permite aos usuários pesquisar pokémons por nome, com opções de ordenação alfabética ou por comprimento do nome.
- `GET /pokemons/highlight`: Semelhante ao endpoint anterior, mas destaca a parte do nome do pokémon que corresponde à consulta.

### Service

O serviço é a camada intermediária que processa as solicitações vindas do controller. Ele realiza as seguintes tarefas:

- Conecta-se à PokéAPI para obter a lista de pokémons.
- Filtra a lista de pokémons com base na consulta do usuário.
- Realiza a ordenação dos pokémons usando o algoritmo TimSort.
- Destaca a parte correspondente à consulta no nome do pokémon (caso necessário).

# Algoritmo de Ordenação TimSort

O algoritmo de ordenação TimSort foi escolhido para ordenar a lista de pokémons neste projeto. TimSort é uma variação do Merge Sort otimizada para lidar com dados parcialmente ordenados e pequenos grupos de dados chamados "runs".

## Lógica do Algoritmo

- `timsort(pokemons: MutableList<Pokemon>, sortType: SortType)`: Este é o ponto de entrada do algoritmo. Ele divide a lista em runs, ordena cada run com o algoritmo de ordenação por inserção e mescla runs adjacentes para obter a lista ordenada final.

- `calculateMinRun(n: Int)`: Este método calcula o tamanho mínimo do run com base no tamanho da lista. O tamanho mínimo do run é determinado usando um algoritmo que garante eficiência.

- `insertionSort(pokemons: MutableList<Pokemon>, start: Int, end: Int, sortType: SortType)`: Implementa o algoritmo de ordenação por inserção para ordenar um run específico.

- `compare(p1: Pokemon, p2: Pokemon, sortType: SortType)`: Este método compara dois Pokémon com base no critério de ordenação especificado (alfabética ou por comprimento).

- `mergeRuns(pokemons: MutableList<Pokemon>, minRun: Int, sortType: SortType)`: Realiza a mesclagem de runs adjacentes na lista.

- `mergeAdjacentRuns(pokemons: MutableList<Pokemon>, stack: MutableList<Pair<Int, Int>>, sortType: SortType)`: Mescla runs adjacentes na pilha.

## Complexidade de Tempo (Big-Ω)

A complexidade de tempo do algoritmo TimSort depende do tamanho da lista de entrada e do grau de ordenação dos dados.

- Melhor Caso: O melhor caso ocorre quando os dados já estão parcialmente ordenados, e a complexidade de tempo é O(n).

- Caso Médio: O caso médio do TimSort é próximo de O(n * log n).

- Pior Caso: O pior caso é O(n * log n), mas o algoritmo é eficiente em dados parcialmente ordenados.

Portanto, o TimSort é uma escolha eficiente para a ordenação de dados parcialmente ordenados como a lista de pokémons neste projeto.

## Como Executar

### Usando Gradle

Para executar o projeto localmente usando Gradle, siga as etapas abaixo:

1. Clone este repositório: `git clone https://github.com/jeffersontavaresdm/pokedex-microservice.git`
2. Navegue até o diretório do projeto: `cd pokedex-microservice`
3. Execute o aplicativo Spring Boot com Gradle: `./gradlew bootRun`

### Usando Docker

Para executar o projeto em um contêiner Docker, siga as etapas abaixo:

1. Clone este repositório: `git clone https://github.com/jeffersontavaresdm/pokedex-microservice.git`
2. Navegue até o diretório do projeto: `cd pokedex-microservice`
3. Certifique-se de que o Gradle já construiu a aplicação: `./gradlew build`.
4. Use o comando Docker Compose para criar a imagem Docker e executar a aplicação: `docker-compose up --build`

Em ambos os casos o aplicativo estará disponível em `http://localhost:8080`.

## Requisitos Adicionais

- Java 11 ou superior
- Kotlin 1.4 ou superior
- Gradle

## Testes Unitários

O projeto inclui testes unitários para garantir que as funcionalidades estejam funcionando conforme o esperado. Você pode executar os testes usando o seguinte comando:

`./gradlew test`

## Autor
[Jefferson Tavares](https://github.com/jeffersontavaresdm)

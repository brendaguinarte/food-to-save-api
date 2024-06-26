# API - FoodToSave - POC

## Visão geral
Esta é uma aplicação Spring Boot que interage com um banco de dados H2 e utiliza Redis para caching. A aplicação permite a criação, leitura, atualização e exclusão de objetos do tipo Restaurante. O cache Redis é configurado para armazenar os dados por um período de 10 segundos, melhorando a performance nas consultas subsequentes.

## Como Rodar a Aplicação
### Pré-requisitos
* Docker e Docker Compose instalados.

### Passos para rodar:

1- Clonar o repositório:

    git clone <URL_DO_REPOSITORIO>
    cd <NOME_DO_REPOSITORIO>

2- Construir e iniciar os serviços com Docker Compose:

    docker-compose up --build

Após as etapas anteriores, a aplicação está pronta para receber solicitações no Postman.

## Documentação

https://documenter.getpostman.com/view/21371773/2sA3drHEQP

## Rotas Disponíveis

`GET /api/v1/restaurante:` _Retorna uma lista de todos os restaurantes utilizando o cache._

`GET /api/v1/restaurante/{id}` _Retorna um restaurante específico baseado no ID fornecido._

`POST /api/v1/restaurante` _Cria um novo restaurante._

Exemplo de corpo da requisição: 
    
        {
            "nome": "Restaurante Exemplo",
            "cnpj": "12.345.678/0001-99",
            "tipoCozinha": "Italiana"
        }
`PUT /api/v1/restaurante/{id}` _Atualiza as informações de um restaurante existente._

Exemplo de corpo da requisição: 
    
        {
            "nome": "Restaurante Atualizado",
            "cnpj": "98.765.432/0001-11",
            "tipoCozinha": "Mexicana"
        }

`DELETE /api/v1/restaurante/{id}` _Deleta um restaurante baseado no ID fornecido._

## Como Acessar o Banco de dados H2

Através do seu navegador, acessa o link: http://localhost:8080/h2-console/
Copiar o id que virá no console ao rodar o docker-compose (conforme imagem abaixo):

![image](https://github.com/brendaguinarte/food-to-save-api/assets/170212422/109864c9-d2a0-49e5-8980-d26ce62b252d)

Preencher no campo JDBC URL na tela de login do banco H2:

![image](https://github.com/brendaguinarte/food-to-save-api/assets/170212422/7724b4b3-18da-4456-88e8-fd21b079979b)

## Como Acessar o Banco de Dados REDIS (Cache)

Para verificar os restaurantes em cache, verifique se tem o redis-cli instalado e siga as etapas abaixo:

`$ redis-cli`

`127.0.0.1:6379> keys *`

Depois de 10 segundos após o cache ser salvo, todos os dados serão apagados até que seja criado um novo cache.

## Contato
Para mais informações, entre em contato com brendaguinarte@gmail.com
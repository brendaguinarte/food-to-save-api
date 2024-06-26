# API - FoodToSave - POC

## Visão geral
Esta é uma aplicação Spring Boot que interage com um banco de dados H2 e utiliza Redis para caching. A aplicação permite a criação, leitura, atualização e exclusão de objetos do tipo Restaurante. O cache Redis é configurado para armazenar os dados por um período de 10 segundos, melhorando a performance nas consultas subsequentes.

## Como Rodar a Aplicação
### Pré-requisitos
* Docker e Docker Compose instalados.

### Passos para rodar:

1- Clonar o repositório:

    git clone https://github.com/brendaguinarte/food-to-save-api.git

2- Acessar diretório do projeto:
   
    cd food-to-save-api

3- Construir e iniciar os serviços com Docker Compose:

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

![image](https://github.com/brendaguinarte/food-to-save-api/assets/170212422/520455b5-7643-4726-afce-f049682866fe)


Preencher no campo JDBC URL na tela de login do banco H2:

![image](https://github.com/brendaguinarte/food-to-save-api/assets/170212422/9368573a-697b-400d-a7e0-9fec26e21c91)


## Como Acessar o Banco de Dados REDIS (Cache)

Para verificar os restaurantes em cache, verifique se tem o redis-cli instalado e siga as etapas abaixo:

`$ redis-cli`

`127.0.0.1:6379> keys *`

Depois de 10 segundos que o cache foi salvo, todos os dados serão apagados até que seja criado um novo cache.

## Rastreabilidade do tempo

Tempo de desenvolvimento foi rastreado utilizando tarefas criadas por mim no TRELLO, facilitando a organização do tempo e esforço sem perder o caminho para o objetivo.

https://trello.com/b/7yPvDDDy/desafio-food-to-save

## Contato
Para mais informações, entre em contato com brendaguinarte@gmail.com

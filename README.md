## Projeto de Gerenciamento de Vagas de Estacionamento

Este projeto é um sistema de gerenciamento de vagas de estacionamento desenvolvido em Spring Boot com Java 11 e JPA.

## Funcionalidades

- Cadastrar uma nova vaga de estacionamento
- Editar os dados de uma vaga de estacionamento existente
- Excluir uma vaga de estacionamento
- Listar todas as vagas de estacionamento cadastradas
- Buscar uma vaga de estacionamento por número da vaga
- Buscar todas as vagas de estacionamento por cor do carro

## Tecnologias utilizadas

- Java 11
- Spring Boot
- JPA/Hibernate
- PostgreSQL
- Maven

## Requisitos

- Java 11
- Maven
- PostgreSQL

## Como executar

1. Clone o repositório:

```
git clone https://github.com/wdson91/Api-ParkingControl.git
```

2. Altere as configurações de banco de dados no arquivo `application.properties`.

3. Execute o seguinte comando no diretório raiz do projeto:

```
mvn spring-boot:run
```

## Endpoints da API

### Cadastrar uma nova vaga de estacionamento

```
POST /parking-spot
```

Body da requisição:

```
{
  "parkingSpotNumber": "A1",
  "licensePlateCar": "ABC1234",
  "brandCar": "Chevrolet",
  "modelCar": "Onix",
  "colorCar": "Preto",
  "responsibleName": "João da Silva",
  "apartment": "101",
  "block": "A"
}
```

### Editar os dados de uma vaga de estacionamento existente

```
PUT /parking-spot/{id}
```

Body da requisição:

```
{
  "parkingSpotNumber": "A1",
  "licensePlateCar": "ABC1234",
  "brandCar": "Chevrolet",
  "modelCar": "Onix",
  "colorCar": "Branco",
  "responsibleName": "João da Silva",
  "apartment": "101",
  "block": "A"
}
```

### Excluir uma vaga de estacionamento

```
DELETE /parking-spot/{id}
```

### Listar todas as vagas de estacionamento cadastradas

```
GET /parking-spot
```
### Funcionalidades Futuras

### Buscar uma vaga de estacionamento por número da vaga

```
GET /parkingSpots/search/findByParkingSpotNumber?parkingSpotNumber=A1
```

### Buscar todas as vagas de estacionamento por cor do carro

```
GET /parkingSpots/search/findByColorCar?colorCar=Branco
```



# Use uma imagem base do Maven para construir o projeto
FROM maven:3.8.4-openjdk-17 AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie os arquivos do projeto para o diretório de trabalho
COPY pom.xml .
COPY src ./src

# Execute o build do Maven
RUN mvn clean package

# Use uma imagem base do OpenJDK para rodar o projeto
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR gerado pelo Maven
COPY --from=build /app/target/restaurante-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta da aplicação
EXPOSE 8080

# Defina o comando de inicialização do container
CMD ["java", "-jar", "app.jar"]

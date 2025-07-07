# Etapa de build
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia arquivos de configuração e código fonte
COPY pom.xml .
COPY src ./src

# Gera o pacote, pulando testes para agilizar
RUN mvn clean package -DskipTests

# Etapa final para rodar a aplicação
FROM openjdk:17
WORKDIR /app

# Copia o JAR gerado pelo assembly plugin da etapa builder
COPY --from=builder /app/target/*-jar-with-dependencies.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 8081

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]

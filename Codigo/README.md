# Como rodar o Projeto??

## Por que usamos Docker, Kubernetes e Azure?
* Primeiro de tudo, veja a pasta **Manual** onde explica cada passo que demos e como implementar.

Durante o desenvolvimento do projeto, diferentes tecnologias foram utilizadas para facilitar testes e deploy:

🐳 Docker
Inicialmente optamos por utilizar o Docker, diretamente da Cloud Shell, porque ainda estávamos aprendendo o funcionamento da Azure. O Docker permite um ambiente controlado e replicável, o que me ajudou a garantir que a aplicação funcionasse localmente, usando apenas o terminal.

☸️ Kubernetes
Em seguida, integramos o Kubernetes como forma de validar se a aplicação Java, o banco de dados e os componentes distribuídos (como Spark) funcionavam corretamente em um ambiente orquestrado, mais próximo de produção. O uso de kompose facilitou a conversão do docker-compose.yml para os manifests Kubernetes.

☁️ Azure
Alguns membros da equipe já estavam familiarizados com Azure e preferiram usar os recursos em nuvem, como o banco de dados PostgreSQL e deploy de aplicações. Apesar disso, mantivemos a compatibilidade com ambientes locais para garantir que qualquer desenvolvedor pudesse rodar o sistema.

```bash
# use
$ mvn compile
$ mvn exec:java
```

Utilize o **MAKEFILE** para qualquer dúvida use:

```bash
$ make help
```
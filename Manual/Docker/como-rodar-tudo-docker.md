# ✅ RODE O PROJETO TODO COM UM SÓ COMANDO

O comando docker-compose up cria e inicializa os containers definidos no docker-compose.yml, ele é um gerenciador!
Já o dockerfile é específico, diz ao sistema como vai ser feito as coisas!!

* Rode o Docker reaproveitando imagens antigas

Ex.: Quando for iniciar ou atualizar o Banco de Dados.

```bash
docker-compose up -d
```
* Rode o Docker usando imagens atualizadas 

Ex.: Quando fazemos modificações nos arquivos java ou no pom.xml.
```bash
docker compose up -d --build
```

* Interrompa o serviço do docker.

Ex.: Acabei de rodar o projeto e quero sair dele.

```bash 
docker-compose down
```

## ❌ EXCLUSÃO: 
### Mas e se eu quiser excluir tudo? Use isso com moderação, ok?

* Apaga todos os cointainers órfãos, inclusive **apaga todos os dados do banco de dados**.

Ex.: Quero começar a fazer tudo do zero, apagando o banco de dados.

```bash 
docker compose down --volumes --remove-orphans
```

* Exlui os containers órfãos **sem apagar os dados do banco de dados**.

Ex.: Quero fazer novas atualizações e liberar espaço de memória no meu computador.

```bash
docker compose down --remove-orphans
```

## 🐳 BAIXAR O DOCKER: 
### Está começando? Veja como baixar seu Docker em um ambiente Linux

Para quem está no  e não sabe onde começar: 

Atualiza o Docker:
```bash
sudo apt install docker-ce docker-ce-cli containerd.io
```

## 🔁🌱 CICLO DE VIDA DOS CONTAINERS: 
### Para Devs mais avançados - Veja o Ciclo de Vida dos Containers

Verificar containers em execução:
```bash
docker ps
```

Parar os containers:
```bash
docker-compose down
```
 Se você quiser remover os volumes também, use:
```bash
docker-compose down -v
```

Ver logs dos containers:
```bash
docker-compose logs
```

Ver logs de um container específico:
```bash
docker logs <container_id>
```

# Ambiente de Desenvolvimento em Nuvem usando o Google Cloud Console
### Uma solução alternativa que não precisa baixar linguagens nativas ou banco de dados

## Passo a Passo (CLI ou VS Code - Editor)
* Crie uma conta na Google Cloud e crie um novo projeto, depois vá em Console Editor
* Você dispõe de 20 minutos em uma VM efêmera, após isso qualquer aplicação baixada com será excluída:

```bash
$sudo apt install aplicacao ou #apt install aplicacao (dentro do sudo su)
```
* Há um comando específico **gcloud** que interage diretamente com sua VM
* As VM's, docker, hosting, serviços de IA são pagos ou por créditos que expiram, mas há maneiras de burlar usando os comandos citados via shell
* A linguagem padrão é Shell Script Bash - debian ou ubuntu

## PostgreSql
Copie os seguintes comandos:

```bash
docker pull postgres 
# baixe a imagem em um docker
sudo apt-get install postgresql postgresql-contrib
# baixando o postgresql, é temporário. Ephemeral, você tem 20min quando está off-line e sem tempo de limite online
psql – version
# psql (PostgreSQL) 17.3 (Ubuntu 17.3-3.pgdg24.04+1)
sudo su postgres 
# muda o usuário da cloud para o postgresql
psql 
# deve ser um comando após o sudo su postgres
```
Ou use o arquivo **chmod +x setup-postgre.sh** e depois **./setup-postgre.sh**

## Maven
* mvn clean compile
Ou use o arquivo **chmod +x setup-maven-spark.sh** e depois **./setup-maven-spark.sh**
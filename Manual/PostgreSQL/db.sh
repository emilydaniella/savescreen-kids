#!/usr/bin/env bash

# Este script automatiza a criação do banco de dados e a execução do script SQL
# Usage: ./automatiza_bd.sh [db_user] [db_host] [db_port] [db_name]

set -euo pipefail

# variaveis globais
DB_USER=${1:-save4kids}
DB_HOST=${2:-127.0.0.1}
DB_PORT=${3:-5432}
DB_NAME=${4:-dbsavescreen}


echo "-> Criando banco de dados '$DB_NAME' (usuário: $DB_USER)"
psql -U "$DB_USER" -h "$DB_HOST" -p "$DB_PORT" -tc "SELECT 1 FROM pg_database WHERE datname = '$DB_NAME'" | grep -q 1 || \
    psql -U "$DB_USER" -h "$DB_HOST" -p "$DB_PORT" -c "CREATE DATABASE \"$DB_NAME\";"

echo "-> Executando script SQL no banco '$DB_NAME'"
psql -U "$DB_USER" -h "$DB_HOST" -p "$DB_PORT" -d "$DB_NAME" <<'EOSQL'

-- Criação das tabelas
CREATE TABLE responsavel (
  id_responsavel SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL
);

CREATE TABLE crianca (
  id_crianca SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  data_nascimento DATE,
  id_responsavel INT NOT NULL,
  FOREIGN KEY (id_responsavel) REFERENCES responsavel(id_responsavel)
);

CREATE TABLE restricao_uso (
  id_restricao SERIAL PRIMARY KEY,
  descricao VARCHAR(255) NOT NULL,
  tempo_lim_min INT NOT NULL,
  id_responsavel INT NOT NULL,
  FOREIGN KEY (id_responsavel) REFERENCES responsavel(id_responsavel)
);

CREATE TABLE uso_tela (
  id_uso SERIAL PRIMARY KEY,
  data DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fim TIME NOT NULL,
  id_crianca INT NOT NULL,
  id_restricao INT NOT NULL,
  FOREIGN KEY (id_crianca) REFERENCES crianca(id_crianca),
  FOREIGN KEY (id_restricao) REFERENCES restricao_uso(id_restricao)
);

CREATE TABLE postagem (
  id_postagem SERIAL PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  descricao TEXT NOT NULL,
  id_responsavel INT NOT NULL,
  FOREIGN KEY (id_responsavel) REFERENCES responsavel(id_responsavel)
);

CREATE TABLE assinatura (
  id_assinatura SERIAL PRIMARY KEY,
  data_inicio DATE NOT NULL,
  ativo BOOLEAN DEFAULT TRUE,
  id_responsavel INT NOT NULL,
  FOREIGN KEY (id_responsavel) REFERENCES responsavel(id_responsavel)
);

	CREATE TABLE rotina (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  descricao TEXT NOT NULL,
  horario TIME NOT NULL,
  id_responsavel INT NOT NULL,
  FOREIGN KEY (id_responsavel) REFERENCES responsavel(id_responsavel)
);

CREATE TABLE user (
  id_responsavel SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL,
  telefone VARCHAR (100) NOT NULL UNIQUE

);

-- Inserção de dados
INSERT INTO responsavel (nome, email, senha) VALUES
  ('Daniella Emily',   'daniella.emily@savescreen.com',  'senhaDani123'),
  ('Eduardo Aniceto',  'eduardo.aniceto@savescreen.com','senhaEdu123'),
  ('Izabel Chaves',    'izabel.chaves@savescreen.com',  'senhaIza123'),
  ('Joao Pedro',       'joao.pedro@savescreen.com',     'senhaJP123');

INSERT INTO crianca (nome, data_nascimento, id_responsavel) VALUES
  ('Lucas Silva',   '2015-03-12', 1),
  ('Mariana Costa', '2014-07-23', 1),
  ('Elói Ribeiro',  '2025-08-19', 2),
  ('Pedro Souza',   '2016-01-05', 3),
  ('Laura Lima',    '2015-11-30', 4),
  ('Rafael Reis',   '2013-09-18', 4);

INSERT INTO restricao_uso (descricao, tempo_lim_min, id_responsavel) VALUES
  ('Máximo 60 minutos por dia',      60, 1),
  ('Apenas 30 minutos após as 18h',  30, 2),
  ('20 minutos antes de dormir',     20, 3),
  ('45 minutos em finais de semana', 45, 4),
  ('Restrição completa em dias de prova', 0, 4);

INSERT INTO uso_tela (data, hora_inicio, hora_fim, id_crianca, id_restricao) VALUES
  ('2025-05-10', '14:00:00', '15:00:00', 1, 1),
  ('2025-05-11', '18:30:00', '19:00:00', 2, 2),
  ('2025-05-12', '20:00:00', '20:20:00', 3, 3),
  ('2025-05-09', '10:00:00', '10:45:00', 4, 4);

INSERT INTO postagem (titulo, descricao, id_responsavel) VALUES
  ('Dicas para limitar tempo de tela', 'Confira estratégias eficazes para reduzir o uso excessivo de dispositivos.', 1),
  ('Apps educativos recomendados',      'Selecionamos aplicativos que combinam diversão e aprendizado.',     2),
  ('Como criar rotina saudável',        'Estabeleça horários fixos para estudo, lazer e descanso.',        3),
  ('Importância do sono infantil',      'Saiba como o uso de telas afeta a qualidade do sono.',          4),
  ('Atividades ao ar livre',            'Sugestões de brincadeiras ao ar livre para crianças.',          4);

INSERT INTO assinatura (data_inicio, ativo, id_responsavel) VALUES
  ('2025-01-01', TRUE,  1),
  ('2025-02-15', FALSE, 2),
  ('2025-03-10', TRUE,  3),
  ('2025-04-05', TRUE,  4);

EOSQL

echo "-> Script executado com sucesso!"


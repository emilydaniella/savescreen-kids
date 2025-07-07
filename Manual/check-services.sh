#!/bin/bash

# Configurações
BACKEND_PORT=4567
FRONTEND_PORT=3000
POSTGRES_PORT=5432
POSTGRES_CONTAINER_NAME=meu_postgres
ROTA_TESTE_BACKEND="http://localhost:${BACKEND_PORT}/api/status"
ROTA_TESTE_FRONTEND="http://localhost:${FRONTEND_PORT}"

# Função para verificar se a porta está escutando
check_port() {
    local PORT=$1
    nc -z localhost $PORT
    if [ $? -eq 0 ]; then
        echo "✅ Porta $PORT está aberta."
    else
        echo "❌ Porta $PORT não está respondendo."
    fi
}

# Função para verificar rota via curl
check_route() {
    local URL=$1
    local EXPECTED_STATUS=${2:-200}
    local STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$URL")
    if [ "$STATUS" -eq "$EXPECTED_STATUS" ]; then
        echo "✅ Rota $URL respondeu com status $STATUS."
    else
        echo "❌ Rota $URL respondeu com status $STATUS (esperado $EXPECTED_STATUS)."
    fi
}

# Verifica se os containers estão rodando
echo "🔍 Verificando containers Docker..."
docker ps

# Verificando portas
echo -e "\n🔌 Verificando portas locais..."
check_port $BACKEND_PORT
check_port $FRONTEND_PORT
check_port $POSTGRES_PORT

# Verificando rotas
echo -e "\n🌐 Verificando rotas HTTP..."
check_route $ROTA_TESTE_BACKEND
check_route $ROTA_TESTE_FRONTEND

# Verificando PostgreSQL
echo -e "\n🗃️  Verificando PostgreSQL..."
docker exec $POSTGRES_CONTAINER_NAME pg_isready > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo "✅ PostgreSQL está aceitando conexões."
else
    echo "❌ PostgreSQL não está aceitando conexões."
fi

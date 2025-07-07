SHELL := /bin/bash

# -----------------------------
# ⚙️ Variáveis Docker
# -----------------------------
BACKEND_PORT := 8081
POSTGRES_PORT := 5432
POSTGRES_CONTAINER := dbsavescreen
POSTGRES_USER := save4kids
POSTGRES_DB := dbsavescreen
ROTA_TESTE_BACKEND := http://localhost:$(BACKEND_PORT)/api/status

# -----------------------------
# ⚙️ Variáveis Kubernetes
# -----------------------------
K8S_DIR := k8s
DB_DEPLOY := postgres
DB_POD := $(shell kubectl get pod -l io.kompose.service=postgres -o jsonpath="{.items[0].metadata.name}")
DB_USER := save4kids
DB_NAME := dbsavescreen
DB_PORT := 5432
DB_LOCAL_PORT := 15432
APP_DEPLOY := app
APP_PORT := 8081
APP_ROUTE := http://localhost:$(APP_PORT)/api/status

# -----------------------------
# 🆘 Ajuda automática
# -----------------------------
.PHONY: help
help: ## Mostra esta ajuda com todos os comandos disponíveis
	@echo "== Comandos disponíveis == "
	@echo " "
	@echo "🐳 Docker:"
	@grep -E '^(up|down|rebuild|psqldb|logs-app|logs-postgres|clean-volume|check-env|automatiza|run-java):.*?## ' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-25s\033[0m %s\n", $$1, $$2}'
	@echo " "
	@echo "☸️ Kubernetes:"
	@grep -E '^(k8s-up|k8s-down|k8s-restart|k8s-status|k8s-logs|k8s-backend-status|k8s-port-db|k8s-db-connect|k8s-db-tables):.*?## ' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-25s\033[0m %s\n", $$1, $$2}'

# -----------------------------
# 🐳 Docker Commands
# -----------------------------
.PHONY: up down rebuild psqldb logs-app logs-postgres clean-volume check-env automatiza run-java

up: ## Sobe containers Docker
	docker compose up -d
	@echo "🚀 Containers subidos."

down: ## Para containers Docker
	docker compose down
	@echo "🛑 Containers parados."

rebuild: ## Reconstrói imagens e sobe containers
	docker compose up -d --build
	@echo "🔄 Containers reconstruídos e subidos."

psqldb: ## Acessa shell psql do container Postgres
	docker exec -it $(POSTGRES_CONTAINER) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)

logs-app: ## Exibe logs do container java-app
	docker logs -f java-app

logs-postgres: ## Exibe logs do container Postgres
	docker logs -f $(POSTGRES_CONTAINER)

clean-volume: ## Remove volume do Postgres (perda de dados!)
	@echo "⚠️ Removendo volume do Postgres... (dados serão perdidos!)"
	docker volume rm $(POSTGRES_CONTAINER)-postgres-data || echo "Volume não encontrado."
	@echo "🧹 Volume removido."

check-env: ## Verifica containers, portas, rotas e Postgres
	@echo "🔍 Verificando containers Docker ativos..."
	@docker ps
	@echo ""

	@echo "Verificando portas..."
	@if nc -z localhost $(BACKEND_PORT); then \
		echo "✅ Porta $(BACKEND_PORT) aberta (Backend)"; \
	else \
		echo "❌ Porta $(BACKEND_PORT) não está respondendo"; \
	fi
	@if nc -z localhost $(POSTGRES_PORT); then \
		echo "✅ Porta $(POSTGRES_PORT) aberta (Postgres)"; \
	else \
		echo "❌ Porta $(POSTGRES_PORT) não está respondendo"; \
	fi
	@echo ""

	@echo "Verificando rota HTTP do Backend..."
	@status=$$(curl -s -o /dev/null -w "%{http_code}" $(ROTA_TESTE_BACKEND)); \
	if [ $$status -eq 200 ]; then \
		echo "✅ Rota Backend respondeu com status $$status."; \
	else \
		echo "❌ Rota Backend respondeu com status $$status."; \
	fi
	@echo ""

	@echo "Verificando PostgreSQL no container '$(POSTGRES_CONTAINER)'..."
	@if docker exec $(POSTGRES_CONTAINER) pg_isready > /dev/null 2>&1; then \
		echo "✅ PostgreSQL está aceitando conexões."; \
	else \
		echo "❌ PostgreSQL não está aceitando conexões."; \
	fi

automatiza: ## Cria e popula banco PostgreSQL
	@echo "Automatizando banco de dados..."
	@if [ ! -f target/classes/script-db/savescreen.sql ]; then \
		echo "❌ Arquivo SQL não encontrado: target/classes/script-db/savescreen.sql"; \
		exit 1; \
	fi
	@docker exec -i $(POSTGRES_CONTAINER) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB) < target/classes/script-db/savescreen.sql
	@echo "✅ Banco criado e populado com sucesso."

run-java: ## Executa aplicação Java com Maven (exec:java)
	@echo "🧹 Limpando build anterior..."
	mvn clean
	@echo "🚀 Executando aplicação Java com Maven..."
	mvn compile exec:java

# -----------------------------
# ☸️ Kubernetes Commands
# -----------------------------
.PHONY: k8s-up k8s-down k8s-restart k8s-status k8s-logs k8s-backend-status k8s-port-db k8s-db-connect k8s-db-tables

k8s-up: ## Aplica os YAMLs e sobe os pods
	kubectl apply -f $(K8S_DIR)
	@echo "🚀 Recursos Kubernetes aplicados."

k8s-down: ## Remove todos os recursos Kubernetes
	kubectl delete -f $(K8S_DIR)
	@echo "🛑 Recursos Kubernetes removidos."

k8s-restart: k8s-down k8s-up ## Reinicia recursos Kubernetes

k8s-status: ## Mostra status dos pods e serviços no Kubernetes
	@echo "📦 Pods:"
	kubectl get pods
	@echo "🛰️  Serviços:"
	kubectl get svc

k8s-logs: ## Mostra logs do backend no Kubernetes
	kubectl logs -l io.kompose.service=$(APP_DEPLOY) -f

k8s-backend-status: ## Testa a rota HTTP do backend Kubernetes
	@echo "Testando rota $(APP_ROUTE)..."
	@kubectl port-forward deployment/$(APP_DEPLOY) $(APP_PORT):$(APP_PORT) >/dev/null 2>&1 &
	@sleep 2
	@curl -s -o /dev/null -w "%{http_code}\n" $(APP_ROUTE)

k8s-port-db: ## Redireciona a porta do Postgres no Kubernetes
	@echo "⏩ Redirecionando porta $(DB_PORT) para $(DB_LOCAL_PORT)..."
	@kubectl port-forward deployment/$(DB_DEPLOY) $(DB_LOCAL_PORT):$(DB_PORT)

k8s-db-connect: ## Acessa psql no pod Kubernetes
	@kubectl exec -it $(DB_POD) -- psql -U $(DB_USER) -d $(DB_NAME)

k8s-db-tables: ## Lista tabelas do banco via psql no Kubernetes
	@kubectl exec -it $(DB_POD) -- psql -U $(DB_USER) -d $(DB_NAME) -c "\dt"

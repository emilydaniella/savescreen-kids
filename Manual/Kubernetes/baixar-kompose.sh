#!/bin/bash
curl -L https://github.com/kubernetes/kompose/releases/download/v1.31.2/kompose-linux-amd64 -o kompose
chmod +x kompose
sudo mv kompose /usr/local/bin

# kompose - converte docker compose .yml para kubernetes .yaml

# kubectl - CLI que gerencia kubernetes

# minikube - cluster local para testar kubernetes
minikube start

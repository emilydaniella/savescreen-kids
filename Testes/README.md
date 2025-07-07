# Código do Projeto

Para rodar o projeto o grupo pensou em uma solução de Banco de Dados Local, a modo que não fosse necessário instalações de dependênciais ou linguagens nativas, optando por imagens em Docker. 

**Arquivo de setup:** [./setup-docker.sh](https://github.com/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2025-1-g02-savescreenkids/blob/master/Codigo/setup-docker.sh)

No entanto, para tal projeto, automatizamos tudo com o uso de scripts em Shell Script Bash para rodar os comandos necessários em um só arquivo de execução:

No caminho:
```bash
cd "plmg-cc-ti2-2025-1-g02-savescreenkids/Codigo"
```

Dê permissão de execução:
```bash
chmod +x setup-docker.sh
```

Execute:
```bash
./setup-docker.sh
```

E pronto!
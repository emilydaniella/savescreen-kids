# Documentação do Projeto

Esta pasta reúne a documentação oficial do projeto SaveScreen Kids, desenvolvido para a disciplina de **Trabalho Interdisciplinar II** dos cursos de Tecnologia da Informação da **[PUC Minas](https://pucminas.br)**. O objetivo do projeto é propor uma solução digital que auxilie responsáveis a reduzirem o tempo de tela das crianças, promovendo uma rotina mais equilibrada, saudável e educativa.

O projeto surgiu diante da crescente preocupação com os impactos negativos do uso excessivo de dispositivos digitais na infância. Como resposta, o SaveScreen Kids oferece recursos como o cadastro de rotinas personalizadas, sugestões de atividades educativas e um plano de assinatura com envio físico de livros e brinquedos.

A documentação está estruturada para refletir todas as entregas realizadas ao longo das sprints, abordando desde a definição do problema até as decisões de implementação técnica (como front-end, back-end e banco de dados). Também estão presentes os requisitos funcionais e não funcionais, além dos fluxos de usuário e wireframes.

 A versão publicada da documentação está disponível em:
https://ti-docs-savescreen-kids.vercel.app

# Orientações Gerais
Abaixo, você encontra o índice com os principais artefatos e seções documentadas no projeto:

## Índice
1. [Introdução](#introdução)
2. [Problema](#problema)
3. [Objetivos](#objetivos)
4. [Justificativa](#justificativa)
5. [Público-Alvo](#público-alvo)
6. [Requisitos](#requisitos)
- [Requisitos Funcionais](#requisitos-funcionais)
- [Requisitos Não Funcionais](#requisitos-não-funcionais)
7. [Metodologia e Gestão de Projetos](#metodologia-e-gestão-de-projetos)
8. [Fluxo de Usuário e Wireframe](#fluxo-de-usuarios-e-wireframe)

  
## Introdução
O projeto SaveScreen Kids foi idealizado com o objetivo de promover uma alternativa saudável ao uso excessivo de telas por crianças. Em um cenário onde o consumo de conteúdos digitais cresce exponencialmente entre o público infantil, torna-se essencial criar soluções que contribuam para o equilíbrio entre tecnologia e desenvolvimento infantil. Este projeto surge como uma resposta inovadora a essa realidade, oferecendo aos responsáveis uma plataforma interativa e educativa que apoia a criação de rotinas mais saudáveis.


## Problema
Atualmente, o uso excessivo de telas por crianças tem gerado impactos negativos no desenvolvimento físico, cognitivo e social da infância. Estudos apontam que o tempo elevado em frente a dispositivos eletrônicos pode comprometer o sono, a atenção, a criatividade e a socialização das crianças. Muitos responsáveis têm dificuldades em controlar esse tempo e encontrar alternativas atrativas que incentivem outras formas de aprendizado e lazer. O SaveScreen Kids busca solucionar esse problema por meio de uma ferramenta que oferece recursos digitais com foco no desenvolvimento saudável, reduzindo a dependência de telas por meio da integração de atividades lúdicas e educativas.


## Objetivos
O principal objetivo do SaveScreen Kids é reduzir o tempo de exposição das crianças às telas, promovendo um estilo de vida mais equilibrado e educativo. Para isso, a plataforma permite que os responsáveis criem contas personalizadas para seus filhos, organizem suas rotinas, visualizem e acompanhem atividades educativas, criem postagens e interajam com conteúdos voltados ao desenvolvimento infantil. Além disso, o projeto oferece uma assinatura opcional que envia brinquedos e livros infantis físicos, estimulando o contato com experiências sensoriais e criativas fora do ambiente digital.


## Justificativa
A crescente preocupação com os impactos do uso excessivo de telas na infância evidencia a relevância do SaveScreen Kids. A iniciativa propõe uma solução prática e acessível para famílias que buscam formas de acompanhar e estimular o desenvolvimento saudável de seus filhos. Ao unir tecnologia com ações educativas, o projeto não apenas combate os efeitos negativos da exposição prolongada às telas, como também incentiva a construção de hábitos mais saudáveis e conscientes desde a infância. A proposta se destaca pela inovação e pelo cuidado com o bem-estar infantil, sendo altamente pertinente no contexto atual.


## Público-Alvo
O SaveScreen Kids é direcionado a responsáveis por crianças em idade pré-escolar e escolar (aproximadamente de 2 a 10 anos), que desejam estabelecer limites no uso de telas e proporcionar experiências educativas e enriquecedoras para seus filhos. Este público inclui pais, mães, tutores, educadores e cuidadores preocupados com a saúde mental, emocional e intelectual das crianças. Os usuários da plataforma poderão contar com um sistema que respeita a individualidade da criança, oferece conteúdo de qualidade e propõe um acompanhamento ativo do desenvolvimento infantil por parte dos adultos.


## Requisitos

### Requisitos Funcionais

| **ID** | **Descrição** | **Prioridade** |
|------------|-------------------------------------------------------------------------------------------------|----------------|
| **RF-001** | O sistema deve permitir o cadastro de usuário. | Alta |
| **RF-002** | O sistema deve permitir o login de usuários cadastrados. | Alta |
| **RF-003** | O sistema deve permitir a atualização dos dados de um usuário cadastrado. | Média |
| **RF-004** | O sistema deve permitir a exclusão de usuários cadastrados. | Média |
| **RF-005** | O sistema deve permitir a criação de postagens por um usuário cadastrado. | Alta |
| **RF-006** | O sistema deve permitir a atualização dos dados das postagens criadas por um usuário cadastrado.| Média |
| **RF-007** | O sistema deve permitir a exclusão das postagens criadas por um usuário cadastrado. | Média |
| **RF-008** | O sistema deve permitir a exibição de todas as postagens cadastradas. | Alta |
| **RF-009** | O sistema deve permitir a filtragem das postagens cadastradas por nome. | Baixa |
| **RF-010** | O sistema deve permitir contactar os desenvolvedores do processo. | Baixa |
| **RF-011** | O sistema deve permitir que o usuário cadastrado monte a rotina do seu filho. | Alta |
| **RF-012** | O sistema deve permitir a criação das tarefas da rotina pelo usuário cadastrado. | Alta |
| **RF-013** | O sistema deve permitir a atualização das tarefas da rotina criada pelo usuário cadastrado. | Média |
| **RF-014** | O sistema deve permitir a exclusão das tarefas da rotina criada pelo usuário cadastrado. | Média |
| **RF-015** | O sistema deve permitir a filtragem das tarefas da rotina pelo nome. | Baixa |
| **RF-016** | O sistema deve permitir a visualização de informações sobre a empresa. | Baixa |
| **RF-017** | O sistema deve permitir que o usuário navegue facilmente entre as telas através de um menu. | Alta |
| **RF-018** | O sistema deve permitir que os usuários autenticados acessem apenas suas informações cadastradas.| Média |
| **RF-019** | O sistema deve permitir que os usuários cadastrados recebam notificações por e-mail quando uma nova atividade for cadastrada.| Baixa |
| **RF-020** | O sistema deve fornecer um botão de "Voltar ao topo" em todas as páginas para facilitar a navegação do usuário.| Baixa |


### Requisitos Não Funcionais

| **ID** | **Descrição** | **Prioridade** |
|-------------|-----------------------------------------------------------------------------------------------------------------------|----------------|
| **RNF-001** | O sistema deve ser responsivo, adequando-se a diversos tamanhos de telas. | Alta |
| **RNF-002** | O sistema deve ser compatível com diversos navegadores. | Média |
| **RNF-003** | O sistema deve ser acessível para diversos perfis de usuários, permitindo que pessoas com deficiências possam ter acesso.| Alta |
| **RNF-004** | O sistema deve ter um SEO que facilite a busca da página na web. | Baixa |
| **RNF-005** | O sistema deve conter uma boa consistência visual. | Média |
| **RNF-006** | O sistema deve possuir uma navegação de fácil acesso, aumentando a inclusão de diversas faixas etárias. | Alta |
| **RNF-007** | O sistema deve ser otimizado, facilitando o carregamento da página. | Média |
| **RNF-008** | O sistema deve ser modular, com facilidade de manter e atualizar o código. | Alta |


## Metodologia e Gestão de Projetos
O desenvolvimento do projeto SaveScreen Kids foi orientado pela metodologia ágil Scrum, que permitiu organizar o trabalho em ciclos iterativos (sprints), promovendo uma melhor gestão do tempo, adaptação contínua e colaboração entre os integrantes.
A equipe é composta por quatro membros:
Daniella Emily Cornélio da Silva **(Product Owner)**,
Izabel Oliveira da Paz Chaves **(Scrum Master)**,
Eduardo Henrique Aniceto Teixeira e
João Pedro Gomes Machado

Daniella assumiu o papel de Product Owner, sendo responsável por garantir que as funcionalidades desenvolvidas estivessem alinhadas às necessidades do usuário final e aos objetivos do projeto. Izabel atuou como Scrum Master, conduzindo as reuniões, removendo impedimentos e assegurando que os princípios do Scrum fossem seguidos, além de desenvolver a integridade do front-end com o back-end por meio de scripts de automação e docker. Eduardo e João Pedro contribuíram principalmente com a integração com banco de dados, back-end e funcionalidades internas da aplicação, enquanto Daniella também foi responsável pelo desenvolvimento do front-end e pela documentação do projeto.

As principais ferramentas e tecnologias utilizadas incluem:
- HTML, CSS e JavaScript – para a interface do usuário;
- Figma – para o design dos protótipos e wireframes;
- Java com Maven – para o desenvolvimento do back-end e controle de dependências;
- PostgreSQL – como banco de dados relacional;
- Google Cloud Platform (GCP) – para a infraestrutura do projeto e agilidade;
- APIs – utilizadas para integração entre os módulos e funcionalidades;
- Docker - para rodar todo o projeto sem precisar ficar baixando dependências;
- Scripts em Shell Bash - com o objetivo de automatizar tarefas.

O uso do Scrum, aliado a ferramentas modernas de desenvolvimento e gestão, contribuiu para um fluxo de trabalho eficiente e bem distribuído entre os membros da equipe, resultando em uma solução funcional e centrada na experiência dos usuários responsáveis pelas crianças.


## Fluxo de Usuários e Wireframe
**User Flow**

![userflow](https://github.com/user-attachments/assets/9044da67-7636-4795-bf95-1d529c4d4423)

**Wireframe**

![wireframe](https://github.com/user-attachments/assets/be3e1ca2-38d5-4eec-b3de-0055eaa962dd)

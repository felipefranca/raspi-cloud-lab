# raspi-cloud-lab

Laboratório prático para aprender Cloud com uma API Java (Spring Boot), Docker, Docker Hub e deploy automático em Raspberry Pi usando GitHub Actions com runner self-hosted.

## Objetivo

Este projeto demonstra um fluxo completo de CI/CD:

1. Build da aplicação Java com Maven
2. Build e push de imagem Docker multi-arquitetura (`amd64` + `arm64`)
3. Deploy automático na Raspberry Pi (self-hosted runner)
4. Atualização do container com tag imutável por commit (`github.sha`)

## Arquitetura

1. `push` na branch `master`
2. GitHub Actions executa job `build` no `ubuntu-latest`
3. Imagem é publicada no Docker Hub:
   - `felipejofranca/raspi-cloud-lab:latest`
   - `felipejofranca/raspi-cloud-lab:<sha-do-commit>`
4. Job `deploy` roda na Raspberry (`self-hosted`, `linux`, `arm64`)
5. Runner faz:
   - `docker pull` da tag SHA
   - `docker stop/rm` do container antigo
   - `docker run` com variáveis de ambiente e rede `cloud-network`

## Stack

- Java 21
- Spring Boot
- PostgreSQL
- Docker
- GitHub Actions
- Raspberry Pi 3 (aarch64 / arm64)

## Estrutura principal

- `Dockerfile`
- `.github/workflows/ci.yml`
- `src/main/java/com/docker/raspi_cloud_lab`
- `src/main/resources/application.properties`

## Pré-requisitos

1. Docker instalado na Raspberry
2. Rede Docker criada:

```bash
docker network create cloud-network
```

3. Banco PostgreSQL acessível na mesma rede:
   - host: `postgres`
   - port: `5432`
   - db: `appdb`
   - user: `admin`
   - password: `admin`
4. Runner self-hosted configurado no repositório com labels:
   - `self-hosted`
   - `linux`
   - `arm64`

## Pipeline (GitHub Actions)

Arquivo: `.github/workflows/ci.yml`

- Job `build`:
  - `mvn clean package -DskipTests`
  - login no Docker Hub
  - build/push multi-arch (`linux/amd64,linux/arm64`)
- Job `deploy`:
  - executa na Raspberry
  - baixa imagem da tag SHA
  - recria container `raspi-cloud-lab`

## Variáveis de ambiente da aplicação

No deploy, o container recebe:

- `spring.datasource.url=jdbc:postgresql://postgres:5432/appdb`
- `spring.datasource.username=admin`
- `spring.datasource.password=admin`

## Comandos manuais úteis (Raspberry)

Atualizar manualmente com Docker puro:

```bash
docker pull felipejofranca/raspi-cloud-lab:latest
docker stop raspi-cloud-lab || true
docker rm raspi-cloud-lab || true
docker run -d \
  --name raspi-cloud-lab \
  --restart unless-stopped \
  --network cloud-network \
  -p 8080:8080 \
  -e spring.datasource.url="jdbc:postgresql://postgres:5432/appdb" \
  -e spring.datasource.username="admin" \
  -e spring.datasource.password="admin" \
  felipejofranca/raspi-cloud-lab:latest
```

Validar execução:

```bash
docker ps
docker logs -f --tail=100 raspi-cloud-lab
```

## Troubleshooting

### `exec /__cacert_entrypoint.sh: exec format error`

Causa comum: imagem em arquitetura incompatível.

Exemplo do problema:
- host Raspberry: `arm64` (`aarch64`)
- imagem construída apenas para `amd64`

Correção aplicada neste projeto:
- build multi-arquitetura no CI com `linux/amd64,linux/arm64`

### `no match for platform in manifest`

Ocorre quando a base da imagem não suporta a plataforma solicitada (ex.: `linux/arm/v7` em imagem sem suporte).  
Solução: remover a plataforma não suportada do `buildx` ou usar uma base compatível.

## Evidências sugeridas para este README

Adicione capturas em `docs/images/` e referencie aqui:

1. Registro do self-hosted runner com labels `self-hosted,linux,arm64`
2. Runner ativo no `systemd` (`active (running)`)
3. Workflow com `build` e `Deploy on Raspberry` em `Success`
4. Logs do step de deploy (`pull`, `stop/rm`, `run`)
5. Portainer mostrando container `raspi-cloud-lab` em execução com tag SHA
6. Teste da API (`GET /api/v1/messages` e `POST`)

## Licença

Defina a licença que preferir (`MIT`, por exemplo) antes de publicar.

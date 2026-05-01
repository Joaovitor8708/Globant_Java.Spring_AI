# Budgeting

> 🇧🇷 Português | 🇺🇸 [English below](#english-version)

---

## 🇧🇷 Português

### Sobre o Projeto

Projeto desenvolvido durante o **Bootcamp Globant - Java com IA**, promovido pela [DIO (Digital Innovation One)](https://www.dio.me/).

O objetivo é construir uma aplicação de **controle financeiro inteligente** utilizando Java com **Spring AI**, integrando LLMs para processar transações financeiras a partir de descrições em linguagem natural e áudio. O assistente é capaz de identificar o local, o valor gasto e categorizar a transação automaticamente, persistindo os dados em banco de dados.

---

### ⚠️ Diferenças em relação ao projeto do professor

Este projeto possui alterações importantes em relação à versão apresentada nas aulas, pois **não havia acesso gratuito à API da OpenAI**. As principais adaptações foram:

- **API utilizada:** [Groq](https://groq.com/) no lugar da OpenAI
- **Modelo de chat:** `llama-3.3-70b-versatile` (via Groq) no lugar dos modelos GPT da OpenAI
- **Modelo de transcrição:** `whisper-large-v3` (via Groq) no lugar do Whisper da OpenAI
- **Text-to-Speech (TTS) — não implementado:** O Groq **não suporta** geração de áudio (endpoint `/v1/audio/speech`). O `TextToSpeechController` existe no código mas não funciona com Groq. A saída do assistente financeiro foi adaptada para **retornar texto** no lugar de áudio
- **Spring AI BOM:** `1.0.0-M6` (versão milestone) para compatibilidade com o Groq
- **Lombok:** adicionado via plugin `io.freefair.lombok` para reduzir boilerplate nas entidades e classes de domínio

---

### 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas inspirada em **Clean Architecture / Ports and Adapters**:

```
dio.budgeting/
├── domain/                        # Entidades e interfaces do domínio
│   ├── Transaction.java
│   ├── TransactionId.java
│   ├── Category.java (enum: GROCERIES, PHARMA, AUTO)
│   └── TransactionRepository.java
│
├── application/                   # Casos de uso (Tools do Spring AI)
│   ├── PersistTransactionUseCase.java    (@Tool)
│   ├── ListTransactionsByCategoryUseCase.java (@Tool)
│   ├── input/PersistTransactionInput.java
│   └── output/TransactionOutput.java
│
└── infrastructre/
    ├── http/                      # Controllers REST
    │   ├── TransactionController.java  (orquestra IA + persistência)
    │   ├── request/TransactionRequest.java
    │   └── response/TransactionResponse.java
    └── persistence/               # JPA + MySQL
        ├── entity/TransactionEntity.java
        └── repository/
```

---

### 🔄 Fluxo Principal

```
Usuário envia áudio (.m4a/.mp3)
        ↓
TransactionController /transactions/ai
        ↓
Whisper (Groq) → Transcrição em texto PT-BR
        ↓
LLM (llama-3.3-70b via Groq) + System Prompt de assistente financeiro
        ↓
Tool Calling → PersistTransactionUseCase / ListTransactionsByCategoryUseCase
        ↓
MySQL (Docker) ← JPA
        ↓
Resposta em texto para o usuário  ✅
(Audio de resposta não disponível — Groq não suporta TTS ❌)
```

---

### 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Spring Boot | 3.4.5 |
| Spring AI BOM | 1.0.0-M6 |
| Groq API | — |
| Whisper Large V3 | — |
| Llama 3.3 70B Versatile | — |
| MySQL | 9.6 (Docker) |
| Spring Data JPA | — |
| Lombok | 9.2.0 (plugin) |
| Gradle | 8.10 |

---

### 📁 Estrutura de Pastas

```
budgeting/
├── src/
│   ├── main/
│   │   ├── java/dio/budgeting/
│   │   │   ├── BudgetingApplication.java
│   │   │   ├── ChatClientController.java
│   │   │   ├── ChatModelController.java
│   │   │   ├── TranscriptionController.java
│   │   │   ├── TextToSpeechController.java   ⚠️ não funcional com Groq
│   │   │   ├── application/
│   │   │   ├── domain/
│   │   │   └── infrastructre/
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── prompts/system.st
│   └── test/
│       ├── java/dio/budgeting/
│       │   ├── OpenAITranscriptionModelIT.java  ✅
│       │   ├── OpenAISpeechModelIT.java         ⚠️ falha com Groq
│       │   ├── OpenAiChatClientIT.java
│       │   ├── OpenAiChatModelIT.java
│       │   └── ToolCallingIT.java
│       └── resources/audio/                    # Áudios .m4a para testes
├── compose.yml                                 # MySQL via Docker
├── build.gradle
├── settings.gradle
└── gradlew
```

---

### 🌐 Endpoints REST

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/chat` | Chat direto via ChatClient |
| `GET` | `/api/chat-model` | Chat direto via ChatModel |
| `POST` | `/api/transcribe` | Transcrição de áudio para texto |
| `POST` | `/api/sinthesize` | TTS ⚠️ não funcional com Groq |
| `POST` | `/transactions` | Cria transação manualmente |
| `GET` | `/transactions/{category}` | Lista transações por categoria |
| `POST` | `/transactions/ai` | **Endpoint principal:** envia áudio, IA transcreve, classifica e persiste |

---

### ▶️ Como Rodar o Projeto

#### Pré-requisitos

- Java 21+
- Gradle 8.10+
- Docker (para o banco de dados MySQL)
- Conta na [Groq](https://console.groq.com/) para obter uma API Key gratuita

#### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/Joaovitor8708/Globant_Java.Spring_AI.git
cd Globant_Java.Spring_AI
```

2. Configure a variável de ambiente com sua chave da Groq:
```bash
# Linux/macOS
export GROQ_API_KEY=sua_chave_aqui

# Windows (PowerShell)
$env:GROQ_API_KEY="sua_chave_aqui"
```

3. Suba o banco de dados via Docker:
```bash
docker compose up -d
```

4. Execute o projeto:
```bash
./gradlew bootRun
```

#### Executando os testes

```bash
./gradlew test
```

> Os testes de integração requerem que a variável `GROQ_API_KEY` esteja definida.
> O teste `OpenAISpeechModelIT` irá falhar pois o Groq não suporta TTS.

#### Testando o fluxo principal com curl

```bash
# Enviar um áudio e receber a transação classificada em texto
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@seu-audio.m4a"

# Listar transações por categoria
curl http://localhost:8080/transactions/GROCERIES
```

---

---

## English Version

### About the Project

This project was developed during the **Globant Bootcamp - Java with AI**, promoted by [DIO (Digital Innovation One)](https://www.dio.me/).

The goal is to build an **intelligent budgeting application** using Java with **Spring AI**, integrating LLMs to process financial transactions from natural language descriptions and audio. The assistant identifies the location, amount spent, and automatically categorizes the transaction, persisting the data to a database.

---

### ⚠️ Differences from the Professor's Version

This project includes important changes compared to the version shown in class, as **free access to the OpenAI API was not available**. The main adaptations were:

- **API used:** [Groq](https://groq.com/) instead of OpenAI
- **Chat model:** `llama-3.3-70b-versatile` (via Groq) instead of OpenAI's GPT models
- **Transcription model:** `whisper-large-v3` (via Groq) instead of OpenAI's Whisper
- **Text-to-Speech (TTS) — not implemented:** Groq **does not support** audio generation (endpoint `/v1/audio/speech`). The `TextToSpeechController` exists in the code but does not work with Groq. The financial assistant output was adapted to **return text** instead of audio
- **Spring AI BOM:** `1.0.0-M6` (milestone version) for Groq compatibility
- **Lombok:** added via `io.freefair.lombok` plugin to reduce boilerplate in entities and domain classes

---

### 🏗️ Architecture

The project follows a layered architecture inspired by **Clean Architecture / Ports and Adapters**:

```
dio.budgeting/
├── domain/                        # Domain entities and interfaces
│   ├── Transaction.java
│   ├── TransactionId.java
│   ├── Category.java (enum: GROCERIES, PHARMA, AUTO)
│   └── TransactionRepository.java
│
├── application/                   # Use cases (Spring AI Tools)
│   ├── PersistTransactionUseCase.java    (@Tool)
│   ├── ListTransactionsByCategoryUseCase.java (@Tool)
│   ├── input/PersistTransactionInput.java
│   └── output/TransactionOutput.java
│
└── infrastructre/
    ├── http/                      # REST Controllers
    │   ├── TransactionController.java  (orchestrates AI + persistence)
    │   ├── request/TransactionRequest.java
    │   └── response/TransactionResponse.java
    └── persistence/               # JPA + MySQL
        ├── entity/TransactionEntity.java
        └── repository/
```

---

### 🔄 Main Flow

```
User sends audio (.m4a/.mp3)
        ↓
TransactionController /transactions/ai
        ↓
Whisper (Groq) → Transcription to PT-BR text
        ↓
LLM (llama-3.3-70b via Groq) + Financial assistant system prompt
        ↓
Tool Calling → PersistTransactionUseCase / ListTransactionsByCategoryUseCase
        ↓
MySQL (Docker) ← JPA
        ↓
Text response to user  ✅
(Audio response not available — Groq does not support TTS ❌)
```

---

### 🛠️ Technologies Used

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.4.5 |
| Spring AI BOM | 1.0.0-M6 |
| Groq API | — |
| Whisper Large V3 | — |
| Llama 3.3 70B Versatile | — |
| MySQL | 9.6 (Docker) |
| Spring Data JPA | — |
| Lombok | 9.2.0 (plugin) |
| Gradle | 8.10 |

---

### 📁 Project Structure

```
budgeting/
├── src/
│   ├── main/
│   │   ├── java/dio/budgeting/
│   │   │   ├── BudgetingApplication.java
│   │   │   ├── ChatClientController.java
│   │   │   ├── ChatModelController.java
│   │   │   ├── TranscriptionController.java
│   │   │   ├── TextToSpeechController.java   ⚠️ not functional with Groq
│   │   │   ├── application/
│   │   │   ├── domain/
│   │   │   └── infrastructre/
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── prompts/system.st
│   └── test/
│       ├── java/dio/budgeting/
│       │   ├── OpenAITranscriptionModelIT.java  ✅
│       │   ├── OpenAISpeechModelIT.java         ⚠️ fails with Groq
│       │   ├── OpenAiChatClientIT.java
│       │   ├── OpenAiChatModelIT.java
│       │   └── ToolCallingIT.java
│       └── resources/audio/                    # .m4a audio files for tests
├── compose.yml                                 # MySQL via Docker
├── build.gradle
├── settings.gradle
└── gradlew
```

---

### 🌐 REST Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/chat` | Direct chat via ChatClient |
| `GET` | `/api/chat-model` | Direct chat via ChatModel |
| `POST` | `/api/transcribe` | Audio transcription to text |
| `POST` | `/api/sinthesize` | TTS ⚠️ not functional with Groq |
| `POST` | `/transactions` | Manually create a transaction |
| `GET` | `/transactions/{category}` | List transactions by category |
| `POST` | `/transactions/ai` | **Main endpoint:** sends audio, AI transcribes, classifies and persists |

---

### ▶️ How to Run

#### Prerequisites

- Java 21+
- Gradle 8.10+
- Docker (for the MySQL database)
- A [Groq](https://console.groq.com/) account to get a free API Key

#### Setup

1. Clone the repository:
```bash
git clone https://github.com/Joaovitor8708/Globant_Java.Spring_AI.git
cd Globant_Java.Spring_AI
```

2. Set the environment variable with your Groq API key:
```bash
# Linux/macOS
export GROQ_API_KEY=your_key_here

# Windows (PowerShell)
$env:GROQ_API_KEY="your_key_here"
```

3. Start the database via Docker:
```bash
docker compose up -d
```

4. Run the project:
```bash
./gradlew bootRun
```

#### Running Tests

```bash
./gradlew test
```

> Integration tests require the `GROQ_API_KEY` environment variable to be set.
> The `OpenAISpeechModelIT` test will fail because Groq does not support TTS.

#### Testing the main flow with curl

```bash
# Send an audio file and receive the classified transaction as text
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@your-audio.m4a"

# List transactions by category
curl http://localhost:8080/transactions/GROCERIES
```

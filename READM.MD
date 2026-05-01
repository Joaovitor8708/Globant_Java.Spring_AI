# Budgeting

> 🇧🇷 Português | 🇺🇸 [English below](#english-version)

---

## 🇧🇷 Português

### Sobre o Projeto

Projeto desenvolvido durante o **Bootcamp Globant - Java com IA**, promovido pela [DIO (Digital Innovation One)](https://www.dio.me/).

O objetivo é explorar o desenvolvimento Java moderno com **Spring AI**, integrando modelos de linguagem e transcrição de áudio para criar uma aplicação de controle financeiro.

---

### ⚠️ Diferenças em relação ao projeto do professor

Este projeto possui alterações importantes em relação à versão apresentada nas aulas, pois **não havia acesso gratuito à API da OpenAI**. As principais adaptações foram:

- **API utilizada:** [Groq](https://groq.com/) no lugar da OpenAI
- **Modelo de transcrição:** `whisper-large-v3` (via Groq) no lugar do Whisper da OpenAI
- **Text-to-Speech (TTS):** O Groq **não suporta** geração de áudio (endpoint `/v1/audio/speech`), portanto essa funcionalidade não está disponível nesta versão
- **Versão do Spring AI:** `1.0.0-M6` — versão milestone utilizada para compatibilidade com o Groq
- **Modelo de chat:** `llama-3.3-70b-versatile` (via Groq) no lugar dos modelos GPT da OpenAI

---

### 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Spring Boot | 3.4.5 |
| Spring AI BOM | 1.0.0-M6 |
| Groq API | — |
| Whisper Large V3 | — |
| Llama 3.3 70B | — |
| Gradle | 8.10 |

```
budgeting/
├── src/
│   ├── main/
│   │   ├── java/dio/budgeting/
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
│       └── java/dio/budgeting/
├── build.gradle
├── settings.gradle
├── gradlew
└── README.md
```

---

### ▶️ Como Rodar o Projeto

#### Pré-requisitos

- Java 21+
- Gradle 8.10+(https://console.groq.com/) para obter uma API Key gratuita

#### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/Joaovitor8708/budgeting.git
cd budgeting
```

2. Configure a variável de ambiente com sua chave da Groq:
```bash
export GROQ_API_KEY=sua_chave_aqui
```

3. Execute o projeto:
```bash
./gradlew bootRun
```

#### Executando os testes

```bash
./gradlew test
```

> Os testes de integração requerem que a variável `GROQ_API_KEY` esteja definida no ambiente.

---

---

## English Version

### About the Project

This project was developed during the **Globant Bootcamp - Java with AI**, promoted by [DIO (Digital Innovation One)](https://www.dio.me/).

The goal is to explore modern Java development with **Spring AI**, integrating language models and audio transcription to build a budgeting application.

---

### ⚠️ Differences from the Professor's Version

This project includes important changes compared to the version shown in class, as **free access to the OpenAI API was not available**. The main adaptations were:

- **API used:** [Groq](https://groq.com/) instead of OpenAI
- **Transcription model:** `whisper-large-v3` (via Groq) instead of OpenAI's Whisper
- **Text-to-Speech (TTS):** Groq **does not support** audio generation (endpoint `/v1/audio/speech`), so this feature is not available in this version
- **Spring AI version:** `1.0.0-M6` — milestone version used for Groq compatibility
- **Chat model:** `llama-3.3-70b-versatile` (via Groq) instead of OpenAI's GPT models

---

### 🛠️ Technologies Used

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.4.5 |
| Spring AI BOM | 1.0.0-M6 |
| Groq API | — |
| Whisper Large V3 | — |
| Llama 3.3 70B | — |
| Gradle | 8.10 |

```
budgeting/
├── src/
│   ├── main/
│   │   ├── java/dio/budgeting/
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
│       └── java/dio/budgeting/
├── build.gradle
├── settings.gradle
├── gradlew
└── README.md
```

---

### ▶️ How to Run

#### Prerequisites

- Java 21+
- Gradle 8.10+(https://console.groq.com/) account to get a free API Key

#### Setup

1. Clone the repository:
```bash
git clone https://github.com/your-username/budgeting.git
cd budgeting
```

2. Set the environment variable with your Groq API key:
```bash
export GROQ_API_KEY=your_key_here
```

3. Run the project:
```bash
./gradlew bootRun
```

#### Running Tests

```bash
./gradlew test
```

> Integration tests require the `GROQ_API_KEY` environment variable to be set.

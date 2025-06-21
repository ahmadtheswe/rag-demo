# What application is this?
![image](support-files/A%20Minimal%20RAG%20Implementation.png)

This application is a demo on how to build a minimal RAG (Retrieval-Augmented Generation) system with Spring Boot with support from Sping AI, OpenAI, and PostgreSQL pgvector.

It demonstrates how to set up a simple RAG pipeline that can answer questions based on a set of documents.

## How to run it?
**IMPORTANT !!** This application requires OpenAI API key to run. You can get one from [OpenAI](https://platform.openai.com/signup). You must set your balance to at least to run this application.

You also need to set up a PostgreSQL database with the `pgvector` extension enabled.
I have provided a [docker-compose.yml](support-files/docker-compose.yml) file that sets up a PostgreSQL database with the `pgvector` extension enabled.

To run the application, you need to set the following environment variables in application.yaml file:
```yaml
spring:
  application:
    name: rag-demo
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4.1-mini
    vectorstore:
      pgvector:
        index-type: HNSW
        distance-type: COSINE_DISTANCE
        dimensions: 1536
        max-document-batch-size: 10000
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver

```

Make sure to replace `${OPENAI_API_KEY}` with your actual OpenAI API key.
You can also change the `model` to any other model you want to use.
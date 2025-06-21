## Set
```yml
    environment:
      POSTGRES_DB: <your_db>
      POSTGRES_USER: <your_db_user>
      POSTGRES_PASSWORD: <your_db_password>
```

## Build Container
```bash
docker-compose up -d
```

## Drop Container and Remove Volume
```bash
docker-compose down -v
```

## Access psql
```bash
docker exec -it pgvector-db psql -U <your_db_user> -d <your_db>
```

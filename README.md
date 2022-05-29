# Challenge Back-End

> Este sistema permite o upload de arquivos em formato CSV e XML com informações acerca de transações bancárias.
> Após ler, validar e salvar as transações em banco de dados, é possível identificar as transações consideradas suspeitas, caso o montante mensal ultrapasse determinados limites.


## Tecnologias Utilizadas

1. Spring Boot
2. Thymeleaf
3. PostgreSQL


## Validação 

São ignorados:

1. Arquivos vazios;
2. Transações com informações incompletas;
3. Transações cuja data não coincida com a data da primeira transação listada no respectivo arquivo;
4. Transações de datas importadas anteriormente;

## Segurança

A camada de segurança foi implementada por meio do Spring Security.
Novos usuários podem ser cadastrados e recebem por e-mail uma senha numéria aleatória com 6 dígitos.

## Como testar

O sistema pode ser acessado a partir do seguinte endereço: https://desafio-backend-phsn.herokuapp.com, utilizando os seguintes dados de acesso:

Usuário: `admin@email.com.br` 

Senha: `123999`

Arquivos de teste podem ser baixados nos seguintes links:

1. https://desafio-backend-phsn.herokuapp.com/static/janeiro-2022.csv
2. https://desafio-backend-phsn.herokuapp.com/static/janeiro-2022.xml
3. https://desafio-backend-phsn.herokuapp.com/static/junho-2022.csv
4. https://desafio-backend-phsn.herokuapp.com/static/transacoes-invalidas.csv
5. https://desafio-backend-phsn.herokuapp.com/static/transacoes-suspeitas-maio-2021.csv
6. https://desafio-backend-phsn.herokuapp.com/static/vazio.csv








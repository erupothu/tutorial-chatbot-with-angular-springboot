# tutorial-chatbot-with-angular-springboot

#### install Docker

> sudo apt install docker.io

#### Deploy Database
> sudo docker pull postgres
> mkdir ${HOME}/postgres-data/
> docker run -d --name dev-postgres -e POSTGRES_PASSWORD=Pass2020! -v ${HOME}/postgres-data/:/var/lib/postgresql/data -p 5432:5432 postgres

#### Deploy Java Applicaton
> deploy Chat APP java
> https://github.com/erupothu/tutorial-chatbot-with-angular-springboot.git
> cd chatbot-java
> docker build -f Dockerfile -t chatbot-java .
> docker run --name chatbot-java -p 9093:9093 chatbot-java:latest

#### Deploy Angular Application
> https://github.com/erupothu/tutorial-chatbot-with-angular-springboot.git
> cd mychatbot-angular9
> docker build -f Dockerfile -t mychatbot-angular9 .
> docker run --name mychatbot-angular9 -p 9093:9093 mychatbot-angular9:latest


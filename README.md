# TelegramBot
This is a microservice application that serves as a Telegram bot capable of receiving photos or documents from users, storing them in a database, and providing download links back to the users. The bot also supports user registration with email confirmation.

# Technologies Used
* Java 17
* Spring Boot
* Spring Web
* Spring MVC
* Spring Data JPA
* Apache Maven
* Maven Wrapper
* Docker
* Postgresql database
* RabbitMQ
* LiquiBase

# Requirements
* Java 17 or higher
* Docker

# Installation
To run the Banking System application using Docker and Docker Compose, follow these steps:

* Clone the repository to your local machine.
* Navigate to the project root directory.
The following environment variables need to be set before running the application:

* TELEGRAM_BOT_TOKEN: Your Telegram bot token obtained from BotFather.
* DB_URL: URL of the database (e.g., jdbc:mysql://localhost:3306/telegram_bot_db).
* DB_USERNAME: Database username.
* DB_PASSWORD: Database password.
* salt
* spring.mail.password
* spring.mail.username

# Build and Run the Application
RabbitMQ
* docker image build -t rabbitmq:3.11.0-manager -f rabbitmq .
* docker container run --name my-rabbit --rm -d -p 5672:5672 -p 15672:15672 rabbitmq:3.11.0-manager
* docker volume create rabbitmq_data
* docker container run --name my-rabbit -d -p 5672:5672 -p 15672:15672 -v rabbitmq_data:/var/lib/rabbitmq --restart=unless-stopped
 rabbitmq:3.11.0-manager

Database
* docker image build -t telegram/postgres:15.3 -f postgres_server .
* docker volume create postgres_data
* docker container run --name postgres-server -v postgres_data:/var/lib/telegram/postgres --restart=unless-stopped -d -p 5332:5432
 telegram/postgres:15.3

Run microservices

# RabbitMQ Topics
Small Spring Boot project to test and learn to use [RabbitMQ](https://www.rabbitmq.com)'s Topic, based on [this tutorial](https://www.rabbitmq.com/tutorials/tutorial-five-spring-amqp.html).

## Primary goal
On my job we have some applications that connect themselfs via API. To increase performance, each one has a cache with the previous requests they made.

When some resource is added/updated/deleted from one application, we wanted to let the other applications know and automatically clear/update their caches.

One solution we saw is the application with the updated resource notify the others via RabbitMQ, but without manually creating queues and sending messages to each client application.
Topic seemed to make sense, and I wanted to try to understand.

Also, I wanted to create an Abstract Class to listen the queues, instead using the `@RabbitListener`.
This way I can log or apply logic to every received message before the actual method that wanted to receive that message.

## How it works
This project has 2 applications, each one as an independent module:
* `sender` - Web application with Basic CRUD that sends messages to Topic when a "Domain" is created or deleted;
* `receiver` - Application that creates and binds a queue to the Topic, making a request to Sender to update its own cache when it is notified via Topic.

Domain here is a basic object with UUID and a name. Could represent a Person, Product, Client, Car, Animal, Order, whatever.

Starting `RabbitMQTopicsSenderApplication` and accessing [localhost:8080](http://localhost:8080) will allow you to view, create and delete "Domains".
When you create or delete a Domain, Sender will send a message to Topic `fernando.alvarez.server.cache` on your local RabbitMQ, notifiying what Domain you added or deleted.

Each `RabbitMQTopicsReceiverApplication` you start will create one Queue and bind it to Topic `fernando.alvarez.server.cache`.
Each Receiver will receive the same message (Domain update notification) from the Topic, and will make a request to Sender API ([localhost:8080/api/domains](http://localhost:8080/api/domains))
to fetch all Domains and cache them.

## How to run
1. Clone this repository and open it on your favorite IDE (IntelliJ or Eclipse);
1. Run Maven to import all dependencies;
1. Make sure you are running [RabbitMQ](https://www.rabbitmq.com) on your machine. The applications will attempt to connect to the default port (`5672`), user (`guest`) and password (`guest`), but you can change them in `application.yml` of both applications;
1. Create the Exchange `fernando.alvarez.server.cache` with the default settings;
1. Start one `RabbitMQTopicsSenderApplication` and one or many `RabbitMQTopicsReceiverApplication`;
1. Access [localhost:8080](http://localhost:8080) and create a Domain;
1. Check the Receivers's Console if they receive the message and update their cache.

The tutorial I followed automatically creates the Topic/Exchange and Queues, but I couldn't create the Topic/Exchange automatically here. For my goal this won't be a problem, but would be nice.
At least, the Receiver creates and deletes the queues automatically.

## Other
* Rabbit and Carrot ASCII Art got from [asciiart.eu](https://www.asciiart.eu/food-and-drinks/other);
* Wonderful tutorial on official RabbitMQ site: https://www.rabbitmq.com/tutorials/tutorial-five-spring-amqp.html;
* Nice Stack Overflow question about how to create a listener at runtime without using `@RabbitListener`: https://stackoverflow.com/questions/47331469/easiest-way-to-construct-rabbitlistener-at-runtime/47332278;
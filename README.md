
# Lifecoach App

### Welcome to LIFECOACH APP!

* This simple console application is dedicated to one individual user will help you to control your everyday physical activity and hydration (number of steps and number of litres of water that you have drunk). 
Measure and input the number of steps per day and how many litres of water you drink and see if it is enough for balanced healthy life. 
* It is recommended to make at least 3000 steps every day and to drink at least 3 litres of water. These goals are defined by us by default. But you are welcome to change the goals according to your wish.
* Reach your goals and receive a nice motivational picture related to sports from Instagram API. If you don't reach the goal we will send you motivational quote that will help you not to give up on the way to healthier lifestyle.


## How does the App work?

This console application can be used only by one predefined user. 
When the application is run there is a introduction print for the user and the MENU. In order to proceed the user should type the number of operation that he wants to execute. There are the available operations:

 MENU'
     
* 1 - Print your current information
* 2 - Set the number of steps you've made today and check if the goal is hit
* 3 - Set the number of litres of water that you've drunk today and check if the goal is hit
* 4 - Set the goal - number of steps you should make, at least 3000 recommended
* 5 - Set the goal - how many litres of water you should drink, at least 3 litres recommended
* 6 - Print all the current goals 
* 0 - Exit

Depending on the result there will be motivational message print for the user:

- If the goal is reached there will be a link to the motivational picture from Instagram API;
- If the goal is not reached there will be a motivational phrase printed in the console from Quoteondesign API. Though quotes about design are not connected to the health industry, this API is used here as an example of related quote service due to its free usage without any limitations.

##To run the app

     ant start 

## Architecture of the application

There are 5 layers of the architecture + console user interface:

**Local Database Service:** this is a SOAP service. It communicates with the local database directly.

**Adapter Service:** RESTful web service. This service interacts with the external APIs, such as Instagram API and Quoteondesign API. It retrieves pictures and motivational phrases from these APIs. And sends them to the next layer of web service.

**Storage Service:** RESTful web service. This service gets information from the Local DB and Adapter layers and sends it to the other layers. It can be considered as a central middleman service of the application.

**Business Logic Service:** RESTful web service. This layer receives requests from the UI layer and it gets data from the storage layer and processes it to send results back. It is responsible for checking if the goals are hit by the user and for getting the details from the storage (database).

**Process Centric Service:** RESTful web service. This service serves all requests coming directly from a user (from application interface). This is the gateway to all other modules/services in an application context. This layer is doing nothing but redirecting a request to a proper underlying service or a set of services. This is an orchestration layer of the application.

![](https://github.com/IntrosdeFinalProject/introsde-client/blob/master/ApplicationArchitectureScheme.png)

## Technologies used in the project


* The application was developed in Java.

* IntelliJ IDEA 15 was used as an IDE for the project.

* Database SQLite was used to store data at the local database level.

* GitHub was used a a code hosting, for this project the separate organisation was created and can be reached by this link https://github.com/IntrosdeFinalProject 

* Heroku: all five layers (services) of the application are deployed on Heroku which is a PAAS product. Free account has been used. 

* Wiki page was used to describe the APIs documentation.

* JSON format was used for RESTful services due to its readability and lightness. 

* Apache Ant was used to build the Java application.

* Apache Ivy - dependency manager


## How can the application be improved?

The current version of the application was made very simple in order to meet the set of requirements for the project (individual project work). However there are a lot of ways to improve the application, like:

1) More advanced user interface: instead of console user interface a web application or a mobile application can be developed to provide better user experience. Or Telegram/ Slack bots can be used to provide better interaction experience between the user and application. 

2) More external APIs in use: more various external APIs can be used in the application to inform the user, for instance a service that will crawl the web and broadcast news and articles about health issues, healthy nutrition and sports activities. Applications that gather sports data from smart wearable devices (like fitness bracelets) or application that register physical activity data can be also useful and can be used as an external database for application. It will provide more automatic data insertion, so the user will not have to insert the data by himself like it is in console application at the current version.

3) Usage of notification services: email service or sms service APIs can be used in order to notify the user about the goals or recommend some other health related activities.

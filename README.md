# Easy Travel


Easy Travel is an Android application written in Kotlin using the MVVM software architecture. The app is designed to help travelers organize their trip details, whether they are traveling in Israel or around the world. The goal of the project is to create a user-friendly and convenient platform that concentrates all the information needed for a trip, thus providing peace of mind to travelers.

## Architecture
This App based on MVVM software architectural pattern that has three main parts that run it, each part with its own designated responsibilities. </br>
The Model interacts with ***. </br>
Next, the ViewModels process the changed data and notifies the Views about the changed data. </br>
The View then react to the changed data accordingly and shows them.
* Model: responsible for **.
* View:  responsible for showing the**.
* View Model: responsible to connect between the view and the model.

## Language and Architecture
Easy Travel is written in Kotlin and based on the MVVM software architecture. The use of the MVVM architecture provides a clear separation of concerns between the user interface, the presentation logic, and the data layer, making the app easier to maintain and modify.

## Purpose
The purpose of the Easy Travel app is to create a more beneficial travel experience for anyone booking a flight or vacation. By providing a platform that is easy to use and contains all the necessary information in one place, travelers can have a better vacation experience.

## Who is it for?
Easy Travel is for travelers in Israel and around the world who want to organize all the details of their trip. It is suitable for anyone booking a flight or vacation who wants a more convenient and accessible service.

## What does it contain?
Easy Travel contains the following features:

*User login and new user registration
*Personal management for each trip
*Tracking of data before and during the trip, such as flight details, hotel details, tasks, etc.
*Concentration of travel expenses
*Concentration of travel documents

## Screens
The Easy Travel app contains the following main screens:

1.LoginActivity - the login screen that allows users to enter a username and password or register a new user.
2.HomeActivity - a list of the user's previous trips and the option to create a new trip.
3.MainAddActivity - the screen where users can give a name and date for their trip. It contains four buttons - add flights, add hotels, add tasks, and save travel - each of which leads to a suitable activity.
4.AddFlightActivity - contains four fragments - outbound flight details, landing, adding a flight ticket, and saving. Users can add another flight from this screen.
5.AddHotelActivity - contains three fragments - hotel details, adding a picture of the hotel details, and saving the hotel. Users can add another hotel from this screen.
6.AddTaskActivity - contains a list of tasks to do, adding tasks, and an option to mark completed tasks.
7.AllFlightsActivity - displays all the details of the flights the user has entered. Users can view, delete, and re-add flights from this screen.

## Database
Easy Travel uses Firebase for saving user data and storing all trip information.

## Conclusion
Easy Travel is a user-friendly and convenient app that helps travelers organize their trip details, providing peace of mind and a better vacation experience. The use of Kotlin and the MVVM architecture makes the app easy to maintain and modify, and the use of Firebase for the database provides a reliable and scalable solution.

## Installation
1. Use Android Studio version 4.2.1 and above.  </br>

## Running the Application
1. open

## UML


## Video

## Developer
* Noa Miara Levi

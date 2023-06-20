# Easy Travel 🌍

Easy Travel is an Android application written in Kotlin using the MVVM software architecture.

The app is designed to help travelers organize their trip details, whether they are traveling in Israel or around the world. The project aims to create a user-friendly and convenient platform that concentrates all the information needed for a trip, thus providing peace of mind to travelers.

## Purpose 🎯

The purpose of the Easy Travel app is to create a more beneficial travel experience for anyone booking a flight or vacation. By providing a platform that is easy to use and contains all the necessary information in one place, travelers can have a better vacation experience.

## Target Audience 🧳

Easy Travel is for travelers in Israel and around the world who want to organize all the details of their trip. It is suitable for anyone booking a flight or vacation who wants a more convenient and accessible service.

## Features ✨

Easy Travel contains the following features:

- User login and new user registration ✅
- Personal management for each trip 📋
- Tracking of data before and during the trip, such as flight details, hotel details, tasks, etc. 🗂️
- Consolidation of travel expenses 💸
- Consolidation of travel documents 📑

## Architecture 🏛️

This App is based on the MVVM (Model-View-ViewModel) software architectural pattern. It consists of three main parts, each with its own designated responsibilities.

- **Model**: The model is responsible for handling the data and business logic of the application.
- **View**: The view is responsible for presenting data to the user through the user interface.
- **ViewModel**: The ViewModel acts as a mediator between the view and the model. It processes the data from the model and provides it to the view. It also contains the business logic and handles data in a way that they are preserved across configuration changes like screen rotation.

The use of the MVVM architecture provides a clear separation of concerns between the user interface, the presentation logic, and the data layer, making the app easier to maintain and modify.

## Language and Architecture 💻

Easy Travel is written in Kotlin and based on the MVVM software architecture. The use of Kotlin as the programming language provides concise and expressive code, improving productivity and reducing boilerplate code. The app also uses modern Android architecture components like Navigation libraries, View binding, and LiveData.

## Screens 📱

The Easy Travel app contains the following main screens:

1. **LoginActivity**: The login screen that allows users to enter a username and password or register a new user.
2. **HomeActivity**: A list of the user's previous trips and the option to create a new trip.
3. **MainAddActivity**: The screen where users can give a name and date for their trip. It contains buttons to add flights, add hotels, add tasks, and save the travel.
4. **AddFlightActivity**: Contains fragments for outbound flight details, landing, adding a flight ticket, and saving. Users can add multiple flights from this screen.
5. **AddHotelActivity**: Contains fragments for hotel details, adding a picture of the hotel, and saving the hotel. Users can add multiple hotels from this screen.
6. **AddTaskActivity**: Contains a list of tasks to do, adding tasks, and an option to mark completed tasks.
7. **AllFlightsActivity**: Displays all the details of the flights the user has entered. Users can view, delete, and re-add flights from this screen.

## Database 🗄️

Easy Travel uses Firebase for saving user data and storing all trip information. Firebase provides a reliable and scalable solution for data storage and synchronization.

## Installation 🚀

To run the Easy Travel app, follow these steps:
1. Use Android Studio version 4.2.1 and above.
2. Clone the Easy Travel repository from GitHub.
3. Open the project in Android Studio.
4. Build and run the app on an Android device or emulator.
   
## UML


## Video

## Developer

## Conclusion

Easy Travel is a user-friendly and convenient app that helps travelers organize their trip details, providing peace of mind and a better vacation experience. The use of Kotlin and the MVVM architecture makes the app easy to maintain and modify, and the integration with Firebase ensures reliable data storage and synchronization.




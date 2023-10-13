# TomTom Nearby
A simple Android app that obtains nearby places data from TomTom public Nearby Search API and displays them to the user. For a similar app with FourSquare Place Search API, see my [FourSquareNearby](https://github.com/Sepehr1812/FourSquareNearby) repository.

## Features
* **Nearby Places**: The user can see a list of places within a radius of 5 km of the current location.
* **Lazy Loading (Endless Places List)**: At the beginning, data of only the 10 nearest places are obtained from the server, and more places data will be received by scrolling to the end of the list (the app provides data pagination).
* **Responding to Location Change**: If the user's location is changed by 100 meters, new nearby places will be displayed to the user based on the new location.
* **Caching**: On relaunching the app, if the user's location has not been changed more than 100 meters from the last saved location, the app does not call the API again and shows the latest obtained places data (cached locally in the database) to the user.
* **Offline-first**: If no Internet connection is available, the app displays the cached data to the user.
* **Place Details**: By clicking on each item in the list, the user can see details like the title and brief address of the place, place categories, distance from the user, and the exact location of the place on the map.

## API Documentation
The Nearby Search API of TomTom is used in this app for obtaining data on nearby places. You can find the complete API documentation [here](https://developer.tomtom.com/search-api/documentation/search-service/nearby-search).

## Architecture and Technologies
In this project, I benefited from several Android concepts and libraries, including:
- Coroutines
- Room
- Retrofit
- Hilt
- Google Map API
- Location Services
- Pagination
- Kotlin DSL

Also, I implemented this project with MVVM architecture utilizing clean architecture principles. The project is modulized based on the different layers of the app in three modules: data, domain, and app.

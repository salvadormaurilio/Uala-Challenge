# 🎬 Cities Challenge

This is an Android project that displays a list of cities retrieved from this [JSON source](https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json).  
The app includes the following features:

- Alphabetically sorted list of cities by **city name** and **country**.
- City search functionality.
- Mark cities as favorites and filter by favorites.
- City detail screen.
- City map view.

> **Note:**  
> Class and variable naming in the code may refer to "Country" instead of "City" due to an initial naming mistake.
  
---

## 📌 Technologies & Concepts Used

### 🛠 Technologies
- **Kotlin**
- **Coroutines** (including Flow)
- **Hilt** for dependency injection
- **Jetpack Compose** for UI
- **Retrofit** for networking
- **Rooom** to store data in cache

### 🧪 Testing
- **JUnit**
- **Mockito**
- **Hamcrest**
- **Coroutines Test**

### 💡 Architecture & Patterns
- **Clean Architecture**
- **Clean Code**
- **SOLID principles**
- **MVVM**
- **Repository pattern**

---

## 🚀 Search Optimization Criteria[](url)

The following strategies were implemented to optimize the city search:

1. **Initial load optimization**:  
   On the first app launch, the list of cities is sorted alphabetically and stored locally using Room. This avoids fetching and processing the JSON every time the app starts.

2. **In-memory caching**:  
   Once cities are loaded from the local database, they are kept in memory in the `CountriesRepositoryImpl` class. This reduces database access on each search and improves performance.

3. **Binary search for filtering**:  
   When a user enters a query, a binary search is used to find the first and last matching indices. A sublist is then created based on these indices, significantly reducing the search complexity even with thousands of cities.

4. **Favorites filtering**:  
   If the user enables the favorites filter, the resulting sublist is further filtered.  
   All this logic is encapsulated in the extension function:  `List<Country>.filterCountries(query: String, filterFavorites: Boolean): List<Country>`


## 🧪 UI Tests


## 🧪 How to Run & Test the Project

To run the project, make sure to add the following entry to your `local.properties` file:

```properties
MAPS_API_KEY = AIzaSyBpUVcthDLDdubUlpGQL54Xacn0CGINU-Y
```


Also You can build app or run the Unit Tests and Integration Tests with:

```
./gradlew testDebugUnitTest            # Run unit tests
./gradlew connectedDebugAndroidTest    # Run instrumentation tests
```





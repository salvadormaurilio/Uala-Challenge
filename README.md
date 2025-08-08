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





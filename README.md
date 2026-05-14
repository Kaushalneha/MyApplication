# Project Overview


The application serves as a simple yet effective tool for checking live weather conditions. Users input a city name (e.g., "London" or "New Delhi"), and the app fetches the current temperature in Celsius, displaying it instantly on the screen.


# Key Features


1. Real-time Data: Integration with the OpenWeatherMap REST API to ensure the weather data is accurate and up-to-date.


2. Dynamic Network Requests: Uses the Volley Library for asynchronous networking, ensuring the app remains responsive while fetching data.


3. Input Validation: Handles empty inputs and provides feedback to the user via Toasts.


4. Robust Error Handling: Includes specific error messages for scenarios like invalid city names (404), unauthorized API keys (401), or lack of internet connectivity.


5. Custom UI: Features a stylized layout with a background image, custom text styling, and a clean interface.

 
 # Tech Stack


> Language: Java


> UI Framework: Android XML (using LinearLayout)


> Networking: Volley Library (for HTTP requests)


> Data Format: JSON (parsed using org.json objects)


> API: OpenWeatherMap API


# Technical Workflow


1. User Input: The user enters a city name in the cityEditText.


2. Request Construction: Upon clicking the "Get Weather" button, the app constructs a URL containing the encoded city name and a unique API Key.

  
3. Network Call: A JsonObjectRequest is sent via Volley's RequestQueue.

  
4. JSON Parsing: On a successful response, the app drills into the main JSON object to extract the temp value.


5. UI Update: The temperature is formatted and displayed in the resultTextView.


 # Project Structure


> MainActivity.java: Contains the logic for button clicks, network requests, and API response handling.


> activity_main.xml: Defines the visual components including the background, input fields, and result display.


> AndroidManifest.xml: Configures the app and declares the required INTERNET permission.


> strings.xml: Stores reusable string resources for formatting and error messages.

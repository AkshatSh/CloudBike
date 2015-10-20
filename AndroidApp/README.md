# CloudBike
## Android App Sub-directory

#### Description:
  Android application primarily used to communicate with the BlueTooth Stream from the CloudBike Arduino mount. Relays that data to a webserver used to communicate with the website.

#### To-Do:
1. Setup a basic application with Play/Pause button.
2. Add a listener for the Bluetooth Stream
  * Add login functionality tied to Facebook.
  * Grab data from the bluetooth stream
  * Set a time-interval for the stream.
  * Temporarily store data.
3. Add a sender (post-request) to the application.
  * Package data from Arduino
  * Add GPS and/or speed data to the package.
  * Send the package to webserver.
4. Make Pause/Play functional with those two streams.
  * Pause quits the listener and sends last package before stopping.
  * Play resumes the listener, resets time-interval and sends last package on press.
5. Add extra features if time allows.
  * Add a time-interval slider that the user can adjust.
  * Calculate calories.
  * Heartrate data getting wearable tech, setup with a smartwatch.
  * Clean up UI.

Dubhacks Fall 15

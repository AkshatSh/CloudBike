# Cloudbike Android Repo

## Description:
  The Cloudbike Android application is capable of pulling data from our custom Arduino Tachometer or a Microsoft Band while the user is biking. The user can start a trip, during which the phone creates a JSON to store all of their ride data. At the conclusion of the trip, the JSON is sent to Cloudbike's MongoDB server for processing.
  
## Navigating the Directory
  The current working application is stored inside CBDubHacks. The layout follows the standard AndroidSDK layout for a blank android project. The CBRelease folder will be populated once a well-documented, cleaned up prototype is finished. As a disclaimer, since most of this code was written during a Hackathon, the internal structure of the code may appear a bit haphazard. That is the reason a more polished version is in the works.
  
## Key Files
* CBDubHacks/app/src/main/AndroidManifest.xml - Essentially a blueprint of the design.
* CBDubHacks/app/src/main/java/MainActivity.java - Java class that defines the behavior onload.
* CBDubHacks/app/src/main/java/TripManager.java - Java class that handles the rides that the user initiates on the app.


#### Authored By: Robby Mitchell

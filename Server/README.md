# CloudBike

##Node.js server backend

###Description

Node.js server that handels analysis of the data and interactions from the website. Store data in a MongoDB databse from MongoLabwebsite and processes the data that the android device sends.

###Routes:

    GET /api/routes
Returns all the route ids stored in the database. 
Example Response: 


```json
{
  "userRoutes": [
    {
      "_id": "56242dd6b6ca65832a1edb77",
      "id": "56242dd5b6ca65832a1edb76",
      "time": "Sun Oct 18 16:39:49 PDT 2015"
    },
    {
      "_id": "56242ecdb6ca65832a1edb79",
      "id": "56242ecdb6ca65832a1edb78",
      "time": "Sun Oct 18 16:43:38 PDT 2015"
    }, ... 
  ]
}
```
id: the route id 
time: when the route began

    GET /api/route/{route_id}
Returns the route information for a given route id. 

    POST /api/route
Takes a JSON object parameter and computes and stores the proper data in the database

###Host:

The Backend Server written in node.js is running on azure, while the mognodb database is running on AWS, the Virtual Machines for bit coin mining are also on azure.
### TODO
0. Bare Minimum Requirements
   * Handle POST requests from android app and store in MonogoDB
   * Relay database entries to web client upon get requests
   * Decide structure of HTTP protocols to server 
1. Handel Interactions between website and server
   * Allow website to query a user based on facebook login credentials 
   * Allow website to query individual runs along with analytical data provided
2. Handel Interactions between mobile client and server
   * Parse and store all data in database with given user ids
3. Analytics
   * filter bad data, only store valid data
   * compute averages for each field 
   * compute calories burnt
   * compute where gear shifts are necessary


Dubhacks Fall 15

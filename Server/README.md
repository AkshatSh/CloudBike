# CloudBike

##Node.js server backend

###Description

Node.js server that handels analysis of the data and interactions from the website, and processes the data sent from the android device. Takes android data and stores the data in a MongoDB databse.

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
    }
  ]
}
```
* id: the route id 
* time: when the route began

        GET /api/route/{route_id}
    
    
Returns the route information for a given route id. 
Example Response:
```json
{ "_id": "56242dd5b6ca65832a1edb76",
  "name": null,
  "startTime": "Sun Oct 18 16:39:49 PDT 2015",
  "endTime": "Sun Oct 18 16:40:04 PDT 2015",
  "timeElapsed": 14656,
  "data": 
   [ { "freeTime": 1445211589379,
       "lat": 47.6558788,
       "speed": 34.2,
       "lng": -122.3093372 },
     { "freeTime": 1445211592858,
       "lat": 47.6559044,
       "speed": 12.2,
       "lng": -122.3093214 },
     { "freeTime": 1445211594684,
       "lat": 47.6559044,
       "speed": 45.2,
       "lng": -122.3093214 },
     { "freeTime": 1445211599714,
       "lat": 47.6559044,
       "speed": 35,
       "lng": -122.3093214 },
     { "freeTime": 1445211602804,
       "lat": 47.6559019,
       "speed": 32.2,
       "lng": -122.3093261 } ],
  "rpmData": [{"rpm": 34.3231231, "time":5555555, "hashes":97532423}],
  "totalHashes": 1392320 }
```
* name: (optional) a nick name for the route
* startTime: the time at which the route started
* endTime: the time at which the route ended
* timeElapsed: how long the route was (miliseconds) 
* data: Array of points
    * freeTime: milisecond for the data point
    * lat: latitude
    * lng: longitude
    * speed: the speed in feet / second
* rpmData: Array of rpm data points
    * rpm: the rotations per minute 
    * time: time of the data point in miliseconds
    * hashes: the number of hashes computed at that data point
* totalHashes: the number of hashes computed for that bike route
    
        POST /api/route
Takes a JSON object parameter and computes and stores the proper data in the database
Example Parameter: 
```json
{ 
  "name": null,
  "startTime": "Sun Oct 18 16:39:49 PDT 2015",
  "endTime": "Sun Oct 18 16:40:04 PDT 2015",
  "timeElapsed": 14656,
  "data": 
   [ { "freeTime": 1445211589379,
       "lat": 47.6558788,
       "speed": 34.2,
       "lng": -122.3093372 },
     { "freeTime": 1445211592858,
       "lat": 47.6559044,
       "speed": 12.2,
       "lng": -122.3093214 },
     { "freeTime": 1445211594684,
       "lat": 47.6559044,
       "speed": 45.2,
       "lng": -122.3093214 },
     { "freeTime": 1445211599714,
       "lat": 47.6559044,
       "speed": 35,
       "lng": -122.3093214 },
     { "freeTime": 1445211602804,
       "lat": 47.6559019,
       "speed": 32.2,
       "lng": -122.3093261 } ],
  "rpmData": [0, 1, 1, 1, 1, 0],
  "rpmTimeStamp": [0, 552, 1231, 3424, 3789, 23123]
  }
```
* all same as above
* two additional fields rpmData and rpmTime
    * rpmData: array of 0s and 1s, 0 means IR sensor was blocked, 1 means IR was clear (used to compute RPM data in response) 
    * rpmTimeStamp: indicates when each 0 or 1 was captured
###Host:

The Backend Server written in node.js is running on azure, while the mognodb database is running on AWS, the Virtual Machines for bit coin mining are also on azure.

Dubhacks Fall 15

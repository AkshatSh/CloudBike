# CloudBike

## Node.js server backend

### Description 
Node.js server that handels analysis of the data and interactions from the website. 
Store data in a MongoDB databse from MongoLab

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

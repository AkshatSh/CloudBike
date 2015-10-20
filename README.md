# CloudBike

Analyze your bike trips to improve your performance while mining bitcoin. 

###What Cloudbike does: 
Cloudbike is a full stack data analytics platform to show users insight on their bike routes, while generating bitcoin based on the intensitiy of their excersize. 

Components included an arduino board hooked onto the bike giving data about it such as RPM, Microsoft Band giving Heart Rate, Android giving geolocation along with speed, collecting all the data, and sending everything to the web server. The web server process and store the data, for the web app to pull from. The web app visualizes all the data collected. 

###Live Demo:
http://cloudbike.azurewebsites.net/Website/

###DevPost:
http://devpost.com/software/cloudbike#updates 


###Inspiration: 
As students, we spend a lot of time commuting across UW's beautiful campus. Unfortunately, between balancing homework, schoolwork, midterms, finals, and even work, it's nearly impossible to see at a detailed level: What exactly are we doing during our commuting time, and more interestingly, how we could still be involved with great social causes, without any expense of our very valuable time.

###How We Built It:
* Starting from the ground up, we immediately began building the hardware components for the bike -- 3D printing custom parts to fit with the bike's frame and waterproofing our Arduino hooked with our own hand-designed Infrared-Calibrated Tachometer to efficiently, and very accurately read RPM performance.
* The next step involved developing our Android app, and integrating all of our hardware (Arduino, Tachometer, Microsoft Band, Bluetooth), and creating secure and reliable communications between all of our components.
* Next comes the Backend Web Technologies. After gathering our data, we began setting up the services to store the data in our remote databases. Via remote Microsoft Azure Web Services and Virtual Machines, we were able to clean our data, conduct insightful data analysis and begin the incredibly CPU intensive hashing process in cloud mining Bitcoins!
* Lastly, the web app requests data from the Backend Web Server to get all the data from the test run and displays all of it so that the user can visualize different statistics about their bike trips. 

### Awards: 
4th Place Finalist at Dubhacks Fall 2015


### Contributors:

* Akshat Shrivastava (Backend Developer) 

* Robby Mitchell (Android Developer) 

* Cedric Ith (Front End Developer) 

* Trevor Hedges (Hardware Engineer) 

* Joseph Zhong (Full Stack Dev) 

Dubhacks Fall 15

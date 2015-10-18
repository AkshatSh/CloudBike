// All required utils for the app 
var express    = require('express');
var bodyParser = require('body-parser');
var app        = express();
var mongo = require('mongodb');
var Server = mongo.Server;
var Db = mongo.Db;
var uriUtil = require('mongodb-uri');

// connect with server
var server = new Server('ds041154.mongolab.com', 41154, {auto_reconnect : true});
var db = new Db('cloudbike', server);


db.open(function(err, client) {
    client.authenticate('test', 'passw0rd!', function(err, success) {

        if (err) {
        	console.log("error " + err);
        } else {
        	console.log("success " + success);
        }

    });
});

var options = { server: { 
				socketOptions: { 
					keepAlive: 1, 
					connectTimeoutMS: 30000 
				} }, 

                replset: { 
                	socketOptions: { 
                		eepAlive: 1, 
                		connectTimeoutMS : 30000 
                	} 
                } 
            };       


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port     = process.env.PORT || 8080; // set our port
var mongoose   = require('mongoose');
var newUri = 'mongodb://test:passw0rd!@ds041154.mongolab.com:41154/cloudbike';
var mongooseUri = uriUtil.formatMongoose(newUri);
var oldUri = 'mongodb://cbtest:password@ds048368.mongolab.com:48368/cloudbikedb';

//var cbBikeEntry = require('./app/models/cbBikeEntry');
var router = express.Router();

// middleware to use for all requests
router.use(function(req, res, next) {
	// do logging
	console.log('API Being Accessed');
	next();
});

router.get('/', function(req, res){
	res.json({message: "working"});
});

router.route('/route/:route_id')
	.get(function(req, res){
		//console.log(req);
		console.log(req.params.route_id);
		var documents = db.collection('documents');
		var TOPitems = [];
		documents.find().toArray(function(err, items) {
			console.log("items here");
			console.log(items);
			TOPitems = items;
			var target = null;
			for (var i = 0; i < TOPitems.length; i++) {
				if (TOPitems[i]._id == req.params.route_id) {
					target = TOPitems[i];
				}
			}
			//{_id : req.params.route_id});
			console.log(target);

			if (!target) {
				res.json({error: "no data found"});
			} else {

				res.json(target);
			}

			res.send();
		});
		/*cbBikeEntry.findById(req.params.route_id, function (err, route) {
			if (err) {
				res.send(err);
			}
			res.json(route);
		});*/
	})

router.route('/routes')
	.get(function(req, res) {
		var routes = db.collection('routes');
		routes.find().toArray(function(err, items) {
			var allroutes = items;
			var resultData = {
				userRoutes : allroutes
			};

			res.json(resultData);
			res.send();
		})
	});


router.route('/route')
	.post(function(req, res) {
		//var tempcbBikeEntry = new cbBikeEntry();
		//tempcbBikeEntry.name = req.body.name;
		//console.log(tempcbBikeEntry);
		console.log("body start");
		console.log(req.body);
		console.log("body end");
		
		var recievedData = {
			// string 
			name : req.body.name,

			// JAVA date object
			startTime: req.body.startTime,

			// JAVA data object
			endTime: req.body.endTime,

			// in milliseconds 
			timeElapsed: req.body.timeElapsed,

			// Array of JSON objects with data such as 
			// lat, long, rpm, heart rate and so forth 
			data: req.body.bikeData
		};

		insertDocument(db, 'documents', req, res, {recievedData}, function (err, data) {
			storeMenuData(db, 'routes', req, res, {data}, function () {});
		});


		//insertDocument(db , 'routes', req, res, {}, function(err, temp) {res.send();});

	})
app.use('/api', router);

function storeMenuData(_db, collection, _req, _res, data, callback) {
	console.log("test");
	console.log(data.data);

	var customData = {
		id: data.data._id, 
		time: data.data.recievedData.startTime
	};

	_db.collection(collection).insert(customData, function(err, result) {
		if (err) {
			console.log("err: " + err);
		}
		console.log('result store menu: ' + data._id);
		_res.send();
	});
}

function insertDocument(_db, collection, _req, _res, data, callback) {
	console.log(data);
    _db.collection(collection).insert(data,
        function(err, result) {
            if(err)
            {
                console.log('err: ' + err);
            }
            console.log('result: ' + data._id);
            //assert.equal(err, null);
            callback(null, data);
        });
};

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Server on: ' + port);
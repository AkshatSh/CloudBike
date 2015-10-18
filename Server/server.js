var express    = require('express');
var bodyParser = require('body-parser');
var app        = express();
var mongo = require('mongodb');
var Server = mongo.Server;
var Db = mongo.Db;
var uriUtil = require('mongodb-uri');

var server = new Server('ds041154.mongolab.com', 41154, {auto_reconnect : true});
var db = new Db('cloudbike', server);


db.open(function(err, client) {
    client.authenticate('test', 'passw0rd!', function(err, success) {
        // Do Something ...
        if (err) {
        	console.log("error " + err);
        } else {
        	console.log("success " + success);
        }
    });
});

var options = { server: { socketOptions: { keepAlive: 1, connectTimeoutMS: 30000 } }, 
                replset: { socketOptions: { keepAlive: 1, connectTimeoutMS : 30000 } } };       


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
		var resultABC = documents.find({_id: '562330a11f8dd3d912875ab6'}).toArray(function(err, items) {console.log(items)});//{_id : req.params.route_id});
		//console.log(result);
		console.log(resultABC);
		//res.json(resultABC);
		res.send();
		/*cbBikeEntry.findById(req.params.route_id, function (err, route) {
			if (err) {
				res.send(err);
			}
			res.json(route);
		});*/
	})

router.route('/route')
	.post(function(req, res) {
		//var tempcbBikeEntry = new cbBikeEntry();
		//tempcbBikeEntry.name = req.body.name;
		//onsole.log(tempcbBikeEntry);
		var recievedData = {
			name : req.body.name,
			time: req.body.time,
			timeElapsed: req.body.timeElapsed,
			data: req.body.bikeData
		}
		insertDocument(db, req, res, {recievedData}, function (err, temp) {res.send()});

	})
app.use('/api', router);


function insertDocument(_db, _req, _res, data, callback) {
	console.log(data);
    _db.collection('documents').insert(data,
        function(err, result) {
            if(err)
            {
                console.log('err: ' + err);
            }
            console.log('result: ' + result);
            //assert.equal(err, null);
            callback(null, result);
        });
};

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Server on: ' + port);
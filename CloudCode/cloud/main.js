
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

// Status code 0 : request made ; 1 : process started ; 2 : process completed successfully
// Function to set status code to 1 and assign a tutor ; return no tutor availble in ur locality in case of no tutor
// After tutor is assigned , he must be sent a push notification consisting of name of student , subject and topic along with his phone number
// If the tutor accepts the request , a notification must be sent to the student with details of the tutor

Parse.Cloud.define("maestroRequest" , function(request , response) {

	var User = Parse.Object.extend("User");
	var RequestLog = Parse.Object.extend("RequestLog");
	var Installation = Parse.Object.extend("Installation");
	var studentDetails = new Parse.Query(User);
	var maestroDetails = new Parse.Query(User);
	var pushQuery = new Parse.Query(Installation);
	var maestroUsername;

	ObjectId = request.params.ObjectId;
	Subject  = request.params.Subject;
	Topic    = request.params.Topic;
	Place    = request.params.Place;

	studentDetails.equalTo("objectId", ObjectId);
	studentDetails.find({
		success:function(result){
			if(result.length!=1){
				response.success("something is fishy");
			}
			else{
				info = result[0];
				email        = info.get("username");
				studentName  = info.get("Name");
				studentClass = info.get("studentClass");
				mobile       = info.get("Mobile");
			}
		},
		error:function(){
			response.error("Oops !! Could not connect with server");
		}
	});


	maestroDetails.equalTo("isMaestro", true).equalTo("Subject", Subject).equalTo("Location", Place);
	maestroDetails.find({
		success:function(result){
			if(result.length==0){
				response.success("Sorry !! None of the maestros satisfy your conditions");
			}
			else{
				maestroUsername = result[0].get("username").toString();
				pushQuery.equalTo("username", maestroUsername);
				Parse.Push.send({
					where: pushQuery,
					data: {
						alert    : "We have a job for you.",
						action   : "in.co.nebulax.maestro.UPDATE_STATUS",
						name     : studentName.toString(),
						email    : email.toString(),
						topic    : Topic.toString() + ", Class-" + studentClass.toString(),
						city     : Place.toString(),
						mobile   : mobile.toString(),
					}
				}, {
					success: function() {
						// Push was successful
						response.success("Push notification sent.");
					},
					error: function(error) {
						// Handle error
						response.error("Oops !! Could not make the push.")
					}
				});
			}
		},
		error:function(){
			response.error("Oops !! Could not connect with server");
		}
	});
});
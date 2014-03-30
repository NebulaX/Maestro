
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

	var query = new Parse.Query("User");
	query.equalTo("objectId" , request.params.objectId);
	query.find({
		success:function(result){
			if(result.length==0)
				response.success("Not found");
			else
				response.success("Found !!");
		},
		error:function(){
			response.error("Oops !!");
		}
	});
});

/*var query = new Parse.Query(Parse.Installation);
query.equalTo('gender', 'male');

Parse.Push.send({
  where: query, // Set our Installation query
  data: {
    alert: "A test notification from Parse!"
  }
});*/
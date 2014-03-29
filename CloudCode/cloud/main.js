
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

// Status code 0 : request made ; 1 : process started ; 2 : process completed successfully
// Function to set status code to 1 and assign a tutor ; return no tutor availble in ur locality in case of no tutor
// After tutor is assigned , he must be sent a push notification consisting of name of student , subject and topic along with his phone number
// If the tutor accepts the request , a notification must be sent to the student with details of the tutor
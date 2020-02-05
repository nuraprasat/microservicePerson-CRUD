# microService-crud
crud Person Service


To run a project, First clone it
git clone https://github.com/nuraprasat/productManagement.git
After cloning just give the below command to run the service inside the project
java -jar target/microServicePersonCRUD-0.0.1-SNAPSHOT.jar

UI Screen - 
To start the UI, Hit the below URL in the browser with internet connection
http://localhost:8080/view.html
![](/images_readme/UI_index)
To update Values, We can directly select the cell to edit and click update to update the values.
![](/images_readme/UI_update)
![](/images_readme/UI_update2)
To Delete values, Press delete button on row which needs to be deleted.
To add new values, Click create button and insert the new values.
![](images_readme/UI_create)

Post Man - 

To generate the oauth token, Hit the below URL with appid and secret, Also set the grant_type as client_credentials in the header
http://localhost:8080/oauth/token
![](images_readme/oauth_token)
![](images_readme/oauth_token2)

To get all the person records, Hit the below URL with the token in the header as Authorization - Bearer token

GET : http://localhost:8080/api/getAllPersons
Headers : Authorization - Bearer token

![](images_readme/getMethod)

To create a new person record,

POST : http://localhost:8080/api/createPerson
Header : Authorization - Bearer token
Request body : 'application/Json'
{
	"first_name":"xyz",
	"last_name":"abc",
	"age":45,
	"favourite_colour":"white",
	"hobby":["swimming","football"]
	
}
![](images_readme/postMethod)

To update a person record,

PUT : http://localhost:8080/updatePerson/2
Header : Authorization - Bearer token
Request body : 'application/Json'
{
	"first_name":"xyz",
	"last_name":"abc",
	"age":45,
	"favourite_colour":"white",
	"hobby":["swimming","football"]
	
}
![](images_readme/putMethod)

To delete a person record,

DELETE : http://localhost:8080/deletePerson/2
Header : Authorization - Bearer token
![](images_readme/deleteMethod)

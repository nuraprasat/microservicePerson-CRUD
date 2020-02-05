# microService-crud

# Person CRUD

## Getting Started
To run a project, First clone it.
```
git clone https://github.com/nuraprasat/productManagement.git
```

### Installing
After cloning just give the below command, Inside the project to run the service.
```
java -jar target/microServicePersonCRUD-0.0.1-SNAPSHOT.jar
```

## Running

### UI Screen - 
To start the UI, Hit the below URL in the browser with internet connection(Chrome)
```
http://localhost:8080/view.html
```
![](/images_readme/UI_index.png)
To update Values, We can directly select the cell to edit and click update to update the values.
![](/images_readme/UI_update.png)
![](/images_readme/UI_update2.png)
To Delete values, Press delete button on row which needs to be deleted.
To add new values, Click create button and insert the new values.
![](/images_readme/UI_create.png)

### Post Man - 

To generate an oauth token follow the below steps,
```
POST : http://localhost:8080/oauth/token
Authorization : username and password
Body : 'x-www-form-urlencoded'
grant_type:client_credentials
```
![](/images_readme/oauth_token.png)
![](/images_readme/oauth_token2.png)

To get all person records,
```
GET : http://localhost:8080/api/getAllPersons
Headers : Authorization - Bearer token
```
![](/images_readme/getMethod.png)

To create a new person record,
```
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
```
![](/images_readme/postMethod.png)

To update a person record,
```
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
```
![](/images_readme/putMethod.png)

To delete a person record,
```
DELETE : http://localhost:8080/deletePerson/2
Header : Authorization - Bearer token
```
![](/images_readme/deleteMethod.png)

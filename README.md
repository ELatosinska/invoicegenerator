# Invoice Generator
My first self-developed project.  
By creating this project, I am learning Spring framework and H2 database.  
It will be pdf generator for invoices created using web application (it will be also created in the future).  

## Launch  
It is running on localhost:8080.

## API Documentation  
 
### /api/categories  

#### GET (optional request param name)
returns all categories  
with name as parameter returns category by name
#### POST 
creates new category
request body example
```
{
    "name": "drink",
    "taxRateInPercent": "8"
}
```
#### DELETE
deletes all categories
***
### /api/categories/{id}
#### GET
returns category with given id
#### PUT
updates category with given id
requests body, not necessary to update all fields
```
{
    "name": "bread",
    "taxRateInPercent": "5"
}
```
#### DELETE
deletes category with given id

## XML Parser

This Project is for validate xml data and save the data in the mysql

### System Vesrion 

- Java Vesrion: JDK 21
- Maven
- MySQL

### Addon library used

- liquibase for auto sql execution
- mapstruct for auto data transfer from one java object to another 
- lombok for generate getter and setter

### Project setup steps
 - Download sourcecode and setup in IDE


### API list 

#### Parse & Save XML data
 
- MethodType: POST
- URL: http://localhost:8080/iapps/epaper
- Body: Upload XML in request body with variable name "xmlFile"
- CURL:
```curl
curl --location 'http://localhost:8080/iapps/epaper' --form 'xmlFile=@"/D:/Admin/Project/xml-parser-master/Sample.xml"'
```
- Response: 
```json 
{
    "id": 1,
    "deviceInfo": {
        "screenInfo": {
            "width": 4680,
            "height": 832,
            "dpi": 153
        },
        "appInfo": {
            "newspaperName": "ASD"
        }
    },
    "error": false
}
```
![Save API Image](https://github.com/psabuwala/xmlParser/blob/master/Image/saveData.png)


#### Find all
- MethodType: GET
- URL: http://localhost:8080/iapps/epaper?pageNo=1&sortColumn=width&sortOrder=DESC&search=asd
- CURL:
```curl
curl --location 'http://localhost:8080/iapps/epaper?pageNo=1&sortColumn=width&sortOrder=DESC&search=asd'
```
- Response: 
```json 
{
    "data": [
        {
            "id": 1,
            "deviceInfo": {
                "screenInfo": {
                    "width": 4680,
                    "height": 832,
                    "dpi": 153
                },
                "appInfo": {
                    "newspaperName": "ASD"
                }
            },
            "error": false
        }
    ],
    "totalCount": 1
}
```
Description:
> pageNo is start from 1
> If sortColumn value is null then it will sort based on default value newsPaperName
> Searching is working on "newsPaperName"

![FindAll API Image](https://github.com/psabuwala/xmlParser/blob/master/Image/getAll.png)


#### Get by Id
- MethodType: GET
- URL: http://localhost:8080/iapps/epaper/1
- CURL:
```curl
curl --location 'http://localhost:8080/iapps/epaper/1'
```
- Response: 
```json 
{
    "id": 1,
    "deviceInfo": {
        "screenInfo": {
            "width": 4680,
            "height": 832,
            "dpi": 153
        },
        "appInfo": {
            "newspaperName": "ASD"
        }
    },
    "error": false
}
```
![GetById API Image](https://github.com/psabuwala/xmlParser/blob/master/Image/GetById.png)



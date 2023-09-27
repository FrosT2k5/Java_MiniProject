# Restaurant App

Restaurant App built with Java Swing, Python Fastapi for backend and mysql for database.

Sem 3 Miniproject


# Prebuilt jars

prebuilt jar can be found in the jar/ folder of this repo, just download it and run without compilation:
```
java -jar RestaurantApp.jar
```


# How to compile
If you don't want to use prebuilt jar then, Install maven along with it's dependencies, clone the project and run
```
mvn package
```

then run the jar file (that has with-dependencies) in it's name in the target/ folder by
```
java -jar filename.jar
```

# Backend
To run backend on your local machine, install requirements.txt using pip

then, set up the following environment variables  `DB_NAME` , `DB_USER` , `DB_PASSWORD` , `DB_HOST` , `DB_PORT` (use mysql database)

After that, run:

```
uvicorn backend:app --host 127.0.0.1 --port 8000
```
The backend is currently hosted at:
https://funger-1-w1673858.deta.app/

Thanks to https://deta.space/ for awesome free fastapi hosting

# Screenshot
<img src="https://raw.githubusercontent.com/FrosT2k5/Java_MiniProject/master/gitassets/Screenshot.png" width="700px"> </img>

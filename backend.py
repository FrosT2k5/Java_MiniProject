from mysql import connector
from os import environ
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from pydantic import BaseModel

# SQL related vars & functions
db = connector.connect(
    host = environ.get("DB_HOST"),
    port = int(environ.get("DB_PORT")),
    user = environ.get("DB_USER"),
    password = environ.get("DB_PASSWORD"),
    database = environ.get("DB_NAME")
    )

dbcursor = db.cursor()

def printfulltable():
    query = "SELECT * FROM orders"
    db.commit()
    dbcursor.execute(query)
    answer = dbcursor.fetchall()
    for x in answer:
        print(x)

def inserttodb(name,items):
    query = "SELECT * from orders ORDER BY no DESC LIMIT 1" 
    dbcursor.execute(query)
    answer = dbcursor.fetchall()
    if not answer:
        order_no = 1
    else:
        order_no = answer[0][0]+1    

    query = "INSERT INTO orders (no,name,items) VALUES (%s,%s,%s)"
    values = (order_no,name,items)
    dbcursor.execute(query,values)
    db.commit()
    return order_no

def getallorders():
    query = "SELECT * FROM orders"
    dbcursor.execute(query)
    ans = dbcursor.fetchall()
    orderjson = []
    for i in ans:
        currentorder = {}
        currentorder["number"] = i[0]
        currentorder["name"] = i[1]
        currentorder["items"] = i[2]
        orderjson.append(currentorder)
    print(orderjson)
    return orderjson


# FastAPI backend handler

app = FastAPI()

class orderdata(BaseModel):
    name: str
    items: str

@app.post("/bookorder")
async def bookorder(data: orderdata):
    orderdict = data.dict()
    print(orderdict)
    orderno = inserttodb(orderdict['name'],orderdict['items'])
    printfulltable()
    return {"status": "success","orderno": orderno}

@app.get("/getorders")
async def getorders():
    orderjson = getallorders()
    return orderjson

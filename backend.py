from mysql import connector
from os import environ
from fastapi import FastAPI, Response
from fastapi.staticfiles import StaticFiles
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import orjson
import httpx
import json

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
        itemdict = {}
        currentorder["number"] = i[0]
        currentorder["name"] = i[1]
        itemdict = i[2]
        itemdict = json.loads(itemdict)
        currentorder["items"] = itemdict
        orderjson.append(currentorder)
    return orderjson


# FastAPI backend handler
class IndentedResponse(Response):
    media_type = "application/json"

    def render(self, content) -> bytes:
        return orjson.dumps(content, option=orjson.OPT_INDENT_2)
        
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=['*'],
    allow_methods=["*"],
    allow_headers=["*"],
)

class orderdata(BaseModel):
    name: str
    items: str

@app.post("/bookorder")
async def bookorder(data: orderdata):
    orderdict = data.dict()
    orderno = inserttodb(orderdict['name'],orderdict['items'])
    itemlist = json.loads(orderdict['items'])
    
    telemessage = "New order received!\n"
    telemessage += ("\nOrder No: " + str(orderno))
    telemessage += ("\nName: " + orderdict['name'])
    telemessage += "\n\nItems: "
    for item in itemlist:
        telemessage += ("\n" + item + ": " + str(itemlist[item]) )
    try:
        async with httpx.AsyncClient() as client:
            headers = {'Content-Type':'application/json'}
            data = {
                "username": environ.get("TELE_USERNAME"),
                "securitykey": environ.get("TELE_SECURITYKEY"),
                "message": telemessage
            }
            r = await client.post('https://pingme.domcloud.io/api/sendmessage',headers=headers,json=data)
    except:
        None
    return {"status": "success","orderno": orderno}

@app.get("/getorders",response_class=IndentedResponse)
async def getorders():
    orderjson = getallorders()
    return orderjson

@app.get("/health")
async def healthcheck():
    return {"status":"ok"}

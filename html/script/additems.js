async function getOrders() {
    const response = await fetch("https://funger-1-w1673858.deta.app/getorders");
    const orderDict = await response.json();
    return orderDict
}

function choose(choices) {
    var index = Math.floor(Math.random() * choices.length);
    return choices[index];
  }
  
async function updateOrders() {
    const orderDict = await getOrders()
    const imageFromName = {
        "Burger": "images/burger.png",
        "Colddrink": "images/colddrink.png",
        "Fries": "images/fries.png",
        "Pizza": "images/pizza.png",
        "Sandwitch": "images/sandwitch.png",
        "Sandwich": "images/sandwitch.png",
        "Burger and Colddrink": "images/burgeranddrink.png",
        "Coffee": "images/coffee.png"
    }

    const costFromName = {
        "Burger": 100,
        "Colddrink": 50,
        "Fries": 120,
        "Pizza": 100,
        "Sandwitch": 80,
        "Sandwich": 80,
        "Burger and Colddrink": 150,
        "Coffee": 50        
    }

    const orderItemListDivColors = ["#e2d9db","#facbea","#dcf7f3","#e1ecc4","#def5be","#feff86"]

    for (i=0 ; i<orderDict.length ; i++ ) {

        let currentItem = orderDict[i]

        console.log(currentItem.name)

        let orderItemDiv = document.createElement("div")
        orderItemDiv.setAttribute("class","orderItem")
        orderItemDiv.setAttribute("data-aos","zoom-in")
        orderItemDiv.style.backgroundColor = choose(orderItemListDivColors)
        let orderItemListDiv = document.createElement("div")
        orderItemListDiv.setAttribute("class","orderItemList")
        
        let headingDiv = document.createElement("div")
        headingDiv.setAttribute("class","heading")
        let h3Element = document.createElement("h3")
        let pElement = document.createElement("p")
        h3Element.innerHTML = "Order No: " + currentItem["number"]
        pElement.innerHTML = "Customer: " + currentItem["name"]

        headingDiv.appendChild(h3Element)
        headingDiv.appendChild(pElement)
        orderItemListDiv.appendChild(headingDiv)

        let itemDict = currentItem["items"]
        let cost = 0
        for (const item in itemDict) {

            let orderItemElementDiv = document.createElement("div")
            orderItemElementDiv.setAttribute("class","orderItemElement")

            let imageDiv = document.createElement("div")
            let nameDiv = document.createElement("div")
            let countDiv = document.createElement("div")

            let imageElement = document.createElement("img")
            imageElement.setAttribute("src",imageFromName[item])
            imageDiv.appendChild(imageElement)

            let h4Element = document.createElement("h4")
            h4Element.innerHTML = item
            nameDiv.appendChild(h4Element)

            let countElement = document.createElement("h4")
            countElement.innerHTML = itemDict[item]
            countElement.setAttribute("class","amountNumber")
            countDiv.append(countElement)

            orderItemElementDiv.appendChild(imageDiv)
            orderItemElementDiv.appendChild(nameDiv)
            orderItemElementDiv.appendChild(countDiv)
            orderItemListDiv.appendChild(orderItemElementDiv)

            cost += costFromName[item] * itemDict[item]
        }

        let costElementDiv = document.createElement("div")
        costElementDiv.setAttribute("class","costElement")
        let costElement = document.createElement("h5")
        costElement.innerHTML = cost
        let costInfoElement = document.createElement("h5")
        costInfoElement.innerHTML = "Total Cost"

        costElementDiv.appendChild(costElement)
        costElementDiv.appendChild(costInfoElement)

        orderItemDiv.appendChild(orderItemListDiv)
        orderItemDiv.appendChild(costElementDiv)
        mainDiv = document.getElementById("orderListGrid")
        mainDiv.prepend(orderItemDiv)
    }
}

function doLogin(e) {
    e.preventDefault();

    let loginForm = document.forms["login"]
    username = loginForm["username"].value
    password = loginForm["password"].value

    if (username !== "admin" || password !== "admin") {
        alert("Invalid username or password")
        return false
    }

    formDiv = document.getElementById("loginForm")
    formDiv.style.display = "none"

    gridDiv = document.querySelector(".orderListGrid")
    gridDiv.style.display = "grid"

    updateOrders()
    return false
}

document.getElementById("submitButton").addEventListener("click",doLogin)

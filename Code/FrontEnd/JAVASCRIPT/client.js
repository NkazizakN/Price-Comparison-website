window.onload = loaddata;
var degrees= [];
var uni = [];
var db_com = [];

function loaddata()
{
    let xhttp = new XMLHttpRequest(); 
    xhttp.onreadystatechange = () => {
        if(xhttp.status == 200 && xhttp.readyState == 4)
        {
            let responseData = JSON.parse(xhttp.responseText);
            console.log(responseData);
            //Add something to add to the page;(update)
        }
        else{
            alert("Error communicating with server :" + xhttp.status);

        }
    };
    xhttp.open("GET", "/degree", true);
    xhttp.send();
}
 
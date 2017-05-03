const errorMessage = "укажите допустимое значение";

class Point {
    constructor(x, y, r, inArea){
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
    }
}

var points = [];

function pointDraw(point) {
    if (point.x === null || point.y === null || point.r === null || point.inArea === null)
        return;

    var rd = (+point.r) / 160;
    var canvas = document.getElementById("plot");
    var canvasRect = canvas.getBoundingClientRect();

    var xCoord = (point.x / rd) + canvasRect.width / 2;
    var yCoord = canvasRect.height / 2 - (point.y / rd);

    canvas.getContext("2d").fillStyle = point.inArea ? "#00FF00" : "#FF0000";
    canvas.getContext("2d").fillRect(xCoord, yCoord, 3, 3);
}

function plotDraw() {
    var canvasContext = document.getElementById("plot").getContext("2d");
    canvasContext.clearRect(0, 0, canvasContext.width, canvasContext.height);

    var image = new Image();
    image.src = "resources/area.png";
    image.onload = function () {
        canvasContext.drawImage(image, 0, 0);

        points.forEach(function(item, i, arr) {
            pointDraw(item);
        });
    }
}

function plotClick(e) {
    var valueR = getValue(document.getElementsByName("value-r"), 1, 3,
            (element) => element.style.boxShadow !== "");

    document.getElementById("error-r").innerHTML = isNaN(valueR) ? errorMessage : "";
    if (isNaN(valueR))
        return;

    var rd = valueR / 160;
    var canvas = document.getElementById("plot");
    var canvasRect = canvas.getBoundingClientRect();

    var x = (e.clientX - canvasRect.left - canvasRect.width / 2) * rd;
    var y = (canvasRect.height / 2 - e.clientY + canvasRect.top) * rd;

    x = x.toFixed(2);
    y = y.toFixed(2);

    sendRequest(x, y, valueR);
}

function selectRValue(clicked_id) {
    var buttonsR = document.getElementsByName("value-r");

    buttonsR.forEach(
        function (button, i, buttonsR) {
            if(button.style.boxShadow !== ""){
                oldR = button.value;
            }
            if (button.id === clicked_id) {
                button.style.boxShadow = "0 6px 8px 0 rgba(0,0,0,0.30)";
            }
            else {
                button.style.boxShadow = "";
            }
        }
    );
}

function selectR(clicked_id) {
    var valueR = document.getElementById(clicked_id).value;
    var valueX = getValue(document.getElementsByName("value-x"), -3, 5,
        (element) => true);
    var valueY = getValue(document.getElementsByName("value-y"), -4, 4,
        (element) => element.style.boxShadow !== "");

    if(points.length > 0 && isNaN(valueX) && isNaN(valueY)){
        sendRequest(null, null, valueR);
    } else {
        selectRValue(clicked_id);
    }
}

function selectY(clicked_id) {
    var buttonsY = document.getElementsByName("value-y");

    buttonsY.forEach(
        function (button, i, buttonsY) {
            if (button.id === clicked_id && button.style.boxShadow === "")
                button.style.boxShadow = "0 6px 8px 0 rgba(0,0,0,0.30)";
            else
                button.style.boxShadow = "";
        }
    );

}

function checkValues() {
    var valueX = getValue(document.getElementsByName("value-x"), -3, 5,
            (element) => true);
    var valueY = getValue(document.getElementsByName("value-y"), -4, 4,
            (element) => element.style.boxShadow !== "");
    var valueR = getValue(document.getElementsByName("value-r"), 1, 3,
            (element) => element.style.boxShadow !== "");

    document.getElementById("error-x").innerHTML = isNaN(valueX) ? errorMessage : "";
    document.getElementById("error-y").innerHTML = isNaN(valueY) ? errorMessage : "";
    document.getElementById("error-r").innerHTML = isNaN(valueR) ? errorMessage : "";

    if (isNaN(valueX) || isNaN(valueY) || isNaN(valueR))
        return false;

    sendRequest(valueX, valueY, valueR);
    return true;
}

function sendRequest(valueX, valueY, valueR) {
    var xhrequest = new XMLHttpRequest();
    var query = "?";

    if(valueX !== null)
        query += "x=" + valueX;
    if(valueY !== null)
        query += "&y=" + valueY;
    if(valueR !== null)
        query += "&r=" + valueR;

    xhrequest.open("GET", query, true);
    xhrequest.send();

    xhrequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            location.reload();
        }
    }
}

function getValue(elements, lowerLimit, upperLimit, elementCheck) {
    for (var i = 0; i < elements.length; i++) {
        var value = strip(elements[i].value);

        if (elementCheck(elements[i])
            && !isNaN(parseFloat(value))
            && +value >= +lowerLimit
            && +value <= +upperLimit) {
            return elements[i].value;
        }
    }

    return NaN;
}

function strip(number) {
    return number.replace(/[.]00+/, '.0');
}

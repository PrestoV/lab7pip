const errorMessage = "укажите допустимое значение";

function plotClick(e) {
    var valueR = getValue(document.getElementsByName("value-r"), 1, 3, (element) => element.style.boxShadow != "");

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

function selectR(clicked_id) {
    var buttonsR = document.getElementsByName("value-r");

    buttonsR.forEach(
        function (button, i, buttonsR) {
            if (button.id === clicked_id && button.style.boxShadow === "") {
                button.style.boxShadow = "0 6px 8px 0 rgba(0,0,0,0.30)";
            }
            else
                button.style.boxShadow = "";
        }
    );

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

    xhrequest.open("GET", "?x=" + valueX + "&y=" + valueY + "&r=" + valueR, true);
    xhrequest.send();

    xhrequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.open();
            document.write(this.responseText);
            document.close();
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
    var dot_pos = number.indexOf(".");

    if (dot_pos === -1)
        return number;

    var len = number.length - dot_pos - 2;
    dot_pos++;

    while (len-- > 0 && number[dot_pos + 1] === "0") {
        number = number.replace(".0", ".");
    }

    return number;
}

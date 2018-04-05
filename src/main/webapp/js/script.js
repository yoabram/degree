
var serverHostName = window.location.hostname;

var serverProtocolName = window.location.protocol;

var portName = window.location.port;

if (portName.length === 0) {
    portName = "80";
}

if (serverHostName === "localhost")
{
    serverPath = serverProtocolName + "//" + serverHostName + ":" + portName;
}
else
{
    serverPath = serverProtocolName + "//" + serverHostName;
}

function serverConnectFunc(serverUrl, jsonData) {
    $.ajax({
        url: serverUrl + "/",
        type: 'POST',
        data: jsonData,

        dataType: 'json',
        async: true,

        success: function (event) {
            switch (event["answer"])
            {
                case "degrees":
                    var list = event["map"].replace("{", ""). replace("}", "").split(",");
                    $("#table_labs").empty();

                    list.forEach(function(item, i, arr) {
                        var array = item.split("=");
                        $("#table_labs").append("<tr><td>" + array[0] + "</td><td>"+ array[1] + "</td></tr>");
                    });

                    break;
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function showDegrees()
{
    var jsonData = new Object();
    jsonData.command = "0";
    jsonData.arg = $('#NumberInput').val();
    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}



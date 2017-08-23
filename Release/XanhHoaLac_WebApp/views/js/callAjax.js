/**
 * Created by khang on 3/15/2017.
 */


function callAjax(jsonStringSend, urlReciver, methodSend, callBackFunction) {
    var data = (jsonStringSend);
    $.ajax({
        url: urlReciver,
        method: methodSend,
        contentType: "application/json",
        data: data
    }).done(function (response) {
        callBackFunction(response);
    });

}

function transAction(action) {
    //"add", "delete", "modify", "on", "off", "schedule"
    switch (action) {
        case "add": {
            return "Thêm Node"
        }
        case "delete": {
            return "Xóa Node"
        }
        case "modify": {
            return "Sửa Node"
        }
        case "on": {
            return "Bật Node"
        }
        case "off": {
            return "Tắt Node"
        }
        case "schedule": {
            return "Hẹn giờ"
        }
        case "recharge":{
            return "Nạp tiền"
        }
    }
}
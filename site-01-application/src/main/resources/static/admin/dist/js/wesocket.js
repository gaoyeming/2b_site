var websocket;
$(function () {
    var target = $("#websocketUrl").val();
    if (websocket != null) {
        console.log("WebSocket inited!");
        return;
    }
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket(target);
    } else {
        console.log("Not Support WebSocket!");
    }
//连接发生错误的回调方法
    websocket.onerror = function () {
        console.log("websocket发生错误");
    };
//连接成功建立的回调方法
    websocket.onopen = function (event) {
        console.log("websocket连接成功");
    };
//接收到消息的回调方法
    websocket.onmessage = function () {
        console.log("接收消息:" + event.data);
        swal({
            icon: "success",
            text: event.data,
            buttons: {
                cancel: {	//取消按钮
                    text: "取消",
                    value: null,
                    visible: true
                },
                confirm: {	//确认按钮
                    text: "立即前往",
                    value: true,
                    visible: true
                }
            }
        }).then(function (isOk) {		//判断是否点击确认
            if (!isOk)	//如果点击取消
                swal.close();
            else {
                window.location.href = "/backstage/comments/manage";
            }
        });
    };
//连接关闭的回调方法
    websocket.onclose = function () {
        console.log("websocket关闭连接");
    };

});
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
// window.onbeforeunload = function () {
//     console.log("窗口关闭,websocket关闭连接");
//     websocket.close();
// };



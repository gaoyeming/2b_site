$(function () {
    //修改站点信息
    $('#updateWebsiteButton').click(function () {
        $("#updateWebsiteButton").attr("disabled", true);
        //ajax提交数据
        var params = {
            "websiteName":$("#websiteName").val(),
            "websiteDescription":$("#websiteDescription").val(),
            "websiteLogo":$("#websiteLogo").val(),
            "websiteIcon":$("#websiteIcon").val()
        };
        $.ajax({
            type: "POST",
            url: "/backstage/configurations/",
            data: JSON.stringify(params),
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result.code === "000") {
                    swal("保存成功", {
                        icon: "success",
                    });
                }
                else {
                    swal(result.message, {
                        icon: "error",
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    });
    //个人信息
    $('#updateUserInfoButton').click(function () {
        $("#updateUserInfoButton").attr("disabled", true);
        var params = {
            "yourAvatar":$("#yourAvatar").val(),
            "yourName":$("#yourName").val(),
            "yourEmail":$("#yourEmail").val()
        };
        $.ajax({
            type: "POST",
            url: "/backstage/configurations/",
            data: JSON.stringify(params),
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result.code === "000") {
                    swal("保存成功", {
                        icon: "success",
                    });
                }
                else {
                    swal(result.message, {
                        icon: "error",
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    });
    //修改底部设置
    $('#updateFooterButton').click(function () {
        $("#updateFooterButton").attr("disabled", true);
        var params = {
            "footerAbout":$("#footerAbout").val(),
            "footerICP":$("#footerICP").val(),
            "footerCopyRight":$("#footerCopyRight").val(),
            "footerPoweredBy":$("#footerPoweredBy").val(),
            "footerPoweredByURL":$("#footerPoweredByURL").val()
        };
        $.ajax({
            type: "POST",
            url: "/backstage/configurations/",
            data: JSON.stringify(params),
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result.code === "000") {
                    swal("保存成功", {
                        icon: "success",
                    });
                }
                else {
                    swal(result.message, {
                        icon: "error",
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    });

})

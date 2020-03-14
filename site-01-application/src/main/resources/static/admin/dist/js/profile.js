$(function () {
    //修改个人信息
    $('#updateUserNameButton').click(function () {
        $("#updateUserNameButton").attr("disabled", true);
        var userName = $('#loginUserName').val();
        var nickName = $('#nickName').val();
        if (validUserNameForUpdate(userName, nickName)) {
            //ajax提交数据
            var params = {
                "loginUserName": userName,
                "nickName": nickName,
                "opterType":0
            };
            $.ajax({
                type: "POST",
                url: "/backstage/profile/",
                data: JSON.stringify(params),
                contentType: 'application/json;charset=utf-8',
                success: function (r) {
                    if (r.code === "000") {
                        swal("修改成功", {
                            icon: "success",
                        });
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                        $("#updateUserNameButton").prop("disabled", false);
                    }
                },
                error: function () {
                    swal("操作失败", {
                        icon: "error",
                    });
                }
            });
        } else {
            $("#updateUserNameButton").prop("disabled", false);
        }
    });
    //修改密码
    $('#updatePasswordButton').click(function () {
        $("#updatePasswordButton").attr("disabled", true);
        var originalPassword = $('#originalPassword').val();
        var newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = {
                "originalPassword": originalPassword,
                "newPassword": newPassword,
                "opterType":1
            };
            $.ajax({
                type: "POST",
                url: "/backstage/profile/",
                data: JSON.stringify(params),
                contentType: 'application/json;charset=utf-8',
                success: function (r) {
                    if (r.code === "000") {
                        swal("修改成功", {
                            icon: "success",
                        });
                        window.location.href = '/backstage/login';
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                        $("#updatePasswordButton").attr("disabled", false);
                    }
                }
            });
        } else {
            $("#updatePasswordButton").attr("disabled", false);
        }
    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(userName, nickName) {
    if (isNull(userName) || userName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入登陆名称！");
        return false;
    }
    if (isNull(nickName) || nickName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("昵称不能为空！");
        return false;
    }
    if (!validUserName(userName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的登录名！");
        return false;
    }
    if (!validCN_ENString2_18(nickName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的昵称！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！");
        return false;
    }
    return true;
}

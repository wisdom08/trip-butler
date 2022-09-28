// 로그인 상태 버튼
if (sessionStorage.getItem("email") !== null) {
    $('#none-user-button').hide();
}
if (sessionStorage.getItem("email") === null) {
    $('#user-button').hide();
}

// 로그인한 유저 닉네임 출력
document.getElementById("user-nickname").innerText = sessionStorage.getItem("nickname");

// 로그아웃 버튼
function click_logout() {

    let email = sessionStorage.getItem("email")

    $.ajax({
        type: "POST",
        url: "/api/user/logout",
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(email),
        success: function (response) {

        }
    });

    sessionStorage.removeItem("accessToken")
    sessionStorage.removeItem("email")
    sessionStorage.removeItem("nickname")
    sessionStorage.removeItem("imageUrl")
    location.reload()
}
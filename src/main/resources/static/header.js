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
    sessionStorage.removeItem("accessTokenExpiresIn")
    sessionStorage.removeItem("accessToken")
    sessionStorage.removeItem("email")
    sessionStorage.removeItem("nickname")
    location.reload()
}
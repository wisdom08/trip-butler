function checkEmail() {
    $.ajax({
        type: 'POST',
        url: '/api/users/emailconfirm',
        contentType: 'application/json; charset=utf-8',
        data: $('#email').val(),
        success: function (result) {
            $('#emailAvailable').show().text(result).append($('<br />'));
            $('#emailNotAvailable').hide();
        }, error: function (request, status, error) {
            $('#emailAvailable').hide();
            $('#emailNotAvailable').show().text('이미 사용중인 이메일 입니다.').append($('<br />'));
        }
    });
}

function checkNickname() {
    $.ajax({
        type: 'POST',
        url: '/api/users/nicknameconfirm',
        contentType: 'application/json; charset=utf-8',
        data: $('#nickname').val(),
        success: function (result) {
            $('#nicknameAvailable').show().text(result).append($('<br />'));
            $('#nicknameNotAvailable').hide();
            console.log(result)
        }, error: function (request, status, error) {
            $('#nicknameAvailable').hide();
            $('#nicknameNotAvailable').show().text('이미 사용중인 닉네임 입니다.').append($('<br />'));
        }
    });
}

function click_signup() {
    let email = $('#email').val();
    let nickname = $('#nickname').val();
    let password = $('#password').val();

    let reg_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    let reg_nickname = /^[a-z0-9_-]{4,12}$/;
    let reg_password = /(?=.*\d)(?=.*[a-zA-ZS]).{4,12}/;

    if (email === '' || nickname === '' || password === '') {
        alert("내용을 입력해주세요")
        return;
    }

    if (!reg_email.test(email)) {
        alert("올바른 이메일 주소를 입력해주세요.")
        return;
    }

    if (!reg_nickname.test(nickname)) {
        alert("4~12자리의 닉네임을 입력해주세요.")
        return;
    }

    if (!reg_password.test(password)) {
        alert("문자와 숫자를 한개 이상 포함한 비밀번호를 입력해주세요.")
        return;
    }

    let data = {'email': email, 'nickname': nickname, 'password': password};
    $.ajax({
        type: "POST",
        url: "/api/users/signup",
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (response) {
            location.href = "/login";
        }
    });
}
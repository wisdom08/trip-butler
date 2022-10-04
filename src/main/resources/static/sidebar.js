$(document).on("click", ".period-btn", function () {
    $('nav ul .period-show').toggleClass("show1");
    $('nav ul .first').toggleClass("rotate");
});

$(document).on("click", ".section-btn", function () {
    $('nav ul .section-show').toggleClass("show2");
    $('nav ul .second').toggleClass("rotate");
});

$(document).on("click", ".press-btn", function () {
    $('nav ul .press-show').toggleClass("show3");
    $('nav ul .third').toggleClass("rotate");
});

$(document).on("click", ".sidebar ul li", function () {
    $(this).addClass("active").siblings().removeClass("active");
});
$(document).ready(function () {
    showError();
})

function showError() {
    let check = getParamError();
    let container = document.getElementById("error");
    if (check) {
        container.innerHTML = "<div class='row'>"
            + "<div style='color:red; font-weight: bold; margin: 20px 0 0 20px;'>"
            + "Ошибка регистрации. Такой пользователь уже существует."
            + "</div>"
            + "</div>";
    }
    setTimeout(function () {
        container.innerHTML = ''
    }, 5000);
}

function getParamError() {
    return new URLSearchParams(window.location.search).get("error");
}

function checkFormRegistration() {
    let elements = [$("#username"), $("#regEmail"), $("#regPassword")];
    return validate(elements);
}
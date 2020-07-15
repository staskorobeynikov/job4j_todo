$(document).ready(function () {
    showAll();
    getCurrentUser();
});

function showAll() {
    $.ajax({
        type: 'POST',
        url: './show',
        dataType: 'json',
        success: function ($data) {
            addRowsTable($data);
        }
    });
}

function addRowsTable(data) {
    let items = data;
    deleteRows();
    let result = '';
    for (let i = 0; i < items.length; i++) {
        let id = items[i]['id'];
        let desc = items[i]['desc'];
        let created = items[i]['created'];
        let author = items[i]['user'].username;
        let done = items[i]['done'];
        result += '<tr class="rows"><td id="id">' + id + '</td>'
            + '<td>' + desc + '</td>'
            + '<td>' + author + '</td>'
            + '<td>' + created + '</td>';
        if (!done) {
            result += '<td style="text-align: center; background-color: red">'
                + '<input type="checkbox" id="' + id + '" name="task" value="task">'
                + '</td>'
                + '</tr>'
        } else {
            result += '<td style="text-align: center; background-color: lightgreen">'
                + '<input type="checkbox" id="' + id + '" name="task" value="task">'
                + '</td>'
                + '</tr>'
        }
    }
    document.getElementById("tableBody").innerHTML = result;
}

function deleteRows() {
    let rows = document.getElementsByClassName("rows");
    for(let i = 0; i < rows.length; i++){
        rows[i].innerHTML = "";
    }
}

function getCurrentUser() {
    $.ajax({
        type: 'POST',
        url: './current',
        dataType: 'json',
        success: function (data) {
            document.getElementById("current")
                .innerHTML = "Current User | " + data['username'];
        }
    });
}

function filterItems() {
    let check = $("#filter").prop("checked");
    if (check) {
        showAll();
    } else {
        showFilterItems();
    }
}

function showFilterItems() {
    $.ajax({
        type: 'POST',
        url: './filter',
        dataType: 'json',
        success: function ($data) {
            addRowsTable($data);
        }
    });
}

function addItem() {
    if (checkFormCreateItem()) {
        $.ajax({
                method: 'POST',
                url: './add.do',
                data: {desc : $("#description").val()},
                success: function ($data) {
                    let check = $data !== '';
                    if (check) {
                        window.location.href = "./auth.do";
                    }
                }
            }
        );
        location.reload();
    }
}

$(document).on('change', ':checkbox' ,function () {
    let id = $(this).attr("id");
    console.log(id);
    if (id !== "filter") {
        $.ajax({
            type: "POST",
            url: './update.do',
            data: {id : $(this).attr("id"), done : $(this).prop("checked")},
            success: function (data) {
                console.log(data);
                let check = data !== '';
                if (check) {
                    window.location.href = "./auth.do";
                }
            }
        });
        location.reload();
    }
});

function validate(data) {
    let result = true;
    let answer = '';
    for (let i = 0; i < data.length; i++) {
        if (data[i].val() === '') {
            answer += data[i].attr("placeholder") + "\n";
            result = false;
        }
    }
    if (!result) {
        alert(answer);
    }
    return result;
}

function checkFormCreateItem() {
    let elements = [$("#description")];
    return validate(elements);
}

function checkFormRegistration() {
    let elements = [$("#username"), $("#regEmail"), $("#regPassword")];
    return validate(elements);
}

function checkFormAuth() {
    let elements = [$("#email"), $("#password")];
    return validate(elements);
}

function redirectPageLogin() {
    window.location.href = "./auth.do";
}
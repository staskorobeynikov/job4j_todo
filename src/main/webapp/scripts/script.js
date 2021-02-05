$(document).ready(function () {
    showAll();
    getCurrentUser();
    updateItem();
    addNewItem();
});

function showCategories() {
    $.ajax({
        type: 'get',
        url: './categories',
        dataType: 'json',
        success: function (data) {
            document.getElementById("current")
                .innerHTML = "Current User | " + data['username'];
        }
    })
}

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
        result += formStringForInsert(items[i]);
    }
    document.getElementById("tableBody").innerHTML = result;
}

function formStringForInsert(data) {
    let result = '';
    let id = data['id'];
    let desc = data['desc'];
    let created = data['created'];
    let author = data['user'].username;
    let done = data['done'];
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
    return result;
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

function addNewItem() {
    $("#new-item-form").submit(function (e) {
        if (checkFormCreateItem()) {
            let description = $("#description").val();
            $.ajax({
                method: 'POST',
                url: './add.do',
                data: {desc : description},
                success: function (data) {
                    addNewRow(data);
                },
                error: [ function (response) {
                    console.log(response.status);
                    window.location.href = "./auth.do";
                }]
            });
            e.preventDefault();
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

function addNewRow(data) {
    let result = formStringForInsert(data);
    $('#tableBody tr:last').after(result);
}

function updateItem() {
    $(document).on('change', ':checkbox', function () {
        let id = $(this).attr("id");
        console.log(id);
        if (id !== "filter") {
            $.ajax({
                type: "POST",
                url: './update.do',
                data: {id : $(this).attr("id"), done : $(this).prop("checked")},
                success: function (data) {
                    location.reload();
                },
                error: [ function (response) {
                    console.log(response.status);
                    window.location.href = "./auth.do";
                }]
            });
        }
    });
}

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
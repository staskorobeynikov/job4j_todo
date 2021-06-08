$(document).ready(function () {
    showCategories()
    showAll();
    getCurrentUser();
    updateItem();
    addNewItem();
});

function showCategories() {
    $.ajax({
        type: 'GET',
        url: './categories',
        dataType: 'json',
        success: function (data) {
            let rsl = '';
            let items = data;
            for (let i = 0; i < items.length; i++) {
                rsl += '<option value="' + items[i].id + '"/>' + items[i].name + '</option>' + '\n';
            }
            document.getElementById("cIds")
                .innerHTML = rsl;
        }
    });
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
    let categories = data['categories'];
    let created = data['created'];
    let author = data['user'].username;
    let done = data['done'];
    result += '<tr class="rows"><td id="id">' + id + '</td>'
        + '<td>' + desc + '</td><td>';
    for (let i = 0; i < categories.length; i++) {
        result += categories[i]['name'] + '<br>';
    }
    result += '</td><td>' + author + '</td>'
        + '<td>' + created + '</td>';
    if (!done) {
        result += '<td style="text-align: center; background-color: red">'
            + '<input type="checkbox" id="' + id + '" name="task" value="task">'
            + '<label for="task" style="font-weight: bold; margin-left: .5rem"> Выполнить</label>'
            + '</td>'
            + '</tr>'
    } else {
        result += '<td style="text-align: center; background-color: lightgreen">'
            + '<button class="btn btn-link" onclick="deleteItem(' + id + ')">'
            + '<i class="fa fa-times" aria-hidden="true"></i> Удалить</button>'
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
            let ids = $("#cIds").val();
            $.ajax({
                method: 'POST',
                url: './add.do',
                data: {desc : description, cIds : ids},
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
        if (id !== "filter") {
            $.ajax({
                type: "POST",
                url: './update.do',
                data: {id : $(this).attr("id"), done : $(this).prop("checked")},
                success: function () {
                    let container = document.getElementById("message");
                    container.innerHTML = "<div class='row'>"
                        + "<div style='color:darkgreen; font-weight: bold; margin: 20px 0 0 20px;'>"
                        + "Задание успешно выполнено."
                        + "</div>"
                        + "</div>";
                    setTimeout(function () {
                        container.innerHTML = ''
                        location.reload();
                    }, 2000);
                },
                error: [ function (response) {
                    console.log(response.status);
                    window.location.href = "./auth.do";
                }]
            });
        }
    });
}

function deleteItem(id) {
    $.ajax({
        type: "POST",
        url: './delete.do',
        data: {id : id},
        success: function () {
            let container = document.getElementById("message");
            container.innerHTML = "<div class='row'>"
                + "<div style='color:darkgreen; font-weight: bold; margin: 20px 0 0 20px;'>"
                + "Задание успешно удалено."
                + "</div>"
                + "</div>";
            setTimeout(function () {
                container.innerHTML = ''
                location.reload();
            }, 2000);
        },
        error: [ function () {
            window.location.href = "./auth.do";
        }]
    });
}

function checkFormCreateItem() {
    let elements = [$("#description")];
    return validate(elements);
}

function redirectPageLogin() {
    window.location.href = "./auth.do";
}
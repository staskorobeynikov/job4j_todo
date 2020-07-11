function addItem() {
    if (validate()) {
        $.ajax({
                method: 'POST',
                url: './add',
                data: {desc : $("#description").val()},
                success: function ($data) {
                    console.log($data);
                }
            }
        );
    }
    location.reload();
}

function validate() {
    let result = true;
    let answer = '';
    let elements = [$("#description")];
    for (let i = 0; i < elements.length; i++) {
        if (elements[i].val() === '') {
            answer += elements[i].attr("placeholder") + "\n";
            result = false;
        }
    }
    if (!result) {
        alert(answer);
    }
    return result;
}

$(document).ready(function () {
    showAll();
});

function filterItems() {
    let check = $("#filter").prop("checked");
    if (check) {
        showAll();
    } else {
        showFilterItems();
    }
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

function addRowsTable(data) {
    let items = data;
    console.log(items);
    deleteRows();
    for (let i = 0; i < items.length; i++) {
        let id = items[i]['id'];
        let desc = items[i]['desc'];
        let created = items[i]['created'];
        $("#table tr:last").after(
            '<tr class="rows"><td id="id">' + id + '</td>'
            + '<td>' + desc + '</td>'
            + '<td>' + created + '</td>'
            + '<td style="text-align: center"><input type="checkbox" id="' + id + '" name="task" value="task"></td></tr>'
        );
    }
}

function deleteRows() {
    let rows = document.getElementsByClassName("rows");
    for(let i = 0; i < rows.length; i++){
        rows[i].innerHTML = "";
    }
}

$(document).on('change', ':checkbox' ,function () {
    let id = $(this).attr("id");
    console.log(id);
    if (id !== "filter") {
        $.ajax({
            type: "POST",
            url: './update',
            data: {id : $(this).attr("id"), done : $(this).prop("checked")},
            success: function (data) {
                console.log(data);
            }
        });
        location.reload();
    }
});
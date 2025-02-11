
// ************************************** Преподаватели *******************************
function showUsers() {
    console.log("showUsers .................");
    fetch('/api/v1/user')
        .then(response => response.json())
        .then(json => outputUsers(json))
        .catch(error => console.error("Ошибка чтения пользователей ", error));
}

function comboUsers() {
    console.log("comboUsers .................");
    fetch('/api/v1/user')
        .then(response => response.json())
        .then(json => outputComboUsers(json))
        .catch(error => console.error("Ошибка чтения пользователей ", error));
}

function outputComboUsers(users) {
    console.log("outputComboUsers", users);
    let select = document.getElementById("userId");

    users.forEach(user => {
        let option = document.createElement("option");
        option.value = user.id;
        option.text = user.fullName;
        select.append(option);
    });

}

function outputUsers(users) {
    console.log("outputUsers", users);
    let table = document.getElementById("table-items");
    users.forEach(user => {
        let row = document.createElement("tr");
        row.setAttribute("id", user.id);
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.fullName}
            <p><code>${user.info}</code></p>
            </td>
            <td>${user.phone}</td>
            <td>${user.email}</td>                        
            <td><a href="/user/edit?id=${user.id}">Изменить</a></td>
            <td><button onclick="deleteUser(${user.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function deleteUser(id) {
    console.log('deleteUser ....................', id);
    fetch(`api/v1/user/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed user ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления пользователя ", error));
}

function loadUser() {
    const id = document.getElementById("id-input").value;
    console.log("Загрузка пользователя c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/user/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(user => {
            console.log('Получен пользователь', user);
            document.getElementById('lastname-input').value = user.lastName;
            document.getElementById('firstname-input').value = user.firstName;
            document.getElementById('middlename-input').value = user.middleName;
            document.getElementById('username-input').value = user.nickname;
            document.getElementById('phone-input').value = user.phone;
            document.getElementById('email-input').value = user.email;
        })
        .catch(error => console.error("Ошибка загрузки преподавателя по id ", error));
}

function getInputText(elementId) {
    return document.getElementById(elementId).value;
}

function saveUser() {
    console.log('saveUser ................');
    let id = getInputText("id-input");
    const method = id ? "PUT" : "POST";

    const user = {
        id: id,
        lastName: getInputText("lastname-input"),
        firstName: getInputText("firstname-input"),
        middleName: getInputText("middlename-input"),
        phone: getInputText("phone-input"),
        email: getInputText("email-input"),
    };

    console.log('save user', user);

    fetch("/api/v1/user", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            console.log('response', response.json());
            if (response.ok) {
                window.location.href = "/user";
            }

            console.log('response status', response.status);
            console.log('response text', response.body);
            console.log('response statusText', response.statusText);

        })
        .catch(error => {
            console.log("Fetch ошибка сохранения пользователя", error)
        });
}

function showStudents() {
    console.log("showStudents .................");
    fetch('/api/v1/student')
        .then(response => response.json())
        .then(json => outputStudents(json))
        .catch(error => console.error("Ошибка чтения студентов ", error));
}

function comboStudents() {
    console.log("comboStudents .................");
    fetch('/api/v1/student')
        .then(response => response.json())
        .then(json => outputComboStudents(json))
        .catch(error => console.error("Ошибка чтения студентов ", error));
}

function outputComboStudents(students) {
    console.log("outputComboStudents", students);
    let select = document.getElementById("studentId");

    students.forEach(student => {
        let option = document.createElement("option");
        option.value = student.id;
        option.text = student.fullName;
        select.append(option);
    });

}

function outputStudents(students) {
    console.log("outputStudents", students);
    let table = document.getElementById("table-items");
    students.forEach(student => {
        let row = document.createElement("tr");

        row.setAttribute("id", student.id);
        row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.studentname}</td>
            <td>${student.fullName}</td>
            <td>
            <h5><span class="badge bg-warning">${student.roleTitles}</span></h5>
            </td>
            <td>${student.phone == null ? '' : student.phone}</td>
            <td>${student.email}</td>                        
            <td><a href="/student/edit?id=${student.id}">Изменить</a></td>
            <td><button onclick="deleteStudent(${student.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function deleteStudent(id) {
    console.log('deleteStudent ....................', id);
    fetch(`api/v1/student/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed student ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления студента ", error));
}

function loadStudent() {

    comboRoles();

    const id = document.getElementById("id-input").value;
    console.log("Загрузка студента c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/student/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(student => {
            console.log('Получен пользователь', student);
            document.getElementById('lastname-input').value = student.lastName;
            document.getElementById('firstname-input').value = student.firstName;
            document.getElementById('middlename-input').value = student.middleName;
            document.getElementById('studentname-input').value = student.studentname;
            document.getElementById('phone-input').value = student.phone;
            document.getElementById('email-input').value = student.email;

            const roles = document.getElementById("roleId")
            const roleIds = student.roleDtos;
            console.log('roleIds', roleIds);

            for (let i = 0; i < roles.options.length; i++) {
                let optionValue = roles.options[i].value;
                roles.options[i].selected = roleIds.indexOf(optionValue) >= 0;
            }

        })
        .catch(error => console.error("Ошибка загрузки студента по id ", error));
}

function getInputText(elementId) {
    return document.getElementById(elementId).value;
}

function saveStudent() {
    console.log('saveStudent ................');
    let id = getInputText("id-input");
    const method = id ? "PUT" : "POST";

    const roles = document.getElementById("roleId").options;
    const roleIds = Array.from(roles)
        .filter(role => role.selected)
        .map(item => item.value);

    console.log('roleIds', roleIds);

    const student = {
        id: id,
        studentname: getInputText("studentname-input"),
        lastName: getInputText("lastname-input"),
        firstName: getInputText("firstname-input"),
        middleName: getInputText("middlename-input"),
        phone: getInputText("phone-input"),
        email: getInputText("email-input"),
        roleDtos: roleIds
    };

    console.log('save student', student);

    fetch("/api/v1/student", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(student)
    })
        .then(response => {
            console.log('response .... ', response);
            if (response.ok) {
                window.location.href = "/student";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения студента", error)
        });
}

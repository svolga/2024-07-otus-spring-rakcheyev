function getCurrentDateTime() {
    return moment().format("YYYY-MM-DDThh:00:00");
}

function getInputText(elementId) {
    return document.getElementById(elementId).value;
}

function numberFormat(num) {
    return num.toLocaleString().replace(/,/g, " ");
}

function getDtoFromSelect(id){
    const control = document.getElementById(id);
    return {
        id: control.value,
        name: control.options[control.selectedIndex].text
    };
}

// ******************************** Авторы ********************************
function showAuthors() {
    console.log("showAuthors .................");
    fetch('/api/v1/author')
        .then(response => response.json())
        .then(json => outputAuthors(json))
        .catch(error => console.error("Ошибка чтения авторов ", error));
}

function comboAuthors() {
    console.log("comboAuthors .................");
    fetch('/api/v1/author')
        .then(response => response.json())
        .then(json => outputComboAuthors(json))
        .catch(error => console.error("Ошибка чтения авторов ", error));
}

function outputComboAuthors(authors) {
    console.log("outputComboAuthors", authors);
    let select = document.getElementById("authorId");

    authors.forEach(author => {
        let option = document.createElement("option");
        option.value = author.id;
        option.text = author.fullName;
        select.append(option);
    });

}

function outputAuthors(authors) {
    console.log("outputAuthors", authors);
    let table = document.getElementById("table-items");
    authors.forEach(author => {
        let row = document.createElement("tr");
        row.setAttribute("id", author.id);
        row.innerHTML = `
            <td>${author.id}</td>
            <td>${author.fullName}</td>
            <td><a href="/author/edit?id=${author.id}">Изменить</a></td>
            <td><button onclick="deleteAuthor(${author.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function deleteAuthor(id) {
    console.log('deleteAuthor ....................', id);
    fetch(`api/v1/author/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed author ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления автора ", error));
}

function loadAuthor() {
    const id = document.getElementById("id-input").value;
    console.log("Загрузка автора c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/author/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(author => {
            console.log('Получен автор', author);
            document.getElementById('author-name-input').value = author.fullName;
        })
        .catch(error => console.error("Ошибка загрузки автора по id ", error));
}

function saveAuthor() {
    console.log('saveAuthor ................');

    const id = document.getElementById("id-input").value;
    const fullName = document.getElementById("author-name-input").value;
    const method = id ? "PUT" : "POST";

    const author = {
        id: id,
        fullName: fullName
    };

    fetch("/api/v1/author", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(author)
    })
        .then(response => {
            console.log('response', response.json());
            if (response.ok) {
                window.location.href = "/author";
            }
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения автора", error)
        });
}

// ************************************ Жанры *****************************
function showGenres() {
    console.log("showGenres .................");
    fetch('/api/v1/genre')
        .then(response => response.json())
        .then(json => outputGenres(json))
        .catch(error => console.error("Ошибка чтения жанра ", error));
}

function comboGenres() {
    console.log("comboGenres .................");
    fetch('/api/v1/genre')
        .then(response => response.json())
        .then(json => outputComboGenres(json))
        .catch(error => console.error("Ошибка чтения жанров ", error));
}

function outputComboGenres(genres) {
    console.log("outputComboGenres", genres);
    let select = document.getElementById("genreId");

    genres.forEach(genre => {
        let option = document.createElement("option");
        option.value = genre.id;
        option.text = genre.name;
        select.append(option);
    });
}

function outputGenres(genres) {
    console.log("outputGenres", genres);
    let table = document.getElementById("table-items");
    genres.forEach(genre => {
        let row = document.createElement("tr");
        row.setAttribute("id", genre.id);
        row.innerHTML = `
            <td>${genre.id}</td>
            <td>${genre.name}</td>
            <td><a href="/genre/edit?id=${genre.id}">Изменить</a></td>
            <td><button onclick="deleteGenre(${genre.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function loadGenre() {
    const id = document.getElementById("id-input").value;
    console.log("Загрузка жанра c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/genre/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(genre => {
            console.log('Получен жанр', genre);
            document.getElementById('genre-name-input').value = genre.name;
        })
        .catch(error => console.error("Ошибка загрузки жанра по id ", error));
}

function deleteGenre(id) {
    console.log('deleteGenre ....................', id);
    fetch(`api/v1/genre/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed genre ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления жанра ", error));
}

function saveGenre() {
    console.log('saveGenre ................');

    const id = document.getElementById("id-input").value;
    const name = document.getElementById("genre-name-input").value;
    const method = id ? "PUT" : "POST";

    const genre = {
        id: id,
        name: name
    };

    fetch("/api/v1/genre", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(genre)
    })
        .then(response => {
            console.log('response', response.json());
            if (response.ok) {
                window.location.href = "/genre";
            }
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения жанра", error)
        });
}


// ******************************** Книги ************************************
function showBooks() {
    console.log("showBooks .................");
    fetch('/api/v1/book')
        .then(response => response.json())
        .then(json => outputBooks(json))
        .catch(error => console.error("Ошибка чтения книг ", error));
}

function outputBooks(books) {
    console.log("outputBooks", books);
    let table = document.getElementById("table-items");
    books.forEach(book => {
        let row = document.createElement("tr");
        row.setAttribute("id", book.id);
        row.innerHTML = `
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.genreTitles}</td>
            <td>${book.authorTitle}</td>
            <td><a href="/book/edit?id=${book.id}">Изменить</a></td>
            <td><button onclick="deleteBook(${book.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function deleteBook(id) {
    console.log('deleteBook ....................', id);
    fetch(`api/v1/book/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed book ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления книги ", error));
}

function saveBook() {
    console.log('saveBook ................');

    const id = document.getElementById("id-input").value;
    const name = document.getElementById("book-name-input").value;
    const method = id ? "PUT" : "POST";

    const author = document.getElementById("authorId");
    const authorDto = {
        id: author.value,
        fullName: author.options[author.selectedIndex].text
    };

    console.log('authorDto', authorDto);

    const genres = document.getElementById("genreId").options;
    const genreIds = Array.from(genres)
        .filter(genre => genre.selected)
        .map(item => item.value);

    console.log('genreIds', genreIds);

    const book = {
        id: id,
        title: name,
        authorDto: authorDto,
        genreDtos: genreIds
    };

    console.log('book', book);

    fetch("/api/v1/book", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(book)
    })
        .then(response => {
            console.log('response', response.json());
            if (response.ok) {
                window.location.href = "/";
            }
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения книги", error)
        });
}

function loadBook() {

    comboGenres();
    comboAuthors();

    const id = document.getElementById("id-input").value;
    console.log("Загрузка книги c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/book/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(book => {
            console.log('Получена книга', book);

            document.getElementById("authorId").value = book.authorDto.id;
            const genres = document.getElementById("genreId")
            const genreIds = book.genreDtos;

            console.log('genreIds', genreIds);
            for (let i = 0; i < genres.options.length; i++) {
                let optionValue = Number.parseInt(genres.options[i].value)
                genres.options[i].selected = genreIds.indexOf(optionValue) >= 0;
            }

            document.getElementById('book-name-input').value = book.title;
        })
        .catch(error => console.error("Ошибка загрузки книги по id ", error));
}


// ************************************** Курсы *******************************
function showCourses() {
    console.log("showCourses .................");
    fetch('/api/v1/course')
        .then(response => response.json())
        .then(json => outputCourses(json))
        .catch(error => console.error("Ошибка чтения курсов ", error));
}

function comboCourses() {
    console.log("comboCourse .................");
    fetch('/api/v1/course')
        .then(response => response.json())
        .then(json => outputComboCourses(json))
        .catch(error => console.error("Ошибка чтения курсов ", error));
}

function outputComboCourses(courses) {
    console.log("outputComboCourses", courses);
    let select = document.getElementById("courseId");

    courses.forEach(course => {
        let option = document.createElement("option");
        option.value = course.id;
        option.text = course.name;
        select.append(option);
    });
}

function outputCourses(courses) {
    console.log("outputCourses", courses);
    let table = document.getElementById("table-items");
    courses.forEach(course => {
        let row = document.createElement("tr");
        row.setAttribute("id", course.id);
        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name} <p><code>${course.info}</code></p></td>
            <td>${numberFormat(course.price)}</td>
            <td><a href="/course/edit?id=${course.id}">Изменить</a></td>
            <td><button onclick="deleteCourse(${course.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function loadCourse() {

    const id = document.getElementById("id-input").value;
    console.log("Загрузка курса c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/course/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(course => {
            console.log('Получен курс', course);
            document.getElementById('name-input').value = course.name;
            document.getElementById('info-input').value = course.info;
            document.getElementById('price-input').value = course.price;
        })
        .catch(error => console.error("Ошибка загрузки курса по id ", error));
}

function deleteCourse(id) {
    console.log('deleteCourse ....................', id);
    fetch(`api/v1/course/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed course ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления курса ", error));
}

function saveCourse() {
    console.log('saveCourse ................');

    const id = document.getElementById("id-input").value;
    const price = document.getElementById("price-input").value;
    const method = id ? "PUT" : "POST";

    const course = {
        id: id,
        name: document.getElementById("name-input").value,
        info: document.getElementById("info-input").value,
        price: price
    };

    fetch("/api/v1/course", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(course)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/course";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения курса", error)
        });
}

// ************************************** Группы *******************************
function showGroups() {
    console.log("showGroups .................");
    fetch('/api/v1/group')
        .then(response => response.json())
        .then(json => outputGroups(json))
        .catch(error => console.error("Ошибка чтения курсов ", error));
}

function comboGroups() {
    console.log("comboGroup .................");
    fetch('/api/v1/group')
        .then(response => response.json())
        .then(json => outputComboGroups(json))
        .catch(error => console.error("Ошибка чтения групп ", error));
}

function outputComboGroups(groups) {
    console.log("outputComboGroups", groups);
    let select = document.getElementById("groupId");

    groups.forEach(group => {
        let option = document.createElement("option");
        option.value = group.id;
        option.text = group.name;
        select.append(option);
    });
}

function outputGroups(groups) {
    console.log("outputGroups", groups);
    let table = document.getElementById("table-items");
    groups.forEach(group => {
        let row = document.createElement("tr");
        row.setAttribute("id", group.id);

        row.innerHTML = `
            <td>${group.id}</td>
            <td>${group.name} <p><code>${group.info}</code></p></td>
            <td>${group.courseName}</td>
            <td>${group.startAt == null ? '' : group.startAt}</td>
            <td>${group.endAt == null ? '' : group.endAt}</td>
            <td><a href="/group/edit?id=${group.id}">Изменить</a></td>
            <td><button onclick="deleteGroup(${group.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function loadGroup() {

    comboCourses();

    const id = document.getElementById("id-input").value;
    console.log("Загрузка группы c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/group/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(group => {
            console.log('Получена группа', group);

            document.getElementById('group-name-input').value = group.name;
            document.getElementById('group-info-input').value = group.info;
            document.getElementById('group-start-input').value = group.startAt;
            document.getElementById('group-end-input').value = group.endAt;
            document.getElementById("courseId").value = group.courseDto == null ? 0 : group.courseDto.id;

        })
        .catch(error => console.error("Ошибка загрузки курса по id ", error));
}

function deleteGroup(id) {
    console.log('deleteGroup ....................', id);
    fetch(`api/v1/group/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed group ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления курса ", error));
}

function saveGroup() {
    console.log('saveGroup ................');

    const id = document.getElementById("id-input").value;
    const name = document.getElementById("group-name-input").value;
    const info = document.getElementById("group-info-input").value;
    const startAt = document.getElementById("group-start-input").value;
    const endAt = document.getElementById("group-end-input").value;

    const course = document.getElementById("courseId");
    const courseDto = {
        id: course.value,
        name: course.options[course.selectedIndex].text
    };

    console.log('courseDto', courseDto);

    const method = id ? "PUT" : "POST";

    const group = {
        id: id,
        name: name,
        info: info,
        startAt: startAt,
        endAt: endAt,
        courseDto: courseDto,
    };

    console.log('save group', group);

    fetch("/api/v1/group", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(group)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/group";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения курса", error)
        });
}

// ************************************** Преподаватели *******************************

function showTeachers() {
    console.log("showTeachers .................");
    fetch('/api/v1/teacher')
        .then(response => response.json())
        .then(json => outputTeachers(json))
        .catch(error => console.error("Ошибка чтения преподавателей ", error));
}

function comboTeachers() {
    console.log("comboTeachers .................");
    fetch('/api/v1/teacher')
        .then(response => response.json())
        .then(json => outputComboTeachers(json))
        .catch(error => console.error("Ошибка чтения преподавателей ", error));
}

function outputComboTeachers(teachers) {
    console.log("outputComboTeachers", teachers);
    let select = document.getElementById("teacherId");

    teachers.forEach(teacher => {
        let option = document.createElement("option");
        option.value = teacher.id;
        option.text = teacher.fullName;
        select.append(option);
    });

}

function outputTeachers(teachers) {
    console.log("outputTeachers", teachers);
    let table = document.getElementById("table-items");
    teachers.forEach(teacher => {
        let row = document.createElement("tr");
        row.setAttribute("id", teacher.id);
        row.innerHTML = `
            <td>${teacher.id}</td>
            <td>${teacher.username}</td>
            <td>${teacher.fullName}
            <td><b>${teacher.profileInfo == null ? '' : teacher.profileInfo}</b</td>
            <td>${teacher.phone}</td>
            <td>${teacher.email}</td>                        
            <td><a href="/teacher/edit?id=${teacher.id}">Изменить</a></td>
        `;

        table.append(row);
    });
}


function loadTeacher() {
    const id = document.getElementById("id-input").value;
    console.log("Загрузка преподавателя c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/teacher/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(teacher => {
            console.log('Получен преподаватель', teacher);
            document.getElementById('fullname-input').value = teacher.fullName;
            document.getElementById('username-input').value = teacher.username;
            document.getElementById('phone-input').value = teacher.phone;
            document.getElementById('email-input').value = teacher.email;
            document.getElementById('info-input').value = teacher.profileInfo;
        })
        .catch(error => console.error("Ошибка загрузки преподавателя по id ", error));
}


function saveTeacher() {
    console.log('saveTeacher ................');
    let id = getInputText("id-input");
    const method = id ? "PUT" : "POST";

    const teacher = {
        id: id,
        profileInfo: getInputText("info-input"),
    };

    console.log('save teacher', teacher);
    console.log('method', method);

    fetch("/api/v1/teacher", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(teacher)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/teacher";
                return false;
            }
            return response;

        })
        .then(response => response.json())
        .then(value => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения преподавателя", error)
        });
}


// ************************************** Пользователь *******************************
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
            <td>${user.username}</td>
            <td>${user.fullName}</td>
            <td>
            <h6><span class="badge bg-info">${user.roleTitles}</span></h6>
            </td>
            <td>${user.phone == null ? '' : user.phone}</td>
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

    comboRoles();

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
            document.getElementById('username-input').value = user.username;
            document.getElementById('phone-input').value = user.phone;
            document.getElementById('email-input').value = user.email;

            const roles = document.getElementById("roleId")
            const roleIds = user.roleDtos;
            console.log('roleIds', roleIds);

            for (let i = 0; i < roles.options.length; i++) {
                let optionValue = roles.options[i].value;
                roles.options[i].selected = roleIds.indexOf(optionValue) >= 0;
            }

        })
        .catch(error => console.error("Ошибка загрузки преподавателя по id ", error));
}


function saveUser() {
    console.log('saveUser ................');
    let id = getInputText("id-input");
    const method = id ? "PUT" : "POST";

    const roles = document.getElementById("roleId").options;
    const roleIds = Array.from(roles)
        .filter(role => role.selected)
        .map(item => item.value);

    console.log('roleIds', roleIds);

    const user = {
        id: id,
        username: getInputText("username-input"),
        lastName: getInputText("lastname-input"),
        firstName: getInputText("firstname-input"),
        middleName: getInputText("middlename-input"),
        phone: getInputText("phone-input"),
        email: getInputText("email-input"),
        roleDtos: roleIds
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
            console.log('response .... ', response);
            if (response.ok) {
                window.location.href = "/user";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения пользователя", error)
        });
}

// ****************************** Роли ************************************
function comboRoles() {
    console.log("comboRoles .................");
    fetch('/api/v1/role')
        .then(response => response.json())
        .then(json => outputComboRoles(json))
        .catch(error => console.error("Ошибка чтения ролей ", error));
}

function outputComboRoles(roles) {
    console.log("outputComboRoles", roles);
    let select = document.getElementById("roleId");

    roles.forEach(role => {
        let option = document.createElement("option");
        option.value = role.name;
        option.text = role.name;
        select.append(option);
    });
}

// *********************************** ApuiError ********************************************
function errorInfo(response) {
    console.log('errorInfo', response);

    console.log('response.message ............', response.message);
    if (typeof response.message === 'undefined') {
        document.getElementById('errorId').innerText = "Ошибка при сохранении";
    } else {
        document.getElementById('errorId').innerText = response.message;
    }
}

// *********************************** Студенты ********************************************
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
            <td>${student.username}</td>
            <td>${student.fullName}</td>
            <td>${student.phone == null ? '' : student.phone}</td>
            <td>${student.email}</td>                        
        `;
        table.append(row);
    });
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


// ************************************** Занятия *******************************
function showTasks() {
    console.log("showTasks .................");
    fetch('/api/v1/task')
        .then(response => response.json())
        .then(json => outputTasks(json))
        .catch(error => console.error("Ошибка чтения занятий ", error));
}

function comboTasks() {
    console.log("comboTask .................");
    fetch('/api/v1/task')
        .then(response => response.json())
        .then(json => outputComboTasks(json))
        .catch(error => console.error("Ошибка чтения занятий ", error));
}

function outputComboTasks(tasks) {
    console.log("outputComboTasks", tasks);
    let select = document.getElementById("taskId");

    tasks.forEach(task => {
        let option = document.createElement("option");
        option.value = task.id;
        option.text = task.name;
        select.append(option);
    });
}

function outputTasks(tasks) {
    console.log("outputTasks", tasks);
    let table = document.getElementById("table-items");
    tasks.forEach(task => {
        let row = document.createElement("tr");
        row.setAttribute("id", task.id);

        row.innerHTML = `
            <td>${task.id}</td>
            <td>${task.name}</td>
            <td>${task.info}
            
            <div id = "taskMaterials${task.id}" class = "taskMaterials"></div>
            
            </td>
            <td>${task.startAt == null ? '' : task.startAt}</td>
            <td>${task.teacherFullName}</td>
            <td>${task.groupName}</td>
            <td><b>${task.courseName}</b></td>
            <td><a href="/task/edit?id=${task.id}">Изменить</a></td>
            <td><button onclick="deleteTask(${task.id})" class="link">Удалить</button></td>
        `;
        table.append(row);

        outputTaskMaterials(task, task.taskMaterialDtoList);

    });
}

function deleteTaskMaterial(id) {

    console.log('remove task material', id);

    let divId = `taskMaterial${id}`;
    console.log('remove task material divId', divId);

    fetch(`api/v1/task-material/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed taskMaterial ${id}`);
            document.getElementById(divId).remove();
        })
        .catch(error => console.error("Ошибка удаления taskMaterial ", error));
}


function saveMaterial() {
    console.log('Сохранение материала');

    const taskMaterial = {
        taskId: document.getElementById("materialTaskId").value,
        name: document.getElementById("materialName").value,
        url: document.getElementById("materialUrl").value
    };

    fetch("/api/v1/task-material", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(taskMaterial)
    })
        .then(response => {
            if (response.ok) {
                console.log('response', response);
                $('#formMaterial').modal('hide');
            }
            return response;
        })
        .then(response => response.json())
        .then((taskMaterialDto) => {
            outputTaskMaterial(taskMaterialDto);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения материала", error)
        });
}


// При открытии модального окна с добавлением материалов
$(document).on('show.bs.modal', '#formMaterial', function (event) {
    console.log('show.bs.modal .........................................................');
    var button = $(event.relatedTarget); // Button that triggered the modal
    var taskName = button.data('taskname'); // Extract info from data-* attributes
    var taskId = button.data('taskid'); // Extract info from data-* attributes
    document.getElementById("nameMaterial").innerHTML = taskName;
    document.getElementById("materialTaskId").value = taskId;

    document.getElementById("materialName").value = '';
    document.getElementById("materialUrl").value = '';

})

function getTaskMaterialsContainer(taskId) {
    return document.getElementById(`taskMaterials${taskId}`);
}

function outputTaskMaterials(task, taskMaterialDtoList) {

    var taskMaterialsContainer = getTaskMaterialsContainer(task.id);

    console.log('task ...........', task);

    taskMaterialsContainer.innerHTML = `<div><a href="#"  
        data-toggle="modal" 
        data-target="#formMaterial" 
        data-taskid="${task.id}" 
        data-taskname="${task.name}"><i class="bi bi-plus"></i> Добавить материал</a></div>`;

    for (let i = 0; i < taskMaterialDtoList.length; i++) {
        outputTaskMaterial(taskMaterialDtoList[i]);
    }
}

function outputTaskMaterial(taskMaterialDto) {

    var taskMaterialsContainer = getTaskMaterialsContainer(taskMaterialDto.taskId);
    var id = taskMaterialDto.id;
    let div = document.createElement("div");
    div.id = `taskMaterial${id}`;
    div.innerHTML = `<a href="#" onclick="deleteTaskMaterial(${id}); return false;"><i class="bi bi-x"></i></a>
            <a href="${taskMaterialDto.url}" target="_blank">${taskMaterialDto.name}</a>`;

    taskMaterialsContainer.append(div);
}

function loadTask() {

    comboTeachers();
    comboGroups();

    const id = document.getElementById("id-input").value;
    console.log("Загрузка занятия c id --> ", id);
    if (!id) {
        document.getElementById('start-input').value = getCurrentDateTime();
        return;
    }

    fetch(`/api/v1/task/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(task => {
            console.log('Получено занятие', task);
            document.getElementById('name-input').value = task.name;
            document.getElementById('info-input').value = task.info;
            document.getElementById('start-input').value = task.startAt ?? getCurrentDateTime();
            document.getElementById("groupId").value = task.groupDto == null ? 0 : task.groupDto.id;
            document.getElementById("teacherId").value = task.teacherDto == null ? 0 : task.teacherDto.id;
        })
        .catch(error => console.error("Ошибка загрузки занятия по id ", error));
}

function deleteTask(id) {
    console.log('deleteTask ....................', id);
    fetch(`api/v1/task/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed task ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления занятия ", error));
}

function saveTask() {
    console.log('saveTask ................');

    const id = document.getElementById("id-input").value;

    const teacher = document.getElementById("teacherId");
    const teacherDto = {
        id: teacher.value,
        name: teacher.options[teacher.selectedIndex].text
    };

    const group = document.getElementById("groupId");
    const groupDto = {
        id: group.value,
        name: group.options[group.selectedIndex].text
    };

    const method = id ? "PUT" : "POST";

    const task = {
        id: id,
        name: document.getElementById("name-input").value,
        info: document.getElementById("info-input").value,
        teacherDto: teacherDto,
        groupDto: groupDto,
        startAt: document.getElementById("start-input").value,
    };

    console.log('save task', task);

    fetch("/api/v1/task", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/task";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения занятия", error)
        });
}


// ************************************** ДЗ *******************************
function showHomeworks() {
    console.log("showHomeworks .................");
    fetch('/api/v1/homework')
        .then(response => response.json())
        .then(json => outputHomeworks(json))
        .catch(error => console.error("Ошибка чтения ДЗ ", error));
}

function comboHomeworks() {
    console.log("comboHomework .................");
    fetch('/api/v1/homework')
        .then(response => response.json())
        .then(json => outputComboHomeworks(json))
        .catch(error => console.error("Ошибка чтения ДЗ ", error));
}

function outputComboHomeworks(homeworks) {
    console.log("outputComboHomeworks", homeworks);
    let select = document.getElementById("homeworkId");

    homeworks.forEach(homework => {
        let option = document.createElement("option");
        option.value = homework.id;
        option.text = homework.name;
        select.append(option);
    });
}

function outputHomeworks(homeworks) {
    console.log("outputHomeworks", homeworks);
    let table = document.getElementById("table-items");
    homeworks.forEach(homework => {
        let row = document.createElement("tr");
        row.setAttribute("id", homework.id);

        row.innerHTML = `
            <td>${homework.id}</td>
            <td>${homework.name} <p><code>${homework.info}</code></p></td>
            <td>${homework.taskName}</td>
            <td>${homework.groupName}</td>
            <td>${homework.courseName}</td>
            <td>${homework.mark}</td>
            <td><a href="/homework/edit?id=${homework.id}">Изменить</a></td>
            <td><button onclick="deleteHomework(${homework.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function loadHomework() {

    comboTasks();

    const id = document.getElementById("id-input").value;
    console.log("Загрузка ДЗ c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/homework/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(homework => {
            console.log('Получено ДЗ', homework);

            document.getElementById('name-input').value = homework.name;
            document.getElementById('info-input').value = homework.info;
            document.getElementById('mark-input').value = homework.mark;
            document.getElementById("taskId").value = homework.taskDto == null ? 0 : homework.taskDto.id;

        })
        .catch(error => console.error("Ошибка загрузки ДЗ по id ", error));
}

function deleteHomework(id) {
    console.log('deleteHomework ....................', id);
    fetch(`api/v1/homework/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed homework ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления ДЗ ", error));
}

function saveHomework() {
    console.log('saveHomework ................');

    const id = document.getElementById("id-input").value;
    const name = document.getElementById("name-input").value;
    const info = document.getElementById("info-input").value;
    const mark = document.getElementById("mark-input").value;

    const task = document.getElementById("taskId");
    const taskDto = {
        id: task.value,
        name: task.options[task.selectedIndex].text
    };

    console.log('taskDto', taskDto);

    const method = id ? "PUT" : "POST";

    const homework = {
        id: id,
        name: name,
        info: info,
        mark: mark,
        taskDto: taskDto,
    };

    console.log('save homework', homework);

    fetch("/api/v1/homework", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(homework)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/homework";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения ДЗ", error)
        });
}


// ************************************** Статусы ДЗ *******************************
function showRanks() {
    console.log("showRanks .................");
    fetch('/api/v1/rank')
        .then(response => response.json())
        .then(json => outputRanks(json))
        .catch(error => console.error("Ошибка чтения статуса ", error));
}

function comboRanks() {
    console.log("comboRank .................");
    fetch('/api/v1/rank')
        .then(response => response.json())
        .then(json => outputComboRanks(json))
        .catch(error => console.error("Ошибка чтения статуса ", error));
}

function outputComboRanks(ranks) {
    console.log("outputComboRanks", ranks);
    let select = document.getElementById("rankId");

    ranks.forEach(rank => {
        let option = document.createElement("option");
        option.value = rank.id;
        option.text = rank.name;
        select.append(option);
    });
}

function outputRanks(ranks) {
    console.log("outputRanks", ranks);
    let table = document.getElementById("table-items");
    ranks.forEach(rank => {
        let row = document.createElement("tr");
        row.setAttribute("id", rank.id);
        row.innerHTML = `
            <td>${rank.id}</td>
            <td>${rank.ukey}</td>            
            <td style="background-color: ${rank.color}">${rank.name}</td>
            <td style="background-color: ${rank.color}">${rank.color}</td>
            <td><a href="/rank/edit?id=${rank.id}">Изменить</a></td>
            <td><button onclick="deleteRank(${rank.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}

function loadRank() {

    const id = document.getElementById("id-input").value;
    console.log("Загрузка статуса c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/rank/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(rank => {
            console.log('Получен курс', rank);
            document.getElementById('name-input').value = rank.name;
            document.getElementById('ukey-input').value = rank.ukey;
            document.getElementById('color-input').value = rank.color;
        })
        .catch(error => console.error("Ошибка загрузки статуса по id ", error));
}

function deleteRank(id) {
    console.log('deleteRank ....................', id);
    fetch(`api/v1/rank/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed rank ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления курса ", error));
}

function saveRank() {
    console.log('saveRank ................');

    const id = getInputText("id-input");
    const method = id ? "PUT" : "POST";

    const rank = {
        id: id,
        name: getInputText("name-input"),
        ukey: getInputText("ukey-input"),
        color: getInputText("color-input")
    };

    fetch("/api/v1/rank", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(rank)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/rank";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения статуса", error)
        });
}


// ************************************** Ведомость ДЗ *******************************
function showResults() {
    console.log("showResults .................");
    fetch('/api/v1/result')
        .then(response => response.json())
        .then(json => outputResults(json))
        .catch(error => console.error("Ошибка чтения ведомости ", error));
}

function comboResults() {
    console.log("comboResult .................");
    fetch('/api/v1/result')
        .then(response => response.json())
        .then(json => outputComboResults(json))
        .catch(error => console.error("Ошибка чтения ведомости ", error));
}

function outputComboResults(results) {
    console.log("outputComboResults", results);
    let select = document.getElementById("resultId");

    results.forEach(result => {
        let option = document.createElement("option");
        option.value = result.id;
        option.text = result.name;
        select.append(option);
    });
}

function outputResults(results) {
    console.log("outputResults", results);
    let table = document.getElementById("table-items");
    results.forEach(result => {
        let row = document.createElement("tr");
        row.setAttribute("id", result.id);

        row.innerHTML = `
            <td style="background-color: ${result.rankColor}">${result.id}</td>
            <td style="background-color: ${result.rankColor}">${result.homeworkName}</td>
            <td style="background-color: ${result.rankColor}">${result.studentFullName}</td>
            <td style="background-color: ${result.rankColor}">${result.rankName}</td>
            <td style="background-color: ${result.rankColor}">${result.step}</td>
            <td style="background-color: ${result.rankColor}">${result.score}</td>
            <td><a href="/result/edit?id=${result.id}">Изменить</a></td>
            <td><button onclick="deleteResult(${result.id})" class="link">Удалить</button></td>
        `;
        table.append(row);
    });
}


function loadResult() {

    comboStudents();
    comboHomeworks();
    comboRanks();

    const id = document.getElementById("id-input").value;
    console.log("Загрузка результата c id --> ", id);
    if (!id) {
        return;
    }

    fetch(`/api/v1/result/${id}`, {
        method: "GET",
    })
        .then(response => response.json())
        .then(result => {
            console.log('Получена результат по ДЗ', result);
            document.getElementById("rankId").value = result.rankDto == null ? 0 : result.rankDto.id;
            document.getElementById("studentId").value = result.studentDto == null ? 0 : result.studentDto.id;
            document.getElementById("homeworkId").value = result.homeworkDto == null ? 0 : result.homeworkDto.id;
            document.getElementById('score-input').value = result.score;
            document.getElementById('step-input').value = result.step;

        })
        .catch(error => console.error("Ошибка загрузки результата по id ", error));
}

function deleteResult(id) {
    console.log('deleteResult ....................', id);
    fetch(`api/v1/result/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            console.log(`removed result ${id}`);
            document.getElementById(id).remove();
        })
        .catch(error => console.error("Ошибка удаления результата ", error));
}


function saveResult() {
    console.log('saveResult ................');

    const id = document.getElementById("id-input").value;
    const method = id ? "PUT" : "POST";

    const result = {
        id: id,
        step: getInputText("step-input"),
        score: getInputText("score-input"),
 	    rankDto: getDtoFromSelect("rankId"),
        studentDto:  getDtoFromSelect("studentId"),
		homeworkDto: getDtoFromSelect("homeworkId")
    };

    console.log('save result', result);

    fetch("/api/v1/result", {
        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(result)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = "/result";
                return false;
            }
            return response;
        })
        .then(response => response.json())
        .then((response) => {
            errorInfo(response);
        })
        .catch(error => {
            console.log("Fetch ошибка сохранения результата", error)
        });
}

//*********************************************** Отчеты ****************************************************
function showReportBest() {
    console.log("showReportBest results .................");
    fetch('/api/v1/best')
        .then(response => response.json())
        .then(json => outputReportBest(json))
        .catch(error => console.error("Ошибка чтения отчета с лучшими оценками ", error));
}


function outputReportBest(datas) {
    console.log("outputReportBest", datas);
    let table = document.getElementById("table-items");
    datas.forEach(data => {
        let row = document.createElement("tr");

        row.setAttribute("id", data.id);
        row.innerHTML = `
            <td>${data.userId}</td>
            <td>${data.fullName}</td>
            <td>${data.score}</td>
        `;
        table.append(row);
    });
}


function showReportMiddle() {
    console.log("showReportBest results .................");
    fetch('/api/v1/middle')
        .then(response => response.json())
        .then(json => outputReportMiddle(json))
        .catch(error => console.error("Ошибка чтения отчета с лучшими оценками ", error));
}


function outputReportMiddle(datas) {
    console.log("outputReportBest", datas);
    let table = document.getElementById("table-items");
    datas.forEach(data => {
        let row = document.createElement("tr");

		var score = data.score.toFixed(2);

        row.setAttribute("id", data.id);
        row.innerHTML = `
            <td>${data.userId}</td>
            <td>${data.fullName}</td>
            <td>${score}</td>
        `;
        table.append(row);
    });
}


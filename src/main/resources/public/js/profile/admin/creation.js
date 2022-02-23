const xmlHttpRequest = new XMLHttpRequest();
const formControlElements = document.getElementsByClassName("form-control");

onload = function () {
    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }

    setAdminModification();

    document.getElementById("headline").innerHTML = "Create an account";
    document.getElementById("submit").innerHTML = "Register";

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(formControlElement.id, formControlElement.value);
        }

        formControlElement.value = localStorage.getItem(formControlElement.id);
    }

    document.getElementById("submit").onclick = function () {
        if (!validate()) {
            return;
        }

        const body = {};
        body.uuid = null;

        const roleValue = document.getElementById("roleValue").value;

        for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
            if (value === roleValue) {
                body.roleUuid = key;
            }
        }

        body.login = document.getElementById("login").value;
        body.passwordUser = document.getElementById("password").value;
        body.email = document.getElementById("email").value;
        body.firstName = document.getElementById("firstName").value;
        body.lastName = document.getElementById("secondName").value;
        body.birthday = document.getElementById("birthday").value;

        xmlHttpRequest.open("POST", usersApiUrl);
        xmlHttpRequest.setRequestHeader("Content-Type", "application/vnd.rozetka.user+json");
        xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
        xmlHttpRequest.send(JSON.stringify(body));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            for (const formControlElement of formControlElements) {
                localStorage.removeItem(formControlElement.id);
            }

            alert("success", "A user has been successfully created");
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", "Some errors occurred while creating a user");
        }
    }
}
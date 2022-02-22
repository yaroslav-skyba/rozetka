const xmlHttpRequest = new XMLHttpRequest();
const formControlElements = document.getElementsByClassName("form-control");

onload = function () {
    setAdminModification();

    document.getElementById("headline").innerHTML = "Create an account";
    document.getElementById("submit").innerHTML = "Register";

    for (const element of formControlElements) {
        element.onchange = function () {
            localStorage.setItem(element.id, element.value);
        }

        element.value = localStorage.getItem(element.id);
    }

    document.getElementById("submit").onclick = function () {
        const body = {};

        const roleValue = document.getElementById("roleValue").value;

        for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesKey)))) {
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

        if (Object.values(body).filter(value => !value || value.length > 255).length) {
            return;
        }

        body.uuid = null;

        xmlHttpRequest.open("POST", usersApiUrl);
        xmlHttpRequest.setRequestHeader("Content-Type", "application/vnd.rozetka.user+json");
        xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtKey));
        xmlHttpRequest.send(JSON.stringify(body));
    }

    xmlHttpRequest.onreadystatechange = function () {
        if (xmlHttpRequest.readyState === 4) {
            if (xmlHttpRequest.status === 201) {
                for (const element of formControlElements) {
                    localStorage.removeItem(element.id);
                }

                alert("success", "A user has been successfully created");
            } else if (xmlHttpRequest.status === 409) {
                alert("danger", "Some errors occurred while creating a user");
            }
        }
    }
}
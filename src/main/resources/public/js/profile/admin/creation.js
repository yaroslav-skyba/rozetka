onload = function () {
    setUserForm();
    setRole();

    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }

    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(creationStorageKeyPrefix + formControlElement.id, formControlElement.value);
        }
    }

    const role = document.getElementById("roleValue");
    role.value = localStorage.getItem(creationStorageKeyPrefix + role.id);

    setUserInputs("Create an account", "Create", creationStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        const password = document.getElementById("password");

        password.required = true;
        document.getElementById("passwordConformation").required = true;

        const body = createModificationRequestBody(null, password.value);

        if (!body) {
            return;
        }

        sendModificationRequest("POST", usersApiUrl, appendRoleToBody(body));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix);
}

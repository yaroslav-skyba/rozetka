let currentUser;

onload = function () {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== userRoleName) {
        location.href = "/";
    }

    currentUser = JSON.parse(localStorage.getItem(currentUserStorageKey));

    setNavigation("../index.html", "../img/logo.png", "../cart.html", "../about.html", "../login.html",
        "../registration.html", "admin/admin.html", "user.html");
    configUserModificationPage();
    setUserEditStorageItems(currentUser);
    setUserFormInputs("Edit your profile", "Save", editStorageKeySuffix);

    submit.onclick = function () {
        sendModificationHttpRequest(createUserToEdit(), "PUT", usersApiUrl + "/" + currentUser[userUuidDtoKey], userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            alert("success", xmlHttpRequest.responseText);

            const requestBody = getUser(currentUser[userUuidDtoKey], currentUser[userRoleUuidDtoKey], password.value);
            requestBody[userRoleUuidDtoKey] = currentUser[userRoleUuidDtoKey];
            localStorage.setItem(currentUserStorageKey, JSON.stringify(requestBody));

            if (localStorage.getItem(editStorageKeySuffix + login.id) !== login.value) {
                xmlHttpRequest.open("POST", authorityApi + "jwts");
                xmlHttpRequest.send(localStorage.getItem(jwtStorageKey));
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 401 || xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }

        for (const formControlElement of formControlElements) {
            localStorage.removeItem(editStorageKeySuffix + formControlElement.id);
        }
    }
}

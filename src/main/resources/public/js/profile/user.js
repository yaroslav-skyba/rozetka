let currentUser;

onload = function () {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== userRoleName) {
        location.href = "/";
    }

    currentUser = JSON.parse(localStorage.getItem(currentUserStorageKey));

    setNavigation("../index.html", "../img/logo.png", "../cart.html", "../about.html", "../login.html",
        "../registration.html", "admin/admin.html", "user.html");
    setUserForm();
    setUserEditStorageItems(currentUser);
    setUserInputs("Edit your profile", "Save", editStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        sendModificationRequest("PUT", usersApiUrl + "/" + currentUser[userUuidDtoKey], createUserEditRequestBody(currentUser),
                                userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            alert("success", xmlHttpRequest.responseText);

            const requestBody = createUserRequestBody(currentUser[userUuidDtoKey], document.getElementById("password").value);
            requestBody[userRoleUuidDtoKey] = currentUser[userRoleUuidDtoKey];
            localStorage.setItem(currentUserStorageKey, JSON.stringify(requestBody));

            if (localStorage.getItem(editStorageKeyPrefix + login.id) !== login.value) {
                xmlHttpRequest.open("POST", authorityApi + "jwts");
                xmlHttpRequest.send(localStorage.getItem(jwtStorageKey));
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 401 || xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }

        for (const formControlElement of formControlElements) {
            localStorage.removeItem(editStorageKeyPrefix + formControlElement.id);
        }
    }
}

const xmlHttpRequest = new XMLHttpRequest();

let currentUser = null;

onload = function () {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== userRoleName) {
        location.href = "/";
    }

    currentUser = JSON.parse(localStorage.getItem(currentUserStorageKey));

    setNavigation("../index.html", "../img/logo.png", "../cart.html", "../about.html", "../login.html",
        "../registration.html", "admin/admin.html", "user.html");
    setUserForm();
    setEditStorageItems(currentUser);
    setUserInputs("Edit your profile", "Save", editStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        sendModificationRequest("PUT", usersApiUrl + "/" + currentUser[userUuidDtoKey], createEditRequestBody(currentUser));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            alert("success", xmlHttpRequest.responseText);

            const login = document.getElementById("login");
            const newCurrentUser = {};
            newCurrentUser[userUuidDtoKey] = currentUser[userUuidDtoKey];
            newCurrentUser[userRoleUuidDtoKey] = currentUser[userRoleUuidDtoKey];
            newCurrentUser[userLoginDtoKey] = login.value;
            newCurrentUser[userPasswordDtoKey] = document.getElementById("password").value;
            newCurrentUser[userEmailDtoKey] = document.getElementById("email").value;
            newCurrentUser[userFirstNameDtoKey] = document.getElementById("firstName").value;
            newCurrentUser[userLastNameDtoKey] = document.getElementById("secondName").value;
            newCurrentUser[userBirthdayDtoKey] = document.getElementById("birthday").value;
            localStorage.setItem(currentUserStorageKey, JSON.stringify(newCurrentUser));

            let isLoginChanged = false;

            if (localStorage.getItem(editStorageKeyPrefix + login.id) !== newCurrentUser[userLoginDtoKey]) {
                isLoginChanged = true;
            }

            for (const formControlElement of formControlElements) {
                localStorage.removeItem(editStorageKeyPrefix + formControlElement.id);
            }

            if (isLoginChanged) {
                xmlHttpRequest.open("POST", jwtsApiUrl);
                xmlHttpRequest.send(localStorage.getItem(jwtStorageKey));
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 401 || xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

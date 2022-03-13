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
            const newCurrentUser = {};
            newCurrentUser[userUuidDtoKey] = currentUser[userUuidDtoKey];
            newCurrentUser[userRoleUuidDtoKey] = currentUser[userRoleUuidDtoKey];
            newCurrentUser[userLoginDtoKey] = document.getElementById("login").value;
            newCurrentUser[userPasswordDtoKey] = document.getElementById("password").value;
            newCurrentUser[userEmailDtoKey] = document.getElementById("email").value;
            newCurrentUser[userFirstNameDtoKey] = document.getElementById("firstName").value;
            newCurrentUser[userLastNameDtoKey] = document.getElementById("secondName").value;
            newCurrentUser[userBirthdayDtoKey] = document.getElementById("birthday").value;

            localStorage.setItem(currentUserStorageKey, JSON.stringify(newCurrentUser));

            for (const formControlElement of formControlElements) {
                localStorage.removeItem(editStorageKeyPrefix + formControlElement.id);
            }

            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

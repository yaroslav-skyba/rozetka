let userLogin;

onload = function () {
    redirectUnauthorized(userRoleName);

    configUserModificationPage("../", "", "admin/");
    setUserFormInputs("Edit your profile", "Save", currentUserStorageKey);

    const currentUser = JSON.parse(localStorage.getItem(currentUserStorageKey));

    userLogin = currentUser[userLoginDtoKey];
    setFormControlElementOnchange(currentUserStorageKey, function () {
        return createUser(currentUser[userUuidDtoKey]);
    });

    submit.onclick = function () {
        sendModificationHttpRequest(getUser(currentUserStorageKey),"PUT", usersApiUrl + "/" + currentUser[userUuidDtoKey], userContentType);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            alert("success", xmlHttpRequest.responseText);

            if (login.value !== userLogin) {
                xmlHttpRequest.open("POST", authorityApi + "jwts");
                xmlHttpRequest.send(localStorage.getItem(jwtStorageKey));
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status > 400) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

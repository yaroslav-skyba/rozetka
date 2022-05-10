onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, currentUserStorageKey);
    setUserPage(
        userRoleName, "", "profile/", "profile/admin/",
        "register", "Register", "POST"
    );
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            localStorage.setItem(successUserCreationHttpResponseStorageKey, xmlHttpRequest.responseText);
            localStorage.clear();

            location.href = "login.html";
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

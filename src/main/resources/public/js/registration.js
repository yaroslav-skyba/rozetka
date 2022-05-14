onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, currentUserStorageKey);
    setUserPage(
        userRoleName, "", "profile/", "profile/admin/",
        "registration", "Register", "POST"
    );
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            localStorage.clear();
            localStorage.setItem(successUserCreationHttpResponseStorageKey, xmlHttpRequest.responseText);

            location.href = "login.html";
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

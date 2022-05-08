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
            alertMessage("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

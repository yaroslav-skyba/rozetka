const userLoginStorageKey = "userLogin";

onload = function () {
    localStorage.setItem(modificationStorageKeyStorageKey, currentUserStorageKey);
    setUserPage(
        userRoleName, "../", "", "admin/", "edit your profile", innerHtmlEditSubmit, "PUT"
    );

    if (localStorage.getItem(userLoginStorageKey) == null) {
        localStorage.setItem(userLoginStorageKey, userLogin.value);
    }
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            alertMessage("success", xmlHttpRequest.responseText);

            if (userLogin.value !== localStorage.getItem(userLoginStorageKey)) {
                xmlHttpRequest.open("POST", authorityApi + "jwts");
                xmlHttpRequest.send(localStorage.getItem(jwtStorageKey));
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
            localStorage.removeItem(userLoginStorageKey);
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

onload = function () {
    redirectWithoutSpecificRole(roleName);

    localStorage.setItem(modificationStorageKeyStorageKey, currentUserStorageKey);
    setUserPage(
        userRoleName, "../", "", "admin/", "edit your profile", innerHtmlEditSubmit, "PUT"
    );
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            alertMessage("success", xmlHttpRequest.responseText);

            if (userLogin.value !== localStorage.getItem(currentUserLoginStorageKey)) {
                sendHttpRequest(
                    "PUT", jwtsApiUrl, "Content-Type", "text/plain", localStorage.getItem(jwtStorageKey)
                );
            }
        } else if (xmlHttpRequest.status === 201) {
            localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
            localStorage.setItem(currentUserLoginStorageKey, userLogin.value);
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

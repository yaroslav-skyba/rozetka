onload = function () {
    redirectWithoutSpecificRole(userRoleName);

    localStorage.setItem(modificationStorageKeyStorageKey, currentUserStorageKey);
    setUserPage(
        userRoleName, "../", "", "admin/", "edit your profile", innerHtmlEditSubmit, "PUT"
    );
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 200) {
            if (xmlHttpRequest.responseURL === usersApiUrl) {
                alertMessage("success", xmlHttpRequest.responseText);

                if (userLogin.value !== localStorage.getItem(currentUserLoginStorageKey)) {
                    sendHttpRequest(
                        "PUT", jwtsApiUrl, "Content-Type", "text/plain", localStorage.getItem(jwtStorageKey)
                    );
                }
            } else if (xmlHttpRequest.responseURL === jwtsApiUrl) {
                localStorage.setItem(jwtStorageKey, xmlHttpRequest.responseText);
                localStorage.setItem(currentUserLoginStorageKey, JSON.parse(localStorage.getItem(currentUserStorageKey))[userLoginDtoKey]);
            }
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

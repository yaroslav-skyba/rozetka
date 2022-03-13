onload = function () {
    const userToEdit = localStorage.getItem(userToEditStorageKey);

    if (!userToEdit) {
        location.href = "/profile/admin/admin.html";
    }

    const userToEditParsed = JSON.parse(userToEdit);

    for (const [key, value] of Object.entries(JSON.parse(localStorage.getItem(rolesStorageKey)))) {
        if (key === userToEditParsed[userRoleUuidDtoKey]) {
            localStorage.setItem(editStorageKeyPrefix + document.getElementById("roleValue").id, value.toString());
            break;
        }
    }

    setUserForm();
    setRole();
    setEditStorageItems(userToEditParsed);
    setAdminForm("Edit an account", "Save", editStorageKeyPrefix);

    document.getElementById("submit").onclick = function () {
        localStorage.removeItem(userToEditStorageKey);
        sendModificationRequest("PUT", usersApiUrl + "/" + userToEditParsed[userUuidDtoKey], createEditRequestBody(userToEditParsed));
    }
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(200, editStorageKeyPrefix);
}

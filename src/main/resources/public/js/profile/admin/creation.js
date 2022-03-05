onload = function () {
    setAdminModification("Create an account", "Create", creationStorageKeyPrefix, "POST", usersApiUrl);
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix, "A user has been successfully created",
        "Some errors occurred while creating a user");
}
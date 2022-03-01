onload = function () {
    setAdminModification();
}

xmlHttpRequest.onreadystatechange = function () {
    setXmlHttpRequest(201, creationStorageKeyPrefix, "A user has been successfully created",
        "Some errors occurred while creating a user");
}
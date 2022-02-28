onload = function () {
    setAdminModification();
}

xmlHttpRequest.onreadystatechange = function () {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === 201) {
            for (const formControlElement of formControlElements) {
                localStorage.removeItem(formControlElement.id);
            }

            alert("success", "A user has been successfully created");
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", "Some errors occurred while creating a user");
        }
    }
}
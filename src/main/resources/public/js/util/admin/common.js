const innerHtmlCreationSubmit = "Create";
const innerHtmlEditSubmit = "Edit";

let submit;

function redirectUnready(storageKey) {
    if (!localStorage.getItem(storageKey)) {
        location.href = "/profile/admin/admin.html";
    }
}

function receiveModificationHttpResponse(successStatus, storageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.removeItem(storageKey);
            localStorage.removeItem(rolesStorageKey);

            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

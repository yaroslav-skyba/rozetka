const innerHtmlCreationSubmit = "Create";
const innerHtmlEditSubmit = "Edit";

let submit;

function redirectUnready(modificationStorageKey) {
    if (!localStorage.getItem(modificationStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }
}

function receiveModificationHttpResponse(successStatus, modificationStorageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.removeItem(modificationStorageKey);
            localStorage.removeItem(rolesStorageKey);

            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

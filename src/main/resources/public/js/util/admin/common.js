const innerHtmlCreationSubmit = "Create";
const innerHtmlEditSubmit = "Edit";

let submit;

function redirectUnready(storageKey) {
    if (!localStorage.getItem(storageKey)) {
        location.href = "/profile/admin/admin.html";
    }
}

function setSubmitOnclick(storageKey, method, url, contentType) {
    submit.onclick = function () {
        sendModificationHttpRequest(JSON.parse(localStorage.getItem(storageKey)), method, url, contentType);
    }
}

function receiveModificationHttpResponse(successStatus, storageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.removeItem(storageKey);
            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

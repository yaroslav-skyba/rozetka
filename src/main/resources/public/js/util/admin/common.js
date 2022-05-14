const innerHtmlCreationSubmit = "Create";

let submit;

function setSubmitOnclick(create, method, url, contentType) {
    submit.onclick = function () {
        sendModificationHttpRequest(method, url, contentType, create());
    }
}

function receiveModificationHttpResponse(successStatus) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.setItem(successModificationHttpResponseStorageKey, xmlHttpRequest.responseText);
            location.href = "/profile/admin/admin.html";
        } else if (xmlHttpRequest.status === 409) {
            alertMessage("danger", xmlHttpRequest.responseText);
        }
    }
}

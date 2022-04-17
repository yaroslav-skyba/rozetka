const innerHtmlCreationSubmit = "Create";

let submit;

function setSubmitOnclick(body, method, url, contentType) {
    submit.onclick = function () {
        sendModificationHttpRequest(method, url, contentType, body);
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

const innerHtmlCreationSubmit = "Create";
const innerHtmlEditSubmit = "Edit";

let submit;

function setFormControlElementOnchange(create) {
    for (const formControlElement of document.getElementsByClassName("form-control")) {
        formControlElement.onchange = function () {
            localStorage.setItem(modificationStorageKeyValue, JSON.stringify(create()));
        }
    }
}

function setSubmitOnclick(body, method, url, contentType) {
    submit.onclick = function () {
        sendModificationHttpRequest(body, method, url, contentType);
    }
}

function receiveModificationHttpResponse(successStatus) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            modificationStorageKeyValue = xmlHttpRequest.responseText;
            location.href = "/profile/admin/admin.html";
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

const innerHtmlCreationSubmit = "Create";
const innerHtmlEditSubmit = "Edit";

let submit;

function setModificationPage(headlineInnerHtml, submitInnerHtml) {
    setMainAttributes();
    document.getElementById("headline").innerHTML = headlineInnerHtml;

    submit = document.getElementById("submit");
    submit.innerHTML = submitInnerHtml;

    modificationStorageKey = localStorage.getItem(modificationStorageKeyStorageKey);
}

function setFormControlElementOnchange(create) {
    for (const formControlElement of document.getElementsByClassName("form-control")) {
        formControlElement.onchange = function () {
            localStorage.setItem(modificationStorageKey, JSON.stringify(create()));
        }
    }
}

function sendModificationHttpRequest(body, method, url, contentType) {
    xmlHttpRequest.open(method, url);
    xmlHttpRequest.setRequestHeader("Content-Type", contentType);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send(JSON.stringify(body));
}

function setSubmitOnclick(body, method, url, contentType) {
    submit.onclick = function () {
        sendModificationHttpRequest(body, method, url, contentType);
    }
}

function receiveModificationHttpResponse(successStatus) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.setItem(successModificationHttpResponseStorageKey, xmlHttpRequest.responseText);
            location.href = "/profile/admin/admin.html";
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

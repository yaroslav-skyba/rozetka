const formControlElements = document.getElementsByClassName("form-control");

const userToEditStorageKey = "userToEdit";

function redirectUnauthorized() {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== adminRoleName) {
        location.href = "/";
    }
}

function redirectUnready(storageKey) {
    if (!localStorage.getItem(storageKey)) {
        location.href = "/profile/admin/admin.html";
    }
}

function configModificationPage(headlineInnerHtml, submitInnerHtml) {
    setMainAttributes();

    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;
}

function setFormControlElementOnchange(storageKey, create) {
    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKey, JSON.stringify(create()));
        }
    }
}

function sendModificationRequest(httpMethod, url, body, contentType) {
    xmlHttpRequest.open(httpMethod, url);
    xmlHttpRequest.setRequestHeader("Content-Type", contentType);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send(JSON.stringify(body));
}

function sendModificationRequestIfBodyNotNull(httpMethod, body, url, contentType) {
    if (body) {
        sendModificationRequest(httpMethod, url, body, contentType);
    }
}

function setModificationXmlHttpRequest(successStatus, storageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.removeItem(storageKey);
            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

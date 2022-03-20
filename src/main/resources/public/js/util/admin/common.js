const formControlElements = document.getElementsByClassName("form-control");

const userToEditStorageKey = "userToEdit";
const rolesStorageKey = "roles";

function redirectUnauthorizedModification() {
    if (localStorage.getItem(currentUserRoleNameStorageKey) !== adminRoleName) {
        location.href = "/";
    }

    if (!localStorage.getItem(rolesStorageKey)) {
        location.href = "/profile/admin/admin.html";
    }
}

function configModificationPage(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    setMainAttributes();

    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;

    for (const formControlElement of formControlElements) {
        formControlElement.onchange = function () {
            localStorage.setItem(storageKeyPrefix + formControlElement.id, formControlElement.value);
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

function removeStorageItems(storageKeyPrefix) {
    for (const formControlElement of document.getElementsByClassName("form-control")) {
        localStorage.removeItem(storageKeyPrefix + formControlElement.id);
    }

    alert("success", xmlHttpRequest.responseText);
    location.href = "/profile/admin/admin.html";
}

function setModificationXmlHttpRequest(successStatus, storageKeyPrefix) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            removeStorageItems(storageKeyPrefix);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

function setCreationXmlHttpRequest(successStatus, storageKeyPrefix, storageKey) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            localStorage.removeItem(storageKey);
            removeStorageItems(storageKeyPrefix);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

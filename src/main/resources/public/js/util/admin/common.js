const formControlElements = document.getElementsByClassName("form-control");

const userToEditStorageKey = "userToEdit";
const rolesStorageKey = "roles";

const creationStorageKeyPrefix = "creation_";
const editStorageKeyPrefix = "edit_";

const rolesApiUrl = authorityApi + "roles";

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

function setModificationXmlHttpRequest(successStatus, storageKeyPrefix) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            for (const formControlElement of document.getElementsByClassName("form-control")) {
                localStorage.removeItem(storageKeyPrefix + formControlElement.id);
            }

            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

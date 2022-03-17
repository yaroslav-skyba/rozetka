const xmlHttpRequest = new XMLHttpRequest();

function setXmlHttpRequest(successStatus, storageKeyPrefix) {
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

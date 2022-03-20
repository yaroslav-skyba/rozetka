const roleStorageKeyPrefix = "role_";
const roleContentType = contentTypeRoot + "role" + contentTypeSuffix;

let name;

function setRoleModificationForm(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    redirectUnauthorizedModification();
    setNavigation("../../../", "../../", "../");

    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
        
        <div id="form">
            <div class="form-outline mb-4">
                <input id="name" class="form-control form-control-lg" required/>
                <label for="name">A name</label>
                <div class="invalid-feedback">Please type a name</div>
            </div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);

    name = document.getElementById("name");

    storageKeyPrefix = "role_" + storageKeyPrefix;
    configModificationPage(headlineInnerHtml, submitInnerHtml);

    name.value = localStorage.getItem(storageKeyPrefix + name.id);
}

function createRoleModificationRequestBody(uuid) {
    if (!name.checkValidity()) {
        alert("danger", document.getElementsByClassName("invalid-feedback")[0].innerHTML);
        return false;
    }

    const maxElementLength = 255;

    if (name.value.length > maxElementLength) {
        alert("danger", "A field length should be equal or less than " + maxElementLength + " symbols");
        return false;
    }

    const body = {};
    body["uuid"] = uuid;
    body["name"] = name.value;

    return body;
}

const roleStorageKeyPrefix = "role_";

const roleContentType = contentTypeRoot + "role" + contentTypeSuffix;

let name;

function createRole(uuid) {
    const role = {};
    role[roleUuidDtoKey] = uuid;
    role[roleNameDtoKey] = name.value;

    return role;
}

function configRoleModificationPage(headlineInnerHtml, submitInnerHtml, storageKey, uuid) {
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

    configModificationPage(headlineInnerHtml, submitInnerHtml);
    name = document.getElementById("name");

    const role = JSON.parse(localStorage.getItem(storageKey));

    if (role) {
        name.value = role[roleNameDtoKey];
    }

    setFormControlElementOnchange(storageKey, function () {
        return createRole(uuid);
    });
}

function createNullableRole(uuid) {
    if (!areFormInputsValid(name, document)) {
        return null;
    }

    return createRole(uuid);
}

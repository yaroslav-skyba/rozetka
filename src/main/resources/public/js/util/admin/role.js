const roleContentType = contentTypeRoot + "role" + contentTypeSuffix;

let name;

function createRole(uuid) {
    const role = {};
    role[roleUuidDtoKey] = uuid;
    role[roleNameDtoKey] = name.value;

    return role;
}

function getRole(roleStorageKey) {
    if (!areFormInputsValid(name, document)) {
        return null;
    }

    return JSON.parse(localStorage.getItem(roleStorageKey));
}

function configRoleModificationPage(headlineInnerHtml, submitInnerHtml, roleStorageKey, uuid) {
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
    configModificationPage(headlineInnerHtml, submitInnerHtml);

    const role = JSON.parse(localStorage.getItem(roleStorageKey));

    if (role) {
        name.value = role[roleNameDtoKey];
    }

    setFormControlElementOnchange(roleStorageKey, function () {
        return createRole(uuid);
    });
}

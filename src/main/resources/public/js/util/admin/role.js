let roleName;

function createRole(uuid) {
    const role = {};
    role[roleUuidDtoKey] = uuid;
    role[roleNameDtoKey] = roleName.value;

    return role;
}

function setRolePage(headlineInnerHtml, submitInnerHtml, httpMethod) {
    redirectWithoutAdminRole(adminRoleName);

    setNavigation("../../../", "../../", "../");
    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
        
        <div id="form">
            <div class="form-outline mb-4">
                <input id="name" class="form-control form-control-lg"/>
                <label for="name">A name</label>
                <div class="invalid-feedback">Please, type a name</div>
            </div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);
    setModificationPage(headlineInnerHtml, submitInnerHtml);

    roleName = document.getElementById("name");

    const modificationStorageKey = localStorage.getItem(modificationStorageKeyStorageKey);

    const role = JSON.parse(localStorage.getItem(modificationStorageKey));
    if (role) {
        roleName.value = role[roleNameDtoKey];
    } else {
        localStorage.setItem(modificationStorageKey, JSON.stringify(createRole(null)));
    }

    setFormControlElementOnchange(function () {
        return createRole(JSON.parse(localStorage.getItem(modificationStorageKey))[roleUuidDtoKey]);
    });

    setSubmitOnclick(
        createRole(JSON.parse(localStorage.getItem(modificationStorageKey))[roleUuidDtoKey]), httpMethod, rolesApiUrl,
        contentTypePrefix + "role" + contentTypeSuffix
    );
}

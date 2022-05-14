let roleName;

function createRole(uuid) {
    const role = {};
    role[roleUuidDtoKey] = uuid;
    role[roleNameDtoKey] = roleName.value;

    return role;
}

function setRolePage(headlineInnerHtml, submitInnerHtml, httpMethod) {
    redirectWithoutSpecificRole(adminRoleName);

    setNavigation("../../../", "../../", "../");
    setContainer(`
        <div class="card-body">
            <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
            
            <div>
                <div class="mb-4">
                    <input id="name" class="form-control form-control-lg"/>
                    <label for="name">Name</label>
                    
                </div>
                
                <div class="d-flex justify-content-center">
                    <button class="btn btn-dark btn-outline-success" id="submit"></button>
                </div>
            </div>
            
            <div id="alert" class="mt-3"></div>
        </div>
    `);
    setModificationPage(headlineInnerHtml, submitInnerHtml);

    roleName = document.getElementById("name");

    submit = document.getElementById("submit");

    const modificationStorageKey = localStorage.getItem(modificationStorageKeyStorageKey);

    const role = JSON.parse(localStorage.getItem(modificationStorageKey));
    if (role) {
        roleName.value = role[roleNameDtoKey];
    } else {
        localStorage.setItem(modificationStorageKey, JSON.stringify(createRole(null)));
    }

    setFormControlOnchange(function () {
        return createRole(JSON.parse(localStorage.getItem(modificationStorageKey))[roleUuidDtoKey]);
    });

    setSubmitOnclick(function () {
        return JSON.parse(localStorage.getItem(modificationStorageKey));
    }, httpMethod, rolesApiUrl, contentTypePrefix + "role" + contentTypeSuffix);
}

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

    storageKeyPrefix = "role_" + storageKeyPrefix;
    configModificationPage(headlineInnerHtml, submitInnerHtml, storageKeyPrefix);
    name.value = localStorage.getItem(storageKeyPrefix + name.id);
}

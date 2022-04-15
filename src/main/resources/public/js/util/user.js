let userRole;
let userLogin;
let userPassword;
let userPasswordConformation;
let userEmail;
let userFirstName;
let userLastName;
let userBirthday;

function createUser(uuid) {
    const user = {};
    user[userUuidDtoKey] = uuid;
    user[userRoleUuidDtoKey] = userRole.value;
    user[userLoginDtoKey] = userLogin.value;
    user[userPasswordDtoKey] = userPassword.value;
    user[userEmailDtoKey] = userEmail.value;
    user[userFirstNameDtoKey] = userFirstName.value;
    user[userLastNameDtoKey] = userLastName.value;
    user[userBirthdayDtoKey] = userBirthday.value;

    return user;
}

function setUserPage(roleName, rootDestination, userDestination, adminDestination, headlineInnerHtml, submitInnerHtml, httpMethod) {
    redirectWithoutSpecificRole(roleName);

    setNavigation(rootDestination, userDestination, adminDestination);
    setContainer(`
        <div class="card-body">
            <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
            
            <div id="form">
                <div class="form-outline mb-4">
                    <input id="firstName" class="form-control form-control-lg"/>
                    <label for="firstName">A first name</label>
                    <div class="invalid-feedback">Please, type a first name</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input id="lastName" class="form-control form-control-lg"/>
                    <label for="lastName">A last name</label>
                    <div class="invalid-feedback">Please, type a last name</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input id="login" class="form-control form-control-lg"/>
                    <label for="login">A login</label>
                    <div class="invalid-feedback">Please, type a login</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input type="email" id="email" class="form-control form-control-lg"/>
                    <label for="email">An email</label>
                    <div class="invalid-feedback">Please, type an email ([text]@[text])</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input type="date" id="birthday" class="form-control form-control-lg"/>
                    <label for="birthday">A birthday</label>
                    <div class="invalid-feedback">Please, select a birthday</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input type="password" id="password" class="form-control form-control-lg"/>
                    <label for="password">A password</label>
                    <div class="invalid-feedback">Please, type a password</div>
                </div>
                
                <div class="form-outline mb-4">
                    <input type="password" id="passwordConformation" class="form-control form-control-lg"/>
                    <label for="passwordConformation">A password conformation</label>
                    <div class="invalid-feedback">Please, type a password conformation</div>
                </div>
    
                <div id="roleDiv" class="form-outline mb-4" hidden>
                    <select id="role" class="form-control form-control-lg"></select> 
                    <label for="role">A role</label>
                    <div class="invalid-feedback">Please, select a role</div>
                </div>
                
                <div class="d-flex justify-content-center">
                    <button class="btn btn-dark btn-outline-success" id="submit"></button>
                </div>
            </div>
            
            <div id="alert" class="mt-3"></div>
        </div>
    `);
    setModificationPage(headlineInnerHtml, submitInnerHtml);

    userRole = document.getElementById("role");
    userLogin = document.getElementById("login");
    userPassword = document.getElementById("password");
    userPasswordConformation = document.getElementById("passwordConformation");
    userEmail = document.getElementById("email");
    userFirstName = document.getElementById("firstName");
    userLastName = document.getElementById("lastName");
    userBirthday = document.getElementById("birthday");

    const modificationStorageKey = localStorage.getItem(modificationStorageKeyStorageKey);
    const user = JSON.parse(localStorage.getItem(modificationStorageKey));

    const roles = localStorage.getItem(rolesStorageKey);
    if (roles) {
        for (const role of Object.values(JSON.parse(roles))) {
            userRole.innerHTML += `<option value="` + role[roleUuidDtoKey] + `">` + role[roleNameDtoKey] + `</option>`;
        }
    } else {
        userRole.innerHTML =
            `<option value="` + user[userRoleUuidDtoKey] + `">` + localStorage.getItem(currentUserRoleNameStorageKey) + `</option>`;
    }

    if (user) {
        userRole.value = user[userRoleUuidDtoKey];
        userLogin.value = user[userLoginDtoKey];
        userEmail.value = user[userEmailDtoKey];
        userFirstName.value = user[userFirstNameDtoKey];
        userLastName.value = user[userLastNameDtoKey];
        userBirthday.value = new Date(user[userBirthdayDtoKey]).toISOString().slice(0, 10);
    } else {
        localStorage.setItem(modificationStorageKey, JSON.stringify(createUser(null)));
    }

    setFormControlElementOnchange(function () {
        return createUser(JSON.parse(localStorage.getItem(modificationStorageKey))[userUuidDtoKey]);
    });

    submit.onclick = function () {
        const user = JSON.parse(localStorage.getItem(modificationStorageKey));

        if (!user[userPasswordDtoKey]) {
            user[userPasswordDtoKey] = null;
        }

        if (userPassword.value === userPasswordConformation.value) {
            sendModificationHttpRequest(user, httpMethod, usersApiUrl, contentTypePrefix + "user" + contentTypeSuffix);
        } else {
            alertMessage("danger", "Passwords should match");
        }
    }
}

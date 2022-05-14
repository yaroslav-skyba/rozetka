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
    setNavigation(rootDestination, userDestination, adminDestination);
    setContainer(`
        <div class="card-body">
            <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
            
            <div>
                <div class="mb-4">
                    <input id="firstName" class="form-control form-control-lg"/>
                    <label for="firstName">First name</label>                    
                </div>
            
                <div class="mb-4">
                    <input id="lastName" class="form-control form-control-lg"/>
                    <label for="lastName">Last name</label>
                </div>
            
                <div class="mb-4">
                    <input id="login" class="form-control form-control-lg"/>
                    <label for="login">Login</label>
                </div>
            
                <div class="mb-4">
                    <input type="email" id="email" class="form-control form-control-lg"/>
                    <label for="email">Email</label>
                </div>
            
                <div class="mb-4">
                    <input type="date" id="birthday" class="form-control form-control-lg"/>
                    <label for="birthday">Birthday</label>
                </div>
            
                <div class="mb-4">
                    <input type="password" id="password" class="form-control form-control-lg"/>
                    <label for="password">Password</label>
                </div>
                
                <div class="mb-4">
                    <input type="password" id="passwordConformation" class="form-control form-control-lg"/>
                    <label for="passwordConformation">Password conformation</label>
                </div>
    
                <div id="roleDiv" class="mb-4" hidden>
                    <select id="role" class="form-control form-control-lg"></select> 
                    <label for="role">Role</label>
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

    submit = document.getElementById("submit");

    const modificationStorageKey = localStorage.getItem(modificationStorageKeyStorageKey);
    const user = JSON.parse(localStorage.getItem(modificationStorageKey));

    const roles = localStorage.getItem(rolesStorageKey);
    if (roles) {
        for (const role of Object.values(JSON.parse(roles))) {
            userRole.innerHTML += `<option value="` + role[roleUuidDtoKey] + `">` + role[roleNameDtoKey] + `</option>`;
        }
    }

    if (user) {
        userRole.value = user[userRoleUuidDtoKey];
        userLogin.value = user[userLoginDtoKey];
        userEmail.value = user[userEmailDtoKey];
        userFirstName.value = user[userFirstNameDtoKey];
        userLastName.value = user[userLastNameDtoKey];

        const birthday = user[userBirthdayDtoKey]
        const lastBirthdaySliceIndex = 10;

        if (birthday.length > lastBirthdaySliceIndex) {
            userBirthday.value = new Date(birthday).toISOString().slice(0, lastBirthdaySliceIndex);
        } else {
            userBirthday.value = birthday;
        }
    } else {
        localStorage.setItem(modificationStorageKey, JSON.stringify(createUser(null)));
    }

    setFormControlOnchange(function () {
        return createUser(JSON.parse(localStorage.getItem(modificationStorageKey))[userUuidDtoKey]);
    });

    submit.onclick = function () {
        const user = JSON.parse(localStorage.getItem(modificationStorageKey));

        if (!user[userRoleUuidDtoKey]) {
            user[userRoleUuidDtoKey] = null;
        }

        if (!user[userPasswordDtoKey]) {
            user[userPasswordDtoKey] = null;
        }

        if (userPassword.value === userPasswordConformation.value) {
            sendModificationHttpRequest(httpMethod, usersApiUrl, contentTypePrefix + "user" + contentTypeSuffix, user);
        } else {
            alertMessage("danger", "Passwords should match");
        }
    }
}

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
    user[userRoleUuidDtoKey] = null;
    user[userLoginDtoKey] = userLogin.value;
    user[userPasswordDtoKey] = userPassword.value;
    user[userEmailDtoKey] = userEmail.value;
    user[userFirstNameDtoKey] = userFirstName.value;
    user[userLastNameDtoKey] = userLastName.value;
    user[userBirthdayDtoKey] = userBirthday.value;

    return user;
}

function setUserPage(rootDestination, userDestination, adminDestination, headlineInnerHtml, submitInnerHtml, httpMethod) {
    redirectWithoutAdminRole(adminRoleName);

    setNavigation(rootDestination, userDestination, adminDestination);
    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
        
        <div id="form">
            <div class="form-outline mb-4">
                <input id="firstName" class="form-control form-control-lg" required/>
                <label for="firstName">A first name</label>
                <div class="invalid-feedback">Please, type a first name</div>
            </div>
        
            <div class="form-outline mb-4">
                <input id="lastName" class="form-control form-control-lg" required/>
                <label for="lastName">A last name</label>
                <div class="invalid-feedback">Please, type a last name</div>
            </div>
        
            <div class="form-outline mb-4">
                <input id="login" class="form-control form-control-lg" required/>
                <label for="login">A login</label>
                <div class="invalid-feedback">Please, type a login</div>
            </div>
        
            <div class="form-outline mb-4">
                <input type="email" id="email" class="form-control form-control-lg" required/>
                <label for="email">An email</label>
                <div class="invalid-feedback">Please, type an email ([text]@[text])</div>
            </div>
        
            <div class="form-outline mb-4">
                <input type="date" id="birthday" class="form-control form-control-lg" required/>
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

            <div id="role"></div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);
    setModificationPage(headlineInnerHtml, submitInnerHtml);

    userLogin = document.getElementById("login");
    userPassword = document.getElementById("password");
    userPasswordConformation = document.getElementById("passwordConformation");
    userEmail = document.getElementById("email");
    userFirstName = document.getElementById("firstName");
    userLastName = document.getElementById("lastName");
    userBirthday = document.getElementById("birthday");

    const user = JSON.parse(localStorage.getItem(modificationStorageKeyValue));
    if (user) {
        userLogin.value = user[userLoginDtoKey];
        userEmail.value = user[userEmailDtoKey];
        userFirstName.value = user[userFirstNameDtoKey];
        userLastName.value = user[userLastNameDtoKey];
        userBirthday.value = user[userBirthdayDtoKey];
    } else {
        localStorage.setItem(modificationStorageKeyValue, JSON.stringify(createUser(null)));
    }

    setFormControlElementOnchange(function () {
        return createUser(JSON.parse(localStorage.getItem(modificationStorageKeyValue))[userUuidDtoKey]);
    });

    submit.onclick = function () {
        const user = JSON.parse(localStorage.getItem(modificationStorageKeyValue));

        if (!user[userPasswordDtoKey]) {
            user[userPasswordDtoKey] = null;
        }

        if (userPassword.value === userPasswordConformation.value) {
            document.getElementById("alert").innerHTML = "";
            sendModificationHttpRequest(user, httpMethod, usersApiUrl, contentTypePrefix + "user" + contentTypeSuffix);
        } else {
            alert("danger", "Passwords should match");
        }
    }
}

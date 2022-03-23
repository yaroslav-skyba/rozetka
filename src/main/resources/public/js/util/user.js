//noinspection DuplicatedCode

const usersApiUrl = authorityApi + "users";

const userUuidDtoKey = "uuid";
const userRoleUuidDtoKey = "uuidRole";
const userLoginDtoKey = "login";
const userPasswordDtoKey = "password";
const userEmailDtoKey = "email";
const userFirstNameDtoKey = "firstName";
const userLastNameDtoKey = "lastName";
const userBirthdayDtoKey = "birthday";

let login;
let password;
let passwordConformation;
let email;
let firstName;
let lastName;
let birthday;

function configUserModificationPage(rootDestination, userDestination, adminDestination) {
    setNavigation(rootDestination, userDestination, adminDestination);
    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
        
        <div id="form">
            <div class="form-outline mb-4">
                <input id="firstName" class="form-control form-control-lg" required/>
                <label for="firstName">A first name</label>
                <div class="invalid-feedback">Please type a first name</div>
            </div>
        
            <div class="form-outline mb-4">
                <input id="lastName" class="form-control form-control-lg" required/>
                <label for="lastName">A last name</label>
                <div class="invalid-feedback">Please type a last name</div>
            </div>
        
            <div class="form-outline mb-4">
                <input id="login" class="form-control form-control-lg" required/>
                <label for="login">A login</label>
                <div class="invalid-feedback">Please type a login</div>
            </div>
        
            <div class="form-outline mb-4">
                <input type="email" id="email" class="form-control form-control-lg" required/>
                <label for="email">An email</label>
                <div class="invalid-feedback">Please type an email ([text]@[text])</div>
            </div>
        
            <div class="form-outline mb-4">
                <input type="date" id="birthday" class="form-control form-control-lg" required/>
                <label for="birthday">A birthday</label>
                <div class="invalid-feedback">Please select a birthday</div>
            </div>
        
            <div class="form-outline mb-4">
                <input type="password" id="password" class="form-control form-control-lg"/>
                <label for="password">A password</label>
                <div class="invalid-feedback">Please type a password</div>
            </div>
            
            <div class="form-outline mb-4">
                <input type="password" id="passwordConformation" class="form-control form-control-lg"/>
                <label for="passwordConformation">A password conformation</label>
                <div class="invalid-feedback">Please type a password conformation</div>
            </div>

            <div id="role"></div>
            
            <div class="d-flex justify-content-center">
                <button class="btn btn-dark btn-outline-success" id="submit"></button>
            </div>
        </div>
        
        <div id="alert" class="mt-3"></div>
    `);

    login = document.getElementById("login");
    password = document.getElementById("password");
    passwordConformation = document.getElementById("passwordConformation");
    email = document.getElementById("email");
    firstName = document.getElementById("firstName");
    lastName = document.getElementById("lastName");
    birthday = document.getElementById("birthday");
}

function setUserFormInputs(headlineInnerHtml, submitInnerHtml, storageKey, uuid, roleUuid) {
    configModificationPage(headlineInnerHtml, submitInnerHtml);

    const user = JSON.parse(localStorage.getItem(storageKey));

    if (user) {
        login.value = user[userLoginDtoKey];
        email.value = user[userEmailDtoKey];
        firstName.value = user[userFirstNameDtoKey];
        lastName.value = user[userLastNameDtoKey];
        birthday.value = user[userBirthdayDtoKey];
    }

    setFormControlElementOnchange(storageKey, function () {
        let passwordValue = password.value;

        if (!passwordValue) {
            passwordValue = null;
        }

        const user = {};
        user[userUuidDtoKey] = uuid;
        user[userRoleUuidDtoKey] = roleUuid;
        user[userLoginDtoKey] = login.value;
        user[userPasswordDtoKey] = passwordValue;
        user[userEmailDtoKey] = email.value;
        user[userFirstNameDtoKey] = firstName.value;
        user[userLastNameDtoKey] = lastName.value;
        user[userBirthdayDtoKey] = birthday.value;

        return user;
    });
}

function createUser(userStorageKey) {
    for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
        if (!areFormInputsValid(formOutlineElement.getElementsByClassName("form-control")[0], formOutlineElement)) {
            return null;
        }
    }

    if (password.value !== passwordConformation.value) {
        alert("danger", "Passwords should match");
        return null;
    }

    return JSON.parse(localStorage.getItem(userStorageKey));
}

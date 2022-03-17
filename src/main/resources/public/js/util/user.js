const xmlHttpRequest = new XMLHttpRequest();
const formControlElements = document.getElementsByClassName("form-control");

let login;
let email;
let firstName;
let lastName;
let birthday;

function setUserForm() {
    setContainer(`
        <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
            <div id="form">
                <div class="form-outline mb-4">
                    <input id="firstName" class="form-control form-control-lg" required/>
                    <label class="form-label" for="firstName">A first name</label>
                    <div class="invalid-feedback">Please type a first name</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input id="lastName" class="form-control form-control-lg" required/>
                    <label class="form-label" for="lastName">A last name</label>
                    <div class="invalid-feedback">Please type a last name</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input id="login" class="form-control form-control-lg" required/>
                    <label class="form-label" for="login">A login</label>
                    <div class="invalid-feedback">Please type a login</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input type="email" id="email" class="form-control form-control-lg" required/>
                    <label class="form-label" for="email">An email</label>
                    <div class="invalid-feedback">Please type an email ([text]@[text])</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input type="date" id="birthday" class="form-control form-control-lg" required/>
                    <label class="form-label" for="birthday">A birthday</label>
                    <div class="invalid-feedback">Please select a birthday</div>
                </div>
            
                <div class="form-outline mb-4">
                    <input type="password" id="password" class="form-control form-control-lg"/>
                    <label class="form-label" for="password">A password</label>
                    <div class="invalid-feedback">Please type a password</div>
                </div>
                
                <div class="form-outline mb-4">
                    <input type="password" id="passwordConformation" class="form-control form-control-lg"/>
                    <label class="form-label" for="passwordConformation">A password conformation</label>
                    <div class="invalid-feedback">Please type a password conformation</div>
                </div>
    
                <div id="role"></div>
                
                <div class="d-flex justify-content-center">
                    <button class="` + buttonClasses + `" id="submit"></button>
                </div>
            </div>
            
            <div id="alert" class="mt-3"></div>
    `);

    login = document.getElementById("login");
    email = document.getElementById("email");
    firstName = document.getElementById("firstName");
    lastName = document.getElementById("lastName");
    birthday = document.getElementById("birthday");
}

function setEditStorageItems(userToEditParsed) {
    localStorage.setItem(editStorageKeyPrefix + login.id, userToEditParsed[userLoginDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + email.id, userToEditParsed[userEmailDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + firstName.id, userToEditParsed[userFirstNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + lastName.id, userToEditParsed[userLastNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + birthday.id, userToEditParsed[userBirthdayDtoKey]);
}

function setUserInputs(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;

    login.value = localStorage.getItem(storageKeyPrefix + login.id);
    email.value = localStorage.getItem(storageKeyPrefix + email.id);
    firstName.value = localStorage.getItem(storageKeyPrefix + firstName.id);
    lastName.value = localStorage.getItem(storageKeyPrefix + lastName.id);
    birthday.value = localStorage.getItem(storageKeyPrefix + birthday.id);
}

function sendModificationRequest(httpMethod, url, body) {
    xmlHttpRequest.open(httpMethod, url);
    xmlHttpRequest.setRequestHeader("Content-Type", userMediaType);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send(JSON.stringify(body));
}

function createRequestBody(userUuid, passwordValue) {
    const body = {};
    body[userUuidDtoKey] = userUuid;
    body[userLoginDtoKey] = login.value;
    body[userPasswordDtoKey] = passwordValue;
    body[userEmailDtoKey] = email.value;
    body[userFirstNameDtoKey] = firstName.value;
    body[userLastNameDtoKey] = lastName.value;
    body[userBirthdayDtoKey] = birthday.value;

    return body;
}

function createModificationRequestBody(userUuid, passwordValue) {
    for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
        const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

        if (!formControlElement.checkValidity()) {
            alert("danger", formOutlineElement.getElementsByClassName("invalid-feedback")[0].innerHTML);
            return null;
        }

        const maxElementLength = 255;

        if (formControlElement.value.length > maxElementLength) {
            alert("danger", "A field should be equal or less than " + maxElementLength + " symbols");
            return null;
        }
    }

    if (document.getElementById("password").value !== document.getElementById("passwordConformation").value) {
        alert("danger", "Passwords do not match");
        return null;
    }

    return createRequestBody(userUuid, passwordValue);
}

function createEditRequestBody(userToEditParsed) {
    let passwordValue = document.getElementById("password").value;

    if (!passwordValue) {
        passwordValue = null;
    }

    return createModificationRequestBody(userToEditParsed[userUuidDtoKey], passwordValue);
}

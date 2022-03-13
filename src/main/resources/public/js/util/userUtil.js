function setUserForm() {
    document.getElementById("main").innerHTML =
        `<section>
            <div class="container">
                <div class="row min-vh-100 justify-content-center align-items-center">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card bg-dark btn-dark" style="border-radius: 15px">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
    
                                <div id="form">
                                    <div class="form-outline mb-4">
                                        <input id="firstName" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="firstName">A first name</label>
                                        <div class="invalid-feedback">Please type a first name</div>
                                    </div>
                                
                                    <div class="form-outline mb-4">
                                        <input id="secondName" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="secondName">A second name</label>
                                        <div class="invalid-feedback">Please type a second name</div>
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
                                        <button class="btn btn-success btn-lg custom-gradient-2 text-body" id="submit"></button>
                                    </div>
                                </div>
                                
                                <div id="alert" class="mt-3"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>`;
}

function setEditStorageItems(userToEditParsed) {
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("login").id, userToEditParsed[userLoginDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("email").id, userToEditParsed[userEmailDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("firstName").id, userToEditParsed[userFirstNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("secondName").id, userToEditParsed[userLastNameDtoKey]);
    localStorage.setItem(editStorageKeyPrefix + document.getElementById("birthday").id, userToEditParsed[userBirthdayDtoKey]);
}

function setUserInputs(headlineInnerHtml, submitInnerHtml, storageKeyPrefix) {
    document.getElementById("headline").innerHTML = headlineInnerHtml;
    document.getElementById("submit").innerHTML = submitInnerHtml;

    const role = document.getElementById("roleValue");
    role.value = localStorage.getItem(storageKeyPrefix + role.id);

    const login = document.getElementById("login");
    login.value = localStorage.getItem(storageKeyPrefix + login.id);

    const email = document.getElementById("email");
    email.value = localStorage.getItem(storageKeyPrefix + email.id);

    const firstName = document.getElementById("firstName");
    firstName.value = localStorage.getItem(storageKeyPrefix + firstName.id);

    const secondName = document.getElementById("secondName");
    secondName.value = localStorage.getItem(storageKeyPrefix + secondName.id);

    const birthday = document.getElementById("birthday");
    birthday.value = localStorage.getItem(storageKeyPrefix + birthday.id);
}

function sendModificationRequest(httpMethod, url, body) {
    xmlHttpRequest.open(httpMethod, url);
    xmlHttpRequest.setRequestHeader("Content-Type", userMediaType);
    xmlHttpRequest.setRequestHeader("Authorization", localStorage.getItem(jwtStorageKey));
    xmlHttpRequest.send(JSON.stringify(body));
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

    const body = {};
    body[userUuidDtoKey] = userUuid;
    body[userLoginDtoKey] = document.getElementById("login").value;
    body[userPasswordDtoKey] = passwordValue;
    body[userEmailDtoKey] = document.getElementById("email").value;
    body[userFirstNameDtoKey] = document.getElementById("firstName").value;
    body[userLastNameDtoKey] = document.getElementById("secondName").value;
    body[userBirthdayDtoKey] = document.getElementById("birthday").value;
}

function createEditRequestBody(userToEditParsed) {
    let passwordValue = document.getElementById("password").value;

    if (!passwordValue) {
        passwordValue = null;
    }

    return createModificationRequestBody(userToEditParsed[userUuidDtoKey], passwordValue);
}

function setXmlHttpRequest(successStatus, storageKeyPrefix) {
    if (xmlHttpRequest.readyState === 4) {
        if (xmlHttpRequest.status === successStatus) {
            for (const formControlElement of formControlElements) {
                localStorage.removeItem(storageKeyPrefix + formControlElement.id);
            }

            alert("success", xmlHttpRequest.responseText);
        } else if (xmlHttpRequest.status === 409) {
            alert("danger", xmlHttpRequest.responseText);
        }
    }
}

function setUserModification() {
    document.getElementById("main").innerHTML =
        `<section>
            <div class="container">
                <div class="row min-vh-100 justify-content-center align-items-center">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card bg-dark btn-dark" style="border-radius: 15px">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5" id="headline"></h2>
    
                                <form id="form" novalidate>
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
                                        <div class="invalid-feedback">Please type an email</div>
                                    </div>
                                
                                    <div class="form-outline mb-4">
                                        <input type="date" id="birthday" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="birthday">A birthday</label>
                                        <div class="invalid-feedback">Please select a birthday</div>
                                    </div>
                                
                                    <div class="form-outline mb-4">
                                        <input type="password" id="password" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="password">A password</label>
                                        <div class="invalid-feedback" id="passwordValidation">Please type a password</div>
                                    </div>
    
                                    <div id="role"></div>
                                    
                                    <div class="d-flex justify-content-center">
                                        <button class="btn btn-success btn-lg custom-gradient-2 text-body" id="submit"></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>`;

    validate();
}
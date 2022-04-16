onload = function() {
    if (!localStorage.getItem(jwtStorageKey)) {
        location.href = "/";
    }

    setNavigation("", "profile/", "profile/admin/");
    setMainAttributes();
    setContainer(`
        <div class="card-body p-5">
            <h2 class="text-center mb-5">LOGIN</h2>

            <form id="form">
                <div class="form-outline mb-4">
                    <input id="login" class="form-control form-control-lg"/>
                    <label for="login">Your login</label>
                    <div class="invalid-feedback">Please, type your login</div>
                </div>

                <div class="form-outline mb-4">
                    <input type="password" id="password" class="form-control form-control-lg"/>
                    <label for="password">Your password</label>
                    <div class="invalid-feedback" id="passwordValidation">Please, type your password</div>
                </div>

                <div class="d-flex justify-content-center">
                    <button class="btn btn-dark btn-outline-success" id="submit">Login</button>
                </div>
            </form>
            
            <div id="alert" class="mt-3"></div>
        </div>
    `);


}

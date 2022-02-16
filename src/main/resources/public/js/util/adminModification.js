function setAdminModification() {
    setNavigation("../../index.html", "../../img/logo.png", "../../cart.html", "../../about.html",
        "../../login.html", "../../registration.html", "admin.html", "../user.html");
    setUserModification();

    document.getElementById("role").innerHTML =
        `<div class="form-outline mb-4">
            <select id="role" class="form-control form-control-lg" required>
                <option value="">-Select a role-</option>
                <option value="user">User</option>
                <option value="admin">Admin</option>
            </select>
    
            <label class="form-label" for="role">A role</label>
            <div class="invalid-feedback" id="roleValidation">Please select a role</div>
        </div>`;
}
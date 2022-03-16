const jwtStorageKey = "jwt";
const currentUserRoleNameStorageKey = "roleName";
const currentUserStorageKey = "currentUser";
const userToEditStorageKey = "userToEdit";
const rolesStorageKey = "roles";

const creationStorageKeyPrefix = "creation_";
const editStorageKeyPrefix = "edit_";

const roleUuidDtoKey = "uuid";
const roleNameDtoKey = "name";
const userUuidDtoKey = "uuid";
const userRoleUuidDtoKey = "uuidRole";
const userLoginDtoKey = "login";
const userPasswordDtoKey = "password";
const userEmailDtoKey = "email";
const userFirstNameDtoKey = "firstName";
const userLastNameDtoKey = "lastName";
const userBirthdayDtoKey = "birthday";

const authorityApi = "http://localhost:8080/api/v1/";
const usersApiUrl = authorityApi + "users";
const rolesApiUrl = authorityApi + "roles";
const jwtsApiUrl = authorityApi + "jwts";

const buttonClasses = "btn btn-dark btn-outline-success";

const userMediaType = "application/vnd.rozetka.user+json";

const adminRoleName = "admin";
const userRoleName = "user";

function setContainer(content) {
    document.getElementById("main").innerHTML =
        `<section>
            <div class="container">
                <div class="row min-vh-100 justify-content-center align-items-center">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card bg-dark btn-dark" style="border-radius: 15px">
                            <div class="card-body p-5">` + content + `</div>
                        </div>
                    </div>
                </div>
            </div>
        </section>`;
}

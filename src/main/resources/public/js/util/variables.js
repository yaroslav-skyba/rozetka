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

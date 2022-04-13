function setUserAdminPage(storageKey, headlineInnerHtml, submitInnerHtml, httpMethod) {
    localStorage.setItem(modificationStorageKeyStorageKey, storageKey);
    setUserPage("../../../", "../../", "../", headlineInnerHtml, submitInnerHtml, httpMethod);
    userRole.hidden = false;
}

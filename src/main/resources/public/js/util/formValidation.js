function validate() {
    for (const formOutlineElement of document.getElementsByClassName("form-outline")) {
        const formControlElement = formOutlineElement.getElementsByClassName("form-control")[0];

        if (!formControlElement.checkValidity()) {
            alert("danger", formOutlineElement.getElementsByClassName("invalid-feedback")[0].innerHTML);
            return false;
        }

        if (formControlElement.value.length > 255) {
            alert("danger", "A field can't be longer than 255 symbols");
            return false;
        }
    }

    return true;
}
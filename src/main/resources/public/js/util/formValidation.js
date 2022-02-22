function validate() {
    for (const formControlElement of document.getElementsByClassName("form-control")) {
        formControlElement.addEventListener('input', () => {
            formControlElement.setCustomValidity('');
            formControlElement.checkValidity();
        });
    }

    const form = document.getElementById("form");

    form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        form.classList.add('was-validated');
    }, false);
}
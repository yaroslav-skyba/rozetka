const jwtKey = "jwt";
const roleNameKey = "roleName";

function setNavigation(brandHref, brandImgSrc, cartHref, aboutHref, loginHref, registrationHref, adminHref, userHref) {
    const main = document.getElementById("main");
    main.className = "min-vh-100";
    main.style.backgroundColor = "#201c24";

    new Promise(resolve => setTimeout(resolve, 100)).then(() => {
        setHeader(brandHref, brandImgSrc, cartHref, aboutHref);

        document.getElementById("footer").innerHTML =
            `<footer class="bg-dark text-center text-white">
                <div class="container p-4 pb-0">
                    <section class="mb-4">
                        <a class="btn btn-outline-light btn-floating m-1" href="https://www.linkedin.com/in/yaroslavskyba" role="button">
                            <em class="fab fa-linkedin-in"></em>
                        </a>
            
                        <a class="btn btn-outline-light btn-floating m-1" href="https://github.com/yaroslavskybadev" role="button">
                            <em class="fab fa-github"></em>
                        </a>
            
                        <a class="btn btn-outline-light btn-floating m-1" href="https://gitlab.com/yaroslavskyba" role="button">
                            <em class="fab fa-gitlab"></em>
                        </a>
                    </section>
                </div>
                
                <div class="text-center p-3" style="background-color: #00000033">
                    © 2022 Copyright:
                    <a class="text-white" href="https://thispersondoesnotexist.com/">Rozetka</a>
                </div>
            </footer>`;

        const navItem1 = document.getElementById("navItem1");
        const navItem2 = document.getElementById("navItem2");

        if (localStorage.getItem(jwtKey) == null) {
            navItem1.innerHTML = "Login";
            navItem1.href = loginHref;

            navItem2.innerHTML = "Registration";
            navItem2.href = registrationHref;
        } else {
            navItem1.innerHTML = "Profile";

            const roleName = localStorage.getItem(roleNameKey);

            if (roleName === "admin") {
                navItem1.href = adminHref;
            } else if (roleName === "user") {
                navItem1.href = userHref;
            } else {
                alert("Some errors occurred");
            }

            navItem2.innerHTML = "Logout";
            navItem2.href = loginHref;
            navItem2.onclick = function () {
                localStorage.removeItem(jwtKey);
                localStorage.removeItem(roleNameKey);
            }
        }
    });
}
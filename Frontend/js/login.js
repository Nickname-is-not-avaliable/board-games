document
    .getElementById("loginForm")
    .addEventListener("submit", function (event) {
        event.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        fetch("http://localhost:8080/api/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        }).then((response) => {
            if (response.status === 200) {
                response.json().then((data) => {

                    document.cookie = `id=${data.id}; path=/`;
                    document.cookie = `role=${data.role}; path=/`;

                        window.location.href = "catalog.html";

                    document.getElementById("loginMessage").textContent =
                        "Авторизация успешна.";
                });
            } else {
                document.getElementById("loginMessage").textContent =
                    "Ошибка авторизации.";
            }
        });
    });

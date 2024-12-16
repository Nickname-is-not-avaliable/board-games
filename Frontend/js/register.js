document
    .getElementById("registrationForm")
    .addEventListener("submit", function (event) {
        event.preventDefault();
        const email = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const repeatPassword = document.getElementById("repeatPassword").value;


        if (password.length < 8 || password.length > 15) {
                alert("Пароль должен содержать от 8 до 15 символов");
            return;
        }

        if (password !== repeatPassword) {
            alert("Пароли не совпадают");
            return;
        }

        fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email:email, password:password, role:"USER", username:`Инкогнито`}),
        })
            .then((response) => {
                if (response.status === 201) {
                    response.json().then((data) => {
                        document.cookie = `id=${data.userId}; path=/`;
                        document.cookie = `role=${data.role}; path=/`;
                        window.location.href = "landing.html";
                    })
                } else {
                    alert("Ошибка регистрации.");
                }

            });
    });

function updateEditUser(userId) {
    console.log(userId);
    fetch(`http://localhost:8080/api/users/${userId}`)
        .then((response) => response.json())
        .then((userData) => {
            document.getElementById("editUserId").value = userData.userId;
            document.getElementById("editUsername").value = userData.username;
            document.getElementById("editUserPassword").value = "oleg";
            document.getElementById("editUserEmail").value = userData.email;
        });
}

submitEditButton.onclick = function () {
    const userId = document.getElementById("editUserId").value;
    const username = document.getElementById("editUsername").value;
    console.log(username);
    const password = document.getElementById("editUserPassword").value;
    const email = document.getElementById("editUserEmail").value;

    editUser(userId, username, password, email);
};

function editUser(userId, username, password, email) {
    fetch(`http://localhost:8080/api/users/${userId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
            email: email,
        }),
    }).then((response) => {
        if (response.status === 200) {
            alert("Данные успешно изменены");
            updateUsers();
        } else {
            alert("Произола ошибка. Проверьте правильность введённых данных");
        }
    });
}

updateEditUser(getCookieValue('id'));


//===ORDERS===
function updateOrderTable() {
    const userId = getCookieValue('id');
    const orderStatus = document.getElementById("orderStatus").value;
    fetch(`http://localhost:8080/api/orders/by-user/${userId}`)
        .then((response) => response.json())
        .then(
            (data) => {
                const filteredData = filterByStatus(data, orderStatus,"status");
                const tableBody = document.querySelector("#OrderTable tbody");
                tableBody.innerHTML = "";
                filteredData.forEach((order) => {
                    const row = document.createElement("tr");

                    let orderStatus = order.status;
                    switch (order.status) {
                        case "CANCELLED":
                            orderStatus = "Отменён";
                            break;
                        case "OPENED":
                            orderStatus = "Активный";
                            break;
                        case "PREORDER":
                            orderStatus = "Предзаказ";
                            break;
                        case "DELIVERY":
                            orderStatus = "В пути";
                            break;
                        case "CONFIRMED":
                            orderStatus = "Куплен";
                            break;
                    }

                    row.innerHTML = `
              <td>${order.orderId}</td>
              <td>${order.orderDetails}</td>
              <td>${order.totalPrice}</td>
              <td>${orderStatus}</td>
              <td>${formatDateTime(order.orderDate)}</td>
              <td>
                <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" onclick="showDeclineOrderModal(${order.orderId})">Отменить</button>
              </td>
            `;
                    tableBody.appendChild(row);
                });
            });
}

const orderStatusSelect = document.getElementById("orderStatus");

orderStatusSelect.addEventListener("change", () => {
    updateOrderTable();
});

function showDeclineOrderModal(OrderId) {
    const modal = document.getElementById("deleteOrderModal");
    const confirmButton = document.getElementById("confirmDeclineOrder");
    const cancelButton = document.getElementById("cancelDeleteOrder");

    confirmButton.onclick = function () {
        modal.style.display = "none";
        alert(OrderId);
        declineOrder(OrderId);
    };

    cancelButton.onclick = function () {
        modal.style.display = "none";
    };

    modal.style.display = "block";
}

function declineOrder(OrderId) {
    fetch(`http://localhost:8080/api/orders/${OrderId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            status: "CANCELLED"
        }),
    }).then((response) => {
        if (response.status === 204) {
            updateOrderTable();
        }
    });
}

updateOrderTable();
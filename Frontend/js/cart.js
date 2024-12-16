async function updateOrderTable() {
    try {
        const userId = getCookieValue('id');
        const response = await fetch(`http://localhost:8080/api/orders/by-user/${userId}`);
        
        if (!response.ok) {
            throw new Error(`Failed to fetch orders: ${response.statusText}`);
        }

        const data = await response.json();
        const orders = Array.isArray(data) ? data : data.orders || [];

        if (!Array.isArray(orders)) {
            throw new Error("Expected 'orders' to be an array but received: " + typeof orders);
        }

        const filteredData = filterByStatus(orders, "CART", "status");
        const tableBody = document.querySelector("#OrderTable tbody");
        tableBody.innerHTML = "";

        for (const order of filteredData) {
            const gameId = await getGameIdByTitle(order.orderDetails);

            const row = document.createElement("tr");
            row.innerHTML = `
                <td>
                    <a href="game.html?id=${gameId || ''}"
                    class="link-offset-2 link-light link-underline link-underline-opacity-0">
                    ${order.orderDetails}
                    </a>
                </td>
                <td>${order.totalPrice}</td>
                <td>${formatDateTime(order.orderDate)}</td>
                <td>
                    <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" onclick="declineOrder(${order.orderId})">
                        Удалить из избранного
                    </button>
                </td>
            `;
            tableBody.appendChild(row); // Добавляем строку в таблицу
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

async function getGameIdByTitle(title) {
    try {
        const response = await fetch("http://localhost:8080/api/board-games");
        if (!response.ok) {
            throw new Error(`Failed to fetch board games: ${response.statusText}`);
        }

        const games = await response.json();
        const game = games.find((g) => g.title === title);
        return game ? game.id : null; // Возвращаем ID или null, если игра не найдена
    } catch (error) {
        console.error("Error fetching board games:", error);
        return null;
    }
}

function showDeclineOrderModal(OrderId) {
    const modal = document.getElementById("declineOrderModal");
    const confirmButton = document.getElementById("confirmDeclineOrder");
    const cancelButton = document.getElementById("cancelDeclineOrder");

    confirmButton.onclick = function () {
        modal.style.display = "none";
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
        if (response.status === 200) {
            updateOrderTable();
        }
    });
}

function aproveOrder(OrderId) {
    fetch(`http://localhost:8080/api/orders/${OrderId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            status: "OPENED"
        }),
    }).then((response) => {
        if (response.status === 200) {
            updateOrderTable();
        }
    });
}

function createAndSendOrder() {
    const tableBody = document.querySelector("#OrderTable tbody");
    const rows = tableBody.querySelectorAll("tr");

    let totalOrderPrice = 0;
    let orderDetails = [];

    rows.forEach(row => {
        const orderId = row.querySelector("td:nth-child(1)").innerText;
        const details = row.querySelector("td:nth-child(2)").innerText;
        const price = parseFloat(row.querySelector("td:nth-child(3)").innerText);

        orderDetails.push(`${orderId}: ${details}`);
        totalOrderPrice += price;

        deleteOrder(orderId);
    });

    const newOrder = {
        orderDetails: orderDetails.join(", "),
        totalPrice: totalOrderPrice,
        status: "OPENED"
    };

    fetch('http://localhost:8080/api/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newOrder),
    }).then(response => {
        if (response.status === 201) {
            tableBody.innerHTML = ""; // Очищаем таблицу после создания заказа
            updateOrderTable();
        }
    });
}

function deleteOrder(orderId) {
    fetch(`http://localhost:8080/api/orders/${orderId}`, {
        method: "DELETE"
    }).then(response => {
        if (response.status === 200) {
            console.log(`Order ${orderId} deleted`);
        }
    });
}

updateOrderTable();
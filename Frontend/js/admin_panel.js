//===ORDERS===
// function updateOrderTable() {
//     const orderStatus = document.getElementById("orderStatus").value;
//     fetch("http://localhost:8080/api/orders")
//         .then((response) => response.json())
//         .then(
//             (data) => {
//                 const filteredData = filterByStatus(data, orderStatus, "status");
//                 const tableBody = document.querySelector("#OrderTable tbody");
//                 tableBody.innerHTML = "";
//                 filteredData.forEach((order) => {
//                     const row = document.createElement("tr");

//                     fetch(`http://localhost:8080/api/users/${order.userId}`)
//                         .then(response => {
//                             if (!response.ok) {
//                                 throw new Error(`Ошибка HTTP: ${response.status}`);
//                             }
//                             return response.json();
//                         })
//                         .then(userData => {

//                             row.innerHTML = `
//               <td>${order.orderId}</td>
//               <td>${order.orderDetails}</td>
//               <td>${userData.username}</td>
//               <td>${order.totalPrice}</td>
//               <td>${formatDateTime(order.orderDate)}</td>
//               <td>
//                 <button class="btn btn-outline-light" type="button" data-bs-toggle="modal" data-bs-target="#editOrderModal" data-bs-whatever="@getbootstrap" onclick="showEditOrder(${order.orderId})">Редактировать</button>
//                 <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" onclick="showDeleteOrderModal(${order.orderId})">Удалить</button>
//               </td>
//             `;
//                             tableBody.appendChild(row);
//                         });
//                 });
//             });
// }

// const orderStatusSelect = document.getElementById("orderStatus");

// orderStatusSelect.addEventListener("change", () => {
//     updateOrderTable();
// });

// function showEditOrder(orderId) {
//     const modal = document.getElementById("editBookingModal");
//     fetchAPI(`orders/${orderId}`, orderData => {
//             document.getElementById("editOrderId").value = orderData.orderId;
//             document.getElementById("orderDetails").value = orderData.orderDetails;
//             document.getElementById("totalPrice").value = orderData.totalPrice;
//             document.getElementById("editOrderStatus").value = orderData.status;

//     });

//     submitEditOrder.onclick = function () {
//         const orderId = parseInt(document.getElementById("editOrderId").value);
//         const orderDetails = document.getElementById("orderDetails").value;
//         const totalPrice = parseInt(document.getElementById("totalPrice").value);
//         const status = document.getElementById("editOrderStatus").value;

//         editOrder(orderId, orderDetails, totalPrice, status)
//     }
// }

// function editOrder(orderId, orderDetails, totalPrice, status) {
//     fetch(`http://localhost:8080/api/orders/${orderId}`, {
//         method: "PATCH",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify({
//             total_price: totalPrice,
//             order_details: orderDetails,
//             status: status
//         }),
//     }).then((response) => {
//         if (response.status === 200) {
//             updateOrderTable();
//         }
//     });
// }

// function showDeleteOrderModal(OrderId) {
//     const modal = document.getElementById("deleteOrderModal");
//     const confirmButton = document.getElementById("confirmDeleteOrder");
//     const cancelButton = document.getElementById("cancelDeleteOrder");

//     confirmButton.onclick = function () {
//         modal.style.display = "none";
//         deleteOrder(OrderId);
//     };

//     cancelButton.onclick = function () {
//         modal.style.display = "none";
//     };

//     modal.style.display = "block";
// }

// function deleteOrder(OrderId) {
//     fetch(`http://localhost:8080/api/orders/${OrderId}`, {
//         method: "DELETE",
//     }).then((response) => {
//         if (response.status === 204) {
//             updateOrderTable();
//         }
//     });
// }

// updateOrderTable();


//===USERS===
function updateUsers() {
    const userStatus = document.getElementById("userStatus").value;

    fetchAPI("users", data => {
        const tableBody = document.querySelector("#userTable tbody");

        const filteredData = filterByStatus(data, userStatus, "role");

        tableBody.innerHTML = "";
        let i = 0;
        filteredData.forEach((user) => {
            i++;
            const row = document.createElement("tr");
            row.innerHTML = `
                  <td>${i}</td>
                  <td>${user.username}</td>
                  <td>${user.email}</td>
                  <td>${user.dateOfRegistration}</td>
                  <td>
                      <button class="btn btn-outline-light" type="button" data-bs-toggle="modal" data-bs-target="#editUserModal" data-bs-whatever="@getbootstrap" onclick="showEditUserModal(${user.userId})">Редактировать</button>
                      <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteUserModal" onclick="showDeleteUserModal(${user.userId})">Удалить</button>
                  </td>
              `;
            tableBody.appendChild(row);
        });
    });
}

const userStatusSelect = document.getElementById("userStatus");

userStatusSelect.addEventListener("change", () => {
    updateUsers();
});


function showEditUserModal(userId) {
    const modal = document.getElementById("editUserModal");

    fetch(`http://localhost:8080/api/users/${userId}`)
        .then((response) => response.json())
        .then((userData) => {
            document.getElementById("editUserId").value = userData.userId;
            document.getElementById("editUsername").value = userData.username;
            document.getElementById("editUserPassword").value = "oleg";
            document.getElementById("editUserEmail").value = userData.email;
            document.getElementById("editUserRole").value = userData.role;
        });

    submitEditUser.onclick = function () {
        const userId = document.getElementById("editUserId").value;
        const username = document.getElementById("editUsername").value;
        console.log(username);
        const password = document.getElementById("editUserPassword").value;
        const email = document.getElementById("editUserEmail").value;
        const role = document.getElementById("editUserRole").value;

        editUser(userId, username, password, email, role);
    };


    modal.style.display = "block";
}


function editUser(userId, username, password, email, role) {
    fetch(`http://localhost:8080/api/users/${userId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
            email: email,
            role: role
        }),
    }).then((response) => {
        if (response.status === 200) {
            const modal = document.getElementById("editUserModal");
            modal.style.display = "none";
            updateUsers();
        }
    });
}

function showDeleteUserModal(userId) {
    const confirmButton = document.getElementById("confirmDeleteUser");
    confirmButton.onclick = function () {
        deleteUser(userId);
    };
}

function deleteUser(userId) {
    if (userId == getCookieValue("id")) {
        alert("Вы не можете удалить себя!");
    } else {
        fetch(`http://localhost:8080/api/users/${userId}`, {
            method: "DELETE",
        }).then((response) => {
            if (response.status === 204) {
                updateUsers();
            }
        });
    }
}

updateUsers();

//===PRODUCTS===

async function uploadFile() {
    const fileInput = document.getElementById('previewImage');
    const file = fileInput.files[0];

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch("http://localhost:8080/api/files/uploadFile", {
            method: "POST",
            body: formData
        });
    } catch (error) {
        console.error('Произошла ошибка:', error);
    }
}

function updateProducts() {
    const productStatus = document.getElementById("productStatus").value;
    fetchAPI("board-games", data => {
        const filteredData = filterByStatus(data, productStatus, "category");
        const tableBody = document.querySelector("#productTable tbody");
        tableBody.innerHTML = "";
        let i = 0;
        filteredData.forEach((product) => {
            const row = document.createElement("tr");
            fetchAPI(`stocks/by-board-game/${product.id}`, stockDataArray => {
                const totalQuantity = stockDataArray.reduce((total, stockData) => {
                    return total + (stockData.quantity || 0);
                }, 0);
                i++;
                row.innerHTML = `
                  <td>${i}</td>
                  <td>${product.title}</td>
                  <td>${product.price}</td>
                  <td>${product.category}</td>
                  <td>
                      <button class="btn btn-outline-light" type="button" data-bs-toggle="modal" data-bs-target="#editProductModal" data-bs-whatever="@getbootstrap" onclick="showEditProductModal(${product.id})">Редактировать</button>
                      <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteProductModal" onclick="showDeleteProductModal(${product.id})">Удалить</button>
                  </td>
              `;
            });
            tableBody.appendChild(row);
        });
    });
}

const productStatusSelect = document.getElementById("productStatus");

productStatusSelect.addEventListener("change", () => {
    updateProducts();
});

function showEditProductModal(productId) {
    const modal = document.getElementById("editProductModal");

    fetch(`http://localhost:8080/api/board-games/${productId}`)
        .then((response) => response.json())
        .then((productData) => {
            document.getElementById("title").value = productData.title;
            document.getElementById("description").value = productData.description;
            document.getElementById("category").value = productData.category;
            document.getElementById("releaseDate").value = productData.releaseDate;
            document.getElementById("countryOfManufacture").value = productData.countryOfManufacture;
            document.getElementById("price").value = productData.price;
            document.getElementById("numberOfPlayers").value = productData.numberOfPlayers;
            document.getElementById("age").value = productData.age;
            document.getElementById("playtime").value = productData.playtime;
            document.getElementById("publisher").value = productData.publisher;
            document.getElementById("reviewLink").value = productData.reviewLink;
            const imageUrl = productData.previewImage;
            console.log("Image URL:", imageUrl);

            fetch(imageUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.blob();
                })
                .then(blob => {
                    const imageUrl = blob.url;

                    console.log("Created URL:", imageUrl);

                    const previewImage = document.getElementById("previewImage");
                    if (previewImage) {
                        previewImage.src = imageUrl;
                    }
                })
                .catch(error => {
                    console.error('Fetch error:', error);
                });

        });

    submitEditProduct.onclick = function () {
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;
        const category = document.getElementById("category").value;
        const releaseDate = document.getElementById("releaseDate").value;
        const countryOfManufacture = document.getElementById("countryOfManufacture").value;
        const price = parseFloat(document.getElementById("price").value);
        const numberOfPlayers = parseInt(document.getElementById("numberOfPlayers").value);
        const age = parseInt(document.getElementById("age").value);
        const playtime = document.getElementById("playtime").value;
        const publisher = document.getElementById("publisher").value;
        const reviewLink = document.getElementById("reviewLink").value;
        const quantity1 = parseInt(document.getElementById("quantity1").value);
        const quantity2 = parseInt(document.getElementById("quantity2").value);


        const inputElement = document.getElementById("previewImage");
        const selectedFile = inputElement.files[0];

        const previewImage = selectedFile.name;

        editProduct(productId, title, description, category, releaseDate, countryOfManufacture, price, numberOfPlayers, age, playtime, publisher, reviewLink, quantity1, quantity2, previewImage);
    };


    modal.style.display = "block";
}


function editProduct(productId, title, description, category, releaseDate, countryOfManufacture, price, numberOfPlayers, age, playtime, publisher, reviewLink, quantity1, quantity2, previewImage) {
    fetch(`http://localhost:8080/api/board-games/${productId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            title: title,
            description: description,
            publisher: publisher,
            release_date: releaseDate,
            category: category,
            price: price,
            preview_image: "http://localhost:8080/api/files/search/" + previewImage,
            number_of_players: numberOfPlayers,
            age: age,
            playtime: playtime,
            review_link: reviewLink,
            country_of_manufacture: countryOfManufacture,
        })
    })
        .then(response => response.json())
        .then(boardGameData => {
            updateProducts();
            if (boardGameData.id) {
                const boardGameId = boardGameData.id;

                fetchAPI(`stocks/by-board-game/${productId}`, stockData => {
                    const stockIds = stockData.map(stock => stock.stockId);

                    stockIds.forEach(stockId => {
                        fetchAPI(`stocks/${stockId}`, response => {
                            if (response.ok) {
                                console.log(`Stock with ID ${stockId} deleted successfully`);
                            } else {
                                console.error(`Error deleting stock with ID ${stockId}`);
                            }
                        }, {method: "DELETE"});
                    });
                });

                fetch("http://localhost:8080/api/stocks", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        boardGameId: boardGameId,
                        storeId: 1,
                        quantity: quantity1,
                    }),
                })
                    .then(response => {
                        if (response.status === 201) {
                        } else {
                            alert("Произошла ошибка. Проверьте правильность указанного количества товара в магазине 1");
                        }
                    })
                    .catch(error => {
                        console.error("Error adding stock information:", error);
                        alert("Произошла ошибка при добавлении информации о количестве товара");
                    });
                fetch("http://localhost:8080/api/stocks", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        boardGameId: boardGameId,
                        storeId: 2,
                        quantity: quantity2,
                    }),
                })
                    .then(response => {
                        if (response.status === 201) {
                        } else {
                            alert("Произошла ошибка. Проверьте правильность указанного количества товара в магазине 2");
                        }
                    })
                    .catch(error => {
                        console.error("Error adding stock information for storeId 2:", error);
                        alert("Произошла ошибка при добавлении информации о количестве товара для магазина 2");
                    });
            } else {
                alert("Не найдено товара для добавления его в магазины");
            }
        });
}

function showDeleteProductModal(userId) {
    const confirmButton = document.getElementById("confirmDeleteProduct");
    confirmButton.onclick = function () {
        deleteProduct(userId);
    };
}

function deleteProduct(productId) {
    fetch(`http://localhost:8080/api/board-games/${productId}`, {
        method: "DELETE",
    }).then((response) => {
        if (response.status === 204) {
            updateProducts();
        }
    });
}

updateProducts();
updateUsers();
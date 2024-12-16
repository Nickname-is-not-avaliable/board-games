function updateProducts(endpoint) {

    console.log(`Fetching from: ${endpoint}`);
    if (typeof productContainer === 'undefined' || productContainer === null) {
        console.error('productContainer is not defined. Please define it before calling updateProducts.');
        return;
    }

    fetchAPI(`${endpoint}`, data => {

        fetchAPI(`${endpoint}`, async data => {

                data.forEach(product => {

                    const productId = document.createElement("span");
                    productId.textContent = product.id;
                    productId.style.display = "none";

                    const preview = document.createElement("div");
                    preview.classList.add("product-card");

                    const image = document.createElement("img");
                    image.src = product.previewImage;
                    image.alt = product.title;
                    image.title = product.title;
                    image.classList.add("product-image");

                    const title = document.createElement("div");
                    title.textContent = product.title;
                    title.classList.add("product-title");

                    const price = document.createElement("h4");
                    price.textContent = `Цена: ${product.price} руб.`;
                    price.className = "ms-2 text-light";


                    const buyButton = document.createElement("button");
                    buyButton.style.display = "none";

                    const category = document.createElement("span");
                    category.textContent = `${product.category}  ${product.age || 0}+`;
                    category.className = "ms-2 text-light";
                    
                    buyButton.style.display = "inline-block";
                    buyButton.textContent = "★ В избранное";
                    buyButton.className = "btn btn-outline-success btn-lg";
                    buyButton.style.width = "100%";
                    buyButton.addEventListener("click", function () {
                        handleBuyButtonClick(product.id);
                    });
                    
                    preview.appendChild(image);
                    preview.appendChild(title);
                    preview.appendChild(price);
                    preview.appendChild(category);
                    preview.appendChild(buyButton);
                    preview.appendChild(productId);


                    productContainer.appendChild(preview);

                    preview.addEventListener("click", function () {
                        window.location.href = `game.html?id=${product.id}`;
                    });

                    buyButton.addEventListener("click", function (event) {
                        console.log(`Buy button clicked for product ID: ${product.id}`);
                        event.stopPropagation();
                    });
                });

                if (getCookieValue("role") === "ADMIN") {
                    const productCard = document.createElement("div");
                    productCard.classList.add("product-card");

                    const productImage = document.createElement("img");
                    productImage.src = "http://localhost:8080/api/files/search/plus.png";
                    productImage.alt = "new";
                    productImage.title = "Добавить товар";
                    productImage.classList.add("new-image");

                    const productTitle = document.createElement("div");
                    productTitle.textContent = "Добавить товар";
                    productTitle.classList.add("product-title");

                    productCard.appendChild(productImage);
                    productContainer.appendChild(productCard);

                    productCard.addEventListener("click", function () {
                        window.location.href = `addGame.html`;
                    });
                }
            });
    });
}

function handleBuyButtonClick(productId) {

    const userId = getCookieValue("id");

    if (productId && userId) {
        fetch(`http://localhost:8080/api/board-games/${productId}`)
            .then(response => response.json())
            .then(productData => {
                const title = productData.title;
                const price = productData.price;

                const currentDate = new Date();


                fetch("http://localhost:8080/api/orders", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        userId: userId,
                        status: "CART",
                        orderDetails: title,
                        totalPrice: price,
                        orderDate: currentDate
                    }),
                })
                    .then(response => {
                        if (response.ok) {
                            alert("Игра добавлена в избранное");
                        } else {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                    })
                    .catch(error => {
                        console.error("Error adding product to order:", error);
                        alert("Произошла ошибка при добавлении игры в избранное");
                    });
            })
            .catch(error => {
                console.error("Error fetching product details:", error);
                alert("Произошла ошибка при получении деталей товара");
            });
    } else {
        alert("Не удалось получить идентификатор продукта или пользователя");
    }
}

const currentURL = window.location.href;
const urlObject = new URL(currentURL);
const productId = urlObject.searchParams.get("id");
const productCategory = urlObject.searchParams.get("category");
const popular = urlObject.searchParams.get("popular") === "1"
const best = urlObject.searchParams.get("best") === "1"

if (!productId && !productCategory && !popular && !best) {
    updateProducts('board-games');
} else if (Boolean(popular)) {
    updateProducts('board-games/sorted-by-category');
} else if (Boolean(productId)) {
    updateProducts(`board-games/search?searchString=${productId}`);
} else if (Boolean(productCategory)) {
    updateProducts(`board-games/by-category/${productCategory}`);
} else if (Boolean(best)) {
    updateProducts(`orders/sorted-by-best-games`);
} else {
    console.error('Invalid parameters');
}


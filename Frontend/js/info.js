document.addEventListener("DOMContentLoaded", function () {

    function calculateStatistics() {
        let totalOrders = 0;
        const serviceStats = {};
        const dailyOrders = {}; 

        fetchAPI("orders", data => {
            const filteredData = filterByStatus(data, "CART", "status");

            totalOrders = filteredData.length;
            filteredData.forEach((filteredDatum) => {
                const orderDate = moment(filteredDatum.orderDate).format('YYYY-MM-DD'); 

                // Считаем количество заказов для каждого дня
                dailyOrders[orderDate] = (dailyOrders[orderDate] || 0) + 1;

                const orderDetail = filteredDatum.orderDetails;
                serviceStats[orderDetail] = (serviceStats[orderDetail] || 0) + 1;
            });

            const salesDates = Object.keys(dailyOrders).sort(); 
            const orderCounts = salesDates.map(date => dailyOrders[date]); 
            new Chart(salesChart, {
                type: 'bar',
                data: {
                    labels: salesDates,
                    datasets: [{
                        label: 'Количество заказов',
                        data: orderCounts,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        x: {
                            type: 'time',
                            time: {
                                parser: 'YYYY-MM-DD',
                                unit: 'day',
                                displayFormats: {
                                    day: 'MMM D, YYYY'
                                },
                            },
                            title: {
                                display: true,
                                text: 'Дата'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            suggestedMax: Math.max(...orderCounts) + 1, 
                            title: {
                                display: true,
                                text: 'Количество заказов'
                            }
                        }
                    }
                }
            });

            document.getElementById("totalOrders").textContent = totalOrders;

            const orderStatsList = document.getElementById("serviceStats");
            for (const service in serviceStats) {
                const listItem = document.createElement("li");
                listItem.textContent = `${service}: ${serviceStats[service]} раз`;
                orderStatsList.appendChild(listItem);
            }

        });

    }
    calculateStatistics();
});

function fetchAPI(endpoint, callback, options = {}) {
    const apiUrl = `http://localhost:8080/api/${endpoint}`;

    fetch(apiUrl, options)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => callback(data))
        .catch(error => console.error('Fetch error:', error));
}
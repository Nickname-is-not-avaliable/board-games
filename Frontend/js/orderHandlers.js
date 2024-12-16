function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);
    return `${date.toLocaleDateString()}, ${date.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'})}`;
}

function filterByStatus(data, status, statusName) {
    if (status === "ALL") {
        return data;
    } else {
        return data.filter((Thing) => Thing[statusName] === status);
    }
}
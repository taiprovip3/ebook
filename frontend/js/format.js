function formatDate(input) {
    const date = new Date(input);

    const hours = date.getUTCHours().toString().padStart(2, '0');
    const minutes = date.getUTCMinutes().toString().padStart(2, '0');
    const day = date.getUTCDate().toString().padStart(2, '0');
    const month = (date.getUTCMonth() + 1).toString().padStart(2, '0'); // Tháng tính từ 0
    const year = date.getUTCFullYear();

    return `${hours}:${minutes} ${day}/${month}/${year}`;
}
function formatVND(number) {
    return number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

function formatNumber(number) {
    return new Intl.NumberFormat().format(number);
}
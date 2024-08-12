function formatDate(input) {
    const date = new Date(input);

    let hours = date.getUTCHours();
    const minutes = date.getUTCMinutes().toString().padStart(2, '0');
    const day = date.getUTCDate().toString().padStart(2, '0');
    const month = (date.getUTCMonth() + 1).toString().padStart(2, '0'); // Tháng tính từ 0
    const year = date.getUTCFullYear();

    const period = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12 || 12; // Chuyển đổi giờ từ 24h sang 12h định dạng
    const formattedHours = hours.toString().padStart(2, '0');

    return `${formattedHours}:${minutes} ${period} ${day}/${month}/${year}`;
}

function formatVND(number) {
    return number.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

function formatNumber(number) {
    return new Intl.NumberFormat().format(number);
}
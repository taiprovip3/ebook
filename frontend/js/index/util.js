/**
 * 
 * Chứa các hàm xử lý của index.html
 */
/* FlashSale Countdown */
const interval = setInterval(updateCountdown, 1000);
updateCountdown(); // Cập nhật ngay lập tức khi trang được load

function updateCountdown() {
    const now = new Date();
    const saleEndTime = new Date('2024-07-28T14:05:00'); // Thời gian kết thúc flashsale
    const timeDifference = saleEndTime - now;

    if (timeDifference > 0) {
        const hours = Math.floor(timeDifference / (1000 * 60 * 60));
        const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

        // document.getElementById('flashSaleExpired').textContent = `${hours} giờ ${minutes} phút ${seconds} giây`;
        document.getElementById('flashSaleExpired').innerHTML = `
            <span class="badge bg-dark p-2 mx-1">${hours} Giờ</span>:<span class="badge bg-dark p-2 mx-1">${minutes} Phút</span>:<span class="badge bg-dark p-2 mx-1">${seconds} Giây</span>
        `;

    } else {
        document.getElementById('flashSaleExpired').textContent = 'Flash sale đã kết thúc';
        clearInterval(interval);
    }
}

function searchBooks() {
    const keySearchBook = $('#keySearchBook').val();
    if (keySearchBook) {
        window.location.href = './search.html?key=' + encodeURIComponent(keySearchBook);
    }
}
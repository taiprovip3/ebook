function searchBooks() {
    const keySearchBook = $('#keySearchBook').val();
    if (keySearchBook) {
        window.location.href = './search.html?key=' + encodeURIComponent(keySearchBook);
    }
}
async function getBookById(bookId) {
    return fetch(`http://localhost:8080/api/v1/book/${bookId}`)
        .then(response => response.json());
}
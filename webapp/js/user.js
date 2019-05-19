// app.service('User', function() {
//     function Book(title, author, isbn) {
//         this.title = title;
//         this.author = author;
//         this.isbn = isbn;
//     }
//     this.create = function(title, author, isbn) {
//         return new Book(title, author, isbn);
//     }
// });
//
// app.controller('BookController', function (Book) {
//     console.log(Book);
//     this.books = [
//         Book.create('Henryk Sienkiewicz', 'Krzyżacy', '123456789'),
//         Book.create('Adam Mickiewicz', 'Dziady', "1324354657"),
//         Book.create('Maria Konopnicka', 'Dym', "9786756453")
//     ];
//     this.addBook = function(book) {
//         this.books.push(new Book(book.title, book.author, book.isbn));
//     }
// });
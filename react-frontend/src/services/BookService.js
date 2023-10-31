import axios from 'axios';

const BOOK_API_URL = "http://localhost:8002/book";

class BookService {

    getBooks(){
        return axios.get(BOOK_API_URL);
    }

    createBook(book){
        return axios.post(BOOK_API_URL, book);
    }

    getBookById(bookId){
        return axios.get(BOOK_API_URL + '/' + bookId);
    }
}

export default new BookService()
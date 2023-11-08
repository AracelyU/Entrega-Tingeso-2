import React, { Component } from 'react'
import BookService from '../services/BookService'

class ListBookComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            books: []
        }
        this.addBook = this.addBook.bind(this);
    }

    addBook(){
        this.props.history.push('/add-book');
    }

    viewBook(id){
        this.props.history.push('/view-book/' + id);
    }

    updateBook(id){
        alert("Updating a Book is still under construction...");
    }

    deleteBook(id){
        alert("Deleting a Book is still under construction...");
    }

    componentDidMount(){
        BookService.getBooks().then((res) => {
            this.setState({ books: res.data});
        });
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">List of Books</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addBook}>Add Book</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Book Title</th>
                                    <th> Book Author</th>
                                    <th> StudentId</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.books.map(
                                        book => 
                                        <tr key = {book.id}>
                                             <td> {book.title} </td>   
                                             <td> {book.author}</td>
                                             <td> {book.studentId}</td>
                                             <td>
                                                 <button onClick={ () => this.viewBook(book.id)} className="btn btn-info">View</button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.updateBook(book.id)} className="btn btn-info">Update</button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteBook(book.id)} className="btn btn-danger">Delete</button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListBookComponent

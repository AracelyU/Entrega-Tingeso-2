import React, { Component } from 'react'
import BookService from '../services/BookService'

class ViewBookComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            book: {}
        }
    
    }

    componentDidMount(){
        BookService.getBookById(this.state.id).then( res => {
            this.setState({book: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Book Details</h3>
                    <div className = "card-body">
                        <div class="card-body">
                            <label> Book Title: </label>
                            <div class="card"> { this.state.book.title }</div>
                        </div>
                        <div class="card-body">
                            <label> Book Author: </label>
                            <div class="card"> { this.state.book.author }</div>
                        </div>
                        <div class="card-body">
                            <label> Student ID: </label>
                            <div class="card"> { this.state.book.studentId }</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default ViewBookComponent

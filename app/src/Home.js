import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/store">Магазин</Link></Button>
                </Container>
                <Container fluid>
                    <Button color="link"><Link to="/stock">Склад</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;
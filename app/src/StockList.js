import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class StockList extends Component {

    constructor(props) {
        super(props);
        this.state = {stock: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('api/stock')
            .then(response => response.json())
            .then(data => this.setState({stock: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/api/stock/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedStore = [...this.state.stock].filter(i => i.id !== id);
            this.setState({stock: updatedStore});
        });
    }

    render() {
        const {stock, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const storeList = stock.map(clothes =>
            <tr key={clothes.id}>
                <td style={{whiteSpace: 'nowrap'}}>{clothes.name}</td>

                <td>
                    <div key={clothes.id}>
                        {'вид одежды: ' + clothes.type + ', '}
                        {'размер: ' + clothes.size + ', '}
                        {'цена: ' + clothes.cost + ', '}
                        {'цвет: ' + clothes.color + '. '}</div>
                </td>
                <td>
                    <div key={clothes.id}>
                        {' ' + clothes.description + '.'}</div>
                </td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/stock/" + clothes.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(clothes.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>);

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/stock/new">Добавить одежду</Button>
                    </div>
                    <h3>Склад</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Наименование</th>
                            <th width="20%">Характеристики</th>
                            <th width="20%">Описание</th>
                            <th width="10%">Действия</th>
                        </tr>
                        </thead>
                        <tbody>
                        {storeList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default StockList;
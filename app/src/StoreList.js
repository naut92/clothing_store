import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class StoreList extends Component {

    constructor(props) {
        super(props);
        this.state = {store: [], isLoading: true};
        this.remove = this.remove.bind(this);
        this.move = this.move.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/api/store')
            .then(response => response.json())
            .then(data => this.setState({store: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/api/store/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedStore = [...this.state.store].filter(i => i.id !== id);
            this.setState({store: updatedStore});
        });
    }

    async move(id) {
        await fetch(`/api/store/move/${id}`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedStore = [...this.state.store].filter(i => i.id !== id);
            this.setState({store: updatedStore});
        });
    }

    render() {
        const {store, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const storeList = store.map(clothes => {
            return <tr key={clothes.id}>
                <td style={{whiteSpace: 'nowrap'}}> <p key={clothes.id}>
                    {clothes.name}</p>
                </td>
                <td><div key={clothes.id}>
                        {'вид одежды: ' + clothes.type + ', '}<br/>
                        {'размер: ' + clothes.size + ', '}<br/>
                        {'цена: ' + clothes.cost + ', '}<br/>
                        {'цвет: ' + clothes.color + '. '}</div>
                </td>
                <td>
                    <div key={clothes.id}>
                        {' ' + clothes.description + '.'}</div>
                </td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/store/" + clothes.id}>Изменить</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(clothes.id)}>Удалить</Button>
                        <Button size="sm" color="warning" onClick={() => this.move(clothes.id)}>Move to Stock</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/store/new">Добавить одежду</Button>
                    </div>
                    <h3>Магазин</h3>
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

export default StoreList;
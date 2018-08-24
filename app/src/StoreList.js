import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class StoreList extends Component {

    constructor(props) {
        super(props);
        this.state = {store: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('api/store')
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

    render() {
        const {store, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const storeList = store.map(store => {
            return <tr key={store.id}>
                <td style={{whiteSpace: 'nowrap'}}>{store.clothesName}</td>

                <td>{store.storeByClothes.map(clothes => {
                    return <div key={clothes.id}>
                        {'вид одежды: ' + clothes.type + ', '}
                    {'размер: ' + clothes.size + ', '}
                    {'цена: ' + clothes.cost + ', '}
                    {'цвет: ' + clothes.color + '. '}</div>
                })}</td>
                <td>{store.storeByClothes.map(clothes => {
                    return <div key={clothes.id}>
                        {' ' + clothes.description + '.'}</div>
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/store/" + store.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(store.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/store/new">Add Clothes</Button>
                    </div>
                    <h3>Store</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Наименование</th>
                            <th width="20%">Характеристики</th>
                            <th width="20%">Описание</th>
                            <th width="10%">Actions</th>
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
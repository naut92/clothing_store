import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class StockEdit extends Component {

    emptyItem = {
        name: '',
        size: '',
        cost: '',
        color: '',
        type: '',
        description: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const stock = await (await fetch(`/api/stock/${this.props.match.params.id}`)).json();
            this.setState({item: stock});
        }
    }

    handleChange(store) {
        const target = store.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/api/stock', {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/stock');
    }

    render() {
        const {item} = this.state;
        console.log(item.name);
        const title = <h2>{item.id ? 'Внести изменения' : 'Добавить одежду'}</h2>;
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Наименование</Label>
                        <Input type="text" name="name" id="name"
                               value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Описание</Label>
                        <Input type="text" name="description" id="description"
                               value={item.description || ''}
                               onChange={this.handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="cost">Цена</Label><Input type="text" name="cost" id="cost"
                                                             value={item.cost || ''}
                                                             onChange={this.handleChange} autoComplete="cost"/>
                    </FormGroup>
                    <div className="row">
                        <FormGroup className="col-md-4 mb-3">
                            <Label for="color">Цвет</Label>
                            <Input type="text" name="color" id="color"
                                   value={item.color || ''}
                                   onChange={this.handleChange} autoComplete="color"/>
                        </FormGroup>
                        <FormGroup className="col-md-5 mb-3">
                            <Label for="type">Вид одежды</Label>
                            <Input type="text" name="type" id="type"
                                   value={item.type || ''}
                                   onChange={this.handleChange} autoComplete="type"/>
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="size">Размер</Label>
                            <Input type="text" name="size" id="sise"
                                   value={item.size || ''}
                                   onChange={this.handleChange} autoComplete="size"/>
                        </FormGroup>
                    </div>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/stock">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(StockEdit);

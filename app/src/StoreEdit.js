import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class StoreEdit extends Component {

    emptyItem = {
        clothesName: '',
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
            const store = await (await fetch(`/api/store/${this.props.match.params.id}`)).json();
            this.setState({item: store});
        }
    }

    handleChange(store) {
        const target = store.target;
        const value = target.value;
        const name = target.clothesName;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/api/store', {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/store');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Clothes' : 'Add Clothes'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.clothesName || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={this.handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="cost">Cost</Label>
                        <Input type="text" name="cost" id="cost" value={item.cost || ''}
                               onChange={this.handleChange} autoComplete="cost"/>
                    </FormGroup>
                    <div className="row">
                        <FormGroup className="col-md-4 mb-3">
                            <Label for="color">Color</Label>
                            <Input type="text" name="color" id="color" value={item.color || ''}
                                   onChange={this.handleChange} autoComplete="color"/>
                        </FormGroup>
                        <FormGroup className="col-md-5 mb-3">
                            <Label for="type">Type clothes</Label>
                            <Input type="text" name="type" id="type" value={item.type || ''}
                                   onChange={this.handleChange} autoComplete="type"/>
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="size">Size</Label>
                            <Input type="text" name="size" id="sise" value={item.size || ''}
                                   onChange={this.handleChange} autoComplete="size"/>
                        </FormGroup>
                    </div>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/store">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(StoreEdit);

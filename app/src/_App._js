import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
    state = {
        isLoading: true,
        store: []
    };

    async componentDidMount() {
        const response = await fetch('/api/store');
        const body = await response.json();
        this.setState({ store: body, isLoading: false });
    }

    render() {
        const {store, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <h1 className="App-title">Welcome to React</h1>
                </header>
                <div className="App-intro">
                    <h2>Clothes Store</h2>
                    {store.map(store =>
                        <div key={store.id}>
                            {'Наименование: ' + store.clothesName}
                            {store.storeByClothes.map(clothes =>
                            <div key={clothes.id}>
                                {'вид одежды: ' + clothes.type + ', '}
                                {'размер: ' + clothes.size + ', '}
                                {'цена: ' + clothes.cost + ', '}
                                {'цвет: ' + clothes.color + '. '}
                            </div>
                                )}
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

export default App;
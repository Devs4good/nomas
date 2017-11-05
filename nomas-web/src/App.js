import React, { Component } from 'react';
import './App.css';
import PhotoGallery from './PhotoGallery';


class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src='Logo.png' className="App-logo" alt="#NOMAS" />
          <img src='logoletras.png' className="App-logo" alt="#NOMAS" />
          <h1>Denunciá situaciones sospechosas que puedan desencadenar en secuestros</h1>
        </header>
        <PhotoGallery/>
      </div>
    );
  }
}

export default App;

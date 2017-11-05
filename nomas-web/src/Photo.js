import React, { Component } from 'react';

export default class Photo extends Component {

    render() {
        console.log(this.props.photo);
        return (
            <div className={"photo-container-" + this.props.id}>
                <span>{this.props.photo.date}</span>
                <img src={this.props.photo.src} alt={this.props.photo.situacion}/>
            </div>
        );
    }
}
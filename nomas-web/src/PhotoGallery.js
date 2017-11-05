import React, { Component } from 'react';
import Gallery from 'react-grid-gallery';
import './photogallery.css';
import fetchData from './utils/fetch-data';
import buildPhotoFromData from './utils/build-photo-from-data';


class PhotoGallery extends Component {

    constructor(props) {
        super(props);
        this.state = {
            items: []
        }
    }
    componentDidMount = () => {
        fetchData().then(items => {
            const photos = items.map(item => buildPhotoFromData(item));
            this.setState({
                items: photos
            })
        })
    }
    render() {
        // <div className="gallery-container">
        //     {this.state.items.map( (photo, index) => (<Photo id={`${'grid-' + index}`} key={index} photo={photo}/>))}
        // </div>
        return (
            <Gallery images={this.state.items} />
        );
    }
}

export default PhotoGallery;

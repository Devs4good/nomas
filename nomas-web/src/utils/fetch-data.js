import firebase from './firebase-config';

const fetchData = () => {
    let data = []
    return new Promise(resolve => {
        const items = firebase.database().ref('denuncias');
        items.on('value', (values) => {
            values.forEach(child => {
                data.push(child.val())
            })
            resolve(data);
        });

    })
}

export default fetchData;
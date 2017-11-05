import firebase from 'firebase';
const config = {
    "firebase": {
        "apiKey": "",
        "authDomain": "nomas-2b0d9.firebaseio.com/",
        "databaseURL": "https://nomas-2b0d9.firebaseio.com/",
        "storageBucket": "gs://nomas-2b0d9.appspot.com/",
        "messagingSenderId": ""
    }
}
firebase.initializeApp(config.firebase);

console.log("FIREBAE", firebase);
export default firebase;
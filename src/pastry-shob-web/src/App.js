import './App.css';
import {useEffect, useState} from "react";

function App() {

    const [jwt, setJwt] = useState()

    useEffect(() => {
        const requestBody = {
            "username": "Ivzilol",
            "password": "asdasd",
        }
        fetch("api/auth/login", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(requestBody)
        }).then(response => Promise.all([response.json(), response.headers]))
            .then(([body, headers]) => {
                setJwt(headers.get("authorization"));
            });
    }, [jwt]);


    return (
        <div className="App">
            <h1>Pastry Shop</h1>
            <div>JWT Values is {jwt}</div>
        </div>
    );
}

export default App;

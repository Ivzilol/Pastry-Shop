import './App.css';
import React, {useEffect} from "react";
import {Route, Routes} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import {useLocalState} from "./util/useLocalStorage";


function App() {

    const [jwt, setJwt] = useLocalState("", "jwt")

    useEffect(() => {
        if (!jwt) {
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
                    console.log(jwt)
                });
        }
    }, []);

    return (
        // <Routes>
        //     <Route path="/dashboard" element={<Dashboard/>}/>
        //     <Route path="/" element={() => {
        //         return <div>Pastry-Shop</div>
        //     }
        //     }>
        //
        //     </Route>
        // </Routes>

        <div>
            <h1>Pastry Shop</h1>
            <div>JWT Values is {jwt}</div>
        </div>
    );
}

export default App;

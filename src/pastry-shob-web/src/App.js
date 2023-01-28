import './App.css';
import React, {useEffect} from "react";
import {Route, Routes} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import {useLocalState} from "./util/useLocalStorage";
import Homepage from "./components/homepage/Homepage";


function App() {

    const [jwt, setJwt] = useLocalState("", "jwt")

    // useEffect(() => {
    //     if (!jwt) {
    //         const requestBody = {
    //             "username": "Ivzilol",
    //             "password": "asdasd",
    //         }
    //         fetch("api/auth/login", {
    //             headers: {
    //                 "Content-Type": "application/json"
    //             },
    //             method: "post",
    //             body: JSON.stringify(requestBody)
    //         }).then(response => Promise.all([response.json(), response.headers]))
    //             .then(([body, headers]) => {
    //                 setJwt(headers.get("authorization"));
    //             });
    //     }
    // }, []);

    return (
        <Routes>
            <Route path="/dashboard" element={<Dashboard/>}/>
            <Route path="/" element={<Homepage/>}>
            </Route>
        </Routes>
    );
}

export default App;

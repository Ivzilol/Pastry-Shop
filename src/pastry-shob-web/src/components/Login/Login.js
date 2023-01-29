import React, {useState} from 'react';
import {useLocalState} from "../../util/useLocalStorage";

const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [jwt, setJwt] = useLocalState("", "jwt")

    function sendLoginRequest() {
        const requestBody = {
            "username": username,
            "password": password,
        }

        fetch("api/auth/login", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(requestBody)
        }).then(response => {
            if (response.status === 200) {
                return Promise.all([response.json(), response.headers])
            } else {
                return Promise.reject("Invalid Login")
            }
        })
            .then(([body, headers]) => {
                setJwt(headers.get("authorization"));
                window.location.href = "dashboard"
            }).catch((message) => {
            alert(message)
        });
    }

    return (
        <>
            <div>
                <label htmlFor="username">Username</label>
                <input
                    type="email"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </div>
            <div>
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <div>
                <button id="submit" type="button" onClick={() => sendLoginRequest()}>
                    Login
                </button>
            </div>
        </>
    );
};

export default Login;
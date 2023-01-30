import React, {useState} from 'react';
import {useLocalState} from "../../util/useLocalStorage";
import {Button, Col, Container, Row, Form} from "react-bootstrap";

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
            <Container className="mt-sm-5">
                <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label htmlFor="username" className="fs-3">Username</Form.Label>
                        <Form.Control
                            type="email"
                            id="username"
                            placeholder="John Doe"
                            size="lg"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />

                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">

                                <Form.Label htmlFor="password" className="fs-3">Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    id="password"
                                    placeholder="password"
                                    size="lg"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                </Form.Group>
                                <Button
                                    className="fs-5, mt-sm-3"
                                    id="submit"
                                    type="button"
                                    onClick={() => sendLoginRequest()}
                                >
                                    Login
                                </Button>
            </Container>
        </>
);
};

export default Login;
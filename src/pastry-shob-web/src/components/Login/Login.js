import React, {useEffect, useState} from 'react';
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import NavBar from "../NavBar/NavBar";

const Login = () => {
    const user = useUser();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        if (user.jwt) navigate("/")
    }, [navigate, user]);


    // const  baseUrl = require('dotenv').config(process.env.REACT_APP_BASE_URL);
    function sendLoginRequest() {
        const requestBody = {
            "username": username,
            "password": password,
        };
        fetch(`${baseUrl}api/auth/login`, {
            method: "post",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody)
        })
            .then((response) => {
                if (response.status === 200)
                    return Promise.all([response.json(), response.headers])
                else return Promise.reject("Invalid login attempt")
            })
            .then(([, headers]) => {
                user.setJwt(headers.get("Authorization"))
            }).catch((message) => {
            alert(message)
        });
    }


    return (
        <main className="login">
            <NavBar/>
            <Container
                className="mt-sm-5"
                id="login-users"
            >
                <Row className="justify-content-center align-items-center">
                    <Col md="8"
                         lg="6"
                    >
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label className="fs-3">Username</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="John Doe"
                                size="lg"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center align-items-center">
                    <Col md="8"
                         lg="6"
                    >
                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label className="fs-4">Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Password"
                                size="lg"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center align-items-center">
                    <Col md="8" lg="6"
                         className="mt-4 d-flex flex-column gap-3 flex-md-row justify-content-between border-0">
                        <Button
                            id="submit"
                            type="button"
                            size="lg"
                            onClick={() => sendLoginRequest()}
                        >
                            Login
                        </Button>
                        <Button
                            variant="secondary"
                            id="submit"
                            type="button"
                            size="lg"
                            onClick={() => {
                                navigate("/register")
                            }}
                        >
                            Register
                        </Button>
                    </Col>
                </Row>
            </Container>
            <a className="forgotten-password-link"
            onClick={() => navigate("/forgotten-password")}
            >Забравена парола</a>
        </main>
    );
}

export default Login;
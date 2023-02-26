import React, {useEffect, useState} from 'react';
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";

const Login = () => {
    const user = useUser();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    let navigate = useNavigate();

    useEffect(() => {
        if (user.jwt) navigate("/shops")
    }, [user])

    function sendLoginRequest() {
        const requestBody = {
            "username": username,
            "password": password,
        };

        fetch("api/auth/login", {
            headers: {
                "Content-Type": "application/json"
            },
            method: "post",
            body: JSON.stringify(requestBody)
        })
            .then((response) => {
                if (response.status === 200)
                return Promise.all([response.json(), response.headers])
                else return Promise.reject("Invalid login attempt")
            })
            .then(([body, headers]) => {
                user.setJwt(headers.get("authorization"))
            }).catch((message) => {
                alert(message)
        });
    }


    return (
        <>
            {/*<NavBar/>*/}
            <Container className="mt-sm-5">
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
                                placeholder="password"
                                size="lg"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>
                    </Col>
                </Row>
                {/*{errorMessage ? (*/}
                {/*    <Row className="justify-content-center mb-4">*/}
                {/*        <Col md="8" lg="6">*/}
                {/*            <div className="" style={{ color: "red", fontWeight: "bold" }}>*/}
                {/*                {errorMessage}*/}
                {/*            </div>*/}
                {/*        </Col>*/}
                {/*    </Row>*/}
                {/*) : (*/}
                <></>
                {/*)}*/}
                <Row className="justify-content-center align-items-center">
                    <Col md="8" lg="6" className="mt-4 d-flex flex-column gap-3 flex-md-row justify-content-between">
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
                                navigate("/")
                            }}
                        >
                            Exit
                        </Button>
                    </Col>
                </Row>
            </Container>
        </>
    );
}

export default Login;
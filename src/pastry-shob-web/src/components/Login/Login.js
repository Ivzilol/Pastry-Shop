import React, {useState} from 'react';
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";

const Login = () => {
    const user =useUser()
    const navigate = useNavigate()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] =useState(null);


    function sendLoginRequest() {
        setErrorMessage("");
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
        }).then((response) =>{
            if (response.status === 200) return response.text()
            else if (response.status === 401 || response.status === 403) {
                setErrorMessage("Invalid username ot password")
            } else {
                setErrorMessage("Something wrong")
            }
        }).then((data) => {
            if (data){
                user.setJwt(data)
                navigate("/")
            }
        })
    }

    return (
        <>
            <Container className="mt-sm-5">
                <Row className="justify-content-center align-items-center" >
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
                {errorMessage ? (
                    <Row className="justify-content-center mb-4">
                        <Col md="8" lg="6">
                            <div className="" style={{ color: "red", fontWeight: "bold" }}>
                                {errorMessage}
                            </div>
                        </Col>
                    </Row>
                ) : (
                    <></>
                )}
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
                                window.location.href = "/"
                            }}
                        >
                            Exit
                        </Button>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default Login;
import React, {useEffect, useState} from 'react';
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const Login = () => {
    const user = useUser();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [dialogVisible, setDialogVisible] = useState(false);
    let navigate = useNavigate();
    const {t} = useTranslation();

    useEffect(() => {
        if (user.jwt) navigate("/")
    }, [navigate, user]);


    function sendLoginRequest() {
        const requestBody = {
            "username": username,
            "password": password,
        };
        fetch(`${baseURL}api/auth/login`, {
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
            }).catch(() => {
            setDialogVisible(true)
        });
    }

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    function closeDialog() {
        setDialogVisible(false)
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
                            <Form.Label className="fs-3">{t('login.fs-3')}</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="John Doe"
                                size="lg"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                onFocus={closeDialog}
                            />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center align-items-center">
                    <Col md="8"
                         lg="6"
                    >
                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label className="fs-4">{t('login.fs-4')}</Form.Label>
                            <Form.Control
                                type={showPassword ? "text" : "password"}
                                placeholder="Password"
                                size="lg"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                onFocus={closeDialog}
                            />
                            {dialogVisible &&
                                <h5 className="login-invalid">{t('login.error')}
                                </h5>
                            }
                            <a className="forgotten-password-send-show-password-login"
                               onClick={togglePasswordVisibility}>
                                {showPassword ? t('login.hide-password') : t('login.show-password')}
                            </a>
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="justify-content-center align-items-center">
                    <Col md="8" lg="6"
                         className="mt-1 d-flex flex-column gap-3 flex-md-row justify-content-between border-0">
                        <Button
                            id="submit"
                            type="button"
                            size="lg"
                            onClick={() => sendLoginRequest()}
                        >
                            {t('login.login-button')}
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
                            {t('login.register-button')}
                        </Button>
                    </Col>
                </Row>
            </Container>
            <a className="forgotten-password-link"
               onClick={() => navigate("/forgotten-password")}
            >{t('login.forgotten-password')}</a>
        </main>
    );
}

export default Login;
import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";
import Loading from "../Loading/Loading";
import ajax from "../../Services/FetchService";


const Register = () => {

    const user = useUser();
    const navigate = useNavigate()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("")
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const {t} = useTranslation();
    const [showPassword, setShowPassword] = useState(false);
    const [successfulRegistrationDialog] = useState(false);
    const [successfulRegistration] = useState("");
    const [unsuccessfulRegistrationDialog] = useState(false)
    const [unsuccessfulRegistration] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [regError, setRegError] = useState(false)

    useEffect(() => {
        if (user.jwt) navigate("/");
    }, [navigate, user]);

    function createAndLoginUser() {
        setIsLoading(true)
        const requestBody = {
            username: username,
            password: password,
            confirmPassword: confirmPassword,
            firstName: firstName,
            lastName: lastName,
            email: email,
            address: address,
            phoneNumber: phoneNumber
        }

        ajax(`${baseURL}api/users/register`, "POST", null, requestBody)
            .then((response) => {
                setIsLoading(false)
                if (!isNaN(response.id)) {
                    alert("You have registered successfully, please check your email for activation");
                    navigate("/login");
                } else {
                    setRegisterError(response)
                    setRegError(true);
                }
            });
    }

    const [registerError, setRegisterError] = useState({
        usernameError: '',
        passwordError: '',
        emailError: '',
        firstNameError: '',
        lastNameError: '',
        addressError: '',
        phoneNumberError: '',
        confirmPasswordError: ''
    });

    const [error, setError] = useState({
        username: '',
        password: '',
        confirmPassword: "",
        email: '',
        firstName: '',
        lastName: '',
        address: '',
        phoneNumber: ''
    })

    const validateUsername = (e) => {
        const username = e.target.value;
        let errorMessage = ''
        if (username.length < 3) {
            errorMessage = 'Username must be longer form 3 characters';
        } else if (username.length > 20) {
            errorMessage = 'Username must be in short from 20 characters';
        }
        setError(state => ({
            ...state,
            username: errorMessage,
        }));
    }

    const validatePassword = (e) => {
        const password = e.target.value;
        let errorMessage = ''
        if (password.length < 3) {
            errorMessage = 'Password must be longer form 3 characters';
        } else if (password.length > 20) {
            errorMessage = 'Password must be in short from 20 characters';
        }
        setError(state => ({
            ...state,
            password: errorMessage,
        }));
    }

    const validateConfirmPassword = (e) => {
        const confirmPassword = e.target.value;
        let errorMessage = ''
        if (confirmPassword.length < 3) {
            errorMessage = 'Password must be longer form 3 characters';
        } else if (confirmPassword.length > 20) {
            errorMessage = 'Password must be in short from 20 characters';
        }
        setError(state => ({
            ...state,
            confirmPassword: errorMessage,
        }));
    }

    const validateFirstName = (e) => {
        const firstName = e.target.value;
        let errorMessage = ''
        if (firstName.length < 1) {
            errorMessage = 'First Name it can not be empty';
        }
        setError(state => ({
            ...state,
            firstName: errorMessage,
        }));
    }

    const validateLastName = (e) => {
        const lastName = e.target.value;
        let errorMessage = ''
        if (lastName.length < 1) {
            errorMessage = 'Last Name it can not be empty';
        }
        setError(state => ({
            ...state,
            lastName: errorMessage,
        }));
    }

    const validateEmail = (e) => {
        const email = e.target.value;
        let errorMessage = ''
        if (email.length < 1) {
            errorMessage = 'Email it can not be empty';
        }
        setError(state => ({
            ...state,
            email: errorMessage,
        }));
    }

    const validateAddress = (e) => {
        const address = e.target.value;
        let errorMessage = ''
        if (address.length < 1) {
            errorMessage = 'Address it can not be empty';
        }
        setError(state => ({
            ...state,
            address: errorMessage,
        }));
    }

    const validatePhoneNumber = (e) => {
        const phoneNumber = e.target.value;
        let errorMessage = ''
        if (phoneNumber.length < 1) {
            errorMessage = 'Phone number it can not be empty';
        }
        setError(state => ({
            ...state,
            phoneNumber: errorMessage,
        }));
    }

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };


    return (
        <>
            <NavBar/>
            {isLoading ?
                <Loading/>
                :
                <section className="register">
                    <article className="register-form">
                        <h1>{t('register.h1')}</h1>
                        <label
                            htmlFor="username"
                        >{t('register.username')}
                        </label>
                        <input
                            type="text"
                            id="username"
                            autoComplete="off"
                            name="username"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            onBlur={validateUsername}
                        />
                        {error.username &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {error.username}</span>
                        }

                        {regError && registerError.usernameError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.usernameError}</span>
                        }

                        <label form="password">{t('register.password')}</label>
                        <input
                            type={showPassword ? "text" : "password"}
                            id="password"
                            name="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            onBlur={validatePassword}
                        />
                        <a className="forgotten-password-send-show-password-register"
                           onClick={togglePasswordVisibility}>
                            {showPassword ? t('login.hide-password') : t('login.show-password')}
                        </a>
                        {error.password &&
                            <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.password}</span>
                        }

                        {regError && registerError.passwordError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.passwordError}</span>
                        }
                        <label form="confirmPassword">{t('register.confirm-password')}</label>
                        <input
                            type={showPassword ? "text" : "password"}
                            id="confirmPassword"
                            name="confirmPassword"
                            placeholder="ConfirmPassword"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            onBlur={validateConfirmPassword}
                        />
                        <a className="forgotten-password-send-show-password-register"
                           onClick={togglePasswordVisibility}>
                            {showPassword ? t('login.hide-password') : t('login.show-password')}
                        </a>
                        {error.confirmPassword &&
                            <span id="validate-username"><FontAwesomeIcon
                                icon={faInfoCircle}/> {error.confirmPassword}</span>
                        }
                        {regError && registerError.confirmPasswordError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.confirmPasswordError}</span>
                        }
                        <label form="firstName">{t('register.name')}</label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            placeholder="First Name"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            onBlur={validateFirstName}
                        />
                        {error.firstName &&
                            <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.firstName}</span>
                        }

                        {regError && registerError.firstNameError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.firstNameError}</span>
                        }
                        <label form="lastName">{t('register.last-name')}</label>
                        <input
                            type="text"
                            id="lastName"
                            name="lastName"
                            placeholder="Last Name"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            onBlur={validateLastName}
                        />
                        {error.lastName &&
                            <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.lastName}</span>
                        }
                        {regError && registerError.lastNameError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.lastNameError}</span>
                        }
                        <label form="email">{t('register.email')}</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            onBlur={validateEmail}
                        />
                        {error.email &&
                            <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.email}</span>
                        }
                        {regError && registerError.emailError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.emailError}</span>
                        }
                        <label form="address">{t('register.address')}</label>
                        <input
                            type="text"
                            id="address"
                            name="address"
                            placeholder="Address"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            onBlur={validateAddress}
                        />
                        {error.address &&
                            <span id="validate-username"><FontAwesomeIcon icon={faInfoCircle}/> {error.address}</span>
                        }
                        {regError && registerError.addressError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.addressError}</span>
                        }
                        <label form="phoneNumber">{t('register.phone-number')}</label>
                        <input
                            type="text"
                            id="phoneNumber"
                            name="phoneNumber"
                            placeholder="PhoneNumber"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            onBlur={validatePhoneNumber}
                        />
                        {error.phoneNumber &&
                            <span id="validate-username"><FontAwesomeIcon
                                icon={faInfoCircle}/> {error.phoneNumber}</span>
                        }
                        {regError && registerError.phoneNumberError !== null &&
                            <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {registerError.phoneNumberError}</span>
                        }
                        {successfulRegistrationDialog &&
                            <h5 className="registration-message-successful">{successfulRegistration}
                            </h5>
                        }
                        {unsuccessfulRegistrationDialog &&
                            <h5 className="registration-message-unsuccessful">{unsuccessfulRegistration}
                            </h5>
                        }
                        <button
                            id="submit"
                            type="button"
                            onClick={() => createAndLoginUser()
                            }
                        >
                            {t('register.button')}
                        </button>
                    </article>
                </section>
            }
        </>
    )
}

export default Register;